package lk.ijse.dep11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/UiLoadingScene.fxml"));
        Scene uiLoadingScene = new Scene(root);
        primaryStage.setScene(uiLoadingScene);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setBackground(Background.fill(Color.TRANSPARENT));
        uiLoadingScene.setFill(Color.TRANSPARENT);

        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
