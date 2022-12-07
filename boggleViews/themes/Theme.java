package boggleViews.themes;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public interface Theme {
    void apply(Text text, Text textBottom, BorderPane pane, HBox box, Button nightmode);
}
