package client;

import util.Message;
import util.NetworkUtil;

import java.net.ServerSocket;
import java.net.Socket;

public class AcceptThread implements Runnable
{
    private ClientChatController waiter;
    ServerSocket serverSocket;
    private Thread thr;
    private Socket socket;
    private NetworkUtil nu;
    ClientMain clientMain;
    NetworkUtil nuCommunication;

    public AcceptThread (String username, ClientChatController waiter )
    {
        try
        {
            serverSocket = new ServerSocket ( 12345 );
            //this.username = username;
            this.waiter = waiter;
            this.thr = new Thread ( this );
            thr.start ();
        }

        catch ( Exception e )
        {
            System.out.println ("Problem in AcceptThread\n" + e);
        }
    }

    public void run ()
    {
        try
        {
            while ( true )
            {
                socket = serverSocket.accept();
                nuCommunication = new NetworkUtil ( socket );
                Message message = ( Message ) nuCommunication.read ();
                waiter.showMessage ( message.getSender (), message.getMessage () );

            }
        }
        catch ( Exception e )
        {
            System.out.println ( "Problem in ClientConnectionThread\n" + e );
        }
        //nc.closeConnection ();
    }
}
