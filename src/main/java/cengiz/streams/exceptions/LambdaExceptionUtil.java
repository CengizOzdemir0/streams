package cengiz.streams.exceptions;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class LambdaExceptionUtil {

    @FunctionalInterface
    public interface Consumer_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface BiConsumer_WithExceptions<T, U, E extends Exception> {
        void accept(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface Supplier_WithExceptions<T, E extends Exception> {
        T get() throws E;
    }

    @FunctionalInterface
    public interface Runnable_WithExceptions<E extends Exception> {
        void run() throws E;
    }

    /** .forEach(rethrowConsumer(name -> System.out.println(Class.forName(name)))); or .forEach(rethrowConsumer(ClassNameUtil::println)); */
    public static <T, E extends Exception> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T, E> consumer) {
        return t -> {
            try { consumer.accept(t); }
            catch (Exception exception) { throwAsUnchecked(exception); }
        };
    }

    public static <T, U, E extends Exception> BiConsumer<T, U> rethrowBiConsumer(BiConsumer_WithExceptions<T, U, E> biConsumer) {
        return (t, u) -> {
            try { biConsumer.accept(t, u); }
            catch (Exception exception) { throwAsUnchecked(exception); }
        };
    }


    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(Function_WithExceptions<T, R, E> function) {
        return t -> {
            try { return function.apply(t); }
            catch (Exception exception) { throwAsUnchecked(exception); return null; }
        };
    }


    public static <T, E extends Exception> Supplier<T> rethrowSupplier(Supplier_WithExceptions<T, E> function) {
        return () -> {
            try { return function.get(); }
            catch (Exception exception) { throwAsUnchecked(exception); return null; }
        };
    }


    public static void uncheck(Runnable_WithExceptions t)
    {
        try { t.run(); }
        catch (Exception exception) { throwAsUnchecked(exception); }
    }


    public static <R, E extends Exception> R uncheck(Supplier_WithExceptions<R, E> supplier)
    {
        try { return supplier.get(); }
        catch (Exception exception) { throwAsUnchecked(exception); return null; }
    }


    public static <T, R, E extends Exception> R uncheck(Function_WithExceptions<T, R, E> function, T t) {
        try { return function.apply(t); }
        catch (Exception exception) { throwAsUnchecked(exception); return null; }
    }

    @SuppressWarnings ("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E { throw (E)exception; }

}
