package ru.simplexml;

/**
 * @author : faint
 * @date : 02.07.2023
 * @time : 14:47
 */
public interface IConfig extends IXMLReader {
    Object getValue(String key);

    String getValueStr(String key, String dflt);

    int getValueInt(String key, Integer dflt);

    double getValueDouble(String key, Double dflt);

    long getValueLong(String key, Long dflt);

    boolean getValueBool(String key, Boolean dflt);
}
