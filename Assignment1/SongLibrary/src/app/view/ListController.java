// Paul Kotys
// Max Humes

package app.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import app.controller.Song;
import app.controller.Persistence;
import javafx.stage.Stage;

public class ListController {
	@FXML
	private Text nameDisplay;
	@FXML
	private Text artistDisplay;
	@FXML
	private Text albumDisplay;
	@FXML
	private Text yearDisplay;

	@FXML
	private TextField nameTextField;
	@FXML
	private TextField artistTextField;
	@FXML
	private TextField albumTextField;
	@FXML
	private TextField yearTextField;

	@FXML
	private Button moveSongButton;
	@FXML
	private Button deleteSongButton;
	@FXML
	private Button addButton;
	@FXML
	private Button editButton;
	@FXML
	private Button cancelButton;

	@FXML
	private ListView<Song> listView;
	private ObservableList<Song> obsList = FXCollections.observableArrayList(Persistence.loadSongs());
	private Stage primaryStage;

	private void handleListViewClicked() {
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

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
	protected void handleMoveButtonAction(ActionEvent event) {
		if (listView.getSelectionModel().selectedItemProperty() != null) {
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

	private boolean confirmDelete() {
		Song aSong = listView.getSelectionModel().getSelectedItem();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Confirm Delete");
		alert.setHeaderText("Are you sure you want to delete this song?");
		alert.setContentText(aSong.toString());
		Optional<ButtonType> result = alert.showAndWait();

		return (result.isPresent() && result.get() == ButtonType.OK);
	}

	@FXML
	protected void handleDeleteButtonAction(ActionEvent event) {
		boolean response = confirmDelete();
		if (!response) {
			return;
		}

		int deleteAt = listView.getSelectionModel().getSelectedIndex();
		obsList.remove(listView.getSelectionModel().getSelectedItem());
		int newSize = obsList.size();

		if (newSize == 0) {
			handleListViewClicked();
		} else if (deleteAt == newSize) {
			listView.getSelectionModel().select(deleteAt - 1);
			handleListViewClicked();
		} else {
			listView.getSelectionModel().select(deleteAt);
			handleListViewClicked();
		}

	}

	private void invalidArguments() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("Song Not Valid");
		alert.setHeaderText("The values you entered for the song are not valid");

		String songInfo = "name: " + nameTextField.getText() +
				"\nartist: " + artistTextField.getText() +
				"\nalbum: " + albumTextField.getText() +
				"\nyear: " + yearTextField.getText();

		alert.setContentText(songInfo);
		alert.showAndWait();

	}

	private void songExist() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("Song Already in Library");
		alert.setHeaderText("The song you are trying to add is already in the library");

		String songInfo = "name: " + nameTextField.getText() +
				"\nartist: " + artistTextField.getText() +
				"\nalbum: " + albumTextField.getText() +
				"\nyear: " + yearTextField.getText();

		alert.setContentText(songInfo);
		alert.showAndWait();
	}

	private boolean confirmAdd()
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Confirm Add");
		alert.setHeaderText("Are you sure you want to add this song?");

		String songInfo = "name: " + nameTextField.getText() +
				"\nartist: " + artistTextField.getText() +
				"\nalbum: " + albumTextField.getText() +
				"\nyear: " + yearTextField.getText();

		alert.setContentText(songInfo);
		Optional<ButtonType> result = alert.showAndWait();

		return (result.isPresent() && result.get() == ButtonType.OK);

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
			invalidArguments();
			return;
		}

		// Check if song already in obsList
		if (obsList.contains(aSong)) {
			songExist();
			return;
		}

		boolean response = confirmAdd();
		if (!response) {
			return;
		}

		obsList.add(aSong);
		Collections.sort(obsList);

		// Select song automatically in obsList.
		listView.getSelectionModel().select(aSong);
		handleListViewClicked();
		clearTextFields();

	}

	private boolean confirmEdit(Song oldSong, Song newSong)
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Confirm Edit");
		alert.setHeaderText("Are you sure you want to make these edits?");

		String songInfo = "name: " + oldSong.getName() + " -> " + newSong.getName() +
				          "\nartist: " + oldSong.getArtist() + " -> " + newSong.getArtist() +
				          "\nalbum: " + oldSong.getAlbum() + " -> " + newSong.getAlbum() +
				          "\nyear: " + oldSong.getYear() + " -> " + newSong.getYear();

		alert.setContentText(songInfo);
		Optional<ButtonType> result = alert.showAndWait();
		return (result.isPresent() && result.get() == ButtonType.OK);

	}

	@FXML
	protected void handleEditButtonAction(ActionEvent event) {
		Song oldSong = listView.getSelectionModel().getSelectedItem();
		obsList.remove(oldSong);

		Song newSong = new Song();
		if (!newSong.setFields(nameTextField.getText(),
				artistTextField.getText(),
				albumTextField.getText(),
				yearTextField.getText())) {
			invalidArguments();
			obsList.add(oldSong);
			Collections.sort(obsList);
			listView.getSelectionModel().select(oldSong);

		} else if (obsList.contains(newSong)) {
			songExist();
			obsList.add(oldSong);
			Collections.sort(obsList);
			listView.getSelectionModel().select(oldSong);

		} else {
			boolean response = confirmEdit(oldSong, newSong);
			if (response) {
				obsList.add(newSong);
				Collections.sort(obsList);
				listView.getSelectionModel().select(newSong);
			} else {
				obsList.add(oldSong);
				Collections.sort(obsList);
				listView.getSelectionModel().select(oldSong);
			}
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
