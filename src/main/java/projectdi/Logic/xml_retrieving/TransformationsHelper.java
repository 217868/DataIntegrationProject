package xml_retrieving;

import helpers.Const;
import imported.JDOMFunctions_XSLT;

public class TransformationsHelper {
    public void getHTMLwithPhotos(){
        JDOMFunctions_XSLT.transformDocument2(Const.XML_FILE_NAME.getValue(),
                Const.HTML_PHOTOS_TRANSFORMATION.getValue(),
                Const.HTML_PHOTOS_OUTPUT.getValue());
    }

    public void getXMLwithDirectorsFilms(){
        JDOMFunctions_XSLT.transformDocument2(Const.XML_FILE_NAME.getValue(),
                Const.XML_DIRECTORS_TRANSFORMATION.getValue(),
                Const.XML_DIRECTORS_OUTPUT.getValue());
    }

    public void getCountriesWithFilms(){
        JDOMFunctions_XSLT.transformDocument2(Const.XML_FILE_NAME.getValue(),
                Const.TXT_COUNTRIES_TRANSFORMATION.getValue(),
                Const.TXT_COUNTRIES_OUTPUT.getValue());
    }

    public void getActorsWithNumberOfFilms(){
        JDOMFunctions_XSLT.transformDocument2(Const.XML_FILE_NAME.getValue(),
                Const.XML_ACTORS_TRANSFORMATION.getValue(),
                Const.XML_ACTORS_OUTPUT.getValue());
    }

    public void getPopularLanguages(){
        JDOMFunctions_XSLT.transformDocument2(Const.XML_FILE_NAME.getValue(),
                Const.TXT_POPULAR_LANGUAGES_TRANSFORMATION.getValue(),
                Const.TXT_POPULAR_LANGUAGES_OUTPUT.getValue());
    }

    public void getYearsWithMovies(){
        JDOMFunctions_XSLT.transformDocument2(Const.XML_FILE_NAME.getValue(),
                Const.HTML_MOVIES_FROM_YEAR_TRANSFORMATION.getValue(),
                Const.HTML_MOVIES_FROM_YEAR_OUTPUT.getValue());
    }


}
