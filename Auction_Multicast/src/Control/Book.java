package Control;

import Control.Bids;

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Laudelino
 */
public class Book 
{
    private int id;
    private String name;
    private String desc;
    private double startingBid;
    private double expectedBid;
    private double maxBid;
    private double currentBid;
    private int auctionTime;
    private Date endTimeAuction;
    private boolean inAuction = true;
    private final ArrayList<Integer> following;
    private final ArrayList<Bids> bids;
    private int ownerId;
    
    public Book()
    {
        following = new ArrayList<>();
        bids = new ArrayList<>();
    }
    
    /**
     * @return the id
     */
    public int getId() 
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) 
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * @return the desc
     */
    public String getDesc() 
    {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) 
    {
        this.desc = desc;
    }

    /**
     * @return the startingBid
     */
    public double getStartingBid() 
    {
        return startingBid;
    }

    /**
     * @param startingBid the startingBid to set
     */
    public void setStartingBid(double startingBid) 
    {
        this.startingBid = startingBid;
    }

    /**
     * @return the expectedBid
     */
    public double getExpectedBid() {
        return expectedBid;
    }

    /**
     * @param expectedBid the expectedBid to set
     */
    public void setExpectedBid(double expectedBid) {
        this.expectedBid = expectedBid;
    }

    /**
     * @return the maxBid
     */
    public double getMaxBid() {
        return maxBid;
    }

    /**
     * @param maxBid the maxBid to set
     */
    public void setMaxBid(double maxBid) {
        this.maxBid = maxBid;
    }

    /**
     * @return the currentBid
     */
    public double getCurrentBid() {
        return currentBid;
    }

    /**
     * @param currentBid the currentBid to set
     */
    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }
    
    
    @Override
    public String toString()
    {        
        return String.valueOf(getBids().get(getBids().size()-1).getClientId() 
            + ";" + getId()) + ";" + getName() + ";" + getDesc() + ";"
            + String.format("%1.2f", getBids().get(getBids().size()-1).getBid()); 
    }
    
    public String toString(int a)
    {
        return String.valueOf(getId()) + ";" + getName() + ";" + getDesc() + ";"
                + String.format("%1.2f", getStartingBid()) + ";" + 
                        String.valueOf(getAuctionTime());
    }

    /**
     * @return the auctionTime in seconds
     */
    public int getAuctionTime() {
        return auctionTime;
    }

    /**
     * @param auctionTime the auctionTime to set in seconds
     */
    public void setAuctionTime(int auctionTime) 
    {
        this.auctionTime = auctionTime;
    }

    /**
     * @return the endTimeAuction
     */
    public Date getEndTimeAuction() 
    {
        return endTimeAuction;
    }

    /**
     * @param endTimeAuction the endTimeAuction to set
     */
    public void setEndTimeAuction(Date endTimeAuction) 
    {
        this.endTimeAuction = endTimeAuction;
    }

    /**
     * @return the inAuction
     */
    public boolean inAuction() 
    {
        return inAuction;
    }

    /**
     */
    public void endAuction() 
    {
        this.inAuction = false;
    }

    /**
     * @return the following
     */
    public ArrayList<Integer> getFollowing() 
    {
        return following;
    }

    /**
     * @return the bids
     */
    public ArrayList<Bids> getBids() 
    {
        return bids;
    }

    /**
     * @return the ownerId
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
