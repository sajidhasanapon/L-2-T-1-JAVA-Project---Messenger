package server;

import util.Message;
import util.NetworkUtil;

import java.net.ServerSocket;
import java.net.Socket;

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
                Message message = (Message)nu.read();
                serverController.newMessage ( message );
            }
        }
        catch ( Exception e )
        {
            System.out.println ("Problem in ServerCommunicationThread\n" + e);
        }
        //nc.closeConnection ();
    }

}
