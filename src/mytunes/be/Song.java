package mytunes.be;

public class Song {

    private String title;
    private String artist;
    private int time;
    private String path;
    private String genre;

    
    public Song(String title, String artist, int time, String path, String genre) {
        this.title = title;
        this.artist = artist;
        this.time = time;
        this.path = path;
        this.genre = genre;
        
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getTime() {
        return time;
    }

    public String getPath() {
        return path;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    

    

}