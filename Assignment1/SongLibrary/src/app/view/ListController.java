package app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import app.controller.Song;

public class ListController {
    @FXML
    ListView<Song> listView;

    private ObservableList<Song> obsList;

    public void start() {
        obsList = FXCollections.observableArrayList(new Song("hello", "there", "test", 1324));
        listView.setItems(obsList);

    }

}
