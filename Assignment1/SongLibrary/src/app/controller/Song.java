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
	private String year;

	public Song(String name, String artist, String album, String year)
	{
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public Song()
	{
		this("", "", "", "");
	}

	public String getName() {return name;}
	public String getArtist() {return artist;}
	public String getAlbum() {return album;}
	public String getYear() {return year;}

	public boolean setFields(String name, String artist, String album, String year)
	{
		// Requirement: Leading/trailing whitespace must be removed.
		name   = name.strip();
		artist = artist.strip();
		album  = album.strip();
		year   = year.strip();

		// Requirement: name/artist can't be empty
		// Requirement: name/artist/album can't contain pipe
		// Requirement: year must be positive integer. 0?
		if (name.equals("") || name.contains("|") ||
			artist.equals("") || artist.contains("|") ||
			album.contains("|")) {

			return false;
		}

		try {
			if (Integer.parseInt(year) < 0) {return false;}
		} catch (NumberFormatException e) {
			if (!year.equals("")) {return false;}
		}

		this.name   = name;
		this.artist = artist;
		this.album  = album;
		this.year   = year;
		return true;
	}

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
