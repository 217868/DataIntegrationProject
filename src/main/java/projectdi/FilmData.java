package projectdi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import projectdi.Logic.exceptions.XMLNotFoundException;
import projectdi.Logic.films_retrieving.Film;
import projectdi.Logic.mainLogic.MainLogic;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FilmData {

    @FXML
    private HBox hBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Label directorsLabel;
    @FXML
    private Label releaseDateLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label countriesLabel;
    @FXML
    private Label castLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private Label distributedByLabel;
    @FXML
    private Label languagesLabel;
    @FXML
    private Label boxOfficeLabel;
    @FXML
    private Label musicLabel;

    @FXML
    private TextField titleLabelEditTextField;

    @FXML
    private TextField directorsLabelEditTextField;


    @FXML
    private TextField releaseDateLabelEditTextField;


    @FXML
    private TextField yearLabelEditTextField;


    @FXML
    private TextField countriesLabelEditTextField;


    @FXML
    private TextField castLabelEditTextField;


    @FXML
    private TextField durationLabelEditTextField;


    @FXML
    private TextField distributedByLabelEditTextField;


    @FXML
    private TextField languagesLabelEditTextField;


    @FXML
    private TextField boxOfficeLabelEditTextField;


    @FXML
    private TextField musicLabelEditTextField;


    @FXML
    private ImageView posterImageView;

    private MainLogic mainLogic;
    Controller context;
    private Film film;

    public FilmData(MainLogic mainLogic, Controller context)
    {
        this.context = context;
        this.mainLogic = mainLogic;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("filmCell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Film film)
    {
        this.film = film;
        titleLabel.setText(film.getTitle());
        directorsLabel.setText(convertTableToString(film.getDirectors()));



        new Thread(() -> {
            Image img = new Image(film.getImage());
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    posterImageView.setImage(img);
                }
            });
        }).start();




        releaseDateLabel.setText(new SimpleDateFormat("yyyy-MM-dd").format(film.getReleaseDateInUSA()));
        yearLabel.setText(film.getYear() + "");
        countriesLabel.setText(convertTableToString(film.getCountries()));
        castLabel.setText(convertTableToString(film.getCast()));
        durationLabel.setText(film.getDurationInMinutes() + " minutes");
        distributedByLabel.setText(convertTableToString(film.getDistributedBy()));
        languagesLabel.setText(convertTableToString(film.getLanguages()));
        boxOfficeLabel.setText("$" + film.getBoxOffice() + " million");
        musicLabel.setText(convertTableToString(film.getMusicAuthor()));
    }

    public HBox getBox()
    {
        return hBox;
    }

    @FXML
    public void deleteMovie() throws IOException, XMLNotFoundException {
        mainLogic.deleteFilm(film.getTitle());
        context.refreshList();
    }

    @FXML
    public void onEditTitle() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(titleLabel.isManaged()) {
            activateEdit(titleLabel, titleLabelEditTextField);
        } else {
            deactivateEdit(titleLabel, titleLabelEditTextField);
            filmCopy.setTitle(titleLabelEditTextField.getText());
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }



    @FXML
    public void onEditDirectors() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(directorsLabel.isManaged()) {
            activateEdit(directorsLabel, directorsLabelEditTextField );
        } else {
            deactivateEdit(directorsLabel, directorsLabelEditTextField);
            filmCopy.setDirectors(convertStringToList(directorsLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditReleaseDate() throws ParseException, IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(releaseDateLabel.isManaged()) {
            activateEdit(releaseDateLabel, releaseDateLabelEditTextField );
        } else {
            deactivateEdit(releaseDateLabel, releaseDateLabelEditTextField);
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateLabelEditTextField.getText());

            filmCopy.setReleaseDateInUSA(date1);
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditYear() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(yearLabel.isManaged()) {
            activateEdit(yearLabel, yearLabelEditTextField );
        } else {
            deactivateEdit(yearLabel, yearLabelEditTextField);
            filmCopy.setYear(Integer.parseInt(yearLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditCountries() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(countriesLabel.isManaged()) {
            activateEdit(countriesLabel, countriesLabelEditTextField );
        } else {
            deactivateEdit(countriesLabel, countriesLabelEditTextField);
            filmCopy.setCountries(convertStringToList(countriesLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditCast() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(castLabel.isManaged()) {
            activateEdit(castLabel, castLabelEditTextField );
        } else {
            deactivateEdit(castLabel, castLabelEditTextField);
            filmCopy.setCast(convertStringToList(castLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditDuration() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(durationLabel.isManaged()) {
            activateEdit(durationLabel, durationLabelEditTextField );
        } else {
            deactivateEdit(durationLabel, durationLabelEditTextField);
            filmCopy.setDurationInMinutes(Integer.parseInt(durationLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditDistributors() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(distributedByLabel.isManaged()) {
            activateEdit(distributedByLabel, distributedByLabelEditTextField );
        } else {
            deactivateEdit(distributedByLabel, distributedByLabelEditTextField);
            filmCopy.setDistributedBy(convertStringToList(distributedByLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditLanguages() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(languagesLabel.isManaged()) {
            activateEdit(languagesLabel, languagesLabelEditTextField );
        } else {
            deactivateEdit(languagesLabel, languagesLabelEditTextField);
            filmCopy.setLanguages(convertStringToList(languagesLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditBoxOffice() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(boxOfficeLabel.isManaged()) {
            activateEdit(boxOfficeLabel, boxOfficeLabelEditTextField );
        } else {
            deactivateEdit(boxOfficeLabel, boxOfficeLabelEditTextField);
            filmCopy.setBoxOffice(Float.parseFloat(boxOfficeLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }


    @FXML
    public void onEditMusic() throws IOException, XMLNotFoundException {
        Film filmCopy = new Film(film);
        if(musicLabel.isManaged()) {
            activateEdit(musicLabel, musicLabelEditTextField );
        } else {
            deactivateEdit(musicLabel, musicLabelEditTextField);
            filmCopy.setMusicAuthor(convertStringToList(musicLabelEditTextField.getText()));
            mainLogic.editFilm(film.getTitle(), filmCopy);
            context.refreshList();
        }
    }

    private void activateEdit(Label label, TextField editText) {
        editText.setText(label.getText());
        editText.setManaged(true);
        editText.setVisible(true);

        label.setManaged(false);
        label.setVisible(false);
    }

    private void deactivateEdit(Label label, TextField editText) {
        label.setManaged(true);
        label.setVisible(true);

        editText.setManaged(false);
        editText.setVisible(false);
    }


    private String convertTableToString(List<String> table) {
        return table.toString().substring(1, table.toString().length() - 1);
    }

    public static List<String> convertStringToList(String string) {
        return Arrays.asList(string.split(","));
    }


}
