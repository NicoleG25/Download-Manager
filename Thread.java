import java.io.*;
import java.net.*;
import java.util.*;

public class Thread extends java.lang.Thread {
    protected int maxRange;
    protected int start;
    protected String link;
    protected long ID;

    public Thread(String link,int start,int end) {
        this.maxRange = end; //max byte to download
        this.start = start;
        this.link = link;
        this.ID = this.getId();

    }
    // TODO: test
    public void connect() throws IOException {
        URL url = new URL(link);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String byteRange = "bytes=" + this.start + "-" + this.maxRange;
        urlConnection.setRequestProperty("Range", byteRange); //should handle download range
        urlConnection.connect();
        System.out.println("[" + this.ID + "] " + "Start downloading range (" + byteRange +") from:\n" + this.link);

    }

}
