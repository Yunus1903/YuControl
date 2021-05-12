package com.yunus1903.yucontrol.device.io;

import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 19/11/2020
 */
public class DeviceInput<T> extends DeviceIO<T>
{
    private T value;
    private Supplier<T> source = () -> value;

    public DeviceInput(String id, Class<T> type)
    {
        super(id, type);
    }

    public DeviceInput(String id, Class<T> type, Supplier<T> source)
    {
        this(id, type);
        this.source = source;
    }

    public <V> V getValue(Class<V> type)
    {
        T value = source.get();
        if (value == null) return null;
        return type.cast(value);
    }

    public void setValue(T value)
    {
        this.value = value;
    }
}
