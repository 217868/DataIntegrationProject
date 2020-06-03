package projectdi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import projectdi.Logic.exceptions.MovieNotFoundException;
import projectdi.Logic.exceptions.XMLNotFoundException;
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

    private boolean isSuccesfull = true;

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
                try {
                    mainLogic.populateXMLFileFromTitlesListFile(listPathTextField.getText());
                } catch (Exception e) {
                    isSuccesfull = false;
                }
                films.setAll(mainLogic.getCurrentFilms());
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        filmsListView.setItems(films);
                        if (isSuccesfull)
                            loadingLabel.setText("Data downloaded!");
                        else
                        {
                            isSuccesfull = true;
                            loadingLabel.setText("Data downloaded! - One or more films could not be found in Wikipedia");
                        }
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
                try {
                    mainLogic.addMovie(movieTitleTextField.getText());
                } catch (MovieNotFoundException e) {
                    isSuccesfull = false;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XMLNotFoundException e) {
                    e.printStackTrace();
                }
                films.setAll(mainLogic.getCurrentFilms());
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        filmsListView.setItems(films);
                        if (isSuccesfull)
                            loadingLabel.setText("Data downloaded!");
                        else {
                            isSuccesfull = true;
                            loadingLabel.setText("Selected movie could not be found");
                        }
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
