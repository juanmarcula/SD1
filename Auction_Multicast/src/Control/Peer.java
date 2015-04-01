package Control;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laudelino
 */
public class Peer 
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
    
    public Peer(int id, String name, String ip, int port, boolean me)
    {
        this.id = id;
        this.name = name;
        this.priority = (int) Math.ceil(Math.random()*100);
        this.ip = ip;
        this.port = port;
        this.isMyself = me;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return the isServer
     */
    public boolean isIsServer() {
        return isServer;
    }

    /**
     * @param isServer the isServer to set
     */
    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }

    /**
     * @return the isAuctioneer
     */
    public boolean isIsAuctioneer() {
        return isAuctioneer;
    }

    /**
     * @param isAuctioneer the isAuctioneer to set
     */
    public void setIsAuctioneer(boolean isAuctioneer) {
        this.isAuctioneer = isAuctioneer;
    }

    /**
     * @return the isMyself
     */
    public boolean isIsMyself() {
        return isMyself;
    }

    /**
     * @param isMyself the isMyself to set
     */
    public void setIsMyself(boolean isMyself) {
        this.isMyself = isMyself;
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
