package mytunes.bll;


import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.DBManager;
import mytunes.dal.SongDAO;
import mytunes.dal.PlaylistDAO;
import mytunes.dal.DBFacade;

public class LogicManager implements LogicFacade {
    private final DBFacade dbManager;

    public LogicManager() {
        dbManager = new DBManager();

        
    }

    public void Search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addSongToPlaylist() {

    }

    @Override
    public void removeSongFromPlaylist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void editSong() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeSongFromLib() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newPlaylist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editPlaylist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removePlaylist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Song> getAllSongs() {
        return dbManager.getAllSongs();
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return dbManager.getAllPlaylists();
        
    }

    @Override
    public void addSong(Song song) {
        dbManager.addSong(song);
        
    }

    @Override
    public String sec_To_Format(int sec) {
        
        
        return "str";
    }

    @Override
    public int format_To_Sec(String formatString) {
        return 1;
    }

    

}
