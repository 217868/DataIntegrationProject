package xml_retrieving;

import films_retrieving.Film;
import helpers.Const;
import imported.XMLJDomFunctions;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XMLBuilder {

    private Document document;

    public XMLBuilder(Document document){
        this.document = document;
    }

    public void addFilm(Film film){
        Element filmElement = new Element("film");
        Attribute imageLink = new Attribute("image_link", film.getImage());
        filmElement.setAttribute(imageLink);
        Attribute year = new Attribute("year", Integer.toString(film.getYear()));
        filmElement.setAttribute(year);

        Element title = new Element("title").addContent(film.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(film.getReleaseDateInUSA());
        Element releaseDateInUSA = new Element("release_date_in_USA").addContent(strDate);
        Element countries = new Element("countries");
        for (String country : film.getCountries()) {
            Element countryElement = new Element("country").addContent(country);
            countries.addContent(countryElement);
        }

        Element directors = new Element("directors");
        for (String director : film.getDirectors()) {
            Element directorElement = new Element("director").addContent(director);
            directors.addContent(directorElement);
        }

        Element cast = new Element("cast");
        for (String actor : film.getCast()) {
            Element castElement = new Element("actor").addContent(actor);
            cast.addContent(castElement);
        }

        Element durationInMinutes = new Element("duration_in_minutes").addContent(Integer.toString(film.getDurationInMinutes()));

        Element distributors = new Element("distributors");
        for (String distributor : film.getDistributedBy()) {
            Element distributorElement = new Element("distributor").addContent(distributor);
            distributors.addContent(distributorElement);
        }

        Element languages = new Element("languages");
        for (String language : film.getLanguages()) {
            Element languageElement = new Element("language").addContent(language);
            languages.addContent(languageElement);
        }


        Element musicAuthors = new Element("music_authors");
        for (String musicAuthor : film.getMusicAuthor()) {
            Element musicAuthorElement = new Element("musicAuthor").addContent(musicAuthor);
            musicAuthors.addContent(musicAuthorElement);
        }

        Element boxOffice = new Element("box_office").addContent(Float.toString(film.getBoxOffice()));

        filmElement.addContent(title);
        filmElement.addContent(releaseDateInUSA);
        filmElement.addContent(countries);
        filmElement.addContent(directors);
        filmElement.addContent(cast);
        filmElement.addContent(durationInMinutes);
        filmElement.addContent(distributors);
        filmElement.addContent(languages);
        filmElement.addContent(musicAuthors);
        filmElement.addContent(boxOffice);

        document.getRootElement().addContent(filmElement);
        XMLJDomFunctions.writeDocumentToFile(document, Const.XML_FILE_NAME.getValue());
    }

    public void addFilms(List<Film> films){
        for(Film f : films) addFilm(f);
    }

    public static Film createFilmFromElement(Element element){
        String title = element.getChild("title").getValue();
        String image_link = element.getAttributeValue("image_link");
        int year = Integer.parseInt(element.getAttributeValue("year"));
        String date = element.getChild("release_date_in_USA").getValue();
        Date date1 = null;
        try {
             date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> countries  = new ArrayList<>();
        for(Element e : element.getChild("countries").getChildren()){
            countries.add(e.getValue());
        }

        List<String> directors  = new ArrayList<>();
        for(Element e : element.getChild("directors").getChildren()){
            directors.add(e.getValue());
        }

        List<String> cast  = new ArrayList<>();
        for(Element e : element.getChild("cast").getChildren()){
            cast.add(e.getValue());
        }

        int durationInMinutes = Integer.parseInt(element.getChild("duration_in_minutes").getValue());

        List<String> distributors  = new ArrayList<>();
        for(Element e : element.getChild("distributors").getChildren()){
            distributors.add(e.getValue());
        }

        List<String> languages  = new ArrayList<>();
        for(Element e : element.getChild("languages").getChildren()){
            languages.add(e.getValue());
        }

        List<String> musicAuthors  = new ArrayList<>();
        for(Element e : element.getChild("music_authors").getChildren()){
            musicAuthors.add(e.getValue());
        }

        float boxOffice = Float.parseFloat(element.getChild("box_office").getValue());

        return new Film(title, image_link, year, date1, countries, directors,
                cast, durationInMinutes, distributors, languages, musicAuthors, boxOffice);
    }

    public static List<Film> getFilmsFromXML(Document document){
        List<Film> films = new ArrayList<>();
        for(Element e : document.getRootElement().getChildren()) {
            films.add(createFilmFromElement(e));
        }
        return films;
    }

    public void deleteFilm(String title){
        document.getRootElement().removeContent(getElementByTitle(title));
    }

    public void editElement(String title, String subElementName, String oldValue, String newValue){
//        if(getElementByTitle(title).getChild(subElementName) != null){
//            getElementByTitle(title).getChild(subElementName).setText(newValue);
//        }
//        else
        Element toEditElement = getElementByTitle(title).getChild(subElementName);
        if (toEditElement.getChildren().size() > 0) {
            for (Element e : toEditElement.getChildren()) {
                if (e.getValue().equals(oldValue)) e.setText(newValue);
            }
        }
        else {
            toEditElement.setText(newValue);
        }
    }

    public void editAttribute(String title, String attributeName, String newValue){
        Element toEditElement = getElementByTitle(title);
        toEditElement.setAttribute(attributeName, newValue);
    }

    private Element getElementByTitle(String title){
        for(Element e : document.getRootElement().getChildren()){
            if(e.getChild("title").getValue().equals(title)) return e;
        }
        //todo: throw
        System.out.println("not found");
        return null;
    }

}
