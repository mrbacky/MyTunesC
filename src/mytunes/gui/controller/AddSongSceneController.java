package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static javax.management.Query.value;
import mytunes.gui.model.SongModel;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import mytunes.gui.controller.PrimaryController;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddSongSceneController implements Initializable {

    @FXML
    private Button btn_chooseFile;
    @FXML
    private TextField txtField_AddSong_filePath;
    @FXML
    private Button btn_AddSong_cancelSong;
    @FXML
    private Button btn_AddSong_saveSong;
    @FXML
    private TextField txtField_AddSong_title;
    @FXML
    private TextField txtField_AddSong_artist;
    @FXML
    private ChoiceBox<String> choiseBox_AddSong_genre;
    @FXML
    private TextField txtField_AddSong_time;

    
    private SongModel songModel;
    private PrimaryController pCon;
    private ObservableList<String> observableListGenre = FXCollections.observableArrayList("Rock","Pop");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        songModel = new SongModel();
        loadGenres();
    }

    private void loadGenres() {
        //observableListGenre.removeAll(observableListGenre);
        
        String a = "Rock";
        String b = "Pop";
        String c = "Hip-Hop";
        String d = "Metal";
        String e = "Dance";
        //observableListGenre.addAll(a, b, c, d, e);
        //choiseBox_AddSong_genre.getItems().addAll(observableListGenre);
        choiseBox_AddSong_genre.setItems(observableListGenre);

    }

    @FXML
    private void handle_OpenFileChooser(ActionEvent event) throws MalformedURLException {
        //txtField_AddSong_filePath.setText("");
        //txtField_AddSong_time.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"),
                new FileChooser.ExtensionFilter("wav Files", "*.wav")
        );

        File songFile = fileChooser.showOpenDialog(null);
        if (songFile != null) {
            String songPATH = songFile.getAbsolutePath();
            txtField_AddSong_filePath.setText(songPATH);
            Media media = new Media(songFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(new Runnable() {

                @Override
                public void run() {
                    int time, hours, mins, secs;
                    Duration timeDuration = media.getDuration();
                    time = (int) (timeDuration.toSeconds());// it will cut .898956
                    //String stringTime = String.format("%02d:%02d:%02d", hours, mins, secs);

                    txtField_AddSong_time.setText(songModel.sec_To_Format(time));
                }

            });

        }
    }

    @FXML
    private void handle_AddSongToDB(ActionEvent event) throws InterruptedException, IOException {

        txtField_AddSong_title.getText();
        txtField_AddSong_artist.getText();
        txtField_AddSong_time.getText();
        choiseBox_AddSong_genre.getSelectionModel().getSelectedItem();
        txtField_AddSong_filePath.getText();

        songModel.addSong(
                txtField_AddSong_title.getText(),
                txtField_AddSong_artist.getText(),
                txtField_AddSong_time.getText(),
                choiseBox_AddSong_genre.getSelectionModel().getSelectedItem(),
                txtField_AddSong_filePath.getText()
        );
        updateLibrary();

        Stage stage;
        stage = (Stage) btn_AddSong_saveSong.getScene().getWindow();
        stage.close();
    }

    public void updateLibrary() {
        pCon.updateLibrary();
    }

    @FXML
    private void handle_CloseScene(ActionEvent event) {
        Stage stage = (Stage) btn_AddSong_cancelSong.getScene().getWindow();
        stage.close();

    }

    public void setContr(PrimaryController pCon) {
        this.pCon = pCon;
    }
}
