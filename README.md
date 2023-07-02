# XMLConfig

Simple XML config holder/parser.

Just add the library, extend class XMLConfig and use it's methods for getting value from Node and storage theese values in it by HashMap.

Simple example:

```
public class Foo extends XMLConfig {
    @Override
    public void load() {
      try {
            Document doc1 = getDocument("myPath/myConfig.xml");
            for (Node n1 = doc1.getFirstChild(); n1 != null; n1 = n1.getNextSibling()) {
                if (!isNodeName(n1, "list")) {
                    continue;
                }
                for (Node list = n1.getFirstChild(); list != null; list = list.getNextSibling()) {
                    if (isNodeName(list, "config")) {
                        parseConfig(list, log);
                    } else if (isNodeName(list, "nodeName")) {
                        putValue("foo", get(list, "nodeValue"));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public int getFoo() {
        int dflt = 0;
        return getValueInt("foo", dflt);
    }
}
```
