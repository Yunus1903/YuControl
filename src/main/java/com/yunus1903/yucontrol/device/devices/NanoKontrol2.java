package com.yunus1903.yucontrol.device.devices;

import com.yunus1903.yucontrol.device.MidiDevice;
import com.yunus1903.yucontrol.device.io.MidiDeviceInput;
import com.yunus1903.yucontrol.device.io.MidiDeviceOutput;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.Arrays;

/**
 * @author Yunus1903
 * @since 19/11/2020
 */
public class NanoKontrol2 extends MidiDevice
{
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public NanoKontrol2() throws MidiUnavailableException
    {
        super("NanoKONTROL2",
                MidiSystem.getMidiDevice(Arrays.stream(MidiSystem.getMidiDeviceInfo())
                .filter(info -> info.getName().equals("nanoKONTROL2") && info.getDescription().equals("No details available")).findFirst().get()),
                MidiSystem.getMidiDevice(Arrays.stream(MidiSystem.getMidiDeviceInfo())
                .filter(info -> info.getName().equals("nanoKONTROL2") && info.getDescription().equals("External MIDI Port")).findFirst().get()));
    }

    @Override
    public boolean initialize()
    {
        // Inputs
        inputs.add(new MidiDeviceInput("nk2_slider0", 0));
        inputs.add(new MidiDeviceInput("nk2_slider1", 1));
        inputs.add(new MidiDeviceInput("nk2_slider2", 2));
        inputs.add(new MidiDeviceInput("nk2_slider3", 3));
        inputs.add(new MidiDeviceInput("nk2_slider4", 4));
        inputs.add(new MidiDeviceInput("nk2_slider5", 5));
        inputs.add(new MidiDeviceInput("nk2_slider6", 6));
        inputs.add(new MidiDeviceInput("nk2_slider7", 7));
        inputs.add(new MidiDeviceInput("nk2_knob0", 16));
        inputs.add(new MidiDeviceInput("nk2_knob1", 17));
        inputs.add(new MidiDeviceInput("nk2_knob2", 18));
        inputs.add(new MidiDeviceInput("nk2_knob3", 19));
        inputs.add(new MidiDeviceInput("nk2_knob4", 20));
        inputs.add(new MidiDeviceInput("nk2_knob5", 21));
        inputs.add(new MidiDeviceInput("nk2_knob6", 22));
        inputs.add(new MidiDeviceInput("nk2_knob7", 23));
        inputs.add(new MidiDeviceInput("nk2_btn_s0", 32));
        inputs.add(new MidiDeviceInput("nk2_btn_s1", 33));
        inputs.add(new MidiDeviceInput("nk2_btn_s2", 34));
        inputs.add(new MidiDeviceInput("nk2_btn_s3", 35));
        inputs.add(new MidiDeviceInput("nk2_btn_s4", 36));
        inputs.add(new MidiDeviceInput("nk2_btn_s5", 37));
        inputs.add(new MidiDeviceInput("nk2_btn_s6", 38));
        inputs.add(new MidiDeviceInput("nk2_btn_s7", 39));
        inputs.add(new MidiDeviceInput("nk2_btn_play", 41));
        inputs.add(new MidiDeviceInput("nk2_btn_play", 41));
        inputs.add(new MidiDeviceInput("nk2_btn_stop", 42));
        inputs.add(new MidiDeviceInput("nk2_btn_reverse", 43));
        inputs.add(new MidiDeviceInput("nk2_btn_forward", 44));
        inputs.add(new MidiDeviceInput("nk2_btn_record", 45));
        inputs.add(new MidiDeviceInput("nk2_btn_cycle", 46));
        inputs.add(new MidiDeviceInput("nk2_btn_m0", 48));
        inputs.add(new MidiDeviceInput("nk2_btn_m1", 49));
        inputs.add(new MidiDeviceInput("nk2_btn_m2", 50));
        inputs.add(new MidiDeviceInput("nk2_btn_m3", 51));
        inputs.add(new MidiDeviceInput("nk2_btn_m4", 52));
        inputs.add(new MidiDeviceInput("nk2_btn_m5", 53));
        inputs.add(new MidiDeviceInput("nk2_btn_m6", 54));
        inputs.add(new MidiDeviceInput("nk2_btn_m7", 55));
        inputs.add(new MidiDeviceInput("nk2_btn_trk_back", 58));
        inputs.add(new MidiDeviceInput("nk2_btn_trk_next", 59));
        inputs.add(new MidiDeviceInput("nk2_btn_set", 60));
        inputs.add(new MidiDeviceInput("nk2_btn_mrk_next", 61));
        inputs.add(new MidiDeviceInput("nk2_btn_mrk_next", 62));
        inputs.add(new MidiDeviceInput("nk2_btn_r0", 64));
        inputs.add(new MidiDeviceInput("nk2_btn_r1", 65));
        inputs.add(new MidiDeviceInput("nk2_btn_r2", 66));
        inputs.add(new MidiDeviceInput("nk2_btn_r3", 67));
        inputs.add(new MidiDeviceInput("nk2_btn_r4", 68));
        inputs.add(new MidiDeviceInput("nk2_btn_r5", 69));
        inputs.add(new MidiDeviceInput("nk2_btn_r6", 70));
        inputs.add(new MidiDeviceInput("nk2_btn_r7", 71));

        // Outputs
        outputs.add(new MidiDeviceOutput("nk2_btn_s0", 32, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_s1", 33, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_s2", 34, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_s3", 35, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_s4", 36, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_s5", 37, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_s6", 38, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_s7", 39, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_play", 41, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_stop", 42, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_reverse", 43, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_forward", 44, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_record", 45, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_cycle", 46, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m0", 48, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m1", 49, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m2", 50, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m3", 51, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m4", 52, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m5", 53, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m6", 54, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_m7", 55, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r0", 64, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r1", 65, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r2", 66, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r3", 67, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r4", 68, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r5", 69, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r6", 70, this));
        outputs.add(new MidiDeviceOutput("nk2_btn_r7", 71, this));

        return super.initialize();
    }
}
