package util;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Sajid Hasan on 12/3/2015.
 */
public class Message implements Serializable
{
    String sender;
    String receiver;
    String message;
    ObservableList<String> onlineNowUsers;

    public Message ( String sender, String receiver, String message )
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Message ( String message )
    {
        this. sender = "serverod876$%^$^ewlfgh9ewqieuqrwrwrqeuq132rfret5678iukyu&^%&6syqwu";
        this.message = message;
    }

    public Message (ObservableList<String> onlineNowUsers)
    {
        this. sender = "serverod876$%^$^ewlfgh9ewqieuqrwrwrqeuq132rfret5678iukyu&^%&6syqwu";
        this.onlineNowUsers = onlineNowUsers;
    }

    public String getSender ()
    {
        return sender;
    }

    public String getReceiver ()
    {
        return receiver;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setSender ( String sender )
    {
        this.sender = sender;
    }

    public void setReceiver ( String receiver )
    {
        this.receiver = receiver;
    }

    public void setMessage ( String message )
    {
        this.message = message;
    }
}
