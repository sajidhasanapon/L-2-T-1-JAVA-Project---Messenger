package server;

import client.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.ClientInfo;
import util.Message;
import util.NetworkUtil;

import java.net.InetAddress;
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
        nu = info.getNu ();
        nu.write ( message );

        clear ();

        //names.remove ( clientID );
        //nu.closeConnection ();
    }


    public void clear ()
    {
        serverMessage.setText ( "" );
    }


    public void set ( String connectiontype, String username, String password, NetworkUtil nu, String clientAddress ) throws Exception
    {
        Platform.runLater ( () ->{

            if ( connectiontype.equals ( "existing" ) )
            {
                if ( !table.containsKey ( username ) )  // user does not exist
                {
                    //nu.write ( "Invalid" );
                    Message msg = new Message ("Invalid");
                    nu.write(msg);
                }

                else
                {
                    info = table.get ( username );
                    String realPassword = info.getPassword ();

                    if ( !password.equals ( realPassword ) )  // password mismatch
                    {
                        //nu.write ( "Invalid" );
                        Message msg = new Message ("Invalid");
                        nu.write(msg);
                    }
                    else
                    {
                        if(names.contains ( username )) // already logged in somewhere else
                        {
                            info = table.get ( username );
                            NetworkUtil existingLink = info.getNu ();
                            //existingLink.write ( "close previous" );
                            Message msg = new Message ("close previous"); // terminate previous session
                            existingLink.write(msg);

                            info.setNu ( nu );
                            info.setClientAddress ( clientAddress );
                            table.put ( username, info );
                            //nu.write ( "new login" );
                            msg = new Message ("new login"); // update new connection
                            nu.write(msg);
                        }

                        else  // existing user logs in
                        {
                            names.add ( username );
                            info.setNu ( nu );
                            info.setClientAddress ( clientAddress );
                            table.put ( username, info );
                            //nu.write("welcome back");
                            Message msg = new Message ("welcome back");
                            nu.write(msg);
                        }
                    }
                }

            }
            else if ( connectiontype.equals ( "new account" ) )  // new account request
            {
                if(names.contains ( username )) // username already exists
                {
                    //nu.write("occupied");
                    Message msg = new Message ("occupied");
                    nu.write(msg);
                }

                else
                {
                    names.add ( username ); // create new account
                    info = new Info ( password, nu, clientAddress);
                    table.put ( username, info );
                    //nu.write("hello");
                    Message msg = new Message ("hello");
                    nu.write(msg);
                }
            }
        } );
    }

    public void logout(String username)
    {
        Platform.runLater ( () -> {
            names.remove ( username );
            info = table.get ( username );
            nu = info.getNu ();
            //nu.write("logout");
            Message msg = new Message ("logout");
            nu.write(msg);
            nu.closeConnection ();

        } );
    }

    public void newMessage( Message m)throws Exception
    {
        Info info = table.get(m.getReceiver ());
        NetworkUtil nuCommunication = info.getNu ();
        nuCommunication.write ( m );
    }

    public void exit ()
    {
        System.exit ( 0 );
    }
}

