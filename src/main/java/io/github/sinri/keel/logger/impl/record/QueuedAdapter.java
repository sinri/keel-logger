package io.github.sinri.keel.logger.impl.record;

import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.LogWriter;
import io.github.sinri.keel.logger.api.adapter.Render;
import io.github.sinri.keel.logger.api.record.LogRecord;
import io.github.sinri.keel.logger.impl.writer.QueuedLogWriter;

import javax.annotation.Nonnull;
import java.io.IOException;

public record QueuedAdapter<R>(Render<LogRecord, R> render, LogWriter<R> writer) implements Adapter<LogRecord, R> {
    public QueuedAdapter(@Nonnull Render<LogRecord, R> render, @Nonnull QueuedLogWriter<R> writer) {
        this.render = render;
        this.writer = writer;
    }

    @Nonnull
    @Override
    public Render<LogRecord, R> render() {
        return render;
    }

    @Nonnull
    @Override
    public LogWriter<R> writer() {
        return writer;
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}