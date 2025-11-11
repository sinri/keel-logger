package io.github.sinri.keel.logger.impl.record;

import io.github.sinri.keel.logger.api.record.LogRecord;
import io.github.sinri.keel.logger.api.record.LogRecorder;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;

public abstract class QueuedLogRecorder implements LogRecorder, Closeable {
    @Nonnull
    private final String topic;

    public QueuedLogRecorder(@Nonnull String topic) {
        this.topic = topic;
    }

    @Nonnull
    abstract protected QueuedLogWriter<LogRecord> writer();

    @Nonnull
    @Override
    public final String topic() {
        return topic;
    }

    @Override
    public final void recordLog(@Nonnull LogRecord record) {
        this.writer().write(topic, record);
    }

    @Override
    public void close() throws IOException {
        this.writer().close();
    }

}
