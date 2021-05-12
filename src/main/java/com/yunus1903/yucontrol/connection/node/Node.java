package com.yunus1903.yucontrol.connection.node;

import com.yunus1903.yucontrol.connection.module.Module;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunus1903
 * @since 08/03/2021
 */
public class Node<T, R>
{
    private static final Logger LOGGER = LogManager.getLogger();

    public final Module<T, R> module;
    public final List<Node<R, ?>> nodes = new ArrayList<>();

    public Node(Module<T, R> module)
    {
        this.module = module;
    }

    @SuppressWarnings("unchecked")
    public Node<T, R> addNode(Node<?, ?> node)
    {
        if (node == null) throw new NullPointerException("Cannon add a node that is null");
        if (!node.module.primaryType.isAssignableFrom(module.secondaryType))
        {
            LOGGER.error("Types do not match");
            return null;
        }
        nodes.add((Node<R, ?>) node);
        return this;
    }
}
