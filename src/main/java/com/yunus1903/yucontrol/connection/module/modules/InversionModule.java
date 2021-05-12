package com.yunus1903.yucontrol.connection.module.modules;

import com.yunus1903.yucontrol.connection.module.Module;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Yunus1903
 * @since 24/11/2020
 */
public class InversionModule extends Module<Boolean, Boolean> implements Consumer<Boolean>, Supplier<Boolean>
{
    private boolean value;

    public InversionModule()
    {
        super(Boolean.class, Boolean.class);
    }

    @Override
    public void accept(Boolean aBoolean)
    {
        value = !aBoolean;
    }

    @Override
    public Boolean get()
    {
        return value;
    }
}
