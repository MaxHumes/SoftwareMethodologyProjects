// Paul Kotys
// Max Humes

package app.controller;

import java.lang.StringBuilder;

/*
 * This class stores song-related data
 */

public class Song implements Comparable<Song>
{
	private String name;
	private String artist;
	private String album;
	private int year;
	
	public Song(String name, String artist, String album, int year)
	{
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public String getName() {return name;}
	public String getArtist() {return artist;}
	public String getAlbum() {return album;}
	public int getYear() {return year;}

	public int compareTo(Song other)
	{
		int comp = this.name.compareToIgnoreCase(other.getName());
		if (comp == 0) {
			return this.artist.compareToIgnoreCase(other.getArtist());
		} else {
			return comp;
		}
	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(this.name);
		builder.append(" ");
		builder.append(this.artist);
		builder.append(" ");
		builder.append(this.album);
		builder.append(" ");
		builder.append(this.year);
		builder.append(")");

		return builder.toString();
	}


}
