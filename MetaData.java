import java.io.*;

public class MetaData  implements Serializable {
    private long[] progress; // progress in each chunk of the download, stores count of bytes downloaded.
    private int finished;  // total bytes downloaded

    public MetaData() {
        this.finished = 0;
        this.progress = new long[IdcDm.CHUNKS];
    }

    /**
     * update count of total bytes downloaded
     * @param bytes bytes to add
     */
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

    /**
     * serialize metadata file into given name
     * @param name
     */
    public void serialize(String name) {
        // some random names to avoid collision with existing files unrelated to download manager
        String nameMain = name+".tmp_adfg43a.ser";
        String nameSec  = name+".tmp_we11fer.ser";
        try {
            FileOutputStream file = new FileOutputStream(nameMain);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
            file.close();
            // replace temp file after successful serialization
            File filetTemp = new File(nameMain);
            filetTemp.renameTo(new File(nameSec));
        } catch (IOException ex) {
            System.err.println("IOException is caught in serialize");
            System.err.println("Download failed");
            System.exit(1);
        }
    }

    /**
     * deserialize metadata file into MetaData instance
     * @param name name of file to deserialize
     * @return returns a MetaData instance
     */
    public static MetaData deserialize(String name) {
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
     * delete the remaining metadata after successful download
     * @param name name of metadata file to delete
     */
    public static void deleteFile(String name) {
        String nameMain = name+".tmp_adfg43a.ser";
        String nameSec  = name+".tmp_we11fer.ser";
        File file = new File(nameMain);
        File file2 = new File(nameSec);
        file.delete();
        file2.delete();
    }

    /**
     * getter method for total count of bytes downloaded
     * @return
     */
    public synchronized int getFinished() {
        return this.finished;
    }

}
