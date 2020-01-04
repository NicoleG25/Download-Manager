import java.io.*;
import java.net.*;
import java.util.*;

public class SlaveThreader { //Blocking Queue
    String[] links;
    int num_connections;
    int fileSize;

    public SlaveThreader(String[] links, int num_connections) throws MalformedURLException {
        this.links = links;
        this.num_connections = num_connections;
        String strUrl = links[0];
        URL url = new URL(strUrl);
        this.fileSize = getFileSize(url);


    }

    public void runThreads(int num_connections) {
        // creates threads according to the connections number
        for (int i = 0; i <= num_connections; i++) {


        }

    }
    //need to connect to the server to get the size of the file
    public void connect(String[] links) throws IOException {
        int port = 80; //assuming port number
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();

        }




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
