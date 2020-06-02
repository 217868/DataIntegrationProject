package projectdi.Logic.xml_retrieving;


import projectdi.Logic.exceptions.TransformationFailedException;
import projectdi.Logic.exceptions.XMLNotFoundException;
import projectdi.Logic.imported.JDOMFunctions_XSLT;

import java.io.FileNotFoundException;

public class TransformationsHelper {
    public void getHTMLwithPhotos() throws FileNotFoundException, TransformationFailedException, XMLNotFoundException {
        JDOMFunctions_XSLT.transformDocument2(helpers.Const.XML_FILE_NAME.getValue(),
                helpers.Const.HTML_PHOTOS_TRANSFORMATION.getValue(),
                helpers.Const.HTML_PHOTOS_OUTPUT.getValue());
    }

    public void getXMLwithDirectorsFilms() throws FileNotFoundException, TransformationFailedException, XMLNotFoundException {
        JDOMFunctions_XSLT.transformDocument2(helpers.Const.XML_FILE_NAME.getValue(),
                helpers.Const.XML_DIRECTORS_TRANSFORMATION.getValue(),
                helpers.Const.XML_DIRECTORS_OUTPUT.getValue());
    }

    public void getCountriesWithFilms() throws FileNotFoundException, TransformationFailedException, XMLNotFoundException {
        JDOMFunctions_XSLT.transformDocument2(helpers.Const.XML_FILE_NAME.getValue(),
                helpers.Const.TXT_COUNTRIES_TRANSFORMATION.getValue(),
                helpers.Const.TXT_COUNTRIES_OUTPUT.getValue());
    }

    public void getActorsWithNumberOfFilms() throws FileNotFoundException, TransformationFailedException, XMLNotFoundException {
        JDOMFunctions_XSLT.transformDocument2(helpers.Const.XML_FILE_NAME.getValue(),
                helpers.Const.XML_ACTORS_TRANSFORMATION.getValue(),
                helpers.Const.XML_ACTORS_OUTPUT.getValue());
    }

    public void getPopularLanguages() throws FileNotFoundException, TransformationFailedException, XMLNotFoundException {
        JDOMFunctions_XSLT.transformDocument2(helpers.Const.XML_FILE_NAME.getValue(),
                helpers.Const.TXT_POPULAR_LANGUAGES_TRANSFORMATION.getValue(),
                helpers.Const.TXT_POPULAR_LANGUAGES_OUTPUT.getValue());
    }

    public void getYearsWithMovies() throws FileNotFoundException, TransformationFailedException, XMLNotFoundException {
        JDOMFunctions_XSLT.transformDocument2(helpers.Const.XML_FILE_NAME.getValue(),
                helpers.Const.HTML_MOVIES_FROM_YEAR_TRANSFORMATION.getValue(),
                helpers.Const.HTML_MOVIES_FROM_YEAR_OUTPUT.getValue());
    }


}
