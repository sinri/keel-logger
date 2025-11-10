package io.github.sinri.keel.logger.impl.issue.jsonifiable;

import io.github.sinri.keel.logger.api.issue.IssueRecordRender;
import io.vertx.core.json.JsonObject;

import javax.annotation.Nonnull;

public class JsonifiableIssueRecordRender<T extends JsonifiableIssueRecord<T>> implements IssueRecordRender<T, JsonObject> {
    //public class JsonifiableIssueRecordRender implements IssueRecordRender<JsonifiableIssueRecord<?>, JsonObject> {

    @Nonnull
    @Override
    public JsonObject render(@Nonnull String topic, @Nonnull T loggingEntity) {
        return loggingEntity.toJsonObject();
    }

    //    @Nonnull
    //    @Override
    //    public JsonObject render(@Nonnull String topic, @Nonnull JsonifiableIssueRecord<?> loggingEntity) {
    //        return loggingEntity.toJsonObject();
    //    }
}
