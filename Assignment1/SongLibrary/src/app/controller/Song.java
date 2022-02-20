// Paul Kotys
// Max Humes

package app.controller;

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
		int comp = name.compareToIgnoreCase(other.getName());
		if (comp != 0) {
			return comp;
		} else {
			return artist.compareToIgnoreCase(other.getArtist());
		}
	}

	public boolean equals(Object other)
	{
		if (other == this) {return true;}

		if (!(other instanceof Song)) {return false;}

		Song otherSong = (Song) other;

		return (name.compareToIgnoreCase(otherSong.name) == 0) &&
				(artist.compareToIgnoreCase(otherSong.artist) == 0);
	}

	public String toString()
	{
		return name + " " + artist;
	}


}
