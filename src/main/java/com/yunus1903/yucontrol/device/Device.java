package com.yunus1903.yucontrol.device;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Yunus1903
 * @since 19/11/2020
 */
public abstract class Device
{
    protected static final Logger LOGGER = LogManager.getLogger();

    protected final String name;

    protected boolean isReady;

    public Device(String name)
    {
        this.name = name;
    }

    public boolean initialize()
    {
        isReady = true;
        LOGGER.debug("Initialized device: " + name);
        return true;
    }

    public void shutdown()
    {

    }

    public String getName()
    {
        return name;
    }
}
