package io.github.sinri.keel.logger.impl.issue.plain;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.api.writer.StdoutStringWriter;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PlainIssueRecorder<T extends IssueRecord<T>> implements IssueRecorder<T, String> {
    @Nonnull
    private final String topic;
    @Nonnull
    private final Supplier<T> issueRecordSupplier;
    @Nonnull
    private final Adapter<T, String> adapter;
    @Nonnull
    private LogLevel visibleLevel;

    public PlainIssueRecorder(@Nonnull String topic, @Nonnull Supplier<T> issueRecordSupplier, @Nonnull LogLevel visibleLevel) {
        this.topic = topic;
        this.issueRecordSupplier = issueRecordSupplier;
        this.visibleLevel = visibleLevel;

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


    @Nonnull
    @Override
    public final LogLevel visibleLevel() {
        return visibleLevel;
    }

    @Nonnull
    @Override
    public final IssueRecorder<T, String> visibleLevel(@Nonnull LogLevel level) {
        this.visibleLevel = level;
        return this;
    }

    @Nonnull
    @Override
    public final String topic() {
        return topic;
    }
}
