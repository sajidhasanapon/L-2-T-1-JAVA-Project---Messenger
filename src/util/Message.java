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
    int block = 0;

    public Message ( String sender, String receiver, String message )
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Message ( String sender, String receiver, int block )
    {
        this.sender = sender;
        this.receiver = receiver;
        this.block = block;
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

    public int getBlock ()
    {
        return block;
    }
}
