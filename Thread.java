import java.io.*;
import java.net.*;
import java.util.*;

public class Thread extends java.lang.Thread {
    protected int maxRange;
    protected int start;
    protected String link;
    protected long ID;
    protected double buffer; // determines the number of bytes to update the percentage and send


    public Thread(String link,int start,int end, double buffer) {
        this.maxRange = end; //max byte to download
        this.start = start;
        this.link = link;
        this.ID = this.getId();
        this.buffer = buffer;

    }
    // TODO: test + finish implementing errors
    public void connect() {


        try {
            URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String byteRange = "bytes=" + this.start + "-" + this.maxRange;
            urlConnection.setRequestProperty("Range", byteRange); //should handle download range
            urlConnection.setConnectTimeout(500);


            try {
                urlConnection.connect();

            }
            catch(SocketTimeoutException e){

            }

        }
        catch (IOException e) {

        }




    }

}
