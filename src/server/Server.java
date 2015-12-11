package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.ServerSocket;


public class Server extends Application
{

    private ServerSocket servSock;
    private Stage stage;
    Parent root;
    ServerController serverController;

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
        stage = primaryStage;
        setServerController ();
        serverController.showServer();

        try
        {
            new ServerConnectionThread (serverController);
            new ServerLogoutThread (serverController);
            new ServerCommunicationThread (serverController);
        }
        catch ( Exception e )
        {
            System.out.println ( "Problem in starting the server" + e );
        }
    }

    public void setServerController () throws Exception
    {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation ( getClass ().getResource ( "ServerScreen.fxml" ) );
        root = loader.load ();

        // Loading the controller
        serverController = loader.getController ();
        serverController.setStage ( stage );
        serverController.setRoot(root);
    }

    public static void main ( String[] args )
    {
        launch ( args );
    }
}
