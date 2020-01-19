import java.io.*;
import java.net.*;

public class Thread extends java.lang.Thread {
    protected int end;  // last index of progress array the thread downloads
    protected int start;  // first index of progress array the thread downloads
    protected String link;  // download link for the thread to use
    protected long chunkSize;  // size of chunk
    protected FileWriter fw;  // FileWriter instance
    protected MetaData data;  // MetaData instance
    protected long fileSize;  // size of download file
    protected int id;  // id of thread


    public Thread(String link, int start, int end, long chunkSize, FileWriter fw, MetaData data, int id, long fileSize) {
        this.end = end;
        this.start = start;
        this.link = link;
        this.chunkSize = chunkSize;
        this.fw = fw;
        this.data = data;
        this.fileSize = fileSize;
        this.id = id;
    }

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
                String byteRange = "bytes=" + startB + "-" + endB;  // range request format
                urlConnection.setRequestProperty("Range", byteRange); //should handle download range
                urlConnection.setReadTimeout(5000);  // time out in case of disconnect from server
                try {
                    urlConnection.connect();
                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    byte input[] = new byte[1024];  // buffer to download into
                    int byteContent;  // how many bytes were filled on each read
                    while ((byteContent = in.read(input, 0, 1024)) != -1) {
                        fw.writing(startB, input, byteContent, i);
                        startB += byteContent;  // update seek position
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
