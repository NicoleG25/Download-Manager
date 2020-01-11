import java.io.*;
import java.net.*;
import java.util.*;

public class SlaveThreader { //Blocking Queue

    protected int fileSize;
    //protected File file;
    protected RandomAccessFile accessor;
    protected String[] links;
    protected int num_connections;


    public SlaveThreader(MetaData data) throws MalformedURLException {
        this.links = data.links;
        this.num_connections = data.num_connections;
        String strUrl = links[0];
        URL url = new URL(strUrl);
        this.fileSize = getFileSize(url);
        File file = new File(data.fileName);

        try {
            this.accessor = new RandomAccessFile(file, "rw");

            FileWriter fileWriter = new FileWriter(data, accessor);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
        }

    }
    // TODO: test
    public void runThreads(int num_connections) {
        // creates threads according to the connections number
        int rangeSplit = this.fileSize / num_connections;
        int start = 0;
        double bufferSize = fileSize / 100;
        Thread[] threads = new Thread[num_connections];
        for (int i = 0; i <= num_connections; i++) {
            //Thread thread = new Thread(this.links[i % this.links.length], start, rangeSplit,bufferSize);
            threads[i] = new Thread(this.links[i % this.links.length], start, rangeSplit,bufferSize);
            start = rangeSplit + 1;
            rangeSplit += rangeSplit; //might miss bits? or maybe have extra?


        }
        for (int i = 0; i <= num_connections; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.err.println("Failed to join threads");

            }

        }
        try {
            accessor.close();
        }
        catch (IOException e) {
            System.err.println("Failed to close file");
        }



    }




 //TODO: create a method that manages minimal threshold for connections + max connections





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
