import java.io.*;
import java.net.*;
import java.util.*;

public class MetaData  implements Serializable {
    private static String serName = "temp23f3sersdf.ser";
    private long[] progress;
    private long fileSize;
    public String fileName;
    protected String[] links;
    protected int num_connections;


    public MetaData(int num_connections, String[] links) {
        this.num_connections = num_connections;
        this.links = links;

        this.fileName = txtParser(links[0]);


    }

    public void updateProgress(int index, int bytes) {
        this.progress[index] += bytes;


    }

    /**
     * gets a link in order to parse it to the name of the file
     * @param link - path to file
     * @return - returns a string that is the filename
     */
    //TODO : Test that we get the filename without .Extension

    public String txtParser(String link) {
        String fileName = "";
        if (link.contains("\\")) { //if we are dealing with blackslashes
            String newLink = link.replace('\\', '/');

            fileName = newLink.substring(newLink.lastIndexOf('/')+1, newLink.length());
        }
        else { //if we are dealing with forward slashes
            fileName = link.substring(link.lastIndexOf('/') + 1, link.length());
        }

        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        return fileNameWithoutExtension;
        //return fileName;
    }


    public long getFileSize() {
        return this.fileSize;
    }

    public long setFileSize() {
        this.fileSize = 123;
        return this.fileSize;
    }

    //TODO: serlialize data + test

    public void serialize() {


        // Serialization
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(serName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this);

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        } catch (IOException ex) {
            System.out.println("IOException is caught in serialize");
        }

    }

    //TODO: test deserialization

    public static MetaData deserialize() {
        // Deserialization
        MetaData data = null;
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(serName);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            data = (MetaData) in.readObject();
            System.out.println(data.getFileSize());

            in.close();
            file.close();


            System.out.println("Object has been deserialized ");
        } catch (IOException ex) {
            System.out.println("IOException is caught in deserialize");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
        return data;
    }
    /**
     * Deletes the file with the name @param fileName.
     * @param fileName - path to file
     */

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();

    }



}



