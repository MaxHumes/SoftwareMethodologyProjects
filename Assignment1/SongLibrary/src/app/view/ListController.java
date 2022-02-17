package app.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import app.controller.Song;
import app.controller.SongManager;

public class ListController {
    @FXML private ListView<Song> listView;
    @FXML private TextField nameTextField;
    @FXML private TextField artistTextField;
    @FXML private TextField albumTextField;
    @FXML private TextField yearTextField;
    @FXML private Button addSongButton;
    @FXML private Button editSongButton;
    @FXML private Button deleteSongButton;

    private ObservableList<Song> obsList;
    private Song currentSong;

    public void start() {
        //initialization
    	obsList = FXCollections.observableArrayList(new Song("hello", "there", "test", 1324));
        listView.setItems(obsList);
        
        //add listener for list view selected item change
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
			@Override
			public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
				if(newValue != null)
				{
					nameTextField.setText(newValue.getName());
					artistTextField.setText(newValue.getArtist());
					albumTextField.setText(newValue.getAlbum());
					yearTextField.setText(Integer.toString(newValue.getYear()));
					
					currentSong = newValue;
					addSongButton.setVisible(false);
					editSongButton.setVisible(true);
					deleteSongButton.setVisible(true);
				}
			}
        });
    }
    
    @FXML protected void handleNewButtonAction(ActionEvent event)
    {
		listView.getSelectionModel().clearSelection();
		clearFields();
		currentSong = null;
		
		editSongButton.setVisible(false);
		deleteSongButton.setVisible(false);
		addSongButton.setVisible(true);
    }
	@FXML protected void handleAddButtonAction(ActionEvent event)
	{
		SongManager.getInstance().add(new Song
				(nameTextField.getText(), 
				artistTextField.getText(),
				albumTextField.getText(),
				Integer.parseInt(yearTextField.getText()))
				);
		
		clearFields();
		listView.setItems(FXCollections.observableArrayList(SongManager.getInstance().getSongs()));
	}
	@FXML protected void handleEditButtonAction(ActionEvent event)
	{
		
	}
	@FXML protected void handleDeleteButtonAction(ActionEvent event)
	{
		
	}
	
	//HELPERS
	private void clearFields()
	{
		nameTextField.setText("");
		artistTextField.setText("");
		albumTextField.setText("");
		yearTextField.setText("");
	}
}