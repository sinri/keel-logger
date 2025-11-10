package io.github.sinri.keel.logger.impl.record;

import io.github.sinri.keel.logger.api.record.LogRecorder;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;

public abstract class QueuedLogRecorder<R> implements LogRecorder<R>, Closeable {
    @Nonnull
    private final String topic;
    @Nonnull
    private final QueuedAdapter<R> adapter;

    public QueuedLogRecorder(@Nonnull String topic) {
        this.topic = topic;
        this.adapter = initializeAdapter();
    }


    @Nonnull
    @Override
    public final String topic() {
        return topic;
    }

    @Nonnull
    abstract protected QueuedAdapter<R> initializeAdapter();

    @Nonnull
    @Override
    public final QueuedAdapter<R> adapter() {
        return adapter;
    }

    @Override
    public void close() throws IOException {
        this.adapter.close();
    }

}
