package client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import util.ConnectionInfo;
import util.NetworkUtil;

public class NewAccountController
{

    private ClientMain main;
    private String gender;
    private String serverAddress;

    public void setClientMain(ClientMain main)
    {
        this.main = main;
    }

    public void setServerAddress ( String serverAddress )
    {
        this.serverAddress = serverAddress;
    }

    @FXML
    private TextField usernameBox;

    @FXML
    private TextField passwordBox;

    @FXML
    private TextField emailBox;

    @FXML
    private TextField emailBox2;

    @FXML
    private TextField passwordBox2;

    @FXML
    private RadioButton male;

    @FXML
    private ToggleGroup genderToggle;

    @FXML
    private RadioButton female;


    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Button createButton;

    @FXML
    public void genderMale()
    {
        gender = "male";
    }

    @FXML
    public void genderFemale()
    {
        gender = "female";
    }


    @FXML
    public void create()throws Exception
    {
        if (!emailBox.getText ().equals ( emailBox2.getText () ))
        {
            emailBox2.setStyle ( "-fx-control-inner-background: #FF0000" );
            emailBox2.setText ( "Emails don't match !" );
        }

        if(!passwordBox.getText ().equals ( passwordBox2.getText () ))
        {
            passwordBox2.setStyle ( "-fx-control-inner-background: #FF0000" );
            passwordBox2.setText ( "Passwords don't match !" );
        }

        if (emailBox.getText ().equals ( emailBox2.getText () ))
        {
            if(passwordBox.getText ().equals ( passwordBox2.getText () ))
            {
                ConnectionInfo connectionInfo = new ConnectionInfo ( "new account", usernameBox.getText (), passwordBox.getText (), emailBox.getText (), gender );

                main.setUsername ( usernameBox.getText () );
                main.setPassword ( passwordBox.getText () );

                NetworkUtil nuConnection = new NetworkUtil ( serverAddress, 33333 );

                nuConnection.write ( connectionInfo );
                new Threads.ConnectionThread ( nuConnection, main );
            }
        }
    }

    public void usernameConflict()
    {
        usernameBox.setStyle ("-fx-control-inner-background: #FF0000" );
        usernameBox.setText ( "Username already exists !" );
    }

}
