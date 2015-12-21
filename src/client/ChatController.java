package client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.Message;
import util.NetworkUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;


public class ChatController
{
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab homeTab;

    @FXML
    private Tab accountControlTab;


    @FXML
    private ListView<String> viewCommunication;

    @FXML
    void setFromHistory ()
    {
        currentPerson = viewCommunication.getSelectionModel ().getSelectedItem ();
        receiver = currentPerson;
        tabPane.getSelectionModel ().select ( homeTab );
        showMessage ();
    }


    @FXML
    void tryToSend ( KeyEvent event ) throws Exception
    {
        if ( event.getCode () == KeyCode.ENTER )
        {
            send ();
        }
    }


    private ClientMain clientMain;
    private String username;
    private String receiver;
    private String message;
    private String serverAddress;
    private Stage stage;
    Parent root;
    int visibility = 0;
    String currentPerson = null;
    ObservableList<String> onlineNowUsersList = FXCollections.observableArrayList ();
    ObservableList<String> blockedList = FXCollections.observableArrayList ();
    ObservableList<String> communicationList = FXCollections.observableArrayList ();
    Hashtable<String, ArrayList<String>> messages = new Hashtable<> ();
    ArrayList<String> myBlockList;
    ArrayList<String> blockedMeList;


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
    private Button signoutButton;

    @FXML
    private Button blockButton;

    @FXML
    private Button unblockButton;

    @FXML
    TextField titleBar;

    @FXML
    private Button visibilityButton;

    @FXML
    private Button passwordButton;

    @FXML
    private Button emailButton;

    @FXML
    void changeEmail ( ActionEvent event )
    {
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation ( getClass ().getResource ( "Sorry.fxml" ) );

        try
        {
            Parent root = loader.load ();
            Stage st = new Stage ();

            SorryController sorry = loader.getController ();
            sorry.setLabel ( "Sorry, we don't have an email changing option yet." );
            sorry.setStage ( st );
            st.setTitle ( username );
            st.setScene ( new Scene ( root, 700, 500 ) );
            st.show ();
        }

        catch ( Exception e )
        {
            System.out.println ( "EXCEPTION : ClientMain/showChatScreen" );
            e.printStackTrace ();
        }
    }

    @FXML
    void changePassword ( ActionEvent event )
    {
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation ( getClass ().getResource ( "Sorry.fxml" ) );

        try
        {
            Parent root = loader.load ();
            Stage st = new Stage ();

            SorryController sorry = loader.getController ();
            sorry.setLabel ( "Sorry, we don't have a password changing option yet." );
            sorry.setStage ( st );
            st.setTitle ( username );
            st.setScene ( new Scene ( root, 700, 500 ) );
            st.show ();
        }

        catch ( Exception e )
        {
            System.out.println ( "EXCEPTION : ClientMain/showChatScreen" );
            e.printStackTrace ();
        }
    }


    public void show ()
    {
        viewOnlineList.setItems ( onlineNowUsersList );

        stage.setTitle ( username );
        stage.setScene ( new Scene ( root, 900, 600 ) );
        stage.show ();

    }


    public void setClientMain ( ClientMain clientMain )
    {
        this.clientMain = clientMain;
    }

    public void setUsername ( String username )
    {
        this.username = username;

        visibilityButton.setStyle ( "-fx-background-color: #2FEE2A" );
        titleBar.setText ( null );
        receiver = null;
        writerBox.setVisible ( false );
    }


    void send () throws Exception
    {
        message = writerBox.getText ();


        if ( message != null && !message.equals ( "\n" ) && receiver != null && !blockedList.contains ( receiver ) && !blockedMeList.contains ( receiver ) )
        {
            Message msg = new Message ( username, receiver, message );

            NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
            nu.write ( msg );
            System.out.println ( message + " -> " + receiver );
            writerBox.setText ( null );

            if ( !messages.containsKey ( receiver ) )
            {
                messages.put ( receiver, new ArrayList<String> () );
                communicationList.add ( receiver );
                viewCommunication.setItems ( communicationList );
            }

            ( messages.get ( receiver ) ).add ( "[Me" + " > " + receiver + " @ " + new Date () + " ]\n" + message + "\n\n" );

            showMessage ();
        }

        else
        {
            showMessage ();
            return;
        }
    }

