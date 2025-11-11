package io.github.sinri.keel.logger.impl.record;

import io.github.sinri.keel.logger.api.record.LogRecord;
import io.github.sinri.keel.logger.api.record.LogRecorder;
import io.github.sinri.keel.logger.impl.writer.QueuedLogWriter;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;

public abstract class QueuedLogRecorder implements LogRecorder, Closeable {
    @Nonnull
    private final String topic;
    @Nonnull
    private final QueuedLogWriter<LogRecord> writer;

    public QueuedLogRecorder(@Nonnull String topic, @Nonnull QueuedLogWriter<LogRecord> writer) {
        this.topic = topic;
        this.writer = writer;
    }

    @Nonnull
    @Override
    public final String topic() {
        return topic;
    }

    @Override
    public final void recordLog(@Nonnull LogRecord record) {
        this.writer.write(record);
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

}
