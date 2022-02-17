package app.controller;

import java.util.ArrayList;
import java.util.Collections;

public class SongTest {
    public static void main(String[] args) {

        Song aSong = new Song("Hello", "there","general",123);
        Song bSong = new Song("Z","Y","X", 1);
        Song cSong = new Song("hello", "zebra","", 0);

        Song dSong = new Song("Hello", "THERE","general",123);

        //System.out.println(aSong.compareTo(bSong));
        //System.out.println(bSong.compareTo(aSong));
        //System.out.println(aSong.compareTo(cSong));

        /*
        ArrayList<Song> songList = new ArrayList<Song>();
        songList.add(bSong);
        songList.add(aSong);
        songList.add(cSong);

        System.out.println(songList.remove(new Song("test","test","test",0)));

        System.out.println(songList.toString());

        Collections.sort(songList);

        System.out.println(songList.toString());

        System.out.println(aSong.equals(cSong));
        System.out.println(aSong.equals(dSong));*/

        SongManager SM = SongManager.getInstance();

        SM.add(bSong);
        System.out.println(SM);
        SM.add(aSong);
        System.out.println(SM);
        SM.add(cSong);
        SM.add(dSong);
        System.out.println(SM);

        for (Song theSong : SM.getSongs()) {
            System.out.println(theSong);
        }



    }
}
