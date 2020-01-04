import java.io.*;
import java.net.*;
import java.util.*;

public class SlaveThreader { //Blocking Queue
    protected String[] links;
    protected int num_connections;
    protected int fileSize;
    protected int percent;
    protected boolean startedDownload = false;


    public SlaveThreader(String[] links, int num_connections) throws MalformedURLException {
        this.links = links;
        this.num_connections = num_connections;
        String strUrl = links[0];
        URL url = new URL(strUrl);
        this.fileSize = getFileSize(url);
        this.percent = 0;


    }
    // TODO: test
    public void runThreads(int num_connections) {
        // creates threads according to the connections number
        int rangeSplit = this.fileSize / num_connections;
        int start = 0;
        double bufferSize = fileSize / 100;
        for (int i = 0; i <= num_connections; i++) {
            Thread thread = new Thread(this.links[i % this.links.length], start, rangeSplit,bufferSize);
            start = rangeSplit + 1;
            rangeSplit += rangeSplit; //might miss bits? or maybe have extra?


        }

    }


 //TODO: create a method that manages minimal threshold for connections + max connections


    // TODO: test + maybe finish implementing..? perhaps move to FileWriter
    public synchronized void percentageCounter() {
        if (this.startedDownload = false) { // takes care of the 0%
            this.startedDownload = true;
            System.out.println("Downloaded " + this.percent + "%");
        }
        System.out.println("Downloaded " + this.percent + "%");
    }


    //using a HEAD request to get the file size
    private static int getFileSize(URL url) throws MalformedURLException {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }

}
