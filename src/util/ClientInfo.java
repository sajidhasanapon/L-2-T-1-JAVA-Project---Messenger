package util;

import util.NetworkUtil;
import java.io.Serializable;
import java.net.InetAddress;

public class ClientInfo implements Serializable
{
    private String connectionType;
    private String username;
    private String password;
    String address;

    public ClientInfo ( String connectionType, String username, String password, String address)throws Exception
    {
        this.connectionType = connectionType;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public String getConnectionType ()
    {
        return connectionType;
    }

    public String getUsername ()
    {
        return username;
    }

    public String getPassword ()
    {
        return password;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setUsername ( String username )
    {
        this.username = username;
    }

    public void setConnectionType ( String connectionType )
    {
        this.connectionType = connectionType;
    }

    public void setPassword ( String password )
    {
        this.password = password;
    }


}



