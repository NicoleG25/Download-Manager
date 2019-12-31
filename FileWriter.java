import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileWriter {
    public String link;
    public int num_connections;
    public String fileName;

    public FileWriter(String[] link, int num_connections) {
        //this.link = link;
        //this.num_connections = num_connections;
        this.fileName = TxtParser(link[0]);


    }

    public String TxtParser(String link) {
        Pattern pattern = Pattern.compile("[^/\\\\&\\?]+\\.\\w{3,4}(?=([\\?&].*$|$))");
        Matcher m = pattern.matcher(link);
        //figure out how to take the matched pattern and put it in a variable
        String fileName = m.toString();
        System.out.println(fileName); // so we can test
        return fileName;
    }

    //Creates a tmp file to use as metadata
    File file = new File(fileName+".temp");






}
