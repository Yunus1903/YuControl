package com.yunus1903.yucontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Yunus1903
 * @since 16/11/2020
 */
public class Main
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args)
    {
//        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo())
//        {
//            LOGGER.debug(info.hashCode() + " - " + info.getName() + " - " + info.getDescription());
//        }

        new YuControl();

                /*
        AtomicReference<YuControl> yuControl = new AtomicReference<>(null);

        Thread mainThread = new Thread("YuControl")
        {
            @Override
            public void run()
            {
                yuControl.set(new YuControl());
            }
        };
                 */
    }
}
