package Control;

import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laudelino
 */
public class Peer implements Runnable
{
    private int id;
    private String name;
    private int priority;
    private boolean isServer;
    private boolean isAuctioneer;
    private boolean isMyself;
    private int timeOutCounter;
    private String ip;
    private int port;
    private PublicKey publicKey;
    
    private Communication cmm;
    
    public Peer(String name, String ip, int port, boolean main)
    {
        this.name = name;
        this.setIp(ip);
        this.setPort(port);
        
        if(main)
            this.cmm = new Communication();
    }

    @Override
    public void run()
    {
        for(int i = 0; i < 10; i++)
        {
            sleep(100);
            //Send hello message
            cmm.sendMulticast("0;" + this.getName() + ";" + 
                    this.getIp(1) + ";" + this.getPort() + ";");
        }
        //sleep(1000);
        //verifica se é o de maior prioridade, se for, send server
        if(isHighestPriority())
            cmm.sendMulticast("1;" + this.getPort() + ";");
        
        //wait for a server to be elected
        int dt = 0;
        while(getServer() == null && dt < 1000)
        {
            sleep(10);
            dt+=10;
        }

    }

    //Peers ops
    public boolean isHighestPriority()
    {
        int max = -1;
        int mbs = -1;
        for(Peer p : cmm.getPeers())
            if(p.getPort()> max)
                mbs = p.getPort();
        
        if(mbs == this.port)
            return true;
        else
            return false;
    }
    
    public Peer getServer()
    {
        try
        {
             for(Peer p : peers)
             {
                 if(p.isServer == true)
                     return p;
             }
             return null;
        }
        catch(Exception e)
        {
            System.out.println("getserver " + e.toString());
            return null;
        }
    }

    public void setAsServer()
    {
        this.isServer =  true;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param isServer the isServer to set
     */
    public void setServer(int port) 
    {
        for(Peer p : cmm.getPeers())
            if(p.getPort() == port)
                p.setAsServer();
        
    }

    /**
     * @return the timeOutCounter
     */
    public int getTimeOutCounter() {
        return timeOutCounter;
    }

    /**
     * @param timeOutCounter the timeOutCounter to set
     */
    public void setTimeOutCounter(int timeOutCounter) {
        this.timeOutCounter = timeOutCounter;
    }

    /**
     * @return the ip
     */
    public InetAddress getIp() {
        try 
        {
            return InetAddress.getByName(ip);
        } 
        catch (UnknownHostException ex) 
        {   
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * @param a
     * @return the ip
     */
    public String getIp(int a) 
    {
        return this.ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the publicKey
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
    
    

    
}
