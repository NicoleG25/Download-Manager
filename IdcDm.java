import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class IdcDm {

    public static int CHUNKS = 0;  // number of chunks to split the download file into
    public static void main(String[] args){
        String[] linksList = getLinkArray(args[0]);  // set an array of links from first argument
        String fileName = getFileName(linksList[0]);  // get the name of the file were downloading
        long fileSize = getFileSize(linksList[0]);  // size of the file were downloading
        int concDownload = getConcCount(fileSize, args);  // needs to be called before meta, CHUNKS will be defined
        MetaData data = genMetaData(fileName);  // setup MetaData object, includes a chunk array and downloaded bytes
        startDownload(data, linksList, concDownload, fileSize, fileName);
    }

    /**
     * The function checks if the String is a path to a file, if not by default it is set as a URL
     * @param linkInput - arg[0], should be path to file or a URL
     * @return - returns a list of Strings representing URL
     */
    public static String[] getLinkArray(String linkInput){
        if (new File(linkInput).exists()) {  // check if first arg is a local path
            return listFromFile(linkInput);
        }
        else {
            String[] list = {linkInput};  // we got a ling, define an array with this single link
            return list;
        }
    }

    /**
     * parses the download file name out of a download link
     * @param link - URL link to download file
     * @return - returns a string that is the filename
     */
    public static String getFileName(String link) {
        String fileName = "";
        if (link.contains("\\")) {  // in case provided URL/path uses backslashes for some peculiar reason
            String newLink = link.replace('\\', '/');
            fileName = newLink.substring(newLink.lastIndexOf('/')+1, newLink.length());
        }
        else {  // normal use of slashes
            fileName = link.substring(link.lastIndexOf('/') + 1, link.length());
        }
        return fileName;
    }

    /**
     * gets the file size from the URL using a HEAD request.
     * @param link - URL link to download file
     * @return - returns the integer that represents the size of the file.
     */
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
            System.err.print("Error extracting file size from provided link :"+link+"\n Download failed");
            System.exit(1);
        }
        finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
        return 0;
    }

    /**
     * logic to limit number of concurrent downloads
     * @param fileSize - the size of the file
     * @param args - array of arguments, to extract user requested number of threads if was passed
     * @return
     */
    public static int getConcCount(long fileSize, String[] args){
        CHUNKS = (int)fileSize/(1024*1024); // split file into chunks of 1 MB
        if (fileSize%(1024*1024) != 0){  // in case the file is not divisible by 1MB ned to account for remainder
            CHUNKS++;
        }
        if (args.length == 2){
            int conc = Integer.parseInt(args[1]);
            if (fileSize < 1024*1024){
                return 1;
            }
            else return Math.min(conc,CHUNKS);
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
        File f = new File(name+".tmp_we11fer.ser");
        MetaData data;
        if(f.exists()) {  // check if were resuming a download
            data = MetaData.deserialize(name);
        }
        else {
            data = new MetaData();  // generate new metadata
        }
        return data;
    }

    public static void startDownload(MetaData data, String[] linksArray, int concDownload, long fileSize, String name){
        RandomAccessFile accessor = null;  // creating new RandomAccessFile to write into
        try {
            accessor = new RandomAccessFile(name, "rw");
        }
        catch (FileNotFoundException e){
            System.err.println("error generating random access file");
            System.err.println("Download failed");
            System.exit(1);
        }

        Thread[] threads = new Thread[concDownload];  // new threads for URL requests
        FileWriter fw = new FileWriter(data, accessor, fileSize, name);  // setup FileWriter object
        int indexJump = CHUNKS/concDownload;  // for chunks index distribution
        int start = 0;  // starting from index
        int end = indexJump-1; // -1 compensate since we start from 0 and not 1
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
                System.err.println("Thread interrupt exception \n Download failed");
                System.exit(1);
            }
        }
        MetaData.deleteFile(name); // delete remaining metadata
        System.out.println("Download succeeded");
    }

    /**
     * reads a file, returns a string array where each line from the file is an entry
     * @param filePath - path to file
     * @return - returns a string array of all the lines in the file
     */
    public static String[] listFromFile(String filePath){
        List<String> lines = new ArrayList<>();  // need some dynamic structure to read lines from file into
        String line;  // for reading lines from file into
        try{
            // while file has lines, read them
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while((line = br.readLine()) != null){
                lines.add(line);
            }
        }
        catch(IOException e){
            System.out.println("Error reading from file");
            System.exit(1);
        }
        String[] links = lines.toArray(new String[]{});  // convert the list to array
        return links;
    }
}
