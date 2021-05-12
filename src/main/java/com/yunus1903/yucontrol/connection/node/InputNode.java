package com.yunus1903.yucontrol.connection.node;

import com.yunus1903.yucontrol.connection.module.modules.InputModule;
import com.yunus1903.yucontrol.device.Device;
import com.yunus1903.yucontrol.device.InputDevice;
import com.yunus1903.yucontrol.device.io.DeviceInput;

import java.util.List;

/**
 * @author Yunus1903
 * @since 08/03/2021
 */
public final class InputNode<T> extends Node<T, T>
{
    public final InputModule<T> module;
    private final String name;

    public InputNode(InputModule<T> input, String name)
    {
        super(input);
        this.module = input;
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    public InputNode(List<Device> devices, String input)
    {
        this(new InputModule<>((DeviceInput<T>) devices.stream()
                .filter(device -> device instanceof InputDevice)
                .flatMap(device -> ((InputDevice) device).getInputs().stream())
                .filter(devInput -> devInput.getId().equals(input)).findFirst().get()), input);
    }

    @Override
    public InputNode<T> addNode(Node<?, ?> node)
    {
        return (InputNode<T>) super.addNode(node);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
