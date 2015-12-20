package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import util.ConnectionInfo;
import util.NetworkUtil;

public class ConnectionController
{

    String username;
    String password;
    ClientMain main;
    String serverAddress;

    public void setClientMain ( ClientMain clientMain )
    {
        this.main = clientMain;
    }

    public void setServerAddress ( String serverAddress )
    {
        this.serverAddress = serverAddress;
    }

    @FXML
    private Button connectButton;

    @FXML
    private TextField clientName;

    @FXML
    private PasswordField passwordField;

    @FXML
    void newAccount ( ActionEvent event )throws Exception
    {
        main.newAccount();
    }

    @FXML
    void connect ( ActionEvent event ) throws Exception
    {
        username = clientName.getText ();
        password = passwordField.getText ();

        main.setUsername ( username );
        main.setPassword ( password );

        ConnectionInfo connectionInfo = new ConnectionInfo ( "existing", username, password);
        NetworkUtil nuConnection = new NetworkUtil ( serverAddress, 33333 );

        nuConnection.write ( connectionInfo );
        new Threads.ConnectionThread ( nuConnection, main );
    }
}

