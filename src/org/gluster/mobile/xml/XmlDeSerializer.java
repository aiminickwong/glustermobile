package org.gluster.mobile.xml;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by ababu on 12/26/13.
 */
public class XmlDeSerializer<G> {
    private String xmlData;
    private int numberElements;

    public XmlDeSerializer(String xmlData) {
        this.xmlData = xmlData;
    }

    public G deSerialize(Class<G> className) {
        G object = null;
        Persister persister = new Persister();
        Reader xmlStringReader = new StringReader(xmlData);
        try {
            object = persister.read(className, xmlStringReader, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
