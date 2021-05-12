package com.yunus1903.yucontrol.device.io;

/**
 * @author Yunus1903
 * @since 19/11/2020
 */
public abstract class DeviceIO<T>
{
    protected final String id;
    protected final Class<T> dataType;

    public DeviceIO(String id, Class<T> dataType)
    {
        this.id = id;
        this.dataType = dataType;
    }

    public String getId()
    {
        return id;
    }

    public Class<T> getDataType()
    {
        return dataType;
    }
}
