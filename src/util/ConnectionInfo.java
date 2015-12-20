package util;

import util.NetworkUtil;
import java.io.Serializable;
import java.net.InetAddress;

public class ConnectionInfo implements Serializable
{
    private String connectionType;
    private String username;
    private String password;
    private String email;
    private String gender;

    public ConnectionInfo ( String connectionType, String username, String password)throws Exception
    {
        this.connectionType = connectionType;
        this.username = username;
        this.password = password;
    }

    public ConnectionInfo ( String connectionType, String username, String password, String email, String gender )
    {
        this.connectionType = connectionType;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
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

    public String getEmail ()
    {
        return email;
    }

    public void setEmail ( String email )
    {
        this.email = email;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender ( String gender )
    {
        this.gender = gender;
    }
}



