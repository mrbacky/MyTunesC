package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.PlaylistModel;

public class PlaylistSceneController implements Initializable {

    @FXML
    private TextField txtField_name;
    @FXML
    private Button btn_confirm;
    @FXML
    private Button btn_cancel;
        
    private boolean edit;
    private Playlist playlistToEdit;
    private PlaylistModel playlistModel;
    private PrimaryController pCon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playlistModel = new PlaylistModel();
    }    

    public void setContr(PrimaryController pCon) {
        this.pCon = pCon;
    }
     
    @FXML
    private void handle_savePlaylist(ActionEvent event) {
        if (!edit) {
            String name = txtField_name.getText().trim();
            playlistModel.createPlaylist(0, name);
        } else {
            playlistModel.updatePlaylist(playlistToEdit, txtField_name.getText());
        }

        updatePlaylists();

        Stage stage;
        stage = (Stage) btn_confirm.getScene().getWindow();
        stage.close();
    }
    
    private void updatePlaylists() {
        pCon.refreshPlaylists();
    }

    public void editMode(Playlist playlist) {
        edit = true;
        playlistToEdit = playlist;
        
        //sets the existing info of the selected playlist.
        txtField_name.setText(playlistToEdit.getName());
    }
    @FXML
    private void handle_closeScene(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }    
}