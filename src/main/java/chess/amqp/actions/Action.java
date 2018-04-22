package chess.amqp.actions;

public interface Action<T> {
    T proceed(T object);
}
