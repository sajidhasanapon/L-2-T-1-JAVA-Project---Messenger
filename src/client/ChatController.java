package client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import util.Message;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.Hashtable;


public class ChatController
{
    private ClientMain clientMain;
    private String username;
    private String receiver;
    private String message;
    private String serverAddress;
    private Stage stage;
    Parent root;
    int visibility = 0;
    String currentPerson = null;
    ObservableList<String> names = FXCollections.observableArrayList ();
    ObservableList<String> blockedList = FXCollections.observableArrayList ();
    Hashtable<String, ArrayList<String>> messages = new Hashtable<> ();

    public void setServerAddress ( String serverAddress )
    {
        this.serverAddress = serverAddress;
    }

    public void setStage ( Stage stage )
    {
        this.stage = stage;
    }

    public void setRoot ( Parent root )
    {
        this.root = root;
    }


    @FXML
    private ListView<String> viewOnlineList = new ListView<> ();

    @FXML
    private ListView<String> viewBlocked = new ListView<> ();


    @FXML
    private TextArea receiverBox;

    @FXML
    private TextField writerBox;

    @FXML
    private Button sendButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button signoutButton;

    @FXML
    private Button blockButton;

    @FXML
    Label titleBar;

    @FXML
    private Button visibilityButton;

    @FXML
    private Label statusLabel;


    public void show ()
    {
        viewOnlineList.setItems ( names );

        stage.setTitle ( username );
        stage.setScene ( new Scene ( root, 700, 600 ) );
        stage.show ();
    }


    public void setClientMain ( ClientMain clientMain )
    {
        this.clientMain = clientMain;
    }

    public void setUsername ( String username )
    {
        this.username = username;
    }


    @FXML
    void clear ( ActionEvent event )
    {
        writerBox.setText ( "" );
    }

    @FXML
    void send ( ActionEvent event ) throws Exception
    {
        receiver = viewOnlineList.getSelectionModel ().getSelectedItem ();
        message = writerBox.getText ();

        if ( !message.equals ( null ) && !receiver.equals ( null ) )
        {
            Message msg = new Message ( username, receiver, message );

            NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
            nu.write ( msg );
            System.out.println ( message + " -> " + receiver );
            writerBox.setText ( null );

            if (!messages.containsKey ( receiver ))
            {
                messages.put ( receiver, new ArrayList<String> () );
            }

            ( messages.get ( receiver ) ).add ( "[Me" + " > " + receiver + "]   " + message + "\n" );

            showMessage ();
        }

        else return;
    }

    public void newMessage ( String sender, String message )
    {
        System.out.println (sender + " : " + message);

        if (! messages.containsKey ( sender ) )
        {
            messages.put ( sender, new ArrayList<String> () );
        }

        ( messages.get ( sender ) ).add ( "[" + sender + " > Me]   " + message + "\n" );

        if(sender.equals ( currentPerson ))
        {
            showMessage ();
        }
    }

    @FXML
    void setCurrentPerson()
    {
        currentPerson = viewOnlineList.getSelectionModel ().getSelectedItem ();
        titleBar.setText (currentPerson);
        showMessage ();
    }

    public void showMessage ()
    {
        if ( !messages.containsKey (currentPerson) )
        {
            receiverBox.setText ( "Nothing to show\n\n" );
        }

        else
        {
            String chatHistory = "\n";

            for (String s : messages.get (currentPerson))
            {
                chatHistory = chatHistory + s;
            }

            receiverBox.setText ( chatHistory );
        }

        if (blockedList.contains (currentPerson))
        {
            receiverBox.setText ( receiverBox.getText () + "\n\nYOU HAVE BLOCKED THIS PERSON" );
        }

        if (!names.contains ( currentPerson ))
        {
            sendButton.setVisible ( false );
        }

        else
        {
            sendButton.setVisible ( true );
        }
    }


    @FXML
    void signout ( ActionEvent event ) throws Exception
    {
        clientMain.logout ();
    }

    @FXML
    void block ( ActionEvent event )
    {
        receiver = viewOnlineList.getSelectionModel ().getSelectedItem ();
        NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
        nu.write ( new Message ( username, receiver, 1 ) );
        names.remove ( receiver );
        blockedList.add ( receiver );
        viewBlocked.setItems ( blockedList );
        showMessage ();
    }

    @FXML
    void visibilityControl ()
    {
        if ( visibility == 0 )
        {
            statusLabel.setText ( "Chat is turned off" );
            visibilityButton.setText ( "Turn on chat" );
            visibility = 1;
        }

        else
        {
            statusLabel.setText ( "" );
            visibilityButton.setText ( "Turn off chat" );
            visibility = 0;
        }

        clientMain.visibilityControl ();

    }


    public void setOnlineUsersList ( ArrayList<String> onlineUsersList )
    {
        for ( String name : onlineUsersList )
        {
            if ( !name.equals ( username ) )
            {
                names.add ( name );
                System.out.println ( name );
            }
        }
    }

    public void update ( String newUser )
    {
        Platform.runLater ( () -> {

            if ( names.contains ( newUser ) )
            {
                names.remove ( newUser );
            }

            else
            {
                if ( !newUser.equals ( username ) )
                {
                    names.add ( newUser );
                }
            }

            showMessage ();
        } );
    }

    public void setChatHistory(Hashtable<String, ArrayList<String>>  messages)
    {
        this.messages = messages;
    }
}

