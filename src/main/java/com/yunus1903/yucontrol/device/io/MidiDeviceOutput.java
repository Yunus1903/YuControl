package com.yunus1903.yucontrol.device.io;

import com.yunus1903.yucontrol.device.MidiDevice;

/**
 * @author Yunus1903
 * @since 22/11/2020
 */
public class MidiDeviceOutput extends DeviceOutput<Integer>
{
    public MidiDeviceOutput(String id, int cc, MidiDevice device)
    {
        super(id, Integer.class, integer -> device.send(cc, integer));
    }
}
