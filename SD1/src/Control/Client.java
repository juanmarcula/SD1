package Control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Essa classe gerencia o cliente; Ela pega uma lista de livros que o cliente
 * esta interessado (De 3 a 6 livros) de AuctionBooks
 * @author Laudelino
 */
public class Client extends Peer
{
    private final ArrayList<Book> books;
    //books that im selling
    private final ArrayList<Book> auctionBooks;
    private AuctionBooks followingBooks;
    private float wallet;
    
    public Client(int id, String name, String ip, int port, boolean me)
    {
        super(id, name, ip, port, me);
        followingBooks = new AuctionBooks();
        books = followingBooks.getFollowingBooks();
        auctionBooks =  new ArrayList<>();
    }   

    /**
     * @return the wallet
     */
    public float getWallet() {
        return wallet;
    }

    /**
     * @param wallet the wallet to set
     */
    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
    
    public void registerBook(int id, String name, String desc, 
            float startingBid)
    {
        Book b;
        b = new Book();
        b.setId(id);
        b.setName(name);
        b.setDesc(desc);
        b.setStartingBid(startingBid);
        
        auctionBooks.add(b);
        
        System.out.println(b.toString(1));
    }
    
    
    public void setCurrentBid(String bookName, float currentBid)
    {
        for (Book book : books) {
            if (bookName.equals(book.getName())) {
                book.setCurrentBid(currentBid);
            }
        }
    }
    
    
    /**
     * Cliente decide se aumenta o lance no livro
     * Adicionar inteligencia
     * @param id
     * @param currentBid
     * @return
     */
    public float newBid(int id, float currentBid)
    {
        Random rand = new Random();
        if(currentBid >= books.get(id).getMaxBid() || currentBid >= getWallet())
            return -1;
        else if(books.get(id).getMaxBid() > getWallet())
            return ((getWallet()-currentBid)*rand.nextFloat() + currentBid);
        else
            return ((books.get(id).getMaxBid()-currentBid)*rand.nextFloat() 
                    + currentBid);
    }
    
    public float newBid(String bookName, float currentBid, float bid)
    {
        int id = 0;
        for (Book book : books) {
            if (bookName.equals(book.getName())) {
                id = book.getId();
            }
        }
        return 0;
    }
}
