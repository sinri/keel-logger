package io.github.sinri.keel.logger.impl.issue.plain;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.StdoutStringWriter;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.impl.issue.AbstractIssueRecorder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PlainIssueRecorder<T extends IssueRecord<T>> extends AbstractIssueRecorder<T, String> {
    @Nonnull
    private final Supplier<T> issueRecordSupplier;
    @Nonnull
    private final Adapter<T, String> adapter;

    public PlainIssueRecorder(@Nonnull String topic, @Nonnull Supplier<T> issueRecordSupplier, @Nonnull LogLevel visibleLevel) {
        super(topic);
        this.issueRecordSupplier = issueRecordSupplier;

        PlainIssueRecordRender<T> render = new PlainIssueRecordRender<>();
        this.adapter = Adapter.build(render, StdoutStringWriter.getInstance());
    }

    @Nonnull
    @Override
    public final Supplier<T> issueRecordSupplier() {
        return issueRecordSupplier;
    }

    @Nonnull
    @Override
    public Adapter<T, String> adapter() {
        return adapter;
    }

}
