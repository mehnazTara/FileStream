import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 */
public class Server2 {


    public static void main(String[] args) {

        //initialize socket and input stream
        Socket socket = null;
        ServerSocket server = null;
        DataInputStream in = null;
        BufferedOutputStream bufferedOutputStream = null;

        Random random = new Random();

        int bytesRead = 0;


        try {
            server = new ServerSocket(6554);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //while (true) {
            System.out.println("Waiting...");
            try {

                socket = server.accept();
                System.out.println("Accepted connection : " + socket);

                in = new DataInputStream(socket.getInputStream());


                // receive file in chunks
                byte[] chunkArray = new byte[1024];



                while ((bytesRead = in.read(chunkArray)) != -1 )
                {
                    if(bytesRead == chunkArray.length) {
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("chunk_" + random.nextInt() + ".txt"));
                        bufferedOutputStream.write(chunkArray, 0, bytesRead);
                        bufferedOutputStream.flush();
                        System.out.println("File "
                                + " downloaded (" + bytesRead + " bytes read)");
                    }

                    bytesRead = 0;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedOutputStream.close();
                    in.close();
                    server.close();
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
        }
    //}
}