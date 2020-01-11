import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Tester {

    public static void main(String[] args) throws MalformedURLException {
        String[] links = {"http://centos.activecloud.co.il/6.10/isos/x86_64/CentOS-6.10-x86_64-netinstall.iso"};
//        //check if CMD will run
//        String[] links2 = {"http://centos.activecloud.co.il\\6.10\\isos\\x86_64\\CentOS-6.10-x86_64-netinstall.iso"};
//        FileWriter writer = new FileWriter(links2, 0);
//        System.out.println();
//        System.out.println(writer.fileName);

        //String strUrl = "http://centos.activecloud.co.il/6.10/isos/x86_64/CentOS-6.10-x86_64-netinstall.iso";
/*
        String strUrl = "https://download.sketchapp.com/sketch-61.2-89653.zip?_ga=2.137558832.1675698364.1578132420-1989857718.1564751276";
        URL url = new URL(strUrl);
        System.out.println(getFileSize(url));
        String byteRange = "bytes=" + 0 + "-" + 24;
        System.out.println(byteRange);

*/
//        String[] arr = IdcDm.listFromFile("/home/PointBlank/Documents/linksTest");
//        printArray(arr);

//        MetaData data = new MetaData(1, links);
//        data.serialize();
//        data.setFileSize();
//        data = MetaData.deserialize();
//        System.out.println(data.getFileSize());
        MetaData.deleteFile("temp23f3sersdf.ser");
    }



    public static void printArray(String[] arr){
        System.out.println("printing -------");
        for (int i=0; i<arr.length; i++){
            System.out.println( arr[i]);
        }
    }

    //using a HEAD request to get the file size
/*    private static int getFileSize(URL url) throws MalformedURLException {

        URLConnection conn = null;
        try {
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
    }*/
}
