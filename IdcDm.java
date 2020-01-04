import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IdcDm {

    static int PRL_DOWNLOADS = 1;
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

    public static routine checkInputType(String input){
        if (Files.exists(Paths.get(input))){
            return routine.READ_FROM_FILE;
        }
        return routine.DIRECT_LINK;
    }

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

    public static routine checkArgs(String[] args){
        switch (args.length){
            case 1:
                return checkInputType(args[0]);
            case 2:
                try {
                    PRL_DOWNLOADS = Integer.parseInt(args[1]);
                    if (PRL_DOWNLOADS < 1){
                        System.out.println("number of concurrent download should be at least 1");
                        System.exit(0);
                    }
                }
                catch(Exception e){
                    System.out.println("Second argument should be an integer");
                }
                return checkInputType(args[0]);
        }
        return null;
    }
}
