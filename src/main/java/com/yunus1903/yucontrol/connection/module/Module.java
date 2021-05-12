package com.yunus1903.yucontrol.connection.module;

/**
 * @author Yunus1903
 * @since 23/11/2020
 */
public abstract class Module<T, R>
{
    public final Class<T> primaryType;
    public final Class<R> secondaryType;

    public Module(Class<T> primaryType, Class<R> secondaryType)
    {
        this.primaryType = primaryType;
        this.secondaryType = secondaryType;
    }
}
