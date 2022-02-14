package app.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class SongLib extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Song Library");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/view/layout.fxml"));

        GridPane root = (GridPane) loader.load();
        root.setMinHeight(200);
        root.setMinWidth(400);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
