import java.net.URL;

import controller.MainWindowController;
import dao.DAOFactory;
import dao.DAOFactory.Mode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.MainMenu;
import view.Utilities;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            {
                MainWindowController.window = primaryStage;
                URL fxmlURL = getClass().getResource("./view/MainWindow.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
                Scene scene = new Scene(fxmlLoader.<BorderPane>load());
                scene.getStylesheets().add(getClass().getResource("./view/style.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.setTitle("Business Pro Euro Simulator Deluxe Edition");
                primaryStage.setWidth(1000);
                primaryStage.setHeight(700);
                primaryStage.getIcons().add(new Image("assets/icons/icon.png"));
                primaryStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (Utilities.getUserSelection("Use the UI ?\n1. Yes\n2. No", 2) == 2)
            MainMenu.start();
        else
            launch(args);
    }
}
