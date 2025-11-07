package io.github.sinri.keel.logger.issue.recorder.render;

import io.github.sinri.keel.logger.issue.record.KeelIssueRecord;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Set;


/**
 * @since 3.1.10
 */
public class KeelIssueRecordStringRender implements KeelIssueRecordRender<String> {
    private static final KeelIssueRecordStringRender instance = new KeelIssueRecordStringRender();
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    protected KeelIssueRecordStringRender() {

    }

    public static KeelIssueRecordStringRender getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public String renderIssueRecord(@Nonnull String topic, @Nonnull KeelIssueRecord<?> issueRecord) {
        StringBuilder s = new StringBuilder("㏒ ");
        s.append(formatter.format(new Date(issueRecord.timestamp())));
        s.append(" [").append(issueRecord.level().name()).append("]");
        s.append(" ").append(topic)
         .append(" (")
         .append(String.join(",", issueRecord.classification()))
         .append(")");
        s.append(" on ").append(issueRecord.getThreadInfo());
        if (!issueRecord.attributes().isEmpty()) {
            issueRecord.attributes().forEach(attribute -> s.append("\n ▪ ").append(attribute.getKey()).append(": ")
                                                           .append(attribute.getValue()));
        }
        Throwable exception = issueRecord.exception();
        if (exception != null) {
            s.append("\n ⊹ Exception Thrown:\n").append(renderThrowable(exception));
        }
        return s.toString();
    }

    @Nonnull
    @Override
    public String renderThrowable(@Nonnull Throwable throwable) {
        return renderThrowableChain(throwable, ignorableStackPackageSet());
    }

    @Nonnull
    private String renderThrowableChain(@Nullable Throwable throwable, @Nonnull Set<String> ignorableStackPackageSet) {
        if (throwable == null) return "";
        Throwable cause = throwable.getCause();
        StringBuilder sb = new StringBuilder();
        sb
                .append("\t")
                .append(throwable.getClass().getName())
                .append(": ")
                .append(throwable.getMessage())
                .append(System.lineSeparator())
                .append(buildStackChainText(throwable.getStackTrace(), ignorableStackPackageSet));

        while (cause != null) {
            sb
                    .append("\t↑ ")
                    .append(cause.getClass().getName())
                    .append(": ")
                    .append(cause.getMessage())
                    .append(System.lineSeparator())
                    .append(buildStackChainText(cause.getStackTrace(), ignorableStackPackageSet))
            ;

            cause = cause.getCause();
        }

        return sb.toString();
    }

    @Nonnull
    private String buildStackChainText(@Nullable StackTraceElement[] stackTrace, @Nonnull Set<String> ignorableStackPackageSet) {
        StringBuilder sb = new StringBuilder();
        if (stackTrace != null) {
            String ignoringClassPackage = null;
            int ignoringCount = 0;
            for (StackTraceElement stackTranceItem : stackTrace) {
                String className = stackTranceItem.getClassName();
                String matchedClassPackage = null;
                for (var cp : ignorableStackPackageSet) {
                    if (className.startsWith(cp)) {
                        matchedClassPackage = cp;
                        break;
                    }
                }
                if (matchedClassPackage == null) {
                    if (ignoringCount > 0) {
                        sb.append("\t\t")
                          .append("[").append(ignoringCount).append("] ")
                          .append(ignoringClassPackage)
                          .append(System.lineSeparator());

                        ignoringClassPackage = null;
                        ignoringCount = 0;
                    }

                    sb.append("\t\t")
                      .append(stackTranceItem.getClassName())
                      .append(".")
                      .append(stackTranceItem.getMethodName())
                      .append(" (")
                      .append(stackTranceItem.getFileName())
                      .append(":")
                      .append(stackTranceItem.getLineNumber())
                      .append(")")
                      .append(System.lineSeparator());
                } else {
                    if (ignoringCount > 0) {
                        if (Objects.equals(ignoringClassPackage, matchedClassPackage)) {
                            ignoringCount += 1;
                        } else {
                            sb.append("\t\t")
                              .append("[").append(ignoringCount).append("] ")
                              .append(ignoringClassPackage)
                              .append(System.lineSeparator());

                            ignoringClassPackage = matchedClassPackage;
                            ignoringCount = 1;
                        }
                    } else {
                        ignoringClassPackage = matchedClassPackage;
                        ignoringCount = 1;
                    }
                }
            }
            if (ignoringCount > 0) {
                sb.append("\t\t")
                  .append("[").append(ignoringCount).append("] ")
                  .append(ignoringClassPackage)
                  .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

}
