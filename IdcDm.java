import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IdcDm {

    //TODO: check if we need to delete old metada if we restart with a different download

    public static final int CHUNKS = 16;
    public static void main(String[] args){

        String[] linksList = getLinkArray(args[0]);
        String fileName = getFileName(linksList[0]);
        long fileSize = getFileSize(linksList[0]);
        MetaData data = genMetaData(fileName);
        int concDownload = getConcCount(fileSize, args);
        startDownload(data, linksList, concDownload, fileSize, fileName);
    }




    /**
     * The function checks if the String is a path to a file, if not by default it is set as a URL
     * @param linkInput - arg[0], should be path to file or a URL
     * @return - returns a list of Strings representing URL
     */
    public static String[] getLinkArray(String linkInput){
        //TODO: test
        if (new File(linkInput).exists()) {
            return listFromFile(linkInput);
        }
        else {
            String[] list = {linkInput};
            return list;
        }

    }

    /**
     * gets a link in order to parse it to the name of the file
     * @param link - path to file
     * @return - returns a string that is the filename
     */
    public static String getFileName(String link) {
        //TODO : Test
        String fileName = "";
        if (link.contains("\\")) { //if we are dealing with blackslashes
            String newLink = link.replace('\\', '/');
            fileName = newLink.substring(newLink.lastIndexOf('/')+1, newLink.length());
        }
        else { //if we are dealing with forward slashes
            fileName = link.substring(link.lastIndexOf('/') + 1, link.length());
        }
        return fileName;
    }

    /**
     * gets the file size from the URL using a HEAD request.
     * @param link - String link to file
     * @return - returns the integer that represents the size of the file.
     */

    //using a HEAD request to get the file size
    public static long getFileSize(String link) {
        URLConnection conn = null;
        try {
            URL url = new URL(link);
            conn = url.openConnection();
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }

    /**
     * logic to limit number of concurrent downloads
     * @param fileSize - the size of the file
     * @param args - array of arguments, to extract user requested number of threads if was passed
     * @return
     */
    public static int getConcCount(long fileSize, String[] args){
        // TODO: implement
        if (args.length == 2){
            int conc = Integer.parseInt(args[1]);
            if (fileSize < 1024*1024*2){
                return 2;
            }
            else return conc;
        }
        return 1;
    }

    /**
     * checks if metadata file exists, if yes - checks if downloaded file matches - if yes load and continue
     * else generates a new metadata file
     * @param name
     * @return
     */
    public static MetaData genMetaData(String name){
        //TODO: test
        File f = new File(name+".tmp_we11fer.ser");
        MetaData data;
        if(f.exists()) {
            data = MetaData.deserialize(name);
        }
        else {
            data = new MetaData(); //insert arguments when finished constrcution Metadata class
        }
        return data;
    }

    public static void startDownload(MetaData data, String[] linksArray, int concDownload, long fileSize, String name){
        // creating new RandomAccessFile to write into
        RandomAccessFile accessor = null;
        try {
            accessor = new RandomAccessFile(name, "rw");
        }
        catch (FileNotFoundException e){
            System.err.println("error generating random access file");
            System.err.println("Download failed");
            System.exit(1);
        }


        Thread[] threads = new Thread[concDownload];
        FileWriter fw = new FileWriter(data, accessor, fileSize, name);
        int indexJump = CHUNKS/concDownload;
        int start = 0;
        int end = indexJump-1;
        long chunkSize = fileSize/CHUNKS;
        if (fileSize%CHUNKS != 0){
            chunkSize++;
        }

        for (int i = 0; i < concDownload ; i++){
            String link = linksArray[i%linksArray.length]; // gets link by modulo of i over links array length
            if (CHUNKS%concDownload > i){
                end++;
            }
            if (end > CHUNKS -1){
                end = CHUNKS -1;
            }
            threads[i] = new Thread(link, start, end, chunkSize, fw, data, i, fileSize);
            threads[i].start();
            start = end+1;
            end = end+indexJump;
        }
        for (int i = 0; i < concDownload ; i++){
            try{
                threads[i].join();
            } catch(InterruptedException e){
                System.err.println("Thread interrupt exception");
            }
        }
        System.out.println("Download succeeded");
        MetaData.deleteFile(name);
    }

    /**
     * reads a file, returns a string array where each line from the file is an entry
     * @param filePath - path to file
     * @return - returns a string array of all the lines in the file
     */
    public static String[] listFromFile(String filePath){
        List<String> lines = new ArrayList<>();
        String line;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while((line = br.readLine()) != null){
                lines.add(line);
            }
        }
        catch(IOException e){
            System.out.println("Error reading from file");
            System.exit(0);
        }
        String[] links = lines.toArray(new String[]{});
        return links;
    }
}
