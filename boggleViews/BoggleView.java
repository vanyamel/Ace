package boggleViews;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.TetrisModel;

public class BoggleView {
// attempting to put together a proper looking grid, trial and error
    public static BorderPane createExample(){
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("-fx-text-fill: #e8e6e3");
        Label top = createLabel("label", "-fx-text-fill: #e8e6e3");
        top.setPrefHeight(100);
        borderPane.setTop(top);
        Label left = createLabel("label1", "-fx-text-fill: #e8e6e3");
        left.setPrefWidth(150);
        left.setMaxHeight(200);
        BorderPane.setAlignment(left, Pos.BOTTOM_LEFT);
        borderPane.setLeft(left);
        Label center = createLabel("label2", "-fx-text-fill: #e8e6e3");
        center.setMinWidth(250);
        center.setMaxWidth(450);
        center.setMaxHeight(200);
        BorderPane.setAlignment(center, Pos.TOP_CENTER);
        borderPane.setCenter(center);
        Label right = createLabel("label3", "-fx-text-fill: #e8e6e3");
        right.setPrefWidth(75);
        borderPane.setRight(right);
        Label bottom = createLabel("label4", "-fx-text-fill: #e8e6e3");
        borderPane.setBottom(bottom);

        return borderPane;
    }
    private static Label createLabel(String text, String styleClass){
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        BorderPane.setMargin(label, new Insets(5));
        return label;
    }

}
