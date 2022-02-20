// Paul Kotys
// Max Humes

package app.view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import app.controller.Song;
import app.controller.Persistence;

public class ListController {
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
	@FXML private Button cancelButton;

	@FXML private ListView<Song> listView;
	private ObservableList<Song> obsList = FXCollections.observableArrayList(Persistence.loadSongs());

	private void handleListViewClicked(){
		Song selectedSong = listView.getSelectionModel().getSelectedItem();
		if (selectedSong == null) {
			clearDisplays();
			return;
		}

		nameDisplay.setText(selectedSong.getName());
		artistDisplay.setText(selectedSong.getArtist());
		albumDisplay.setText(selectedSong.getAlbum());
		yearDisplay.setText(selectedSong.getYear());

		moveSongButton.setVisible(true);
		deleteSongButton.setVisible(true);

	}

    public void start() {

        listView.setItems(obsList);
        
        //add listener for list view selected item change
		listView.setOnMouseClicked(
				(msEvent) -> handleListViewClicked()
		);

		// Select first item in list if we loaded songs.
		listView.getSelectionModel().selectFirst();
		handleListViewClicked();
	}

	public void stop() {
		Persistence.saveSongs(new ArrayList<Song>(obsList));
	}
    
    @FXML
	protected void handleMoveButtonAction(ActionEvent event)
    {
    	if(listView.getSelectionModel().selectedItemProperty() != null)
    	{
        	nameTextField.setText(listView.getSelectionModel().getSelectedItem().getName());
        	artistTextField.setText(listView.getSelectionModel().getSelectedItem().getArtist());
        	albumTextField.setText(listView.getSelectionModel().getSelectedItem().getAlbum());
        	yearTextField.setText(listView.getSelectionModel().getSelectedItem().getYear());

			deleteSongButton.setVisible(false);
			addButton.setVisible(false);
			editButton.setVisible(true);
			cancelButton.setVisible(true);
    	}
    }

	@FXML
	protected void handleDeleteButtonAction(ActionEvent event)
	{
		int deleteAt = listView.getSelectionModel().getSelectedIndex();
		obsList.remove(listView.getSelectionModel().getSelectedItem());
		int newSize = obsList.size();

		if (newSize == 0) {
			handleListViewClicked();
		} else if (deleteAt == newSize) {
			listView.getSelectionModel().select(deleteAt-1);
			handleListViewClicked();
		} else {
			listView.getSelectionModel().select(deleteAt);
			handleListViewClicked();
		}

	}
    
	@FXML
	protected void handleAddButtonAction(ActionEvent event)
	{
		// Create a new Song object and validate fields
		Song aSong = new Song();
		if (!aSong.setFields(nameTextField.getText(),
							 artistTextField.getText(),
							 albumTextField.getText(),
							 yearTextField.getText())) {
			return;
		}

		// Check if song already in obsList
		if (obsList.contains(aSong)) {
			return;
		}

		obsList.add(aSong);
		Collections.sort(obsList);

		// Select song automatically in obsList.
		listView.getSelectionModel().select(aSong);
		handleListViewClicked();
		clearTextFields();

	}
	@FXML
	protected void handleEditButtonAction(ActionEvent event) {
		Song oldSong = listView.getSelectionModel().getSelectedItem();
		obsList.remove(oldSong);

		Song newSong = new Song();
		if (!newSong.setFields(nameTextField.getText(),
				artistTextField.getText(),
				albumTextField.getText(),
				yearTextField.getText()) || obsList.contains(newSong)) {
			obsList.add(oldSong);
			Collections.sort(obsList);
			listView.getSelectionModel().select(oldSong);

		} else {
			obsList.add(newSong);
			Collections.sort(obsList);
			listView.getSelectionModel().select(newSong);
		}

		handleListViewClicked();
		clearTextFields();
		editButton.setVisible(false);
		cancelButton.setVisible(false);
		deleteSongButton.setVisible(true);
		addButton.setVisible(true);
	}

	@FXML
	protected void handleCancelButtonAction(ActionEvent event){
		clearTextFields();
		editButton.setVisible(false);
		cancelButton.setVisible(false);

		addButton.setVisible(true);
		deleteSongButton.setVisible(true);

	}


	//HELPERS
	private void clearDisplays()
	{
		nameDisplay.setText("");
		artistDisplay.setText("");
		albumDisplay.setText("");
		yearDisplay.setText("");
	}

	private void clearTextFields()
	{
		nameTextField.setText("");
		artistTextField.setText("");
		albumTextField.setText("");
		yearTextField.setText("");
	}

}
