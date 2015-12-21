package server;

import util.NetworkUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class Info
{
    private String password;
    NetworkUtil nuConnection;
    NetworkUtil nuGetMessage;
    ArrayList<String> myBlockList;
    ArrayList<String> blockedMeList;
    ArrayList<String> communicationList;
    Hashtable<String, ArrayList<String>> messages = new Hashtable<> ();

    public Info ( String password, NetworkUtil nuConnection )
    {
        this.password = password;
        this.nuConnection = nuConnection;
    }

    public Info ( String password, NetworkUtil nuConnection, ArrayList<String> myBlockList, ArrayList<String> blockedMeList, ArrayList<String> communicationList)
    {
        this.password = password;
        this.nuConnection = nuConnection;
        this.myBlockList = myBlockList;
        this.blockedMeList = blockedMeList;
        this.communicationList = communicationList;
    }

    public void block(String name)
    {
        if (isBlocked( name ) == false)
        {
            myBlockList.add ( name );
        }
    }

    public void blockedBy (String name)
    {
        blockedMeList.add ( name );
    }

    public boolean isBlocked (String name)
    {
        return (myBlockList.contains ( name ) || blockedMeList.contains ( name ));
    }

    public void unblock(String name)
    {
        if (isBlocked( name ) == true)
        {
            if (myBlockList.contains ( name ))
            {
                myBlockList.remove ( name );
            }

            if ( blockedMeList.contains ( name ))
            {
                blockedMeList.remove ( name );
            }
        }
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword ( String password )
    {
        this.password = password;
    }

    public NetworkUtil getNuConnection ()
    {
        return nuConnection;
    }

    public void setNuConnection ( NetworkUtil nuConnection )
    {
        this.nuConnection = nuConnection;
    }

    public NetworkUtil getNuGetMessage ()
    {
        return nuGetMessage;
    }

    public void setNuGetMessage ( NetworkUtil nuGetMessage )
    {
        this.nuGetMessage = nuGetMessage;
    }

    public ArrayList<String> getmyBlockList ()
    {
        return myBlockList;
    }

    public void setmyBlockList ( ArrayList<String> blockList )
    {
        this.myBlockList = blockList;
    }

    public ArrayList<String> getBlockedMeList ()
    {
        return blockedMeList;
    }

    public void setBlockedMeList ( ArrayList<String> blockList )
    {
        this.blockedMeList = blockList;
    }

    public Hashtable<String, ArrayList<String>> getMessages ()
    {
        return messages;
    }

    public ArrayList<String> getCommunicationList()
    {
        return communicationList;
    }

    public void outgoingMessage( String receiver, String message)
    {
        if (!messages.containsKey ( receiver ))
        {
            messages.put ( receiver, new ArrayList<String> () );
            communicationList.add ( receiver );
        }

        ( messages.get ( receiver ) ).add ( "[Me" + " > " + receiver + " @ " + new Date () + " ]\n"  + message + "\n\n" );
    }

    public void incomingMessage(String sender, String message)
    {
        if (! messages.containsKey ( sender ) )
        {
            messages.put ( sender, new ArrayList<String> () );
            communicationList.add ( sender );
        }

        ( messages.get ( sender ) ).add ( "[" + sender + " > Me" + " @ " + new Date() + " ]\n" + message + "\n\n" );
    }
}
