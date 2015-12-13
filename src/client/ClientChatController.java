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
import java.net.InetAddress;
import java.util.ArrayList;


public class ClientChatController
{
    private ClientMain clientMain;
    private String sender;
    private String receiver;
    private String message;
    private String serverAddress;
    private Stage stage;
    Parent root;
    int visibility = 0;

    public void setServerAddress(String serverAddress)
    {
        this.serverAddress =  serverAddress ;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setRoot(Parent root)
    {
        this.root = root;
    }


    @FXML
    private ListView<String> onlineList = new ListView<> ();

    ObservableList<String> names = FXCollections.observableArrayList ();

    @FXML
    private TextArea receiverBox;

    @FXML
    private TextArea writerBox;

    @FXML
    private Button sendButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button signoutButton;

    @FXML
    private Button visibilityButton;

    @FXML
    private Label statusLabel;


    public void show()
    {
        onlineList.setItems ( names );

        stage.setTitle ( sender );
        stage.setScene ( new Scene ( root, 650, 600 ) );
        stage.show ();
    }



    public void setClientMain ( ClientMain clientMain )
    {
        this.clientMain = clientMain;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }





    @FXML
    void clear ( ActionEvent event )
    {
        writerBox.setText ( "" );
    }

    @FXML
    void send ( ActionEvent event ) throws Exception
    {
        receiver = onlineList.getSelectionModel ().getSelectedItem ();
        message = writerBox.getText ();

        if (!message.equals(null))
        {
            Message msg = new Message ( sender, receiver, message );

            NetworkUtil nu = new NetworkUtil ( serverAddress, 55555 );
            nu.write ( msg );
        }
    }


    @FXML
    void signout ( ActionEvent event ) throws Exception
    {
        clientMain.logout ();
    }

    @FXML
    void visibilityControl()
    {


        if (visibility == 0)
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

        clientMain.visibilityControl();

    }




    public void showMessage ( String sender, String message )
    {
        String all = receiverBox.getText ();
        receiverBox.setText ( all +"\n\n" + sender + " says:\n" + message );
    }


    public void setOnlineUsersList(ArrayList<String> onlineUsersList)
    {
        for ( String name:onlineUsersList)
        {
            if (!name.equals ( sender ))
            {
                names.add ( name );
                System.out.println (name);
            }
        }
    }

    public void update(String newUser)
    {
        Platform.runLater ( () -> {

            if (names.contains ( newUser ))
            {
                names.remove ( newUser );
            }

            else
            {
                if (!newUser.equals ( sender ))
                {
                    names.add ( newUser );
                }
            }
        } );
    }
}

