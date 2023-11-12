package lk.ijse.dep11.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.io.IOException;

public class UiLoadingSceneController {
    public AnchorPane root;
    public Label lblTextDisplay;
    public Point2D point;

    public void initialize (){
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(500), actionEvent -> {
            lblTextDisplay.setText("Loading...");
        });
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1000),actionEvent -> {
            lblTextDisplay.setText("Loading.");
        });
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(1500),actionEvent -> {
            lblTextDisplay.setText("Loading..");
        });
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(2000), actionEvent -> {
            lblTextDisplay.setText("Loading...");
        });
        KeyFrame keyFrame5 = new KeyFrame(Duration.millis(2500),actionEvent -> {
            lblTextDisplay.setText("Loading.");
        });
        KeyFrame keyFrame6 = new KeyFrame(Duration.millis(3000),actionEvent -> {
            lblTextDisplay.setText("Loading..");
        });
        KeyFrame keyFrame7 = new KeyFrame(Duration.millis(3500),actionEvent -> {
            try {
                AnchorPane rootUi  = FXMLLoader.load(getClass().getResource("/view/MainUiScene.fxml"));
                Scene scene = new Scene(rootUi);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("MediaFusion App V 1.0.0");
                stage.setResizable(false);
                stage.show();
                stage.centerOnScreen();
                Stage primaryStage = (Stage)root.getScene().getWindow();
                primaryStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame4,keyFrame5,keyFrame6,keyFrame7);
        timeline.play();
    }
}
