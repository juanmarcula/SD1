package Control;


/**
 *
 * @author Laudelino
 */
public class Bids 
{
    private int clientId;
    private double bid;

    public Bids(int clientId, double bid)
    {
        this.clientId = clientId;            
        this.bid = bid;
    }
    /**
     * @return the clientId
     */
    public int getClientId() 
    {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(int clientId) 
    {
        this.clientId = clientId;
    }

    /**
     * @return the bid
     */
    public double getBid() 
    {
        return bid;
    }

    /**
     * @param bid the bid to set
     */
    public void setBid(double bid) 
    {
        this.bid = bid;
    }
    
}
