package app.view;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import app.controller.Song;
import app.controller.SongManager;

public class ListController {
    @FXML private ListView<Song> listView;
    
    @FXML private Text nameDisplay;
    @FXML private Text artistDisplay;
    @FXML private Text albumDisplay;
    @FXML private Text yearDisplay;
    
    @FXML private TextField nameTextField;
    @FXML private TextField artistTextField;
    @FXML private TextField albumTextField;
    @FXML private TextField yearTextField;
    
    @FXML private Button moveSongButton;
    @FXML private Button deleteSongButton;
    @FXML private Button addButton;
    @FXML private Button editButton;

    private ObservableList<Song> obsList;

    public void start() {
        //initialization
    	obsList = FXCollections.observableArrayList(SongManager.getInstance().getSongs());
        listView.setItems(obsList);
        
        //add listener for list view selected item change
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
			@Override
			public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
				addButton.setVisible(true);
				editButton.setVisible(false);
				
				//change displays if selecting new song
				if(newValue != null)
				{
					nameDisplay.setText(newValue.getName());
					artistDisplay.setText(newValue.getArtist());
					albumDisplay.setText(newValue.getAlbum());
					yearDisplay.setText(Integer.toString(newValue.getYear()));
					
					moveSongButton.setVisible(true);
					deleteSongButton.setVisible(true);
					
					clearFields();
				}
				//change display if song was deleted
				else
				{
					moveSongButton.setVisible(false);
					deleteSongButton.setVisible(false);
				}
			}
        });
    }
    
    
    @FXML protected void handleNewButtonAction(ActionEvent event)
    {
		listView.getSelectionModel().clearSelection();
		clearDisplays();
		clearFields();
		
		moveSongButton.setVisible(false);
		deleteSongButton.setVisible(false);
    }
    
    @FXML protected void handleMoveButtonAction(ActionEvent event)
    {
    	if(listView.getSelectionModel().selectedItemProperty() != null)
    	{
        	nameTextField.setText(listView.getSelectionModel().getSelectedItem().getName());
        	artistTextField.setText(listView.getSelectionModel().getSelectedItem().getArtist());
        	albumTextField.setText(listView.getSelectionModel().getSelectedItem().getAlbum());
        	yearTextField.setText(Integer.toString(listView.getSelectionModel().getSelectedItem().getYear()));
    	}
    }
	@FXML protected void handleDeleteButtonAction(ActionEvent event)
	{
		SongManager.getInstance().delete(listView.getSelectionModel().getSelectedItem());
		clearDisplays();
		
		editButton.setVisible(false);
		addButton.setVisible(true);
		refreshView();
	}
    
	@FXML protected void handleAddButtonAction(ActionEvent event)
	{		
		SongManager.getInstance().addSong(nameTextField.getText(), artistTextField.getText(),albumTextField.getText(), Integer.parseInt(yearTextField.getText()));
		clearFields();
		refreshView();
	}
	@FXML protected void handleEditButtonAction(ActionEvent event)
	{
		SongManager.getInstance().editSong(listView.getSelectionModel().getSelectedItem(), nameTextField.getText(), artistTextField.getText(),albumTextField.getText(), Integer.parseInt(yearTextField.getText()));
		refreshView();
	}

	
	//HELPERS
	private void clearDisplays()
	{
		nameDisplay.setText("");
		artistDisplay.setText("");
		albumDisplay.setText("");
		yearDisplay.setText("");
	}
	private void clearFields()
	{
		nameTextField.setText("");
		artistTextField.setText("");
		albumTextField.setText("");
		yearTextField.setText("");
	}
	private void refreshView() 
	{
		listView.setItems(FXCollections.observableArrayList(SongManager.getInstance().getSongs()));
	}
}
