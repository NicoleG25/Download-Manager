public class DownlaodManager {

    // different subroutines distinction
    enum routine{
        READ_FROM_FILE,
        DIRECT_LINK
    }
    // new comment

    static int paralel_count = 0;
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
                paralel_count = Integer.parseInt(args[1]);
                // add exception for int here
                return routine.READ_FROM_FILE;
        }
        return null;
        //print error/throw exception for invalid arguments number
    }

    public static{

    }
}
