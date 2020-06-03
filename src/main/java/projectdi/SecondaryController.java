package projectdi;

import java.io.File;
import java.io.IOException;

import helpers.Const;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import projectdi.Logic.exceptions.TransformationFailedException;
import projectdi.Logic.exceptions.ValidationFailedException;
import projectdi.Logic.exceptions.XMLNotFoundException;
import projectdi.Logic.mainLogic.MainLogic;
import java.awt.Desktop;

public class SecondaryController {

    MainLogic mainLogic;

    SecondaryController(MainLogic mainLogic) {
        this.mainLogic = mainLogic;
    }

    @FXML
    private Label validLabel;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary", mainLogic);
    }

    @FXML
    private void switchToSearchBy() throws IOException {
        App.setRoot("searchby", mainLogic);
    }

    @FXML
    private void generateHtmlWithPhotos() throws IOException, TransformationFailedException, XMLNotFoundException {
        mainLogic.getHTMLwithPhotosOfFilms();;
        String url = Const.HTML_PHOTOS_OUTPUT.getValue();
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @FXML
    private void generateXmlWithDirectors() throws IOException, TransformationFailedException, XMLNotFoundException {
        mainLogic.getXMLwithDirectorsFilms();
        String url = Const.XML_DIRECTORS_OUTPUT.getValue();
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @FXML
    private void generateTxtWithCountries() throws IOException, TransformationFailedException, XMLNotFoundException {
        mainLogic.getTXTwithCountriesWithFilms();
        String url = Const.TXT_COUNTRIES_OUTPUT.getValue();
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @FXML
    private void generateXmlWithActors() throws IOException, TransformationFailedException, XMLNotFoundException {
        mainLogic.getXMLwithActorsWithNumberOfFilms();
        String url = Const.XML_ACTORS_OUTPUT.getValue();
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @FXML
    private void generateTxtWithLanguages() throws IOException, TransformationFailedException, XMLNotFoundException {
        mainLogic.getTXTwithPopularLanguages();
        String url = Const.TXT_POPULAR_LANGUAGES_OUTPUT.getValue();
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @FXML
    private void generateHtmlWithYears() throws IOException, TransformationFailedException, XMLNotFoundException {
        mainLogic.getHTMLwithYearsWithMovies();
        String url = Const.HTML_MOVIES_FROM_YEAR_OUTPUT.getValue();
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @FXML
    private void validate() {
        try {
            if(mainLogic.validate()) {
                validLabel.setText("VALID");
            }
            else {
                validLabel.setText("INVALID");
            }
        } catch (ValidationFailedException e) {
            validLabel.setText("INVALID");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLNotFoundException e) {
            e.printStackTrace();
        }
    }
}