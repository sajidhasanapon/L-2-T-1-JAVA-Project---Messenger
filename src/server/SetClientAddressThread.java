package server;

import util.Message;
import util.NetworkUtil;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sajid Hasan on 12/16/2015.
 */
public class SetClientAddressThread implements Runnable
{
    private Thread thr;
    private NetworkUtil nu;
    ServerSocket serverSocket;
    Socket clientSocket;
    ServerController serverController;

    SetClientAddressThread (ServerController serverController)
    {
        try
        {
            serverSocket = new ServerSocket ( 34343 );
            this.thr = new Thread ( this );
            this.serverController = serverController;
            thr.start ();
        }

        catch(Exception e )
        {
            System.out.println ("Problem in SetClientAddressThread Construction");
            e.printStackTrace ();
        }
    }

    public void run ()
    {
        try
        {
            while ( true )
            {
                clientSocket = serverSocket.accept ();
                nu = new NetworkUtil ( clientSocket );
                String username = (String) nu.read();
                serverController.SetClientAddress(username, nu);
            }
        }
        catch ( Exception e )
        {
            System.out.println ("Problem starting SetClientAddressThread");
            e.printStackTrace ();
        }
        //nc.closeConnection ();
    }

}
