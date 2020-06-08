package projectdi.Logic.mainLogic;

import projectdi.Logic.data.Data;
import projectdi.Logic.films_retrieving.FilmHelper;
import projectdi.Logic.films_retrieving.FilmURLHelper;
import projectdi.Logic.films_retrieving.Film;
import helpers.Const;
import helpers.XMLElementsToFieldsMapping;
import projectdi.Logic.data.Data;
import projectdi.Logic.exceptions.*;
import projectdi.Logic.xml_retrieving.TransformationsHelper;
import projectdi.Logic.xml_retrieving.ValidatorHelper;
import projectdi.Logic.xml_retrieving.XPathHelper;
import projectdi.Logic.xml_retrieving.XMLBuilder;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainLogic {
    private Data data;
    private FilmHelper filmHelper;
    private FilmURLHelper filmURLHelper;
    private XMLBuilder xmlBuilder;
    private ValidatorHelper validatorHelper;
    private TransformationsHelper transformationsHelper;
    private XPathHelper xPathHelper;

    public MainLogic() throws IOException{
        this.data = new Data();
        this.filmHelper = new FilmHelper();
        this.filmURLHelper = new FilmURLHelper();
        this.xmlBuilder = new XMLBuilder(data.getDocument());
        this.validatorHelper = new ValidatorHelper();
        this.transformationsHelper = new TransformationsHelper();
        this.xPathHelper = new XPathHelper();

    }

    public boolean validate() throws ValidationFailedException, IOException{
        return validatorHelper.validate();
    }
    private void addListOfFilms(List<Film> films) throws IOException{
        xmlBuilder.addFilms(films);
    }

    public void populateXMLFileFromTitlesListFile(String fileName) throws IOException {
        data.clearDocument();
        data.reloadDocumentObject();
        List<Film> films = filmHelper.createFilms(filmURLHelper.getUrlsFromTitles(fileName));
        System.out.println(films.size());
        this.data.setFilms(films);
        System.out.println(films.size());
        addListOfFilms(data.getFilms());
       // data.saveListToFile();

    }

public void addMovie(String title) throws  IOException{
        Film film = filmHelper.createFilm(filmURLHelper.searchFilmURL(title));
        if (data.getFilms().contains(film)) return; //throw
        data.getFilms().add(film);
        xmlBuilder.addFilm(film);
        data.saveListToFile();
    }


    public void deleteFilm(String title) throws IOException {
        boolean doestTitleExist = false;
        int deleteIndex = -1;
        for(Film f : data.getFilms()){
            if(f.getTitle().equals(title)){
                deleteIndex = data.getFilms().indexOf(f);
                doestTitleExist = true;
            }
        }
        data.getFilms().remove(deleteIndex);

        //todo: throw exception
        if(!doestTitleExist) return;
            xmlBuilder.deleteFilm(title);
        data.saveListToFile();
    }

    public void editFilm(String title, Film film) throws IOException{
        Film filmToEdit = null;
        for (Film f : data.getFilms()) {
            if (f.getTitle().equals(title)) filmToEdit = f;
        }
        filmToEdit = film;

            xmlBuilder.editElement(title, film);

        data.saveListToFile();
    }

    public void getHTMLwithPhotosOfFilms() throws FileNotFoundException, XMLNotFoundException, TransformationFailedException {
        transformationsHelper.getHTMLwithPhotos();
    }

    public void getXMLwithDirectorsFilms() throws FileNotFoundException, XMLNotFoundException, TransformationFailedException {
        transformationsHelper.getXMLwithDirectorsFilms();
    }

    public void getTXTwithCountriesWithFilms() throws FileNotFoundException, XMLNotFoundException, TransformationFailedException {
        transformationsHelper.getCountriesWithFilms();
    }

    public void getXMLwithActorsWithNumberOfFilms() throws FileNotFoundException, XMLNotFoundException, TransformationFailedException {
        transformationsHelper.getActorsWithNumberOfFilms();
    }

    public void getTXTwithPopularLanguages() throws FileNotFoundException, XMLNotFoundException, TransformationFailedException {
        transformationsHelper.getPopularLanguages();
    }

    public void getHTMLwithYearsWithMovies() throws FileNotFoundException, XMLNotFoundException, TransformationFailedException {
        transformationsHelper.getYearsWithMovies();
    }

    public XPathHelper getxPathHelper() {
        return this.xPathHelper;
    }

    public List<Film> getCurrentFilms() {
        return data.getFilms();
    }

}
