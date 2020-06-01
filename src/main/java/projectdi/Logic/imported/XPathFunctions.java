package projectdi.Logic.imported;

import net.sf.saxon.s9api.*;

import java.io.File;

public class XPathFunctions {
    public static XdmValue executeXPath(String xp, String xmlFile) throws SaxonApiException {
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
        return resultado;
    }

    public static String getResults(XdmValue lista) {
        StringBuilder texto = new StringBuilder();
        System.out.println("RESULTADO DA PESQUISA XPATH:");
        for (XdmItem item : lista) {
            texto = texto.append(item.getStringValue()).append("\n");
        }
        return texto.toString();
    }
}
