// Paul Kotys
// Max Humes

package app.controller;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.StringBuilder;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/*
 * Class for managing songs added to the user's library
 */
public class SongManager {
	private static final String SAVE_PATH = "songs.txt";
	private ArrayList<Song> songList;
	
	//private SongManager constructor only for use by this class
	private SongManager()
	{
		this.songList = new ArrayList<Song>();
	}
	
	//Singleton Pattern
	private static SongManager _instance;
	public static SongManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new SongManager();
			_instance.loadSongs();
		}
		return _instance;
	}

	// Creates a Song object, and then adds it to the ArrayList.
	public boolean addSong(String name, String artist, String album, int year)
	{
		// Try to create song; checks for invalid arguments
		Song aSong = new Song();
		if (!aSong.setFields(name, artist, album, year)) {return false;}

		// If the song is already in the List, we can't add it.
		// contains() calls the equals() method implemented in Song.
		if (songList.contains(aSong)) return false;

		// Add song to ArrayList.
		songList.add(aSong);

		// Sorts the songList according to name (ignore case), author (ignore case)
		// See compareTo() in Song for comparison algorithm.
		Collections.sort(songList);

		return true;
	}

	public boolean editSong(Song oldSong, String newName, String newArtist, String newAlbum, int newYear)
	{
		// Songs are immutable; must remove oldSong even if edited.
		songList.remove(oldSong);

		// Tries to create a new song and add it to the ArrayList.
		if (!this.addSong(newName, newArtist, newAlbum, newYear)) {
			songList.add(oldSong); // Add old song back
			return false;
		} else {
			return true;
		}
	}
	
	
	public boolean delete(Song song)
	{
		return songList.remove(song);
	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (Song aSong : songList) {
			builder.append(aSong.toString());
			builder.append("\n");
		}

		return builder.toString();
	}

	// Returns a new ArrayList object containing all the songs.
	// Note that songs are immutable, so this is ok.
	public ArrayList<Song> getSongs()
	{
		return new ArrayList<>(songList);
	}

	
	//Writes library songs in JSON format to path
	public void SaveSongs()
	{
		try
		{
			FileWriter writer = new FileWriter(SAVE_PATH, false);
			Gson gson = new Gson();
			
			String songJson = gson.toJson(songList);
			writer.write(songJson);
			
			writer.close();
		}
		catch(IOException e)
		{
			//TODO: Add popup message for couldn't save songs
		}
	}
	//Loads list of songs from saved JSON string
	private void loadSongs()
	{
		try
		{
			Gson gson = new Gson();
			
			String songJson = Files.readString(Paths.get(SAVE_PATH));
			
			Type listType = new TypeToken<ArrayList<Song>>() {}.getType();
			songList = gson.fromJson(songJson, listType);
		}
		catch(IOException e)
		{
			//TODO: Add popup message for couldn't load songs
		}
	}
}
