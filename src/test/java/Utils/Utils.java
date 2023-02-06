package Utils;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Utils {

    public static String getData (String keyName) {
        File fXmlFile = new File("C:\\Users\\Owner\\IdeaProjects\\Class11HW\\src\\main\\resources\\data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc=null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc= dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();


        } catch (Exception e){
            e.printStackTrace();
        }
        return doc.getElementsByTagName(keyName).item(0).getTextContent();

    } // parse xml by keyname
}
