package ru.simplexml;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;

/**
 * @author : faint
 * @date : 02.07.2023
 * @time : 14:46
 */
public abstract class XMLConfig implements IConfig {
    public HashMap<String, Object> settings = new HashMap<>();
    @Override
    public void putValue(String key, Object value) {
        settings.putIfAbsent(key, value);
    }

    @Override
    public Object getValue(String key) {
        return settings.get(key);
    }

    @Override
    public String getValueStr(String key, String dflt) {
        return (String)settings.getOrDefault(key, dflt);
    }

    @Override
    public int getValueInt(String key, Integer dflt) {
        return (Integer)settings.getOrDefault(key, dflt);
    }

    @Override
    public double getValueDouble(String key, Double dflt) {
        return (100. - (double)settings.getOrDefault(key, dflt)) / 100.;
    }

    @Override
    public long getValueLong(String key, Long dflt) {
        return (Long)settings.getOrDefault(key, dflt);
    }

    @Override
    public boolean getValueBool(String key, Boolean dflt) {
        return (Boolean)settings.getOrDefault(key, dflt);
    }

    @Override
    public void compute(String key, Object value) {
        settings.computeIfPresent(key, (k, v) -> value);
    }

    public void parseConfig(Node node, Logger log) {
        if (get(node, "name") == null || get(node, "name").isEmpty()) {
            log.warn("Error parsing config file conf = {}", node.getNodeName());
            return;
        }
        String value = get(node, "val");
        if (NumberUtils.isNumber(value)) {
            settings.putIfAbsent(get(node, "name"), Integer.parseInt(value));
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            settings.putIfAbsent(get(node, "name"), Boolean.parseBoolean(value));
        } else {
            settings.putIfAbsent(get(node, "name"), value);
        }
    }

    /**
     * Returns first child of <list>
     *
     * @param doc
     * @return
     */
    public Node getFirstChildOfList(Document doc) {
        return getFirstChildOf(doc, "list");
    }

    /**
     * Returns first child of (@nodeName) node
     *
     * @param head
     * @param nodeName
     * @return
     */
    public Node getFirstChildOf(Node head, String nodeName) {
        for (var node = head.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (isNodeName(node, nodeName)) {
                return node;
            }
        }
        return head;
    }
}
