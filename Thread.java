import java.io.*;
import java.net.*;
import java.util.*;

public class Thread extends java.lang.Thread {
    protected int end; // last index of progress array the thread downloads
    protected int start; // first index of progress array the thread downloads
    protected String link; // download link for the thread to use
    protected long chunkSize;
    protected FileWriter fw;
    protected MetaData data;
    protected long fileSize;
    protected int id;



    public Thread(String link, int start, int end, long chunkSize, FileWriter fw, MetaData data, int id, long fileSize) {
        this.end = end; // last index of progress array the thread downloads
        this.start = start; // first index of progress array the thread downloads
        this.link = link; // download link for the thread to use
        this.chunkSize = chunkSize; // size of chunk
        this.fw = fw; // FileWriter instance
        this.data = data; // MetaData instance
        this.fileSize = fileSize;
        this.id = id;
        System.out.println("Start Index=" + this.start + " Ending Index=" + this.end);

    }
    // TODO: test + finish implementing
    public void run() {
        System.out.println("["+this.id+"] "+"Start downloading range ("+(this.start*chunkSize)+
                " - "+Math.min((this.end+1)*chunkSize-1, this.fileSize -1) + ") from:\n"+this.link);
        for (int i = this.start; i< this.end+1; i++) {
            long startB = i*this.chunkSize + this.data.getProgress(i);
            long endB = Math.min((i+1)*this.chunkSize - 1, this.fileSize -1);
            if (startB > endB){
                continue;
            }
            try {
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                String byteRange = "bytes=" + startB + "-" + endB;
                System.out.println(byteRange); //TODO: delete
                urlConnection.setRequestProperty("Range", byteRange); //should handle download range
                urlConnection.setConnectTimeout(5000);
                try {
                    urlConnection.connect();
                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    byte input[] = new byte[1024];
                    int byteContent;
                    while ((byteContent = in.read(input, 0, 1024)) != -1) {
                        fw.writing(startB, input, byteContent, i);
                        startB += byteContent; //TODO: should it be +1 or not? need to test
                    }
                }
                catch(SocketTimeoutException e){
                    System.err.println("SocketTimeout Exception in connect method @ Thread: "+this.id);
                    System.err.println("Download failed");
                    System.exit(1);
                }
            }
            catch (IOException e) {
                System.err.println("http URL connection error, thread: "+this.id);
                System.err.println("Download failed");
                System.exit(1);
            }
        }
        System.out.println("["+this.id+"] Finished Downloading");
    }
}
