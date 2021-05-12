package com.yunus1903.yucontrol.connection.module.modules;

import com.yunus1903.yucontrol.connection.module.Module;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 23/11/2020
 */
public class ToggleModule extends Module<Boolean, Boolean> implements Consumer<Boolean>, Supplier<Boolean>
{
    private boolean value;
    private boolean lastValue;

    private boolean flag;

    public ToggleModule()
    {
        super(Boolean.class, Boolean.class);
    }

    @Override
    public void accept(Boolean aBoolean)
    {
        if (aBoolean && !lastValue) value = !value;
        lastValue = aBoolean;
    }

    @Override
    public Boolean get()
    {
        return value;
    }

    public void set(boolean value)
    {
        if (!flag)
        {
            this.value = value;
            flag = true;
        }
    }
}
