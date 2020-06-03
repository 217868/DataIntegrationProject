package projectdi;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.sf.saxon.s9api.SaxonApiException;
import projectdi.Logic.films_retrieving.Film;
import projectdi.Logic.mainLogic.MainLogic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static projectdi.FilmData.convertStringToList;


public class SearchByController implements Initializable, Controller {

    MainLogic mainLogic;

    ObservableList<Film> films = FXCollections.observableArrayList();

    @FXML
    ListView<Film> filmsListView;

    @FXML
    TextField listPathTextField;

    @FXML
    Button getMoviesButton;

    @FXML
    Label loadingLabel;

    @FXML
    TextField searchByTitleTextField;

    @FXML
    TextField searchByDirectorTextField;

    @FXML
    TextField searchByActorsTextField;

    @FXML
    TextField searchByDuration1TextField;

    @FXML
    TextField searchByDuration2TextField;

    @FXML
    TextField searchByCountryTextField;

    SearchByController(MainLogic mainLogic) {
        this.mainLogic = mainLogic;
    }



    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary", mainLogic);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary", mainLogic);
    }

    @FXML
    public void searchByTitle() throws SaxonApiException {
        films.setAll(mainLogic.getxPathHelper().searchMovieByTitle(searchByTitleTextField.getText()));
        filmsListView.setItems(films);
    }

    @FXML
    public void searchByDirector() throws SaxonApiException {
        films.setAll(mainLogic.getxPathHelper().searchMovieByDirector(searchByDirectorTextField.getText()));
        filmsListView.setItems(films);
    }

    @FXML
    public void searchByActors() throws SaxonApiException {
        List<String> list = FilmData.convertStringToList(searchByActorsTextField.getText());
        films.setAll(mainLogic.getxPathHelper().searchMovieByActors(list));
        filmsListView.setItems(films);
    }

    @FXML
    public void searchByDuration() throws SaxonApiException {
        films.setAll(mainLogic.getxPathHelper().searchMovieByDuration(Integer.parseInt(searchByDuration1TextField.getText()), Integer.parseInt(searchByDuration2TextField.getText())));
        filmsListView.setItems(films);
    }

    @FXML
    public void searchByCountry() throws SaxonApiException {
        films.setAll(mainLogic.getxPathHelper().searchMovieCountry(searchByCountryTextField.getText()));
        filmsListView.setItems(films);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        films.addAll(mainLogic.getCurrentFilms());

        filmsListView.setCellFactory(param -> new FilmCell(mainLogic, this));
        filmsListView.setItems(films);

    }

    public void refreshList() {
        films.addAll(mainLogic.getCurrentFilms());
        filmsListView.setItems(films);
    }
}


