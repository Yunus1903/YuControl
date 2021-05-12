package com.yunus1903.yucontrol.connection.module.modules;

import com.yunus1903.yucontrol.connection.module.IOModule;
import com.yunus1903.yucontrol.device.io.DeviceOutput;

/**
 * @author Yunus1903
 * @since 23/11/2020
 */
public class OutputModule<T> extends IOModule<T>
{
    public OutputModule(DeviceOutput<T> io)
    {
        super(io);
    }
}
