import java.io.*;

public class FileWriter {

    public MetaData data;  // instance of MetaData file
    public RandomAccessFile accessor;  // pointer ro RandomAccessFile object
    public long sum;  // local count of bytes downloaded
    public int percent;  // percentage of file downloaded
    private long fileSize;  // download file size
    private String fileName;  // name of download file

    public FileWriter(MetaData data, RandomAccessFile accessor, long fileSize, String fileName) {
        this.data = data;
        this.fileSize = fileSize;
        this.sum = data.getFinished();
        this.percent = (int)(this.data.getFinished()*101/this.fileSize);
        this.fileName = fileName;
        this.accessor = accessor;
        System.out.println("Downloaded " + (this.percent) + "%");
    }

    public synchronized void writing(long position, byte[] buffer, int length, int index) {
        try {
            accessor.seek(position);  // set offset position
            accessor.write(buffer, 0, length);
            data.updateProgress(index, length);
            data.updateFinished(length);
        } catch (IOException e) {
            System.err.println("Invalid Position");
            System.err.println("Donwload failed");
            System.exit(1);
        }

        //calculates the percentages
        int percentTemp = (int)(this.sum * 101 /this.fileSize);
        this.sum += length;
        // updates when percentage grows
        if (percentTemp > this.percent){
            data.serialize(this.fileName);
            System.out.println("Downloaded " + (percentTemp) + "%");
            this.percent = percentTemp;
        }
    }
}
