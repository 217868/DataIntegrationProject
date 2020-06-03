package projectdi.Logic.xml_retrieving;

import projectdi.Logic.films_retrieving.Film;
import helpers.Const;
import helpers.XMLElementsToFieldsMapping;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmValue;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import projectdi.Logic.imported.XPathFunctions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XPathHelper {

    public List<Film> searchMovieByTitle(String title) throws SaxonApiException {
        String xPath = "film_base/film[title = '"+ title +"']";
        XdmValue x = XPathFunctions.executeXPath(xPath, Const.XML_FILE_NAME.getValue());
        return convertXdmToFilms(x);
    }

    public List<Film> searchMovieByDirector(String director) throws SaxonApiException {
        String xPath = "film_base/film[directors/director = '" + director+ "']";
        XdmValue x = XPathFunctions.executeXPath(xPath, Const.XML_FILE_NAME.getValue());
        return convertXdmToFilms(x);
    }

    public List<Film> searchMovieByActors(List<String> actors) throws SaxonApiException {
        String condition = "";
        for(int i = 0; i < actors.size() - 1; i++){
            condition += "cast/actor = '" + actors.get(i) + "' or ";
        }
        condition += "cast/actor = '" + actors.get(actors.size() - 1) + "'";

        String xPath = "film_base/film["+ condition +"]";
        XdmValue x = XPathFunctions.executeXPath(xPath, Const.XML_FILE_NAME.getValue());
        return convertXdmToFilms(x);
    }

    public List<Film> searchMovieByDuration(int startBoundary, int endBoundary) throws SaxonApiException {
        String xPath = "film_base/film[duration_in_minutes >= " + startBoundary + " and duration_in_minutes <= "+ endBoundary +"]";
        XdmValue x = XPathFunctions.executeXPath(xPath, Const.XML_FILE_NAME.getValue());
        return convertXdmToFilms(x);
    }

    public List<Film> searchMovieCountry(String country) throws SaxonApiException {
        String xPath = "film_base/film[countries/country = '" + country + "']";
        XdmValue x = XPathFunctions.executeXPath(xPath, Const.XML_FILE_NAME.getValue());
        return convertXdmToFilms(x);
    }


    private List<Film> convertXdmToFilms(XdmValue value) {
        List<Element> results = new ArrayList<>();
        SAXBuilder builder = new SAXBuilder();
        String output = "";
        Document doc = new Document();
        doc.setRootElement(new Element("root"));
        for (int i = 0; i < value.size(); i++ ) {
            Document tempDoc = null;
            output = value.itemAt(i) + "";
            InputStream targetStream = new ByteArrayInputStream(output.getBytes());
            try {
                tempDoc = builder.build(targetStream);
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element el = tempDoc.getRootElement().detach();
            doc.getRootElement().addContent(el);
        }
        return projectdi.Logic.xml_retrieving.XMLBuilder.getFilmsFromXML(doc);
    }
}
