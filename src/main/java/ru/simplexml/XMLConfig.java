package ru.simplexml;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.w3c.dom.Node;
import ru.StringUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author : faint
 * @date : 02.07.2023
 * @time : 14:46
 */
public abstract class XMLConfig extends XMLReader implements IConfig {
    public HashMap<String, Object> settings = new HashMap<>();

    protected XMLConfig() {
    }

    protected void putValue(String key, Object value) {
        settings.putIfAbsent(key, value);
    }

    @Override
    public Object getValue(String key) {
        return settings.get(key);
    }

    @Override
    public String getValueStr(String key, String dflt) {
        return (String) settings.getOrDefault(key, dflt);
    }

    @Override
    public int getValueInt(String key, Integer dflt) {
        return (Integer) settings.getOrDefault(key, dflt);
    }

    @Override
    public double getValueDouble(String key, Double dflt) {
        return (double) settings.getOrDefault(key, dflt);
    }

    @Override
    public long getValueLong(String key, Long dflt) {
        return (Long) settings.getOrDefault(key, dflt);
    }

    @Override
    public boolean getValueBool(String key, Boolean dflt) {
        return (Boolean) settings.getOrDefault(key, dflt);
    }

    protected void compute(String key, Object value) {
        settings.computeIfPresent(key, (k, v) -> value);
    }

    protected void parseConfig(Node node, Logger log) {
        if (get(node, "name") == null || get(node, "name").isEmpty()) {
            log.warn("Error parsing config file conf = {}", node.getNodeName());
            return;
        }
        String value = get(node, "val");
        if (NumberUtils.isCreatable(value)) {
            try {
                String key = get(node, "name");
                NumberFormat format = NumberFormat.getInstance(Locale.US);
                Number number = format.parse(value);

                if (number.doubleValue() % 1 == 0) {
                    // Целое число
                    long l = number.longValue();
                    if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                        settings.putIfAbsent(key, (int) l);
                    } else {
                        settings.putIfAbsent(key, l);
                    }
                } else {
                    // Дробное число
                    settings.putIfAbsent(key, number.doubleValue());
                }
                return;
            } catch (ParseException e) {
                return;
            }
        }
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            settings.putIfAbsent(get(node, "name"), Boolean.parseBoolean(value));
        } else {
            settings.putIfAbsent(get(node, "name"), value);
        }
    }

    protected String get(Node n, String item) {
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

    protected boolean get(Node n, String item, boolean dflt) {
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

    protected byte get(Node n, String item, byte dflt) {
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

    protected int get(Node n, String item, int dflt) {
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

    protected long get(Node n, String item, long dflt) {
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

    protected double get(Node n, String item, double dflt) {
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

    protected int[] get(Node n, String item, int[] dflt) {
        try {
            final Node d = n.getAttributes().getNamedItem(item);
            if (d == null) {
                return dflt;
            }
            final String val = d.getNodeValue();
            if (val == null) {
                return dflt;
            }
            String strs[] = get(n, item).split("\\s*[;,]\\s*");
            int values[] = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                values[i] = Integer.parseInt(strs[i]);
            }
            return values;
        } catch (Exception e) {
            return dflt;
        }
    }

    protected <E extends Enum<E>> E get(Node n, String item, Class<E> enumClass) {
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
}
