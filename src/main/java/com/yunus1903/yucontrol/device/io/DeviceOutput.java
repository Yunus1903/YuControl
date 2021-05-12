package com.yunus1903.yucontrol.device.io;

import java.util.function.Consumer;

/**
 * @author Yunus1903
 * @since 19/11/2020
 */
public class DeviceOutput<T> extends DeviceIO<T>
{
    private final Consumer<T> action;
    private final boolean retainsValue;

    private T lastValue;

    public DeviceOutput(String id, Class<T> type, Consumer<T> action)
    {
        this(id, type, action, false);
    }

    public DeviceOutput(String id, Class<T> type, Consumer<T> action, boolean retainsValue)
    {
        super(id, type);
        this.action = action;
        this.retainsValue = retainsValue;
    }

    public void sendValue(T value)
    {
        if (!value.equals(lastValue) || retainsValue)
        {
            lastValue = value;
            action.accept(value);
        }
    }
}
