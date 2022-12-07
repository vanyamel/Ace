package boggleViews.themes;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class NightTheme implements Theme{

    @Override
    public void apply(Text text, Text textBottom, BorderPane pane, HBox box, Button nightmode) {
        text.setFill(Paint.valueOf("#ceffc6"));
        textBottom.setFill(Paint.valueOf("#ceffc6"));
        pane.setStyle("-fx-background-color: #000000;");
        box.setStyle("-fx-background-color: #5A5A5A;");
        nightmode.setText("LightMode");
    }
}
