package server;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Message;
import util.NetworkUtil;
import util.ServerNotification;

import java.util.ArrayList;
import java.util.Hashtable;

public class ServerController
{
    Stage stage;
    //Server server;
    Parent root;
    NetworkUtil nu;
    String message;
    String username;
    Info info;
    ArrayList<String> onlineNow = new ArrayList<> ();

    @FXML
    private ListView<String> clientList = new ListView<> ();

    ObservableList<String> names = FXCollections.observableArrayList ();

    Hashtable<String, Info> table = new Hashtable<> ();

    @FXML
    private TextField serverMessage;

    @FXML
    private Button sendButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button exitButton;


    public void setStage ( Stage stage )
    {
        this.stage = stage;
    }


    public void setRoot ( Parent root )
    {
        this.root = root;
    }


    public void showServer ()
    {
        clientList.setItems ( names );
        stage.setTitle ( "Server" );
        stage.setScene ( new Scene ( root, 560, 670 ) );
        stage.show ();
    }


    public void send () throws Exception
    {
        username = clientList.getSelectionModel ().getSelectedItem ();
        message = serverMessage.getText ();

        info = table.get ( username );
        nu = info.getNuConnection ();
        nu.write ( message );

        clear ();
    }


    public void clear ()
    {
        serverMessage.setText ( "" );
    }


    public void set ( String connectiontype, String username, String password, NetworkUtil nu) throws Exception
    {
        Platform.runLater ( () ->{

            if ( connectiontype.equals ( "existing" ) )
            {
                if ( !table.containsKey ( username ) )  // user does not exist
                {
                    nu.write ( new ServerNotification ( "invalid" ) );
                    //nu.write ( "invalid" );
                }

                else
                {
                    info = table.get ( username );
                    String realPassword = info.getPassword ();

                    if ( !password.equals ( realPassword ) )  // password mismatch
                    {
                        nu.write ( new ServerNotification ( "invalid" ) );
                        //nu.write ( "invalid" );
                    }
                    else
                    {
                        if(names.contains ( username )) // already logged in somewhere else
                        {
                            info = table.get ( username );
                            NetworkUtil existingLink = info.getNuConnection ();

                            // terminate previous session
                            existingLink.write ( new ServerNotification ( "close previous" ) );
                            //existingLink.write ( "close previous" );


                            info.setNuConnection ( nu );
                            table.put ( username, info );

                            // update new connection
                            ArrayList<String> available = new ArrayList<String> ();
                            for ( String onlineUser : onlineNow )
                            {
                                if (!onlineUser.equals ( username ) && !isBlocked ( onlineUser, username ) )
                                {
                                    available.add ( onlineUser );
                                }
                            }
                            nu.write ( new ServerNotification ( "new login", available, info.getMessages () ) );
                            //nu.write ( "new login" );
                        }

                        else  // existing user logs in. no session open elsewhere.
                        {
                            names.add ( username );
                            info.setNuConnection ( nu );
                            table.put ( username, info );
                            ArrayList<String> available = new ArrayList<String> ();
                            for ( String onlineUser : onlineNow )
                            {
                                if (!onlineUser.equals ( username ) && !isBlocked ( onlineUser, username ) )
                                {
                                    available.add ( onlineUser );
                                }
                            }
                            nu.write ( new ServerNotification ( "hello", available, info.getMessages () ) );
                            //nu.write ( "welcome back" );

                            updateAllUsers (username);
                            onlineNow.add ( username );
                        }
                    }
                }

            }
            else if ( connectiontype.equals ( "new account" ) )  // new account request
            {
                if(table.containsKey ( username )) // username already exists
                {
                    nu.write ( new ServerNotification ( "occupied") );
                    //nu.write ( "occupied" );
                }

                else
                {
                    names.add ( username ); // create new account
                    info = new Info ( password, nu);
                    table.put ( username, info );

                    ArrayList<String> available = new ArrayList<String> ();
                    for ( String onlineUser : onlineNow )
                    {
                        if (!onlineUser.equals ( username ) && !isBlocked ( onlineUser, username ) )
                        {
                            available.add ( onlineUser );
                        }
                    }
                    nu.write ( new ServerNotification ( "hello", available, info.getMessages () ) );
                    //nu.write ( "hello" );

                    updateAllUsers (username);
                    onlineNow.add ( username );
                }
            }
        } );
    }

    public void logout(String username)
    {
        Platform.runLater ( () -> {
            names.remove ( username );
            info = table.get ( username );
            nu = info.getNuConnection ();
            //Message msg = new Message ("logout");
            //nu.write(msg);
            nu.write ( new ServerNotification ( "logout") );
            nu.closeConnection ();

            if (onlineNow.contains ( username ))
            {
                updateAllUsers (username);
                onlineNow.remove ( username );
            }

        } );
    }

    public void toggleVisibility(String username)
    {

        if (onlineNow.contains ( username ))
        {
            onlineNow.remove ( username );
        }

        else
        {
            onlineNow.add ( username );
        }

        updateAllUsers ( username );
    }

    public void newMessage( Message m)throws Exception
    {
        String sender = m.getSender ();
        String receiver = m.getReceiver ();
        String message = m.getMessage ();

        if (m.getBlock () == 1)
        {
            block(sender, receiver);
        }

        else
        {
            Info info = table.get ( sender );
            info.outgoingMessage ( receiver, message );
            System.out.println (sender + receiver+ message);

            info = table.get ( receiver );
            info.incomingMessage ( sender, message );
            System.out.println (sender + receiver+ message);

            if (onlineNow.contains ( receiver ))
            {
                info = table.get(receiver);
                NetworkUtil nu = info.getNuGetMessage ();
                nu.write ( m );
            }
        }
    }

    public void block(String person1, String person2) // person1 = blocker, person2 = blocked
    {
        Info info = table.get ( person1 );
        info.block ( person2 ); // adding to blockList stored in server

        info = table.get ( person2 );
        info.block ( person1 ); // adding to blockList stored in server

        if (names.contains ( person2 )) // updating the blocked person
        {
            nu = info.getNuConnection ();
            nu.write ( new ServerNotification ( "update", person1 ) );
            //nu.write ( new ServerNotification ( "blocked, person1" ) );
        }




    }

    public void unblock(String person1, String person2)
    {
        Info info = table.get ( person1 );
        info.unblock ( person2 );

        info = table.get ( person2 );
        info.unblock ( person1 );
    }

    public boolean isBlocked(String person1, String person2)
    {
        Info info = table.get ( person1 );
        return info.isBlocked ( person2 );
    }

    public void updateAllUsers (String newOnlineUser)
    {
        for ( String onlineUser : names )
        {
            if (!onlineUser.equals ( newOnlineUser ) && !isBlocked ( onlineUser, newOnlineUser ) )
            {
                info = table.get ( onlineUser );
                nu = info.getNuConnection ();
                nu.write ( new ServerNotification ( "update", newOnlineUser ) );
            }
        }

    }

    public void SetClientAddress(String username, NetworkUtil nu)
    {
        info = table.get ( username );
        info.setNuGetMessage (nu);
    }

    public void exit ()
    {
        System.exit ( 0 );
    }
}

