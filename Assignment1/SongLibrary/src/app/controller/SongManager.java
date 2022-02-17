// Paul Kotys
// Max Humes

package app.controller;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
	private List<Song> songList;
	
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
		}
		return _instance;
	}
	
	//add and remove songs from list
	// returns false if song is already in the SongList.
	public boolean add(Song song)
	{
		// If the song is already in the List, we can't add it.
		// contains() calls the equals() method implemented in Song.
		if (songList.contains(song)) return false;

		// Add song to ArrayList.
		songList.add(song);

		// Sorts the songList according to name (ignore case), author (ignore case)
		// See compareTo() in Song for comparison algorithm.
		Collections.sort(songList);

		return true;
	}
	// Created a Song object, and then calls this.add to add it to the arrayList.
	public boolean addSong(String name, String artist, String album, int year)
	{
		// Requirement: name/artist/album can't contain pipe
		// Requirement: year must be positive integer. 0?
		if (name.contains("|") || artist.contains("|") || album.contains("|") || (year < 0)) {
			return false;
		}

		Song aSong = new Song(name, artist, album, year);
		return this.add(aSong);
	}

	public boolean edit(Song oldSong, Song newSong) 
	{
		if(!delete(oldSong))
		{
			return false;
		}
		
		return add(newSong);
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
		return new ArrayList<Song>(songList);
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
	private List<Song> loadSongs()
	{
		List<Song> songs = new ArrayList<Song>();
		
		try
		{
			Gson gson = new Gson();
			
			String songJson = Files.readString(Paths.get(SAVE_PATH));
			
			Type listType = new TypeToken<List<Song>>() {}.getType();
			songs = gson.fromJson(songJson, listType);
		}
		catch(IOException e)
		{
			//TODO: Add popup message for couldn't load songs
		}
		return songs;
	}
}
