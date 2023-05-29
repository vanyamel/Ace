import boggle.tts.Speaker;
import boggleViews.BoggleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Main method.
     *
     * @param args command line arguments.
     **/
    public static void main(String[] args) {
        Speaker speaker = Speaker.getInstance();
        speaker.init();
        launch();
        speaker.deinit();
    }

    public void start(Stage stage) {
        BoggleView b = new BoggleView();
        Scene scene = new Scene(b.createBoard(), 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

}
