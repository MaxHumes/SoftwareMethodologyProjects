package app.controller;

import java.util.ArrayList;
import java.util.Collections;

public class SongTest {
    public static void main(String[] args) {

        Song aSong = new Song("Hello", "there","general",123);
        Song bSong = new Song("Z","Y","X", 1);
        Song cSong = new Song("hello", "zebra","", 0);

        //System.out.println(aSong.compareTo(bSong));
        //System.out.println(bSong.compareTo(aSong));
        //System.out.println(aSong.compareTo(cSong));

        ArrayList<Song> songList = new ArrayList<Song>();
        songList.add(bSong);
        songList.add(aSong);
        songList.add(cSong);

        System.out.println(songList.toString());

        Collections.sort(songList);

        System.out.println(songList.toString());


    }
}
