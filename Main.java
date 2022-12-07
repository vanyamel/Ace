
import boggle.BoggleGame;
import boggle.tts.Speaker;
import boggleViews.BoggleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class for the first Assignment in CSC207, Fall 2022
 */
public class gtiMain extends Application {
    /**
     * Main method.
     *
     * @param args command line arguments.
     **/
    public static void main(String[] args) {
        launch();
        Speaker speaker = Speaker.getInstance();
        speaker.init();
        BoggleGame b = new BoggleGame();
        b.giveInstructions();
        b.playGame();
        speaker.deinit();
    }

    public void start(Stage stage) {
        BoggleView b = new BoggleView();
        Scene scene = new Scene(b.createBoard(), 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

}
