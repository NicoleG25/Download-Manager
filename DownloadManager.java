import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadManager {

    // different subroutines distinction
    enum routine{
            READ_FROM_FILE,
            DIRECT_LINK
    }
    // new comment 2

    static int parallel_count = 0;
    static String links_file = "";
    static String link = "";  //

    public static void main(String[] args){

        switch (check_args(args)){
            case DIRECT_LINK:
                //
            case READ_FROM_FILE:
                    //
        }
    }

    public static routine check_args(String[] args){
        switch (args.length){
            case 1:
                link = args[0];
                return routine.DIRECT_LINK;
                // add some link validation here
            case 2:
                links_file = args[0];
                parallel_count = Integer.parseInt(args[1]);
                // add exception for int here
                return routine.READ_FROM_FILE;
        }
        return null;
            //print error/throw exception for invalid arguments number
    }

//    public String TxtParser(String link) {
//        Pattern pattern = Pattern.compile("[^/\\\\&\\?]+\\.\\w{3,4}(?=([\\?&].*$|$))");
//        Matcher m = pattern.matcher(link);
//        //figure out how to take the matched pattern and put it in a variable
//        String fileName = m.toString();
//
//
//
//        return null;
//    }

}
