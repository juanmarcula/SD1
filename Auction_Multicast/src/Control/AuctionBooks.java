package Control;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Laudelino
 */
public class AuctionBooks 
{
    private final String[] booksNames;
    private final String[] booksDesc;
    private final ArrayList<Book> books;
    private Random rand;
    
    public AuctionBooks()
    {
        this.rand = new Random();
        this.booksDesc = new String[]{"The Hobbit", "Lord of the Rings", 
            "Harry Potter", "Game of Thrones", "A Dance With Dragons", 
            "A Dream of Spring", "Dom Casmurro", "The Name of the Wind", 
            "Dragoes de Ether", "The First Law", "The Broken Empire", "Eragon"};
        
        this.booksNames = new String[]{"The Hobbit", "Lord of the Rings", 
            "Harry Potter", "Game of Thrones", "A Dance With Dragons", 
            "A Dream of Spring", "Dom Casmurro", "The Name of the Wind", 
            "Dragoes de Ether", "The First Law", "The Broken Empire", "Eragon"}; 
        
        books = new ArrayList<>();
    }    
    
    public ArrayList<Book> getFollowingBooks()
    {
        Book b;
        int nBooks = rand.nextInt(3) + 3;
        
        System.out.println("Livros a serem leiloados: ");
        for(int i = 0; i<nBooks; i++)
        {
            boolean alreadyHas = false;
            int nextBook;
            do
            {
                nextBook = rand.nextInt(12);
                for(int j = 0; j < books.size(); j++)
                    if(booksNames[nextBook].equals(books.get(j).getName()))
                        alreadyHas = true;
            }while(alreadyHas);
            
            b = new Book();
            b.setId(i);
            b.setName(booksNames[nextBook]);
            b.setDesc(booksDesc[nextBook]);
            b.setMaxBid(50*rand.nextFloat() + 20);
            
            books.add(b);
            System.out.println(b.toString(1));
        }      
        return books;
    }
    
    public ArrayList<Book> getAuctionBooks()
    {
        Book b;
        int nBooks = rand.nextInt(4) + 4;
        
        System.out.println("Livros a serem leiloados: ");
        for(int i = 0; i<nBooks; i++)
        {
            boolean alreadyHas = false;
            int nextBook;
            do
            {
                nextBook = rand.nextInt(12);
                for(int j = 0; i < books.size(); i++)
                    if(booksNames[nextBook].equals(books.get(j).getName()))
                        alreadyHas = true;
            }while(alreadyHas);
            
            b = new Book();
            b.setId(i);
            b.setName(booksNames[nextBook]);
            b.setDesc(booksDesc[nextBook]);
            b.setStartingBid(30*rand.nextFloat() + 10);
            b.setAuctionTime(rand.nextInt(50) + 10);
            
            books.add(b);
            System.out.println(b.toString(1));
        } 
        return books;
    }
        
    
}
