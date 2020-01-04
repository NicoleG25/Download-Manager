import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IdcDm {

    static int PRL_DOWNLOADS = 1;  // default value for number of concurrent downloads
    enum routine{
        READ_FROM_FILE,
        DIRECT_LINK
    }

    public static void main(String[] args){
        switch (checkArgs(args)){
            case DIRECT_LINK:
                String[] links = {args[0]};
                try{
                    SlaveThreader st = new SlaveThreader(links, PRL_DOWNLOADS);
                }
                catch(MalformedURLException e){
                    System.out.println("url exception");
                }
                break;
            case READ_FROM_FILE:
                links = listFromFile(args[0]);
                try{
                    SlaveThreader st = new SlaveThreader(links, PRL_DOWNLOADS);
                }
                catch(MalformedURLException e){
                    System.out.println("url exception");
                }
        }
    }

    /**
     * The function checks if the String is a path to a file, if not by default it is set as a URL
     * @param input - arg[0], should be path to file or a URL
     * @return - returns an enum
     */
    public static routine checkInputType(String input){
        if (Files.exists(Paths.get(input))){
            return routine.READ_FROM_FILE;
        }
        return routine.DIRECT_LINK;
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

    /**
     * checks for legal arguments - 1 or 2 arguments, first is a path or URL, second is an integer > 0
     * @param args - passed arguments array
     * @return - returns enum
     */
    public static routine checkArgs(String[] args){
        switch (args.length){
            case 1:
                return checkInputType(args[0]);
            case 2:
                try {
                    PRL_DOWNLOADS = Integer.parseInt(args[1]);
                    if (PRL_DOWNLOADS < 1){
                        System.out.println("number of concurrent downloads should be at least 1");
                        System.exit(0);
                    }
                }
                catch(Exception e){
                    System.out.println("Second argument should be an integer");
                    System.exit(0);
                }
                return checkInputType(args[0]);
        }
        return null;
    }
}
