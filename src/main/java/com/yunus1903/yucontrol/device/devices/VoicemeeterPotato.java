package com.yunus1903.yucontrol.device.devices;

import com.yunus1903.yucontrol.device.Device;
import com.yunus1903.yucontrol.device.InputDevice;
import com.yunus1903.yucontrol.device.OutputDevice;
import com.yunus1903.yucontrol.device.io.DeviceInput;
import com.yunus1903.yucontrol.device.io.DeviceOutput;
import voicemeeter.Voicemeeter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 * @since 20/11/2020
 */
public class VoicemeeterPotato extends Device implements InputDevice, OutputDevice
{
    private final List<DeviceInput<?>> inputs = new ArrayList<>();
    private final List<DeviceOutput<?>> outputs = new ArrayList<>();

    public VoicemeeterPotato()
    {
        super("VoiceMeeter Potato");
    }

    @Override
    public boolean initialize()
    {
        Voicemeeter.init(true);
        Voicemeeter.login();

        // Inputs
        addStripInput(0);
        addStripInput(1);
        addStripInput(2);
        addStripInput(3);
        addStripInput(4);
        addStripInput(5);
        addStripInput(6);
        addStripInput(7);
        addBusInput(0);
        addBusInput(1);
        addBusInput(2);
        addBusInput(3);
        addBusInput(4);
        addBusInput(5);
        addBusInput(6);
        addBusInput(7);

        // Outputs
        addStripOutput(0);
        addStripOutput(1);
        addStripOutput(2);
        addStripOutput(3);
        addStripOutput(4);
        addStripOutput(5);
        addStripOutput(6);
        addStripOutput(7);
        addBusOutput(0);
        addBusOutput(1);
        addBusOutput(2);
        addBusOutput(3);
        addBusOutput(4);
        addBusOutput(5);
        addBusOutput(6);
        addBusOutput(7);

        return super.initialize();
    }

    @Override
    public void shutdown()
    {
        Voicemeeter.logout();
        super.shutdown();
    }

    @Override
    public List<DeviceInput<?>> getInputs()
    {
        return inputs;
    }

    @Override
    public List<DeviceOutput<?>> getOutputs()
    {
        return outputs;
    }

    private void addStripInput(int strip)
    {
        inputs.add(new DeviceInput<>("vmp_strip" + strip + "_mono", Boolean.class, () ->
                Voicemeeter.getParameterFloat("Strip[" + strip + "].Mono") == 1.0F));
        inputs.add(new DeviceInput<>("vmp_strip" + strip + "_mute", Boolean.class, () ->
                Voicemeeter.getParameterFloat("Strip[" + strip + "].Mute") == 1.0F));
        inputs.add(new DeviceInput<>("vmp_strip" + strip + "_solo", Boolean.class, () ->
                Voicemeeter.getParameterFloat("Strip[" + strip + "].Solo") == 1.0F));
        inputs.add(getGainInput("vmp_strip" + strip + "_gain", "Strip[" + strip + "].Gain"));
    }

    private void addBusInput(int bus)
    {
        inputs.add(new DeviceInput<>("vmp_bus" + bus + "_mono", Boolean.class, () ->
                Voicemeeter.getParameterFloat("Bus[" + bus + "].Mono") == 1.0F));
        inputs.add(new DeviceInput<>("vmp_bus" + bus + "_mute", Boolean.class, () ->
                Voicemeeter.getParameterFloat("Bus[" + bus + "].Mute") == 1.0F));
        inputs.add(new DeviceInput<>("vmp_bus" + bus + "_eq", Boolean.class, () ->
                Voicemeeter.getParameterFloat("Bus[" + bus + "].EQ.on") == 1.0F));
        inputs.add(getGainInput("vmp_bus" + bus + "_gain", "Bus[" + bus + "].Gain"));
    }

    private void addStripOutput(int strip)
    {
        outputs.add(new DeviceOutput<>("vmp_strip" + strip + "_mono", Boolean.class, bool ->
                Voicemeeter.setParameterFloat("Strip[" + strip + "].Mono", bool ? 1.0F : 0.0F)));
        outputs.add(new DeviceOutput<>("vmp_strip" + strip + "_mute", Boolean.class, bool ->
                Voicemeeter.setParameterFloat("Strip[" + strip + "].Mute", bool ? 1.0F : 0.0F)));
        outputs.add(new DeviceOutput<>("vmp_strip" + strip + "_solo", Boolean.class, bool ->
                Voicemeeter.setParameterFloat("Strip[" + strip + "].Solo", bool ? 1.0F : 0.0F)));
        outputs.add(getGainOutput("vmp_strip" + strip + "_gain", "Strip[" + strip + "].Gain"));
    }

    private void addBusOutput(int bus)
    {
        outputs.add(new DeviceOutput<>("vmp_bus" + bus + "_mono", Boolean.class, bool ->
                Voicemeeter.setParameterFloat("Bus[" + bus + "].Mono", bool ? 1.0F : 0.0F)));
        outputs.add(new DeviceOutput<>("vmp_bus" + bus + "_mute", Boolean.class, bool ->
                Voicemeeter.setParameterFloat("Bus[" + bus + "].Mute", bool ? 1.0F : 0.0F)));
        outputs.add(new DeviceOutput<>("vmp_bus" + bus + "_eq", Boolean.class, bool ->
                Voicemeeter.setParameterFloat("Bus[" + bus + "].EQ.on", bool ? 1.0F : 0.0F)));
        outputs.add(getGainOutput("vmp_bus" + bus + "_gain", "Bus[" + bus + "].Gain"));
    }

    private DeviceInput<Integer> getGainInput(String id, String parameter)
    {
        return new DeviceInput<>(id, Integer.class, () ->
                (int) ((Voicemeeter.getParameterFloat(parameter) - -60.0F) * 127 / (12.0F - -60.0F)));
    }

    private DeviceOutput<Integer> getGainOutput(String id, String parameter)
    {
        return new DeviceOutput<>(id, Integer.class, integer ->
                Voicemeeter.setParameterFloat(parameter, -60.0F + integer * (12.0F - -60.0F) / 127));
    }
}
