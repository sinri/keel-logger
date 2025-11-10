package io.github.sinri.keel.logger.impl.writer;

import io.github.sinri.keel.logger.api.adapter.LogWriter;
import io.vertx.core.Future;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.sinri.keel.facade.KeelInstance.Keel;

public abstract class QueuedLogWriter<R> implements LogWriter<R>, Closeable {
    private final Queue<R> queue;
    private final AtomicBoolean closeFlag = new AtomicBoolean(false);

    public QueuedLogWriter() {
        this.queue = new SynchronousQueue<>();

        Keel.asyncCallRepeatedly(repeatedlyCallTask -> {
            List<R> buffer = new ArrayList<>();
            while (true) {
                R r = this.queue.poll();
                if (r == null) break;
                buffer.add(r);
            }
            if (buffer.isEmpty()) {
                if (closeFlag.get()) {
                    repeatedlyCallTask.stop();
                    return Future.succeededFuture();
                }
                return Keel.asyncSleep(100L);
            }
            return processLogRecords(buffer);
        });
    }

    @Nonnull
    abstract protected Future<Void> processLogRecords(@Nonnull List<R> batch);

    @Override
    public void write(@Nonnull R renderedEntity) {
        this.queue.add(renderedEntity);
    }

    @Override
    public void close() throws IOException {
        this.closeFlag.set(true);
    }
}
