import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 */
public class Client {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("please provide a file name as command line arg");
            System.exit(0);
        }

        String filename = args[0];

        Socket socket = null;
        DataOutputStream out = null;
        BufferedInputStream bufferedInputStream = null;


        // establish a connection
        try {
            socket = new Socket("127.0.0.1", 6553);
            System.out.println("Connected");
            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            // send file
            File file = new File(filename);

            byte[] byteArray = new byte[(int) file.length()];

            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(byteArray, 0, byteArray.length);

            System.out.println("Sending " + filename + "(" + byteArray.length + " bytes)");

            out.write(byteArray, 0, byteArray.length);
            out.flush();
            System.out.println("Done.");

        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                bufferedInputStream.close();
                out.close();
                socket.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }
}
