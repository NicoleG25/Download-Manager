import java.io.*;
import java.net.*;
import java.util.*;


public class FileWriter {

    public MetaData data;
    public RandomAccessFile accessor;
    public long sum;
    public int percent;

    public FileWriter(MetaData data, RandomAccessFile accessor) {
        this.data = data;

        this.sum = 0;
        this.percent = 0;
        System.out.println("Downloaded " + (this.percent) + "%");

            this.accessor = accessor;
        try {
            this.accessor.setLength(data.getFileSize());
        } catch (IOException e) {
            System.err.println("Cant set file size");
        }


    }
    /**
     * gets a link in order to parse it to the name of the file
     * @param link - path to file
     * @return - returns a string that is the filename
     */
    //TODO : Test that we get the filename with correct extension

    public String txtParser(String link) {
        String fileName = "";
        if (link.contains("\\")) { //if we are dealing with blackslashes
           String newLink = link.replace('\\', '/');

          fileName = newLink.substring(newLink.lastIndexOf('/')+1, newLink.length());
        }
        else { //if we are dealing with forward slashes
            fileName = link.substring(link.lastIndexOf('/') + 1, link.length());
        }

        //String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        //return fileNameWithoutExtension;
        return fileName;
    }



    //TODO: implement percentage prints HERE



    // USE RandomAccessFile when writing.

    //TODO: figure out how to put pieces of the file together
    // https://stackoverflow.com/questions/2243073/java-multiple-connection-downloading/2243731#2243731
    //or https://stackoverflow.com/questions/4532462/how-to-merge-binary-files-using-java

    public synchronized void writing(long position, byte[] buffer, int length, int index) {  //index needs to be computed and sent to by Thread
        int percentTemp = 0;
        try {
            accessor.seek(position);
            accessor.write(buffer, 0, length);
        } catch (IOException e) {
            System.err.println("Invalid Position");
        }
        // TODO : test
        //calculates the percentages
        percentTemp = (int)(this.sum * 100 /data.getFileSize());
        this.sum += length;
        if (percentTemp > this.percent){
            System.out.println("Downloaded " + (percentTemp) + "%");
            this.percent = percentTemp;
        }

        data.updateProgress(index, length);




    }

//        // TODO: test + maybe finish implementing..? perhaps move to FileWriter
//        public synchronized void percentageCounter() {
//            if (this.startedDownload = false) { // takes care of the 0%
//                this.startedDownload = true;
//                System.out.println("Downloaded " + this.percent + "%");
//            }
//            System.out.println("Downloaded " + this.percent + "%");
//        }
//




}
