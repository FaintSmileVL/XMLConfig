package ru.simplexml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : faint
 * @date : 02.07.2023
 * @time : 14:47
 */
public interface IConfig extends IXMLReader {
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
        var file = new File(path);
        var factory1 = DocumentBuilderFactory.newInstance();
        factory1.setValidating(false);
        factory1.setIgnoringComments(true);
        return factory1.newDocumentBuilder().parse(file);
    }

    default Document getDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        var factory1 = DocumentBuilderFactory.newInstance();
        factory1.setValidating(false);
        factory1.setIgnoringComments(true);
        return factory1.newDocumentBuilder().parse(file);
    }

    /**
     * Парсит все XML-файлы в указанной папке и возвращает список документов.
     *
     * @param folderPath Путь к папке с XML-файлами.
     * @return Список успешно обработанных XML-документов.
     */
    default List<Document> getDocuments(String folderPath) throws ParserConfigurationException, IOException, SAXException {
        var documents = new ArrayList<Document>();
        var folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return documents;
        }
        var files = folder.listFiles();
        if (files == null || files.length == 0) {
            return documents;
        }
        for (File file : files) {
            if (!file.isFile() && file.getName().toLowerCase().endsWith(".xml")) {
                continue;
            }
            documents.add(getDocument(file));
        }
        return documents;
    }
}
