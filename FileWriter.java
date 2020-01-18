import java.io.*;
import java.net.*;
import java.util.*;


public class FileWriter {

    public MetaData data;
    public RandomAccessFile accessor;
    public long sum;
    public int percent;
    private long fileSize;
    private String fileName;

    public FileWriter(MetaData data, RandomAccessFile accessor, long fileSize, String fileName) {
        this.data = data;
        this.fileSize = fileSize;
        this.sum = data.getFinished();
        this.percent = (int)(this.data.getFinished()*100/this.fileSize);
        this.fileName = fileName;
        this.accessor = accessor;
        System.out.println(fileSize);

        System.out.println("Downloaded " + (this.percent) + "%");
    }

    public synchronized void writing(long position, byte[] buffer, int length, int index) {

        try {
            accessor.seek(position);
            accessor.write(buffer, 0, length);
            data.updateProgress(index, length);
            data.updateFinished(length);
        } catch (IOException e) {
            System.err.println("Invalid Position");
        }
        // TODO : test percentages
        //calculates the percentages
        int percentTemp = (int)(this.sum * 101 /this.fileSize);
        this.sum += length;
        if (percentTemp > this.percent){
            data.serialize(this.fileName);
            System.out.println("Downloaded " + (percentTemp) + "%");
            this.percent = percentTemp;
        }
    }
}
