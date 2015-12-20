package server;

import util.ConnectionInfo;
import util.NetworkUtil;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionThread implements Runnable
{
    private Thread thr;
    private NetworkUtil nu;
    ServerSocket serverSocket;
    Socket clientSock;
    ServerController serverController;
    ConnectionInfo connectionInfo;

    ConnectionThread ( ServerController serverController)
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
            System.out.println ("Problem in ConnectionThread Construction");
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
                connectionInfo = ( ConnectionInfo ) nu.read ();
                serverController.set( connectionInfo.getConnectionType (), connectionInfo.getUsername (), connectionInfo.getPassword (), nu);
            }
        }
        catch ( Exception e )
        {
            System.out.println ("Problem starting ConnectionThread");
            System.out.println ( connectionInfo.getConnectionType ());
            System.out.println ( connectionInfo.getUsername ());
            System.out.println ( connectionInfo.getPassword ());
            System.out.println ( e );
        }
        //nc.closeConnection ();
    }
}



