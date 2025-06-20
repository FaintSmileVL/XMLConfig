package ru.simplexml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : faint
 * @date : 20.06.2025
 * @time : 19:42
 */
public abstract class XMLReader implements IXMLReader {
    private final AtomicBoolean isReloading = new AtomicBoolean(false);
    private boolean initialized = false;

    /**
     * For initialization. Example: Foo.getInstance().initialize();
     */
    public void initialize() {
        if (!initialized) {
            load0();
            initialized = true;
        }
    }

    private void load0() {
        if (!isReloading.compareAndSet(false, true)) {
            return;
        }
        try {
            load();
        } finally {
            isReloading.set(false);
        }
    }

    public void reload() {
        load0();
    }

    /**
     * Returns first child of <list>
     *
     * @param doc
     * @return
     */
    protected Node getFirstChildOfList(Document doc) {
        return getFirstChildOf(doc, "list");
    }

    /**
     * Returns first child of (@nodeName) node
     *
     * @param head
     * @param nodeName
     * @return
     */
    protected Node getFirstChildOf(Node head, String nodeName) {
        for (var node = head.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (isNodeName(node, nodeName)) {
                return node.getFirstChild();
            }
        }
        return head;
    }

    protected boolean isNodeName(Node node, String name) {
        return node != null && node.getNodeName().equals(name);
    }

    protected Document getDocument(String path) throws ParserConfigurationException, IOException, SAXException {
        var file = new File(path);
        var factory1 = DocumentBuilderFactory.newInstance();
        factory1.setValidating(false);
        factory1.setIgnoringComments(true);
        return factory1.newDocumentBuilder().parse(file);
    }

    protected Document getDocument(File file) throws ParserConfigurationException, IOException, SAXException {
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
    protected List<Document> getDocuments(String folderPath) throws ParserConfigurationException, IOException, SAXException {
        var documents = new ArrayList<Document>();
        var folder = new File(folderPath);
        if (!folder.isDirectory()) {
            return documents;
        }
        var files = folder.listFiles();
        if (files == null) {
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
