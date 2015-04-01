package Control;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Laudelino
 */
public class Server extends Peer
{
    //books in the auction
    private final ArrayList<Book> auctionbooks;
    Calendar calendar;
    
    public Server(int id, String name, String ip, int port, boolean me)
    {
        super(id, name, ip, port, me);
        auctionbooks = new ArrayList<>();
    }
            
    public void registerBook(int bid, String name, String desc, 
            float startingBid, int auctionTime)
    {
        calendar = Calendar.getInstance();
        Book b;
        b = new Book();
        b.setId(bid);
        b.setName(name);
        b.setDesc(desc);
        b.setStartingBid(startingBid);
        b.setAuctionTime(auctionTime);

        calendar.add(Calendar.SECOND, b.getAuctionTime());

        b.setEndTimeAuction(calendar.getTime());

        auctionbooks.add(b);
        
        System.out.println(b.toString(1));
    }
    
    
    public void registerBid(int bookId, int clientId, float bid)
    {
       for(Book b : auctionbooks)
           if(b.getId() == bookId)
           {
               if(b.getCurrentBid() < bid && b.inAuction())
               {
                   b.setCurrentBid(bid);
                   b.getBids().add(new Bids(clientId, bid));
               }
           }
    }
}
