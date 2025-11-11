package io.github.sinri.keel.logger.impl.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.event.EventRecord;

import javax.annotation.Nonnull;

public class PlainEventRecorder extends AbstractEventRecorder<String> {
    private final Adapter<EventRecord, String> adapter;

    public PlainEventRecorder(String topic, LogLevel level) {
        super(topic);
        this.visibleLevel(level);
        this.adapter = PlainEventAdapter.getInstance();
    }

    @Nonnull
    @Override
    public Adapter<EventRecord, String> adapter() {
        return adapter;
    }
}
