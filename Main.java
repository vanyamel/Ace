import boggle.BoggleGame;
import boggleViews.BoggleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * The Main class for the first Assignment in CSC207, Fall 2022
 */
public class Main extends Application {
    /**
    * Main method. 
    * @param args command line arguments.
    **/
    public static void main(String[] args) {
        launch();
        BoggleGame b = new BoggleGame();
        b.giveInstructions();
        b.playGame();
    }
    public void start(Stage stage) {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 640, 480);
        scene.setFill(Color.BLUE);
        stage.setScene(scene);
        stage.show();
    }


}
