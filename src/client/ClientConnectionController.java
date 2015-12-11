package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.ClientInfo;
import util.NetworkUtil;

public class ClientConnectionController
{

    String username;
    String password;
    String serverAddress = "127.0.0.1";
    String connectionType = "existing";
    NetworkUtil nu;
    Stage stage;
    Parent root;
    ClientChatController waiter;
    ClientInfo clientInfo;
    ClientMain clientMain;
    ClientConnectionThread thread;

    public void setClientMain ( ClientMain clientMain )
    {
        this.clientMain = clientMain;
    }

    @FXML
    private Button connectButton;

    @FXML
    private TextField clientName;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton signup;

    @FXML
    private ToggleGroup connectionSelecter;

    @FXML
    private RadioButton login;

    @FXML
    void newAccount ( ActionEvent event )
    {
        connectionType = "new account";
    }

    @FXML
    void oldAccount ( ActionEvent event )
    {
        connectionType = "existing";
    }

    @FXML
    void connect ( ActionEvent event ) throws Exception
    {
        username = clientName.getText ();
        password = passwordField.getText ();

        clientMain.establishConnection ( connectionType, username, password, serverAddress);
    }
}

