package server;

import util.ClientInfo;
import util.NetworkUtil;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnectionThread implements Runnable
{
    private Thread thr;
    private NetworkUtil nu;
    ServerSocket serverSocket;
    Socket clientSock;
    ServerController serverController;
    ClientInfo clientInfo;

    ServerConnectionThread ( ServerController serverController)
    {
        try
        {
            serverSocket = new ServerSocket ( 33333 );
            this.serverController = serverController;
            this.thr = new Thread ( this );
            thr.start ();
        }

        catch(Exception e )
        {
            System.out.println ("Problem in ServerConnectionThread Construction");
            System.out.println (e);
        }
    }

    public void run ()
    {
        try
        {
            while ( true )
            {
                clientSock = serverSocket.accept ();
                nu = new NetworkUtil ( clientSock );
                clientInfo = (ClientInfo) nu.read ();
                serverController.set(clientInfo.getConnectionType (), clientInfo.getUsername (), clientInfo.getPassword (), nu);
            }
        }
        catch ( Exception e )
        {
            System.out.println ("Problem starting ServerConnectionThread");
            System.out.println (clientInfo.getConnectionType ());
            System.out.println (clientInfo.getUsername ());
            System.out.println (clientInfo.getPassword ());
            System.out.println ( e );
        }
        //nc.closeConnection ();
    }
}



