package projectdi.Logic.mainLogic;

import data.Data;
import films_retrieving.FilmHelper;
import films_retrieving.FilmURLHelper;
import helpers.Const;
import helpers.XMLElementsToFieldsMapping;
import projectdi.Logic.xml_retrieving.ValidatorHelper;
import xml_retrieving.TransformationsHelper;
import xml_retrieving.XMLBuilder;

import java.io.File;
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

    public MainLogic(){
        this.data = new Data();
        this.filmHelper = new FilmHelper();
        this.filmURLHelper = new FilmURLHelper();
        this.xmlBuilder = new XMLBuilder(data.getDocument());
        this.validatorHelper = new ValidatorHelper();
        this.transformationsHelper = new TransformationsHelper();

    }

    public boolean validate(){
        try {
            return validatorHelper.validate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void addListOfFilms(List<films_retrieving.Film> films) {
        xmlBuilder.addFilms(films);
    }

    public void populateXMLFileFromTitlesListFile(String fileName) {
        data.clearDocument();
        data.reloadDocumentObject();
        List<films_retrieving.Film> films = filmHelper.createFilms(filmURLHelper.getUrlsFromTitles(fileName));
        System.out.println(films.size());
        this.data.setFilms(films);
        System.out.println(films.size());
        addListOfFilms(data.getFilms());

    }

    public void addMovie(String title) {
        films_retrieving.Film film = filmHelper.createFilm(filmURLHelper.searchFilmURL(title));
        if (data.getFilms().contains(film)) return; //throw
        data.getFilms().add(film);
        xmlBuilder.addFilm(film);
        data.saveListToFile();
    }


    public void deleteFilm(String title){
        boolean doestTitleExist = false;
        int deleteIndex = -1;
        for(films_retrieving.Film f : data.getFilms()){
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

    public void editFilm(String title, String oldValue, String newValue, XMLElementsToFieldsMapping elementName){
        films_retrieving.Film filmToEdit = null;
        for (films_retrieving.Film f : data.getFilms()) {
            if (f.getTitle().equals(title)) filmToEdit = f;
        }
        boolean isAttribute = false;
        switch(elementName) {

            case title:
                filmToEdit.setTitle(newValue);
                break;
            case image:
                filmToEdit.setImage(newValue);
                isAttribute = true;
                break;
            case durationInMinutes:
                filmToEdit.setDurationInMinutes(Integer.parseInt(newValue));
                break;
            case year:
                filmToEdit.setYear(Integer.parseInt(newValue));
                isAttribute = true;
                break;
            case releaseDateInUSA:
                Date date1 = null;
                try {
                    date1=new SimpleDateFormat("yyyy-MM-dd").parse(newValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                filmToEdit.setReleaseDateInUSA(date1);
                break;
            case countries:
                for (String country : filmToEdit.getCountries()) {
                    if (country.equals(oldValue)) country = newValue;
                }
                break;
            case directors:
                for (String director : filmToEdit.getDirectors()) {
                    if (director.equals(oldValue)) director = newValue;
                }
                break;
            case cast:
                for (String actor : filmToEdit.getCast()) {
                    if (actor.equals(oldValue)) actor = newValue;
                }
                break;
            case distributedBy:
                for (String distributor : filmToEdit.getDistributedBy()) {
                    if (distributor.equals(oldValue)) distributor = newValue;
                }
                break;
            case languages:
                for (String language : filmToEdit.getLanguages()) {
                    if (language.equals(oldValue)) language = newValue;
                }
                break;
            case musicAuthor:
                for (String author : filmToEdit.getMusicAuthor()) {
                    if (author.equals(oldValue)) author = newValue;
                }
                break;
            case boxOffice:
                filmToEdit.setBoxOffice(Float.parseFloat(newValue));
                break;
        }
        if (!isAttribute)
            xmlBuilder.editElement(title, elementName.getValue(), oldValue, newValue);
        else
            xmlBuilder.editAttribute(title, elementName.getValue(), newValue);

        data.saveListToFile();
    }

    public void getHTMLwithPhotosOfFilms(){
        transformationsHelper.getHTMLwithPhotos();
    }

    public void getXMLwithDirectorsFilms(){
        transformationsHelper.getXMLwithDirectorsFilms();
    }

    public void getTXTwithCountriesWithFilms(){
        transformationsHelper.getCountriesWithFilms();
    }

    public void getXMLwithActorsWithNumberOfFilms(){
        transformationsHelper.getActorsWithNumberOfFilms();
    }

    public void getTXTwithPopularLanguages(){
        transformationsHelper.getPopularLanguages();
    }

    public void getHTMLwithYearsWithMovies(){
        transformationsHelper.getYearsWithMovies();
    }

}
