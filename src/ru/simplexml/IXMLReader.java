package ru.simplexml;

import org.w3c.dom.Node;
import ru.StringUtils;

/**
 * @author : faint
 * @date : 02.07.2023
 * @time : 14:45
 */
public interface IXMLReader {
    default String get(Node n, String item) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return StringUtils.EMPTY;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return StringUtils.EMPTY;
            }
            return val;
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    default boolean get(Node n, String item, boolean dflt) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return dflt;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return dflt;
            }
            return Boolean.parseBoolean(val);
        } catch (Exception e) {
            return dflt;
        }
    }

    default byte get(Node n, String item, byte dflt) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return dflt;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return dflt;
            }
            return Byte.parseByte(val);
        } catch (Exception e) {
            return dflt;
        }
    }

    default int get(Node n, String item, int dflt) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return dflt;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return dflt;
            }
            return Integer.parseInt(val);
        } catch (Exception e) {
            return dflt;
        }
    }

    default long get(Node n, String item, long dflt) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return dflt;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return dflt;
            }
            return Long.parseLong(val);
        } catch (Exception e) {
            return dflt;
        }
    }

    default double get(Node n, String item, double dflt) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return dflt;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return dflt;
            }
            return Double.parseDouble(val);
        } catch (Exception e) {
            return dflt;
        }
    }

    default int[] get(Node n, String item, int[] dflt) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return dflt;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return dflt;
            }
            String strs[] = get(n, item).split(";");
            int values[] = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                values[i] = Integer.parseInt(strs[i]);
            }
            return values;
        } catch (Exception e) {
            return dflt;
        }
    }

    default <E extends Enum<E>> E get(Node n, String item, Class<E> enumClass) {
        final Node d = n.getAttributes().getNamedItem(item);
        if (d == null) {
            return null;
        }
        final Object val = d.getNodeValue();
        if (val == null) {
            return null;
        }
        if (val != null && enumClass.isInstance(val)) {
            return (E) val;
        }
        if (val instanceof String) {
            return Enum.valueOf(enumClass, (String) val);
        }
        throw new IllegalArgumentException("Enum value of type " + enumClass.getName() + " required, but found: " + val + "!");
    }

    default boolean isNodeName(Node node, String name) {
        return node != null && node.getNodeName().equals(name);
    }
}
