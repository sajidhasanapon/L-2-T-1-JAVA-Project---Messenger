package client;

import util.*;

/**
 * Created by Sajid Hasan on 12/16/2015.
 */
public class Threads
{
    /**
     * Created by Sajid Hasan on 12/16/2015.
     */
    public static class AcceptThread implements Runnable
    {
        private Thread thread;
        private NetworkUtil nuGetMessage;
        private ChatController chatController;

        public AcceptThread ( NetworkUtil nuGetMessage, ChatController chatController )
        {
            this.nuGetMessage = nuGetMessage;
            this.chatController = chatController;
            this.thread = new Thread ( this );
            thread.start ();
        }

        @Override
        public void run ()
        {
            try
            {
                while ( true )
                {
                    Message m = ( Message ) nuGetMessage.read ();
                    //System.out.println ( m.getMessage () );
                    chatController.newMessage ( m.getSender (), m.getMessage () );
                }
            }

            catch ( Exception e )
            {
                System.out.println ("Problem in AcceptThread");
                e.printStackTrace ();
            }
        }

    }



    public static class ConnectionThread implements Runnable
    {
        private Thread thr;
        private NetworkUtil nc;
        ClientMain clientMain;

        public ConnectionThread ( NetworkUtil nc, ClientMain clientMain )
        {
            this.nc = nc;
            this.clientMain = clientMain;
            this.thr = new Thread ( this );
            thr.start ();
        }

        @Override
        public void run ()
        {
            try
            {
                while ( true )
                {
                    ServerNotification serverNotification = ( ServerNotification ) nc.read ();
                    String serverCommand = serverNotification.getCommand ();

                    System.out.println ( serverCommand );


                    if ( serverCommand.equals ( "close previous" ) )
                    {
                        clientMain.closePrevious ( nc );
                    }

                    else if ( serverCommand.equals ( "welcome back" ) )
                    {
                        clientMain.welcomeBack ();
                        clientMain.setOnlineUsersList ( serverNotification.getOnlineNowUsersList () );
                        clientMain.chatHistory(serverNotification.getMessages ());
                    }

                    else if ( serverCommand.equals ( "invalid" ) )
                    {
                        clientMain.invalidLogin ();
                    }

                    else if ( serverCommand.equals ( "new login" ) )
                    {
                        clientMain.newLogin ();
                        clientMain.setOnlineUsersList ( serverNotification.getOnlineNowUsersList () );
                        clientMain.chatHistory(serverNotification.getMessages ());
                    }

                    else if ( serverCommand.equals ( "hello" ) )
                    {
                        clientMain.signUp ();
                        clientMain.setOnlineUsersList ( serverNotification.getOnlineNowUsersList () );
                        clientMain.chatHistory(serverNotification.getMessages ());
                    }

                    else if ( serverCommand.equals ( "occupied" ) )
                    {
                        clientMain.occupied ();
                    }

                    else if (serverCommand.equals ( "update" ))
                    {
                        clientMain.updateOnlineUsersList ( serverNotification.getNewOnlineUser() );

                    }

                    else if (serverCommand.equals ( "blocked" ))
                    {
                        clientMain.updateOnlineUsersList ( serverNotification.getNewOnlineUser () );
                        // blocked code
                    }
                }
            }
            catch ( Exception e )
            {
                System.out.println ( "Problem in ConnectionThread\n" + e );
            }
            //nc.closeConnection ();
        }


    }

    /**
     * Created by Sajid Hasan on 12/16/2015.
     */
    public static class UpdateThread implements Runnable
    {
        @Override
        public void run()
        {

        }
    }
}
