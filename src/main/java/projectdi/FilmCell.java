package projectdi;

import projectdi.Logic.films_retrieving.Film;
import javafx.scene.control.ListCell;
import projectdi.Logic.mainLogic.MainLogic;

public class FilmCell extends ListCell<Film> {

    private MainLogic mainLogic;
    private Controller context;

    public FilmCell(MainLogic mainLogic, Controller context) {
        this.mainLogic = mainLogic;
        this.context = context;
    }

    @Override
    public void updateItem(Film film, boolean empty) {
        super.updateItem(film, empty);
        if (film != null) {
            FilmData data = new FilmData(mainLogic, context);
            data.setInfo(film);
            setGraphic(data.getBox());
        }
    }
}
