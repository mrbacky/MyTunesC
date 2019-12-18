package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Playlist;
import mytunes.be.Song;
import java.sql.PreparedStatement;

/**
 * This DAO class can perform CRUD operations on the database playlist table.
 * The class is connected with the SongOnPlaylistDAO as the database tables of
 * the two classes are connected.
 *
 * @author annem
 */
public class PlaylistDAO {

    private ConnectDAO connectDAO;

    /**
     * Constructor, which creates the connection with the database and the DAO
     * for SongOnPlaylist.
     */
    public PlaylistDAO() {
        connectDAO = new ConnectDAO();
    }

    /**
     * Creates and adds a new playlist to the database.
     *
     * @param playlistToCreate The playlist to create.
     * @return The newly created playlist.
     */
    public Playlist createPlaylist(Playlist playlistToCreate) {
        try ( Connection con = connectDAO.getConnection()) {
            String sql = "INSERT INTO playlist(name, time, nrOfSongs) VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, playlistToCreate.getName());
            pstmt.setInt(2, playlistToCreate.getTime());
            pstmt.setInt(3, playlistToCreate.getNumberOfSongs());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return playlistToCreate;
    }
    /**
     * this fetchAllPlaylist get all the values from playlist in our data base by an sql statment and
     * order it by id ASC and add them to an hashmap.
     * @param id
     * @param name
     * @throws SqlseverException
     * @return allPlaylists
     */
    private HashMap<Integer, Playlist> fetchAllPlaylists() {
        //List<Playlist> allPlaylists = new ArrayList<>();
        HashMap<Integer, Playlist> allPlaylists = new HashMap<Integer, Playlist>();

        try ( Connection con = connectDAO.getConnection()) {
            String sql = "SELECT * FROM playlist ORDER BY id ASC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                allPlaylists.put(id, new Playlist(id, name));
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allPlaylists;
    }
    
    /**
     * Updates the name of the playlist in the database.
     *
     * @param playlist The playlist to be updated.
     * @param editedName The edited name of the playlist.
     * @return The updated playlist.
     */
    public Playlist updatePlaylist(Playlist playlist, String editedName) {
        try (//Get a connection to the database.
                 Connection con = connectDAO.getConnection()) {
            //Create a prepared statement.
            String sql = "UPDATE playlist SET name = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setString(1, editedName);
            pstmt.setInt(2, playlist.getId());
            //Execute SQL query.
            pstmt.executeUpdate();
            playlist.setName(editedName);
            return playlist;

        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Deletes a playlist from the database. Uses DELETE CASCADE to delete all
     * the songs on the deleted playlist (songOnPlaylist).
     *
     * @param playlistToDelete The playlist to be deleted.
     * @throws SQLException
     */
    public void deletePlaylist(Playlist playlistToDelete) throws SQLException {
        //When the playlist is deleted, the corresponding data in the songOnPlaylist is also deleted.
        try ( Connection con = connectDAO.getConnection()) {
            String sql = "DELETE FROM playlist WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, playlistToDelete.getId());
            pstmt.execute();
        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
    *
    * @this fetchAllSongsInPlaylist dose a sql statment with a join to get the valuses of the songOnplaylist
    * table and song table an add it to a hash map and convert it to an arraylist.
    * @param id
    * @param playlist
    * @param time
    * @param sontPath
    * @param title
    * @param 
    * @return unhashedplaylist
    */
    public List<Playlist> fetchAllSongsInPlaylists() throws SQLException {
        //List<Playlist> playlists = new ArrayList<>();
        HashMap<Integer, Playlist> playlists = fetchAllPlaylists();
        try ( Connection con = connectDAO.getConnection()) {
            String sql = "select songonplaylist.songid, song.id, song.title, song.time, song.songpath, songOnPlaylist.[order],songOnPlaylist.playlistid\n"
                    + "from songonplaylist left join song on songonplaylist.songid = song.id";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                int playlistid = rs.getInt("playlistid");
                int time = rs.getInt("time");
                String songPath = rs.getString("songPath");
                String title = rs.getString("title");
                int order = rs.getInt("order");

                playlists.get(playlistid).addSong(new Song(id, title, "artist", time, songPath, "genra"));
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Playlist> unhashedPlaylists = new ArrayList<>();
        for (Map.Entry<Integer, Playlist> entry : playlists.entrySet()) {
            unhashedPlaylists.add(entry.getValue());
        }
        return unhashedPlaylists;
    }
}
