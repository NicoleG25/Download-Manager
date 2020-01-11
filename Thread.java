import java.io.*;
import java.net.*;
import java.util.*;

public class Thread extends java.lang.Thread {
    protected int maxRange;
    protected int start;
    protected String link;
    protected long ID;
    protected double buffer; // determines the number of bytes to update the percentage and send
    protected FileWriter fw;
    protected MetaData data;



    public Thread(String link,int start,int end, double buffer, FileWriter fw, MetaData data) {
        this.maxRange = end; //max byte to download
        this.start = start;
        this.link = link;
        this.ID = this.getId();
        this.buffer = buffer; // need to implement sending a flag back to SlaveThreader
        this.fw = fw;
        this.data = data;


    }
    // TODO: test + finish implementing
    public void connect() {

        while (this.data.getFinished() < MetaData.CHUNKSIZE) {

            //TODO by tolika : finish implementing le logic
            try {
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                String byteRange = "bytes=" + this.start + "-" + this.maxRange;
                urlConnection.setRequestProperty("Range", byteRange); //should handle download range
                urlConnection.setConnectTimeout(500);


                try {
                    urlConnection.connect();
                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    byte input[] = new byte[1024];
                    int byteContent;
                    long position = 0; //TODO : implement by tolik
                    int index = 0; //TODO : implement by tolik
                    while ((byteContent = in.read(input, 0, 1024)) != -1) {
                        fw.writing(position, input, index, byteContent);

                    }


                }
                catch(SocketTimeoutException e){
                    System.err.println("SocketTimeout Exception in connect method @ Thread");

                }

            }
            catch (IOException e) {

            }

        }


    }

}
