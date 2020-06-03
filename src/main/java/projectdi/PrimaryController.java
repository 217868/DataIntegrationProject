package projectdi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import projectdi.Logic.films_retrieving.Film;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.sf.saxon.s9api.SaxonApiException;
import projectdi.Logic.mainLogic.MainLogic;

public class PrimaryController implements Initializable, Controller {

    MainLogic mainLogic;

    ObservableList<Film> films = FXCollections.observableArrayList();

    @FXML
    ListView<Film> filmsListView;

    @FXML
    TextField listPathTextField;

    @FXML
    TextField movieTitleTextField;

    @FXML
    Button getMoviesButton;

    @FXML
    Button getMovieButton;

    @FXML
    Label loadingLabel;

    PrimaryController(MainLogic mainLogic) {
        this.mainLogic = mainLogic;
    }



    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary", mainLogic);
    }

    @FXML
    private void switchToSearchBy() throws IOException {
        App.setRoot("searchby", mainLogic);
    }

    @FXML
    private void getMoviesFromWiki() {
        loadingLabel.setText("Downloading data from Wikipedia. This might take a couple of minutes.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLogic.populateXMLFileFromTitlesListFile(listPathTextField.getText());
                films.setAll(mainLogic.getCurrentFilms());
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        filmsListView.setItems(films);
                        loadingLabel.setText("Data downloaded!");
                    }
                });
            }
        }).start();
    }

    @FXML
    private void getMovieFromWiki() {
        loadingLabel.setText("Downloading data from Wikipedia. This might take a couple of minutes.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLogic.addMovie(movieTitleTextField.getText());
                films.setAll(mainLogic.getCurrentFilms());
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        filmsListView.setItems(films);
                        loadingLabel.setText("Data downloaded!");
                    }
                });
            }
        }).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshList();

        filmsListView.setCellFactory(param -> new FilmCell(mainLogic, this));


    }

    public void refreshList() {
        films.setAll(mainLogic.getCurrentFilms());
        filmsListView.setItems(films);
    }
}
