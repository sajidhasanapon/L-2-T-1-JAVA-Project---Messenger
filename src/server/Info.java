package server;

import util.NetworkUtil;

public class Info
{
    private String password;
    NetworkUtil nu;
    String clientAddress;

    public Info ( String password, NetworkUtil nu , String clientAddress)
    {
        this.password = password;
        this.nu = nu;
    }

    public String getPassword ()
    {
        return password;
    }

    public NetworkUtil getNu ()
    {
        return nu;
    }

    public void setNu ( NetworkUtil nu )
    {
        this.nu = nu;
    }

    public String getClientAddress ()
    {
        return clientAddress;
    }

    public void setClientAddress ( String clientAddress )
    {
        this.clientAddress = clientAddress;
    }
}
