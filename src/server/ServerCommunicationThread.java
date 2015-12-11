package server;

import util.ClientInfo;
import util.Message;
import util.NetworkUtil;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sajid Hasan on 12/4/2015.
 */
public class ServerCommunicationThread implements Runnable
{
    private Thread thr;
    private NetworkUtil nu;
    ServerSocket servSock;
    Socket clientSock;
    ServerController serverController;
    ClientInfo clientInfo;
    String username;

    ServerCommunicationThread (ServerController serverController)
    {
        try
        {
            servSock = new ServerSocket ( 55566 );
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
