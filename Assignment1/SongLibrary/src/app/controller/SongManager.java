// Paul Kotys
// Max Humes

package app.controller;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/*
 * Class for managing songs added to the user's library
 * @author MaxHumes
 */
public class SongManager {
	private static final String SAVE_PATH = "songs.txt";
	private List<Song> songList;
	
	//private app.controller.SongManager constructor only for use by this class
	private SongManager()
	{
		this.songList = loadSongs();
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
	public void Add(Song song)
	{
		songList.add(song);

	}
	public void Delete(Song song)
	{
		songList.remove(song);
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
