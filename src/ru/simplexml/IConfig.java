package ru.simplexml;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * @author : faint
 * @date : 02.07.2023
 * @time : 14:47
 */
public interface IConfig  extends IXMLReader {
    void load();

    void putValue(String key, Object value);

    Object getValue(String key);

    String getValueStr(String key, String dflt);

    int getValueInt(String key, Integer dflt);

    double getValueDouble(String key, Double dflt);

    long getValueLong(String key, Long dflt);

    boolean getValueBool(String key, Boolean dflt);

    void compute(String key, Object value);

    default Document getDocument(String path) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(path);
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        factory1.setValidating(false);
        factory1.setIgnoringComments(true);
        return factory1.newDocumentBuilder().parse(file);
    }

    default Document getDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        factory1.setValidating(false);
        factory1.setIgnoringComments(true);
        return factory1.newDocumentBuilder().parse(file);
    }

    /**
     * Возвращает либо список файла, либо пустой список файлов, если произошла ошибка
     *
     * @param root
     * @param dir
     * @return
     */
    default Collection<File> getFiles(String root, String dir) {
        Collection<File> files = Collections.emptyList();
        try {
            files = FileUtils.listFiles(new File(root, dir), FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());
        } catch (Exception e) {
        }
        return files;
    }
}
