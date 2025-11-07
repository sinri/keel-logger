package io.github.sinri.keel.logger.issue.log4j2;

import io.github.sinri.keel.logger.KeelLogLevel;
import io.github.sinri.keel.logger.issue.record.KeelEventLog;
import io.github.sinri.keel.logger.issue.recorder.adapter.KeelIssueRecorderAdapter;
import io.vertx.core.Handler;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;
import java.util.function.Supplier;

public final class KeelLog4j2LoggerContextFactory implements LoggerContextFactory {

    private final KeelLog4j2LoggerContext loggerContext;

    public KeelLog4j2LoggerContextFactory(
            @Nonnull Supplier<KeelIssueRecorderAdapter> adapterSupplier,
            @Nonnull KeelLogLevel visibleBaseLevel,
            @Nullable Handler<KeelEventLog> issueRecordInitializer
    ) {
        this.loggerContext = new KeelLog4j2LoggerContext(adapterSupplier, visibleBaseLevel, issueRecordInitializer);
    }

    @Override
    public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext) {
        return this.loggerContext;
    }

    @Override
    public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, URI configLocation, String name) {
        return this.loggerContext;
    }

    @Override
    public void removeContext(LoggerContext context) {
        // do nothing
    }
}
