package projectdi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projectdi.Logic.exceptions.XMLNotFoundException;
import projectdi.Logic.mainLogic.MainLogic;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private MainLogic mainLogic;

    @Override
    public void start(Stage stage) throws IOException, XMLNotFoundException {
        mainLogic = new MainLogic();
        FXMLLoader loader = loadFXML("primary");
        PrimaryController primaryController = new PrimaryController(mainLogic);
        loader.setController(primaryController);
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml, MainLogic mainLogic) throws IOException {
        FXMLLoader loader = loadFXML(fxml);
        PrimaryController pc = new PrimaryController(mainLogic);
        SecondaryController sc = new SecondaryController(mainLogic);
        SearchByController sb = new SearchByController(mainLogic);
        if (fxml.equals("primary")) loader.setController(pc);
        if (fxml.equals("secondary")) loader.setController(sc);
        if (fxml.equals("searchby")) loader.setController(sb);
        scene.setRoot(loader.load());
    }

    private static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }


}