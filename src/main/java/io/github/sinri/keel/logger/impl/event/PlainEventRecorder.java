package io.github.sinri.keel.logger.impl.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.event.EventRecord;
import io.github.sinri.keel.logger.api.event.EventRecorder;

import javax.annotation.Nonnull;

public class PlainEventRecorder implements EventRecorder<String> {
    private final String topic;
    private final Adapter<EventRecord, String> adapter;
    private LogLevel level;

    public PlainEventRecorder(String topic, LogLevel level) {
        this.topic = topic;
        this.level = level;
        this.adapter = PlainEventAdapter.getInstance();
    }

    @Nonnull
    @Override
    public LogLevel visibleLevel() {
        return level;
    }

    @Nonnull
    @Override
    public EventRecorder<String> visibleLevel(@Nonnull LogLevel level) {
        this.level = level;
        return this;
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Nonnull
    @Override
    public Adapter<EventRecord, String> adapter() {
        return adapter;
    }
}
