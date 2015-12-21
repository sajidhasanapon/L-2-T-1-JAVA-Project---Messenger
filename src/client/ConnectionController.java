package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    @FXML
    public void passwordRecovery(ActionEvent event)
    {
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation ( getClass ().getResource ( "Sorry.fxml" ) );

        try {
            Parent root = loader.load ();
            Stage st = new Stage ();

            SorryController sorry = loader.getController ();
            sorry.setLabel("Sorry, we don't have a passoword recovery option yet.");
            sorry.setStage(st);
            st.setTitle ( username );
            st.setScene ( new Scene ( root, 700, 500 ) );
            st.show ();
        }

        catch ( Exception e ) {
            System.out.println ( "EXCEPTION : ClientMain/showChatScreen" );
            e.printStackTrace ();
        }

    }
}

