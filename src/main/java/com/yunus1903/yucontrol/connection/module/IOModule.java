package com.yunus1903.yucontrol.connection.module;

import com.yunus1903.yucontrol.device.io.DeviceIO;

/**
 * @author Yunus1903
 * @since 23/11/2020
 */
public abstract class IOModule<T> extends Module<T, T>
{
    public final DeviceIO<T> io;

    public IOModule(DeviceIO<T> io)
    {
        super(io.getDataType(), io.getDataType());
        this.io = io;
    }

    public Class<T> getType()
    {
        return io.getDataType();
    }
}
