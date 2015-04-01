package Control;
import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;
/**
 * Essa classe gerencia o leiloeiro; Ela pega uma lista de livros a ser 
 * leiloados (De 4 a 8 livros) de AuctionBooks
 * @author Laudelino
 */
public class Auctioneer extends Peer
{
    private final ArrayList<Book> books;
    private AuctionBooks auctionBooks;
    
    public Auctioneer(int id, String name, String ip, int port, boolean me)
    {
        super(id, name, ip, port, me);
        auctionBooks = new AuctionBooks();
        books = auctionBooks.getAuctionBooks();
    }   
    
    /**
     * Leiloeiro decide de encerra ou nao a venda do livro
     * Adicionar inteligencia
     * @param id
     * @param currentBid
     * @return
     */
    public boolean endBookAuction(int id, float currentBid)
    {
        if(currentBid >= books.get(id).getExpectedBid())
            return true;
        else 
            return false;
    }
}
