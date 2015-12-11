package server;

import util.NetworkUtil;

public class Info
{
    private String password;
    NetworkUtil nu;

    public Info ( String password, NetworkUtil nu)
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
}
