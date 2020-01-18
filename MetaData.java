import java.io.*;
import java.net.*;
import java.util.*;

public class MetaData  implements Serializable {
    private long[] progress; // progress in each chunk of the download, stores count of bytes downloaded.
    private int finished; // TODO: figure out wtf is this?

    public MetaData() {
        this.finished = 0; // TODO: UPDATE IN A FUNCTION SOMEWHERE
        this.progress = new long[IdcDm.CHUNKS];  // progress array of constant size
    }


    public void updateFinished(int bytes){
        this.finished += bytes;
    }
    /**
     * updates downloaded bytes count for a chunk given by index
     * @param index index in progress array
     * @param bytes how many bytes to add at array[index]
     */
    public void updateProgress(int index, int bytes) {
        this.progress[index] += bytes;
    }

    /**
     * returns downloaded bytes for a certain chunk
     * @param index index of progress array to return the value of
     * @return
     */
    public long getProgress(int index){
        return this.progress[index];
    }

    //TODO: serlialize data + test
    public void serialize(String name) {
        // Serialization TODO check that we get backup
        String nameMain = name+".tmp_adfg43a.ser";
        String nameSec  = name+".tmp_we11fer.ser";
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(nameMain);
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Method for serialization of object
            out.writeObject(this);
            out.close();
            file.close();
            File filetTemp = new File(nameMain);
            filetTemp.renameTo(new File(nameSec));
        } catch (IOException ex) {
            System.out.println("IOException is caught in serialize");
        }
    }

    //TODO: test deserialization
    /**
     * deserialize an in instance of MetaData
     * @return
     */
    public static MetaData deserialize(String name) {
        // Deserialization
        String nameMain = name+".tmp_we11fer.ser";
        MetaData data = null;
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(nameMain);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            data = (MetaData) in.readObject();
            in.close();
            file.close();
        } catch (IOException ex) {
            System.err.println("IOException is caught in deserialize");
            System.err.println("Download failed");
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException is caught");
            System.err.println("Download failed");
            System.exit(1);
        }
        return data;
    }

    /**
     * Deletes the two temp files
     *
     */
    public static void deleteFile(String name) {
        String nameMain = name+".tmp_adfg43a.ser";
        String nameSec  = name+".tmp_we11fer.ser";
        File file = new File(nameMain);
        File file2 = new File(nameSec);
        file.delete();
        file2.delete();
    }

    public synchronized int getFinished() {
        return this.finished;
    }

}



