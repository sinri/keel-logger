package io.github.sinri.keel.logger;

@Deprecated
public interface SelfInterface<T extends SelfInterface<T>> {
    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }
}
