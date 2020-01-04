import java.io.*;
import java.net.*;
import java.util.*;


public class FileWriter {
    public String link;
    public int num_connections;
    public String fileName;

    public FileWriter(String[] link, int num_connections) {
        //this.link = link;
        //this.num_connections = num_connections;
        this.fileName = txtParser(link[0]);


    }

    public String txtParser(String link) {
        String fileName = "";
        if (link.contains("\\")) { //if we are dealing with blackslashes
           String newLink = link.replace('\\', '/');

          fileName = newLink.substring(newLink.lastIndexOf('/')+1, newLink.length());
        }
        else { //if we are dealing with forward slashes
            fileName = link.substring(link.lastIndexOf('/') + 1, link.length());
        }
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        return fileNameWithoutExtension;
    }

    //Creates a tmp file to use as metadata
    //File file = new File(fileName+".tmp");

    public void writing() {

    }






}
