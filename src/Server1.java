import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 */
public class Server1 {


    public static void main(String[] args) {

        //initialize socket and input stream
        Socket socket = null;
        Socket newServer = null;
        ServerSocket server = null;
        DataInputStream in = null;
        DataOutputStream out = null;


        int bytesRead;
        int count = 0;


        try {
            server = new ServerSocket(6553);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //while (true) {
            System.out.println("Waiting...");
            try {

                socket = server.accept();
                System.out.println("Accepted connection : " + socket);

                in = new DataInputStream(socket.getInputStream());


                newServer = new Socket("127.0.0.1", 6554);
                System.out.println("Connected to data server" + newServer );
                out = new DataOutputStream(newServer.getOutputStream());

                // receive file in chunks
                byte[] chunkArray = new byte[1024];



                int chunkNum = 1;
                while ((count = in.read(chunkArray)) > 0 ) {
                        in.readFully(chunkArray);
                        out.writeInt(chunkNum);
                        out.write(chunkArray, 0, count);
                        out.flush();

                    System.out.println("File chunk"
                            + " downloaded (" + count + " bytes read)");

                    chunkNum++;
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    in.close();
                    server.close();
                    newServer.close();
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
        }
    //}
}