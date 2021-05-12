package com.yunus1903.yucontrol.connection;

import com.yunus1903.yucontrol.connection.module.Module;
import com.yunus1903.yucontrol.connection.module.modules.FunctionModule;
import com.yunus1903.yucontrol.connection.module.modules.InputModule;
import com.yunus1903.yucontrol.connection.module.modules.OutputModule;
import com.yunus1903.yucontrol.connection.node.InputNode;
import com.yunus1903.yucontrol.connection.node.Node;
import com.yunus1903.yucontrol.connection.node.OutputNode;
import com.yunus1903.yucontrol.device.Device;
import com.yunus1903.yucontrol.device.devices.Debug;
import com.yunus1903.yucontrol.device.io.DeviceInput;
import com.yunus1903.yucontrol.device.io.DeviceOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import voicemeeter.Voicemeeter;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 22/11/2020
 */
public final class IOConnections
{
    private static final Logger LOGGER = LogManager.getLogger();

    private final List<Device> devices;

    private final CopyOnWriteArrayList<InputNode<?>> connections = new CopyOnWriteArrayList<>();

    public IOConnections(List<Device> devices)
    {
        this.devices = devices;


//        direct("nk2_slider0", "vmp_strip0_gain");
//        direct("nk2_slider1", "vmp_strip1_gain");
//        direct("nk2_slider2", "vmp_strip2_gain");
//        direct("nk2_slider3", "vmp_strip3_gain");
//        direct("nk2_slider4", "vmp_strip4_gain");
//        direct("nk2_slider5", "vmp_strip5_gain");
//        direct("nk2_slider6", "vmp_strip6_gain");
//        direct("nk2_slider7", "vmp_strip7_gain");
//
//        direct("nk2_knob0", "vmp_bus0_gain");
//        direct("nk2_knob1", "vmp_bus1_gain");
//        direct("nk2_knob2", "vmp_bus2_gain");
//        direct("nk2_knob3", "vmp_bus3_gain");
//        direct("nk2_knob4", "vmp_bus4_gain");
//        direct("nk2_knob5", "vmp_bus5_gain");
//        direct("nk2_knob6", "vmp_bus6_gain");
//        direct("nk2_knob7", "vmp_bus7_gain");
//
//        nk2ToVmButton("nk2_btn_s0", "vmp_bus0_mute");
//        nk2ToVmButton("nk2_btn_s1", "vmp_bus1_mute");
//        nk2ToVmButton("nk2_btn_s2", "vmp_bus2_mute");
//        nk2ToVmButton("nk2_btn_s3", "vmp_bus3_mute");
//        nk2ToVmButton("nk2_btn_s4", "vmp_bus4_mute");
//        nk2ToVmButton("nk2_btn_s5", "vmp_bus5_mute");
//        nk2ToVmButton("nk2_btn_s6", "vmp_bus6_mute");
//        nk2ToVmButton("nk2_btn_s7", "vmp_bus7_mute");
//
//        nk2ToVmButton("nk2_btn_m0", "vmp_strip0_mute");
//        nk2ToVmButton("nk2_btn_m1", "vmp_strip1_mute");
//        nk2ToVmButton("nk2_btn_m2", "vmp_strip2_mute");
//        nk2ToVmButton("nk2_btn_m3", "vmp_strip3_mute");
//        nk2ToVmButton("nk2_btn_m4", "vmp_strip4_mute");
//        nk2ToVmButton("nk2_btn_m5", "vmp_strip5_mute");
//        nk2ToVmButton("nk2_btn_m6", "vmp_strip6_mute");
//        nk2ToVmButton("nk2_btn_m7", "vmp_strip7_mute");
//
//        nk2ToVmButton("nk2_btn_r0", "vmp_bus0_eq");
//        nk2ToVmButton("nk2_btn_r1", "vmp_bus1_eq");
//        nk2ToVmButton("nk2_btn_r2", "vmp_bus2_eq");
//        nk2ToVmButton("nk2_btn_r3", "vmp_bus3_eq");
//        nk2ToVmButton("nk2_btn_r4", "vmp_bus4_eq");
//        nk2ToVmButton("nk2_btn_r5", "vmp_bus5_eq");
//        nk2ToVmButton("nk2_btn_r6", "vmp_bus6_eq");
//        nk2ToVmButton("nk2_btn_r7", "vmp_bus7_eq");

//        connections.add(new InputNode<Date>(devices, "yc_date")
//                .addNode(new Node<>(new FunctionModule<>(Date::toString))
//                        .addNode(new OutputNode<>(devices, "debugwindow_0"))));

//        debug("nk2_btn_play");
    }

    public boolean direct(String input, String output)
    {
        return connections.add(new InputNode<>(devices, input)
                .addNode(new OutputNode<>(devices, output)));
    }

    public void debug(String input)
    {
        devices.stream().filter(device -> device instanceof Debug).findFirst().ifPresent(device ->
                connections.add(new InputNode<>(devices, input)
                        .addNode(new Node<>(new FunctionModule<>(Object.class, String.class, String::valueOf))
                                .addNode(new OutputNode<>(devices, ((Debug) device).addOutput(input))))));
    }

    public void tick()
    {
        Voicemeeter.areParametersDirty();
        connections.forEach(this::runNodes);
    }

    public void add(InputNode<?> node)
    {
        if (node == null) return;
        connections.add(node);
    }

    public void clear()
    {
        connections.clear();
    }

    public List<InputNode<?>> getUnmodifiableList()
    {
        return Collections.unmodifiableList(connections);
    }

    private <T, R> void runNodes(Node<T, R> node)
    {
        if (node == null) throw new NullPointerException("Node cannot be null");

        node.nodes.forEach(tNode ->
        {
            sendValue(node.module, tNode.module);
            runNodes(tNode);
        });
    }

    @SuppressWarnings("unchecked")
    private <T, R, U, S> void sendValue(Module<T, R> moduleIn, Module<U, S> moduleOut)
    {
        Object value = null;

        if (moduleIn instanceof InputModule)
        {
            InputModule<T> inputModule = (InputModule<T>) moduleIn;
            if (inputModule.io instanceof DeviceInput)
            {
                value = ((DeviceInput<T>) inputModule.io).getValue(inputModule.getType());
            }
        }
        else if (moduleIn instanceof Supplier)
        {
            value = ((Supplier<T>) moduleIn).get();
        }

        if (value == null) return;

        if (moduleOut instanceof OutputModule)
        {
            OutputModule<U> outputModule = (OutputModule<U>) moduleOut;
            if (outputModule.io instanceof DeviceOutput)
            {
                ((DeviceOutput<U>) outputModule.io).sendValue((U) value);
            }
        }
        else if (moduleOut instanceof Consumer)
        {
            ((Consumer<U>) moduleOut).accept((U) value);
        }
    }
}
