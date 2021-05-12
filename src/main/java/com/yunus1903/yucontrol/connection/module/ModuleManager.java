package com.yunus1903.yucontrol.connection.module;

import com.google.gson.JsonObject;
import com.yunus1903.yucontrol.connection.IOConnections;
import com.yunus1903.yucontrol.connection.module.modules.FunctionModule;
import com.yunus1903.yucontrol.connection.module.modules.ToggleModule;
import com.yunus1903.yucontrol.connection.node.InputNode;
import com.yunus1903.yucontrol.connection.node.Node;
import com.yunus1903.yucontrol.connection.node.OutputNode;
import com.yunus1903.yucontrol.device.Device;
import com.yunus1903.yucontrol.device.devices.Debug;
import com.yunus1903.yucontrol.json.MappingReader;

import java.util.*;

/**
 * @author Yunus1903
 * @since 05/05/2021
 */
public final class ModuleManager
{
    private static final Map<String, RunModule> MODULES = new HashMap<>();

    public ModuleManager(MappingReader mappingReader, List<Device> devices, IOConnections ioConnections)
    {
        registerModule("toggle", (object, node) ->
        {
            if (node.module.secondaryType != Boolean.class || !object.has("output")) return node;

            Node<?, ?> newNode = new Node<>(new ToggleModule());
            return node.addNode(mappingReader.createConnection(newNode, object.get("output")));
        });
        registerModule("map127ToBoolean", (object, node) ->
        {
            if (node.module.secondaryType != Integer.class || !object.has("output")) return node;

            Node<?, ?> newNode = new Node<>(new FunctionModule<>(Integer.class, Boolean.class, integer -> integer == 127));
            return node.addNode(mappingReader.createConnection(newNode, object.get("output")));
        });
        registerModule("mapBooleanTo127", (object, node) ->
        {
            if (node.module.secondaryType != Boolean.class || !object.has("output")) return node;

            Node<?, ?> newNode = new Node<>(new FunctionModule<>(Boolean.class, Integer.class, aBoolean -> aBoolean ? 127 : 0));
            return node.addNode(mappingReader.createConnection(newNode, object.get("output")));
        });
        registerModule("nk2ToVmButton", (object, node) ->
        {
            if (node.module.secondaryType != Integer.class || !object.has("output") || !(node instanceof InputNode))
                return node;

            ToggleModule toggleModule = new ToggleModule();

            ioConnections.add(new InputNode<>(devices, object.get("output").getAsString())
                    .addNode(new Node<>(new FunctionModule<>(Boolean.class, Integer.class, aBoolean ->
                    {
                        if (!toggleModule.get().equals(aBoolean)) toggleModule.set(aBoolean);
                        return aBoolean ? 127 : 0;
                    })).addNode(new OutputNode<>(devices, node.toString()))));

            Node<?, ?> newNode = new Node<>(new FunctionModule<>(Integer.class, Boolean.class, integer -> integer == 127))
                    .addNode(mappingReader.createConnection(new Node<>(toggleModule), object.get("output")));

            return node.addNode(newNode);
        });
        registerModule("debug", (object, node) ->
        {
            if (!(node instanceof InputNode)) return node;

            Optional<Device> debugDevice = devices.stream().filter(device -> device instanceof Debug).findFirst();
            if (debugDevice.isPresent())
            {
                return node.addNode(new Node<>(new FunctionModule<>(Object.class, String.class, String::valueOf))
                        .addNode(new OutputNode<>(devices, ((Debug) debugDevice.get()).addOutput(node.toString()))));
            }
            return node;
        });
    }

    private void registerModule(String id, RunModule runModule)
    {
        MODULES.put(id, runModule);
    }

    public static Map<String, RunModule> getModules()
    {
        return Collections.unmodifiableMap(MODULES);
    }

    @FunctionalInterface
    public interface RunModule
    {
        Node<?, ?> runModule(JsonObject object, Node<?, ?> node);
    }
}
