package com.yunus1903.yucontrol.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yunus1903.yucontrol.connection.IOConnections;
import com.yunus1903.yucontrol.connection.module.ModuleManager;
import com.yunus1903.yucontrol.connection.node.InputNode;
import com.yunus1903.yucontrol.connection.node.Node;
import com.yunus1903.yucontrol.connection.node.OutputNode;
import com.yunus1903.yucontrol.device.Device;

import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * @author Yunus1903
 * @since 08/03/2021
 */
public final class MappingReader
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final List<Device> devices;
    private final IOConnections ioConnections;

    public MappingReader(List<Device> devices, IOConnections ioConnections)
    {
        new ModuleManager(this, devices, ioConnections);
        this.devices = devices;
        this.ioConnections = ioConnections;
        read();
    }

    public void read()
    {
        JsonObject json = null;

        try
        {
            json = GSON.fromJson(new FileReader("mapping.json"), JsonObject.class);
        }
        catch (FileNotFoundException e)
        {
            // todo error
        }

        if (json != null)
        {
            for (Map.Entry<String, JsonElement> element : json.entrySet())
            {
                // This enables you to comment the input nodes out with a "__" prefix
                if (!element.getKey().startsWith("__"))
                {
                    InputNode<?> inputNode =
                            (InputNode<?>) createConnection(new InputNode<>(devices, element.getKey()), element.getValue());
                    if (inputNode != null) ioConnections.add(inputNode);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T, R> Node<T, R> createConnection(Node<T, R> node, JsonElement json)
    {
        if (json.isJsonPrimitive())
        {
            return node.addNode(new OutputNode<>(devices, json.getAsString()));
        }
        else if (json.isJsonArray())
        {
            for (JsonElement element : json.getAsJsonArray())
                createConnection(node, element);
            return node;
        }
        else if (json.isJsonObject())
        {
            JsonObject object = json.getAsJsonObject();
            if (!object.has("module") || !object.get("module").isJsonPrimitive()) return node;

            final Map<String, ModuleManager.RunModule> modules = ModuleManager.getModules();
            if (modules.containsKey(object.get("module").getAsString()))
            {
                return (Node<T, R>) modules.get(object.get("module").getAsString()).runModule(object, node);
            }
        }

        return node;
    }
}
