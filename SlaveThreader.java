import java.io.*;
import java.net.*;
import java.util.*;

public class SlaveThreader { //Blocking Queue
    protected String[] links;
    protected int num_connections;
    protected int fileSize;

    public SlaveThreader(String[] links, int num_connections) throws MalformedURLException {
        this.links = links;
        this.num_connections = num_connections;
        String strUrl = links[0];
        URL url = new URL(strUrl);
        this.fileSize = getFileSize(url);


    }
    // TODO: test
    public void runThreads(int num_connections) {
        // creates threads according to the connections number
        int rangeSplit = this.fileSize / num_connections;
        int start = 0;
        for (int i = 0; i <= num_connections; i++) {
            Thread thread = new Thread(this.links[i], start, rangeSplit);
            start = rangeSplit + 1;
            rangeSplit += rangeSplit; //might miss bits? or maybe have extra?


        }

    }

    public void connect(String[] links) throws IOException {
        int port = 80; //assuming port number
//        while (true) {
//            Socket socket = new Socket()
//
//        }




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
