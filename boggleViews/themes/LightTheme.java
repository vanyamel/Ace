package boggleViews.themes;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LightTheme implements Theme {

    @Override
    public void apply(Text text, Text textBottom, BorderPane pane, HBox box, Button nightmode) {
        text.setFill(Color.BLACK);
        textBottom.setFill(Color.BLACK);
        pane.setStyle("-fx-background-color: #C4A484;");
        box.setStyle("-fx-background-color: #ADD8E6;");
        nightmode.setText("NightMode");
    }
}
