package projectdi.Logic.data;

import films_retrieving.Film;
import helpers.Const;
import imported.XMLJDomFunctions;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import projectdi.Logic.exceptions.XMLNotFoundException;
import projectdi.Logic.xml_retrieving.XMLBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Film> films;
    private Document document;

    public Data() throws IOException, XMLNotFoundException {
        initializeDocument();
        films = XMLBuilder.getFilmsFromXML(this.document);
        this.document.setDocType(new DocType("film_base", Const.DTD_FILE_NAME.getValue()));
    }


    public List<Film> getFilms() {
        return films;
    }


    public Document getDocument() {
        return document;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public void saveListToFile() throws IOException, XMLNotFoundException {
        XMLJDomFunctions.writeDocumentToFile(this.document, Const.XML_FILE_NAME.getValue());
    }

    public void reloadDocumentObject() throws IOException, XMLNotFoundException {
        initializeDocument();
    }

    public void clearDocument() throws IOException, XMLNotFoundException {
        document.getRootElement().removeContent();
        saveListToFile();
    }

    private void initializeDocument() throws IOException, XMLNotFoundException {
        try {
            this.document = XMLJDomFunctions.readXMLDocument(Const.XML_FILE_NAME.getValue());
        } catch (IOException e) {
            Element root = new Element("film_base");
            this.document = new Document(root);
            XMLJDomFunctions.writeDocumentToFile(this.document, Const.XML_FILE_NAME.getValue());
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }

}
