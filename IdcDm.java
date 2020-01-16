import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IdcDm {

    static int PRL_DOWNLOADS = 1;  // default value for number of concurrent downloads

    public static void main(String[] args){
        String linkInput = args[0];

        String[] linksList = getLinkArray(args[0]);
        String fileName = getFileName(linksList[0]);
        long fileSize = getFileSize(linksList[0]);
        MetaData data = genMetaData(fileName);
        int concDownload = getConcCount(fileSize, args);


        // function to generate threads
        // filewriter



    }




    /**
     * The function checks if the String is a path to a file, if not by default it is set as a URL
     * @param linkInput - arg[0], should be path to file or a URL
     * @return - returns list of URL
     */
    public static String[] getLinkArray(String linkInput){
        //TODO: implement
        return null;
    }

    //TODO : Test
    /**
     * gets a link in order to parse it to the name of the file
     * @param link - path to file
     * @return - returns a string that is the filename
     */
    public static String getFileName(String link) {
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
     * returns file size in bytes
     * @param link - URL link to file
     * @return
     */
    public static long getFileSize(String link){
        // TODO: implement
        return 0;
    }

    /**
     * logic to limit number of concurrent downloads
     * @param fileSize - the size of the file
     * @param args - array of arguments, to extract user requested number of threads if was passed
     * @return
     */
    public static int getConcCount(long fileSize, String[] args){
        // TODO: implement
        return 0;
    }

    /**
     * checks if metadata file exists, if yes - checks if downloaded file matches - if yes load and continue
     * else generates a new metadata file
     * @param name
     * @return
     */
    public static MetaData genMetaData(String name){
        //TODO: implement
        return null;
    }


    //TODO: create a method that manages minimal threshold for connections + max connections
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
