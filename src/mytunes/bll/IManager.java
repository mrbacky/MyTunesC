/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytunes.bll;

import java.util.List;
import mytunes.be.Song;


public interface IManager {
    
    //  Songs on Playlist methods
    public void addSongToPlaylist();
    public void removeSongFromPlaylist();
    
    
    //  Library
    public void newSong();
    public void editSong();
    public void removeSongFromLib();
    
    //
    public void newPlaylist();
    public void editPlaylist();
    public void removePlaylist();
    
    public List<Song> getAllSongs();
    
    
    
    
    
    
}
