import java.io.*;
import java.net.*;
import java.util.*;

public class MetaData  implements Serializable {
    private long[] progress;
    private long fileSize;
    public String fileName;
    protected String[] links;
    protected int num_connections;


    public MetaData(int num_connections, String[] links) {
        this.num_connections = num_connections;
        this.links = links;


    }

    public void updateProgress(int index, int bytes) {
        this.progress[index] += bytes;


    }

    public long getFileSize() {
        return this.fileSize;
    }

    //TODO: serlialize data

    public void serialize() {

    }

}
