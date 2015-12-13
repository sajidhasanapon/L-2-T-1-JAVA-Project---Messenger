package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import util.ClientInfo;
import util.NetworkUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClientMain extends Application
{

    Stage stage;
    String serverAddress;
    ClientConnectionThread thread;
    NetworkUtil nu;
    String username;
    String password;
    ClientChatController waiter;
    ArrayList<String> onlineNowUsersList;
    SplashScreen splashScreen;

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
        stage = primaryStage;
        new TestSplashScreen ().initUI ();
        Thread.sleep ( 3000 );
        showLoginScreen ();
    }


    public void showLoginScreen ()
    {
        try
        {
            // XML Loading using FXMLLoader
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation ( getClass ().getResource ( "ClientConnector.fxml" ) );
            Parent root = loader.load ();

            // Loading the controller
            ClientConnectionController clientConnectionController = loader.getController ();
            clientConnectionController.setClientMain ( this );

            // Set the primary stage
            //stage.setTitle ( "Connector" );
            stage.setScene ( new Scene ( root, 590, 300 ) );
            stage.show ();
        }

        catch ( Exception e )
        {
            System.out.println ( "Prroblem in clientMain\\ClientMain\\showConnectionScreen" + e );
        }
    }

    public void establishConnection(String connectionType, String username, String password, String serverAddress)throws Exception
    {
        ClientInfo clientInfo = new ClientInfo ( connectionType, username, password);

        //String serverAddress = ;
        this.serverAddress = serverAddress;
        int serverPort = 33333;
        nu = new NetworkUtil ( serverAddress, serverPort );

        this.username = username;
        this.password = password;
        nu.write ( clientInfo );
        thread = new ClientConnectionThread ( nu, this );
    }




    public void showChatScreen ()
    {
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation ( getClass ().getResource ( "ClientChatScreen.fxml" ) );
        //Parent root = loader.load();

        try
        {
            Parent root = loader.load ();

            waiter = loader.getController ();
            waiter.setClientMain ( this );
            waiter.setSender ( username );
            waiter.setServerAddress ( serverAddress );
            waiter.setStage(stage);
            waiter.setRoot ( root );
            waiter.setOnlineUsersList ( onlineNowUsersList);

            waiter.show();
        }

        catch ( Exception e )
        {
            System.out.println ( "EXCEPTION : ClientMain/showChatScreen" );
        }

        //new AcceptThread ( username, waiter );

    }


    public void logout ()throws Exception
    {
        showLoginScreen ();

        NetworkUtil nc = new NetworkUtil (serverAddress, 44444);
        nc.write ("0" + username);

        Platform.runLater ( () -> {
            Alert alert = new Alert ( Alert.AlertType.WARNING );
            alert.setTitle ( "Logging out" );
            alert.setHeaderText ( "Successfully Logged Out" );
            alert.setContentText ( "" );
            alert.showAndWait ();

            nu.closeConnection ();
        } );
    }

    public void visibilityControl()
    {
        NetworkUtil nc = new NetworkUtil (serverAddress, 44444);
        nc.write ("1" + username);
    }

    public void invalidLogin ()
    {
        Platform.runLater ( () ->{

            Alert alert = new Alert ( Alert.AlertType.ERROR );
            alert.setTitle ( "Incorrect Credentials" );
            alert.setHeaderText ( "Incorrect Credentials" );
            alert.setContentText ( "The username and password you provided is not correct." );
            alert.showAndWait ();
        });
    }

    public void closePrevious(NetworkUtil nu)
    {
        Platform.runLater ( () -> {

            Alert alert = new Alert ( Alert.AlertType.WARNING );
            alert.setTitle ( "" );
            alert.setHeaderText ( "LOGGING OUT ..." );
            alert.setContentText ( "New login from another device. Logging out from here..." );
            alert.showAndWait ();

            nu.closeConnection ();
            showLoginScreen ();
        } );

    }

    public  void welcomeBack()
    {
        Platform.runLater ( () -> {

            Alert alert = new Alert ( Alert.AlertType.WARNING );
            alert.setTitle ( "" );
            alert.setHeaderText ( "Welcome back, " + username );
            alert.setContentText ( "" );
            alert.showAndWait ();

            showChatScreen ();
        } );

    }

    public void signUp()
    {
        Platform.runLater ( () -> {

            Alert alert = new Alert ( Alert.AlertType.WARNING );
            alert.setTitle ( "" );
            alert.setHeaderText ( "Hello, " + username );
            alert.setContentText ( "" );
            alert.showAndWait ();

            showChatScreen ();
        } );
    }

    public void newLogin()
    {
        Platform.runLater ( () -> {

            Alert alert = new Alert ( Alert.AlertType.WARNING );
            alert.setTitle ( "" );
            alert.setHeaderText ( "Welcome back, " + username + "\nYou were logged in another device" );
            alert.setContentText ( "Previous Session Terminated" );
            alert.showAndWait ();

            showChatScreen ();
        } );
    }

    public void occupied()
    {
        Platform.runLater ( () -> {

            Alert alert = new Alert ( Alert.AlertType.WARNING );
            alert.setTitle ( "" );
            alert.setHeaderText ( "This username already exists !" );
            alert.setContentText ( "Try a new username." );
            alert.showAndWait ();
        } );
    }

    public  void showMessage(String sender, String message)
    {
        waiter.showMessage(sender, message);
    }

    public void setOnlineUsersList( ArrayList<String> onlineNowUsersList)
    {
        this.onlineNowUsersList = new ArrayList<> ( onlineNowUsersList );
    }

    public void updateOnlineUsersList( String newUser)
    {
        onlineNowUsersList.add ( newUser );
        waiter.update(newUser);
    }


    public static void main ( String[] args )
    {
        launch ( args );
    }
}
