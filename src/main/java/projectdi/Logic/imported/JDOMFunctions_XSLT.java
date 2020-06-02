package projectdi.Logic.imported;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.transform.XSLTransformException;
import org.jdom2.transform.XSLTransformer;
import projectdi.Logic.exceptions.NoResultException;
import projectdi.Logic.exceptions.TransformationFailedException;
import projectdi.Logic.exceptions.XMLNotFoundException;

public class JDOMFunctions_XSLT {


    public static Document transformDocument(Document XMLdoc, String xmlFile, String xslFile) throws TransformerException, XMLNotFoundException, NoResultException {

        try{
            DocType d = XMLdoc.getDocType();
            if(d!=null){
                XMLdoc.setDocType(null);
                imported.XMLJDomFunctions.writeDocumentToFile(XMLdoc, xmlFile);
                XMLdoc= imported.XMLJDomFunctions.readXMLDocument(xmlFile);
            }
            XSLTransformer transformer = new XSLTransformer(xslFile);
            Document doc2 = transformer.transform(XMLdoc);
            if(doc2 == null) throw new NoResultException("No result");
            return doc2;
        }
        catch  (XSLTransformException ex) {
            throw new TransformerException("Transformation failed");
        } catch (JDOMException e) {
            throw new XMLNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new XMLNotFoundException(e.getMessage());
        }
    }


    /* Também perimte Transformações para TXT method="text"
       Não cria objeto DOM Document
       Cria diretamente em disco o ficheiro da transformação*/
    public static void transformDocument2(String xmlFile, String xslFile, String sOutFile) throws XMLNotFoundException, TransformationFailedException, FileNotFoundException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(xmlFile));
        } catch (FileNotFoundException e) {
            throw  new XMLNotFoundException("Document XML " + xmlFile + " not found");
        }
        String  sLine;
            StringBuilder   sBuffer = new StringBuilder();
            while (true) {
                try {
                    if (!((sLine = br.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }sBuffer.append(sLine).append("\n");
        String sXML = sBuffer.toString();
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tFactory.newTransformer(new StreamSource(xslFile));
        } catch (TransformerConfigurationException e) {
            throw new TransformationFailedException(e.getMessage());
        }
        try {
            transformer.transform(new StreamSource(new StringReader(sXML)),
                        new StreamResult(new FileOutputStream(sOutFile)));
        } catch (TransformerException e) {
            throw new TransformationFailedException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }

    }
}
