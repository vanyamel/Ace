import boggle.BoggleGame;
import boggleViews.BoggleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
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
        Scene scene = new Scene(BoggleView.createBoard("helloalinadeem19"), 640, 480);
        stage.setScene(scene);
        stage.show();
}

}
