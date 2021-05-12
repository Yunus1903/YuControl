package com.yunus1903.yucontrol.device.io;

/**
 * @author Yunus1903
 * @since 22/11/2020
 */
public class MidiDeviceInput extends DeviceInput<Integer>
{
    private final int cc;

    public MidiDeviceInput(String id, int cc)
    {
        super(id, Integer.class);
        this.cc = cc;
    }

    public int getCC()
    {
        return cc;
    }
}
