package io.github.sinri.keel.logger.impl;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.StdoutStringWriter;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.event.Logger;
import io.github.sinri.keel.logger.api.event.LoggerFactory;
import io.github.sinri.keel.logger.api.event.RecorderFactory;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.impl.event.PlainEventRecorder;
import io.github.sinri.keel.logger.impl.event.StringEventRender;
import io.github.sinri.keel.logger.impl.issue.plain.PlainIssueRecorder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PlainFactory implements RecorderFactory<String>, LoggerFactory {
    private static final PlainFactory instance = new PlainFactory();

    private PlainFactory() {
    }

    public static PlainFactory getInstance() {
        return instance;
    }

    //    @Override
    //    public LogRecorder<String> createLogRecorder(@Nonnull String topic) {
    //        return LogRecorder.embedded(topic);
    //    }

    @Override
    public EventRecorder<String> createEventLogRecorder(@Nonnull String topic) {
        return new PlainEventRecorder(topic, LogLevel.INFO);
    }

    @Override
    public <L extends IssueRecord<L>> IssueRecorder<L, String> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier) {
        return new PlainIssueRecorder<>(topic, issueRecordSupplier, LogLevel.INFO);
    }

    @Nonnull
    @Override
    public Logger createLogger(@Nonnull String topic) {
        return new Logger(topic, LogLevel.INFO, Adapter.build(StringEventRender.getInstance(), StdoutStringWriter.getInstance()));
    }
}
