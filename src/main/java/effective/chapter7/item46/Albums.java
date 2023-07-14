package effective.chapter7.item46;

public class Albums {

    private Artist artist;

    private String albumName;

    private long sales;

    public Albums(Artist artist, String albumName, long sales) {
        this.artist = artist;
        this.albumName = albumName;
        this.sales = sales;
    }

    public Artist getArtist() {
        return artist;
    }

    public long getSales() {
        return sales;
    }

    public String getAlbumName() {
        return albumName;
    }
}
