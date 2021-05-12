package com.yunus1903.yucontrol.connection.node;

import com.yunus1903.yucontrol.connection.module.modules.OutputModule;
import com.yunus1903.yucontrol.device.Device;
import com.yunus1903.yucontrol.device.OutputDevice;
import com.yunus1903.yucontrol.device.io.DeviceOutput;

import java.util.List;

/**
 * @author Yunus1903
 * @since 08/03/2021
 */
public final class OutputNode<T> extends Node<T, T>
{
    private final String name;

    public OutputNode(OutputModule<T> output, String name)
    {
        super(output);
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    public OutputNode(List<Device> devices, String output)
    {
        this(new OutputModule<>((DeviceOutput<T>) devices.stream()
                .filter(device -> device instanceof OutputDevice)
                .flatMap(device -> ((OutputDevice) device).getOutputs().stream())
                .filter(devOutput -> devOutput.getId().equals(output)).findFirst().get()), output);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
