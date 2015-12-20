package server;

import util.NetworkUtil;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerLogoutThread implements Runnable
{
    ServerController serverController;
    Thread thr;
    ServerSocket serverSocket;

    ServerLogoutThread(ServerController serverController)
    {
        try
        {
            serverSocket = new ServerSocket (44444);
            this.serverController = serverController;
            this.thr = new Thread ( this );
            thr.start ();
        }

        catch(Exception e )
        {
            System.out.println ("Problem in ServerLogoutThread Construction");
            System.out.println (e);
        }
    }


    public void run ()
    {
        try
        {
            while ( true )
            {
                Socket clientSock = serverSocket.accept ();
                NetworkUtil nu = new NetworkUtil ( clientSock );
                String username = (String)nu.read ();
                char action = username.charAt ( 0 );
                if (action == '0')
                {
                    serverController.logout ( username.substring ( 1 ) );
                }

                else if (action == '1')
                {
                    serverController.toggleVisibility(username.substring ( 1 ));
                }

            }
        }
        catch ( Exception e )
        {
            System.out.println ("Problem in Problem in ServerLogoutThread run method" + e );
        }
    }
}
