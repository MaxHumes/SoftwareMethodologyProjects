// Paul Kotys
// Max Humes

package app.controller;

import app.view.ListController;
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

        ListController listController = loader.getController();
        listController.start();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    //Save songs on application exit
    @Override
    public void stop()
    {
    	//SongManager.getInstance().SaveSongs();
    }
}