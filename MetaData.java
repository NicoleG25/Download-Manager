import java.io.*;
import java.net.*;
import java.util.*;

public class MetaData  implements Serializable {
    private static String serName = "tempMetaData_agng.ser"; // name for main serialization file
    private static String serName2 = "tempMetaDAta_ngag.ser"; // name for temp serialization file to avoid corruption
    private long[] progress; // progress in each chunk of the download, stores count of bytes downloaded.
    private long fileSize; // the full size of the download file in bytes
    public String fileName; // name of the download file
    protected String[] links; // TODO: move to main, no need to store in metadata
    protected int num_connections; // TODO: move to main, no need to store in metadata
    private int finished; // TODO: figure out wtf is this?
    public static final int ARRAY_SIZE = 1024; // size of array


    public MetaData(int num_connections, String[] links) {
        this.num_connections = num_connections;
        this.links = links;
        this.finished = 0; // TODO: UPDATE IN A FUNCTION SOMEWHERE
        this.progress = new long[ARRAY_SIZE];
        this.fileName = txtParser(links[0]);
    }

    public void updateProgress(int index, int bytes) {
        this.progress[index] += bytes;


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

    /**
     * deserialize an in instance of MetaData
     * @return
     */
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


    public synchronized int getFinished() {
        return this.finished;
    }




}



