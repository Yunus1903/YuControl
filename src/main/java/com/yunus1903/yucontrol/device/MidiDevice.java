package com.yunus1903.yucontrol.device;

import com.yunus1903.yucontrol.device.io.DeviceInput;
import com.yunus1903.yucontrol.device.io.DeviceOutput;
import com.yunus1903.yucontrol.device.io.MidiDeviceInput;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 * @since 19/11/2020
 */
public abstract class MidiDevice extends Device implements InputDevice, OutputDevice
{
    protected final List<DeviceInput<?>> inputs = new ArrayList<>();
    protected final List<DeviceOutput<?>> outputs = new ArrayList<>();
    protected final javax.sound.midi.MidiDevice inputDevice;
    protected final javax.sound.midi.MidiDevice outputDevice;

    public MidiDevice(String name, javax.sound.midi.MidiDevice inputDevice, javax.sound.midi.MidiDevice outputDevice) throws MidiUnavailableException
    {
        super(name);
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;

        inputDevice.getTransmitter().setReceiver(new Receiver()
        {
            @Override
            public void send(MidiMessage message, long timeStamp)
            {
                if (isReady)
                {
                    inputs.forEach(input ->
                    {
                        if (input instanceof MidiDeviceInput)
                        {
                            if (message.getMessage()[1] == ((MidiDeviceInput) input).getCC())
                            {
                                ((MidiDeviceInput) input).setValue((int) message.getMessage()[2]);
                            }
                        }
                    });
                }
            }

            @Override
            public void close() { }
        });
    }

    @Override
    public boolean initialize()
    {
        try
        {
            inputDevice.open();
            outputDevice.open();
        }
        catch (MidiUnavailableException e)
        {
            LOGGER.error("Failed to initialize " + name, e);
            return false;
        }

        return super.initialize();
    }

    public void send(int cc, int value)
    {
        try
        {
            outputDevice.getReceiver().send(new ShortMessage(ShortMessage.CONTROL_CHANGE, 0, cc, value), -1);
        }
        catch (MidiUnavailableException | InvalidMidiDataException e)
        {
            LOGGER.error(e);
        }
    }

    public javax.sound.midi.MidiDevice getInputDevice()
    {
        return inputDevice;
    }

    public javax.sound.midi.MidiDevice getOutputDevice()
    {
        return outputDevice;
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
}
