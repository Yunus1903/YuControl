package com.yunus1903.yucontrol.device.devices;

import com.yunus1903.yucontrol.device.Device;
import com.yunus1903.yucontrol.device.OutputDevice;
import com.yunus1903.yucontrol.device.io.DeviceOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 * @since 22/11/2020
 */
public class Debug extends Device implements OutputDevice
{
    private static int outputId;

    private final List<DeviceOutput<?>> outputs = new ArrayList<>();

    public Debug()
    {
        super("Debug Console");
    }

    @Override
    public List<DeviceOutput<?>> getOutputs()
    {
        return outputs;
    }

    public String addOutput(String name)
    {
        String id = "debug_" + outputId++;
        outputs.add(new DeviceOutput<>(id, String.class, s -> LOGGER.debug("[Debug] " + name + ": " + s)));
        return id;
    }
}
