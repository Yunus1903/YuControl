package com.yunus1903.yucontrol.connection.module.modules;

import com.yunus1903.yucontrol.connection.module.IOModule;
import com.yunus1903.yucontrol.device.io.DeviceInput;

/**
 * @author Yunus1903
 * @since 23/11/2020
 */
public class InputModule<T> extends IOModule<T>
{
    public InputModule(DeviceInput<T> io)
    {
        super(io);
    }
}
