package com.yunus1903.yucontrol.connection.module.modules;

import com.yunus1903.yucontrol.connection.module.Module;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 23/11/2020
 */
public class FunctionModule<T, R> extends Module<T, R> implements Consumer<T>, Supplier<R>
{
    private final Function<T, R> conversion;

    private R value;

    public FunctionModule(Class<T> primaryType, Class<R> secondaryType, Function<T, R> conversion)
    {
        super(primaryType, secondaryType);
        this.conversion = conversion;
    }

    @Override
    public void accept(T t)
    {
        value = conversion.apply(t);
    }

    @Override
    public R get()
    {
        return value;
    }
}
