package util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sajid Hasan on 12/3/2015.
 */
public class Message implements Serializable
{
    String sender;
    String receiver;
    String message;
    ArrayList<String> onlineNowUsersList;
    String newUser;

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

    public Message (String message, ArrayList<String> onlineNowUsersList)
    {
        this. sender = "serverod876$%^$^ewlfgh9ewqieuqrwrwrqeuq132rfret5678iukyu&^%&6syqwu";
        this.message = message;
        this.onlineNowUsersList = new ArrayList<String> (onlineNowUsersList);
    }

    public Message ( String message, String newUser )
    {
        this. sender = "serverod876$%^$^ewlfgh9ewqieuqrwrwrqeuq132rfret5678iukyu&^%&6syqwu";
        this.message = message;
        this.newUser = newUser;
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

    public ArrayList getOnlineNowUsersList()
    {
        return onlineNowUsersList;
    }

    public String getNewUser()
    {
        return newUser;
    }
}
