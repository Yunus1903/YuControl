package com.yunus1903.yucontrol.device;

import com.yunus1903.yucontrol.device.io.DeviceOutput;

import java.util.List;

/**
 * @author Yunus1903
 * @since 19/11/2020
 */
@FunctionalInterface
public interface OutputDevice
{
    List<DeviceOutput<?>> getOutputs();
}
