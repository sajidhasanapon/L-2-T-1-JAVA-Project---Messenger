package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import util.Message;
import util.NetworkUtil;
import java.net.InetAddress;


public class ClientChatController
{
    private ClientMain clientMain;
    private String sender;
    private String receiver;
    private String message;
    private String serverAddress;
    private Stage stage;
    Parent root;

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


    public void show()
    {
        names.add("C1");
        onlineList.setItems ( names );
        names.add("C2");
        onlineList.setItems ( names );
        names.add("C3");

        onlineList.setItems ( names );

        stage.setTitle ( sender );
        stage.setScene ( new Scene ( root, 700, 500 ) );
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

            NetworkUtil nu = new NetworkUtil ( serverAddress, 55566 );
            nu.write ( msg );
        }
    }


    @FXML
    void signout ( ActionEvent event ) throws Exception
    {
        clientMain.logout ();
    }




    public void showMessage ( String sender, String message )
    {
        String all = receiverBox.getText ();
        receiverBox.setText ( all +"\n\n" + sender + " says:\n" + message );
    }


    public void setOnlineList(String name)
    {
        names.add(name);
    }

}

