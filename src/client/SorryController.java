package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Created by Sajid Hasan on 12/21/2015.
 */
public class SorryController
{
    @FXML
    private Label label;

    @FXML
    private Button backButton;

    private Stage st;

    public void setLabel(String s)
    {
        label.setText ( s );
    }

    public void setStage(Stage st)
    {
        this.st = st;
    }

    @FXML
    public void back( ActionEvent event)
    {
        st.close ();
    }

}
