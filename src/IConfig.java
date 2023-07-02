import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

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
}
