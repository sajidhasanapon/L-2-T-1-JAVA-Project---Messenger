package server;

import util.ClientInfo;
import util.Message;
import util.NetworkUtil;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sajid Hasan on 12/3/2015.
 */
public class ServerReadThread
{
    /*



    private Thread thr;
    private NetworkUtil nu;
    ServerSocket servSock;
    Socket clientSock;
    ServerController serverController;
    ClientInfo clientInfo;
    String username;

    ServerReadThread (ServerController serverController)
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
                clientSock = servSock.accept ();
                nu = new NetworkUtil ( clientSock );
                String name = (String)nu.read();
                serverController.setLink ( name, nu );
            }
        }
        catch ( Exception e )
        {
            System.out.println ("Problem starting ServerControllerThread\n" + e);
        }
        //nc.closeConnection ();
    }


     */
}