    public void newMessage ( String sender, String message )
    {
        System.out.println ( sender + " : " + message );

        if ( !messages.containsKey ( sender ) )
        {
            messages.put ( sender, new ArrayList<String> () );
            communicationList.add ( sender );
            viewCommunication.setItems ( communicationList );
        }

        ( messages.get ( sender ) ).add ( "[" + sender + " > Me" + " @ " + new Date () + " ]\n" + message + "\n\n" );

        if ( sender.equals ( currentPerson ) )
        {
            showMessage ();
        }
    }

    @FXML
    void setCurrentPerson ()
    {
        currentPerson = viewOnlineList.getSelectionModel ().getSelectedItem ();
        receiver = currentPerson;
        showMessage ();
    }

    public void showMessage ()
    {
        titleBar.setText ( currentPerson );
        writerBox.setVisible ( true );

        if ( currentPerson == null )
        {
            writerBox.setVisible ( false );
        }

        if ( communicationList.contains ( currentPerson ) )
        {
            String chatHistory = "\n";

            for ( String s : messages.get ( currentPerson ) )
            {
                chatHistory = chatHistory + s;
            }

            receiverBox.setText ( chatHistory );
            receiverBox.positionCaret ( chatHistory.length () );

        }

        if ( blockedList.contains ( currentPerson ) )
        {
            receiverBox.setText ( receiverBox.getText () + "\n\n        YOU HAVE BLOCKED THIS PERSON" );
            writerBox.setVisible ( false );
        }

        if ( blockedMeList.contains ( currentPerson ) )
        {
            receiverBox.setText ( receiverBox.getText () + "\n\n        YOU CAN NOT COMMUNICATE WITH THIS PERSON" );
            writerBox.setVisible ( false );
        }

        if ( !onlineNowUsersList.contains ( currentPerson ) && !communicationList.contains ( currentPerson ) )
        {
            writerBox.setVisible ( false );
        }
    }

    @FXML
    void signout ( ActionEvent event ) throws Exception
    {
        NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
        nu.write ( new Message ( username, null, 5 ) );

        clientMain.logout ();
    }

    @FXML
    void block ( ActionEvent event )
    {
        receiver = viewOnlineList.getSelectionModel ().getSelectedItem ();
        NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
        nu.write ( new Message ( username, receiver, 1 ) );

        blockedList.add ( receiver );
        myBlockList.add ( receiver );
        viewBlocked.setItems ( blockedList );

        showMessage ();
    }

    @FXML
    void unblock ()
    {
        receiver = viewBlocked.getSelectionModel ().getSelectedItem ();
        NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
        nu.write ( new Message ( username, receiver, 2 ) );

        blockedList.remove ( receiver );
        myBlockList.remove ( receiver );
        viewBlocked.setItems ( blockedList );

        showMessage ();
    }

    public void blockedMe ( String person )
    {
        blockedMeList.add ( person );
    }

    public void unblockedMe ( String person )
    {
        blockedMeList.remove ( person );
    }

    @FXML
    void visibilityControl ()
    {
        if ( visibility == 0 )
        {
            visibilityButton.setStyle ( "-fx-background-color: #FF5816" );
            visibilityButton.setText ( "Turn on chat" );
            visibility = 1;
        }

        else
        {
            visibilityButton.setStyle ( "-fx-background-color: #2FEE2A" );
            visibilityButton.setText ( "Turn off chat" );
            visibility = 0;
        }

        NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
        nu.write ( new Message ( username, null, 6 ) );
    }


    public void setOnlineNowUsersList ( ArrayList<String> onlineUsersList )
    {
        for ( String name : onlineUsersList )
        {
            if ( !name.equals ( username ) )
            {
                onlineNowUsersList.add ( name );
                System.out.println ( name );
            }
        }
    }

    public void update ( String newUser )
    {
        Platform.runLater ( () -> {

            if ( onlineNowUsersList.contains ( newUser ) )
            {
                onlineNowUsersList.remove ( newUser );
            }

            else
            {
                if ( !newUser.equals ( username ) )
                {
                    onlineNowUsersList.add ( newUser );
                }
            }

            showMessage ();
        } );
    }

    public void set ( ArrayList<String> onlineNowUsersList, Hashtable<String, ArrayList<String>> messages, ArrayList<String> myBlockList, ArrayList<String> blockedMe, ArrayList<String> communicationList )
    {
        setOnlineNowUsersList ( onlineNowUsersList );

        this.messages = messages;

        this.myBlockList = myBlockList;

        for ( String name : myBlockList )
        {
            blockedList.add ( name );
        }
        viewBlocked.setItems ( blockedList );

        this.blockedMeList = blockedMe;

        for ( String name : communicationList )
        {
            this.communicationList.add ( name );
        }
        viewCommunication.setItems ( this.communicationList );
    }
}

