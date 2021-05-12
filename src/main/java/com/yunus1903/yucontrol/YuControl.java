package com.yunus1903.yucontrol;

import com.yunus1903.yucontrol.connection.IOConnections;
import com.yunus1903.yucontrol.connection.node.Node;
import com.yunus1903.yucontrol.connection.node.OutputNode;
impor  t com.yunus1903.yucontrol.device.Device;
import com.yunus1903.yucontrol.device.devices.Debug;
import com.yunus1903.yucontrol.device.devices.NanoKontrol2;
import com.yunus1903.yucontrol.device.devices.VoicemeeterPotato;
import com.yunus1903.yucontrol.device.devices.YuControlStatus;
import com.yunus1903.yucontrol.json.MappingReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Yunus1903
 * @since 16/11/2020
 */
public final class YuControl
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean shutdown;

    private final List<Device> devices = new ArrayList<>();
    private final IOConnections ioConnections;

    public YuControl()
    {
        LOGGER.info("Starting YuControl...");

        devices.add(new Debug());
        try
        {
            devices.add(new NanoKontrol2());
        }
        catch (MidiUnavailableException e)
        {
            LOGGER.error("Failed to add device", e);
        }
        devices.add(new VoicemeeterPotato());

        devices.forEach(device ->
        {
            if (!device.initialize())
            {
                LOGGER.error("Something went wrong - program will shut down");
                System.exit(0);
            }
        });

        Thread consoleThread = new Thread("Console Thread")
        {
            @Override
            public void run()
            {
                Scanner scanner = new Scanner(System.in);
                while (!shutdown)
                {
                    if (scanner.hasNext())
                    {
                        String rawInput = scanner.next();
                        String[] input = rawInput.toLowerCase().split(" ");
                        String command = input[0];
                        String[] args = Arrays.copyOfRange(input, 1, input.length);

                        switch (command)
                        {
                            case "stop":
                            case "exit":
                                shutdown();
                                break;
                            case "reload":
                                reload();
                                break;
                            case "debug":
                                printDebug();
                                break;
                            case "help":
                            case "?":
                                System.out.println("Commands:");
                                System.out.println("- stop -> Stops program");
                                System.out.println("- reload -> Reloads mappings");
                                System.out.println("- debug -> Prints node connections");
                                System.out.println("- help -> Prints this helpful page");
                                break;
                            default:
                                System.out.println("Command was not recognised, try \"help\" for more info.");
                        }
                    }
                }
            }
        };

        ioConnections = new IOConnections(devices);
        new MappingReader(devices, ioConnections);

        consoleThread.start();

        while (!shutdown)
        {
            ioConnections.tick();
        }

        devices.forEach(Device::shutdown);

        System.exit(0);
    }

    public void shutdown()
    {
        shutdown = true;
    }

    public void reload()
    {
        ioConnections.clear();
        new MappingReader(devices, ioConnections);
        LOGGER.info("YuControl has been reloaded!");
    }

    public void printDebug()
    {
        System.out.println("----- DEBUG START -----");
        ioConnections.getUnmodifiableList().forEach(inputNode ->
        {
            System.out.println(inputNode.toString());
            printSubNodes(inputNode.nodes, 0);
        });
        System.out.println("----- DEBUG END -----");
    }

    private void printSubNodes(List<? extends Node<?, ?>> nodes, int index)
    {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < index; i++) prefix.append("    ");
        prefix.append("└── ");

        nodes.forEach(node ->
        {
            if (node instanceof OutputNode) System.out.println(prefix.toString() + node.toString());
            else System.out.println(prefix.toString() + node.module.getClass().getSimpleName());
            printSubNodes(node.nodes, index + 1);
        });
    }
}
