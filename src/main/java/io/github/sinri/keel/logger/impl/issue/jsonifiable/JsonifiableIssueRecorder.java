package io.github.sinri.keel.logger.impl.issue.jsonifiable;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.vertx.core.json.JsonObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class JsonifiableIssueRecorder<T extends JsonifiableIssueRecord<T>> implements IssueRecorder<T, JsonObject> {

    @Nonnull
    private final String topic;
    @Nonnull
    private final Supplier<T> issueRecordSupplier;
    @Nonnull
    private final Adapter<T, JsonObject> adapter;
    //private final JsonifiableIssueRecordRender<T> render;
    @Nonnull
    private LogLevel visibleLevel;

    public JsonifiableIssueRecorder(@Nonnull String topic, @Nonnull Supplier<T> issueRecordSupplier, @Nonnull LogLevel visibleLevel) {
        this.topic = topic;
        this.issueRecordSupplier = issueRecordSupplier;
        this.visibleLevel = visibleLevel;
        // this.render = new JsonifiableIssueRecordRender<>();
        this.adapter = initializeAdapter();
    }

    @Nonnull
    abstract protected Adapter<T, JsonObject> initializeAdapter();

    @Nonnull
    @Override
    public final Supplier<T> issueRecordSupplier() {
        return issueRecordSupplier;
    }

    @Nonnull
    @Override
    public Adapter<T, JsonObject> adapter() {
        return adapter;
    }

    @Nonnull
    @Override
    public final LogLevel visibleLevel() {
        return visibleLevel;
    }

    @Nonnull
    @Override
    public final IssueRecorder<T, JsonObject> visibleLevel(@Nonnull LogLevel level) {
        this.visibleLevel = level;
        return this;
    }

    @Nonnull
    @Override
    public final String topic() {
        return topic;
    }
}
