package server;

import util.ClientInfo;
import util.Message;
import util.NetworkUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerCommunicationThread implements Runnable
{
    private Thread thr;
    private NetworkUtil nu;
    ServerSocket servSock;
    Socket clientSock;
    ServerController serverController;

    ServerCommunicationThread (ServerController serverController)
    {
        try
        {
            servSock = new ServerSocket ( 55555 );
            this.thr = new Thread ( this );
            this.serverController = serverController;
            thr.start ();
        }

        catch(Exception e )
        {
            System.out.println ("Problem in ServerCommunicationThread Construction");
            System.out.println (e);
        }
    }

    public void run ()
    {
        try
        {
            while ( true )
            {
                clientSock = servSock.accept ();
                nu = new NetworkUtil ( clientSock );
                Message m = (Message)nu.read();


                String sendFileName = m.getSender () + ".txt";
                FileWriter fw = new FileWriter (sendFileName, true);
                BufferedWriter bw = new BufferedWriter (fw);
                bw.write ( m.getReceiver() + "@");
                bw.write ( m.getMessage () + "\n");
                fw.close ();
                bw.close ();

                sendFileName = m.getSender () + ".txt";
                FileReader fr = new FileReader (sendFileName);
                BufferedReader br = new BufferedReader (fr);
                while(true) {
                    String s = br.readLine ();
                    if ( s == null) break;
                    System.out.println (s);
                    StringTokenizer st = new StringTokenizer ( s, "@" );
                    String sender = st.nextToken ();
                    if (sender.equals ( "C1" )) {

                    }
                    String message = st.nextToken ();
                }
                fr.close ();
                br.close ();

                serverController.newMessage ( m );
            }
        }
        catch ( Exception e )
        {
            System.out.println ("Problem starting ServerControllerThread\n" + e);
        }
        //nc.closeConnection ();
    }

}
