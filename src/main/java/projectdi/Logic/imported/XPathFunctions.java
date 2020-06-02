package projectdi.Logic.imported;

import net.sf.saxon.s9api.*;
import projectdi.Logic.exceptions.XMLNotFoundException;

import java.io.File;

public class XPathFunctions {
    public static XdmValue executeXPath(String xp, String xmlFile) throws SaxonApiException, XMLNotFoundException {
        XdmValue resultado = null;
        File f = new File(xmlFile);
        if (f.exists()) {
            Processor proc = new Processor(false);
            XPathCompiler xpath = proc.newXPathCompiler();

            DocumentBuilder builder = proc.newDocumentBuilder();

            XdmNode xml = builder.build(new File(xmlFile));
            XPathSelector selector = xpath.compile(xp).load();

            selector.setContextItem(xml);
            resultado = selector.evaluate();
        }
        else throw new XMLNotFoundException("XML Documnet " + xmlFile + " not found");
        return resultado;
    }
}
