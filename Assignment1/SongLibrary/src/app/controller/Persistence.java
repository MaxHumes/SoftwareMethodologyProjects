// Paul Kotys
// Max Humes

package app.controller;

/*
 * Class for managing songs added to the user's library
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Persistence {
	private static final String SAVE_PATH = "songs.txt";

	
	//Writes library songs in JSON format to path
	public static void saveSongs(ArrayList<Song> songList)
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
	public static ArrayList<Song> loadSongs()
	{
        ArrayList<Song> songList = new ArrayList<>();

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
        return songList;
	}
}
