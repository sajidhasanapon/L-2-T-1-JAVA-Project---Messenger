package client;
import util.Message;
import util.NetworkUtil;

public class ClientConnectionThread implements Runnable
{
    private ClientChatController waiter;
    private Thread thr;
    private NetworkUtil nc;
    ClientMain clientMain;

    public ClientConnectionThread ( NetworkUtil nc , ClientMain clientMain )
    {
        this.nc = nc;
        this.clientMain = clientMain;
        this.waiter = waiter;
        this.thr = new Thread ( this );
        thr.start ();
    }

    public void run ()
    {
        try
        {
            while ( true )
            {
                Message m = ( Message ) nc.read ();
                System.out.println (m.getMessage ());

                if((m.getSender ()).equals ( "serverod876$%^$^ewlfgh9ewqieuqrwrwrqeuq132rfret5678iukyu&^%&6syqwu" ))
                {
                    String message = m.getMessage ();

                    if (message.equals ( "close previous" ))
                    {
                        clientMain.closePrevious(nc);
                    }

                    else if (message.equals("welcome back"))
                    {
                        clientMain.welcomeBack ();
                        clientMain.setOnlineUsersList ( m.getOnlineNowUsersList () );
                    }

                    else if (message.equals ( "Invalid" ))
                    {
                        clientMain.invalidLogin ();
                    }

                    else if (message.equals ( "new login" ))
                    {
                        clientMain.newLogin();
                        clientMain.setOnlineUsersList ( m.getOnlineNowUsersList () );
                    }

                    else if( message.equals("hello") )
                    {
                        clientMain.signUp ();
                        clientMain.setOnlineUsersList ( m.getOnlineNowUsersList () );
                    }

                    else if( message.equals("occupied") )
                    {
                        clientMain.occupied ();
                    }

                    else if (message.equals ( "update" ))
                    {
                        System.out.println (m.getNewUser ());
                        clientMain.updateOnlineUsersList ( m.getNewUser() );

                    }
                }

                else
                {
                    clientMain.showMessage(m.getSender (), m.getMessage ());
                }
            }
        }
        catch ( Exception e )
        {
            System.out.println ( "Problem in ClientConnectionThread\n" + e );
        }
        //nc.closeConnection ();
    }


}
