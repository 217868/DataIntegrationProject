package projectdi.Logic.imported;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import projectdi.Logic.exceptions.ValidationFailedException;
import projectdi.Logic.exceptions.XMLNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JDOMFunctions_Validation {

    public static Document validateDTD(String xmlFile){
        try {
            SAXBuilder builder = new SAXBuilder(true);
            Document doc = builder.build(new File(xmlFile));
            System.out.println("Document XML " + xmlFile + " is valid (DTD)");
            return doc;
        } catch (JDOMException ex) {
           ex.printStackTrace();
        } catch (IOException ex) {
           ex.printStackTrace();
            //Logger.getLogger(JDOMFunctions_Validation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



    public static Document validateXSD(String xmlFile){
        try {
            SAXBuilder builder = new SAXBuilder(true);


            builder.setFeature("http://apache.org/xml/features/validation/schema", true);

            Document doc = builder.build(new File(xmlFile));
            System.out.println("Document XML " + xmlFile + " is valid (XSD)");
            return doc;
        } catch (JDOMException ex) {
            ex.printStackTrace();
           // Logger.getLogger(JDOMFunctions_Validation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
