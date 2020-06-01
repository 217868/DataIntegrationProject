package helpers;

public enum Const {
    SEARCH_URL("https://en.wikipedia.org/wiki/"),
    SEARCH_URL_SHORT("https://en.wikipedia.org"),
    OUTPUT_FILE_NAME("output.txt"),
    XML_FILE_NAME("films.xml"),
    DTD_FILE_NAME("films.dtd"),
    HTML_PHOTOS_TRANSFORMATION("films_to_html_images.xsl"),
    HTML_PHOTOS_OUTPUT("html_photos_output.html"),
    XML_DIRECTORS_TRANSFORMATION("directors_with_films.xsl"),
    XML_DIRECTORS_OUTPUT("directors.xml"),
    TXT_COUNTRIES_TRANSFORMATION("countries_with_films.xsl"),
    TXT_COUNTRIES_OUTPUT("counties.txt"),
    XML_ACTORS_TRANSFORMATION("actors_number_of_films.xsl"),
    XML_ACTORS_OUTPUT("actors.xml"),
    TXT_POPULAR_LANGUAGES_TRANSFORMATION("popular_languages.xsl"),
    TXT_POPULAR_LANGUAGES_OUTPUT("popular_languages.txt"),
    HTML_MOVIES_FROM_YEAR_TRANSFORMATION("films_in_year.xsl"),
    HTML_MOVIES_FROM_YEAR_OUTPUT("films_in_year.html");


    private String value;

    Const(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }
}
