package Control;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;



/**
 *
 * @author Laudelino
 */
public class Communication implements Runnable
{
    private Peer myself;
    private Server server;
    private ArrayList<Peer> peers;
    
    private MulticastSocket mcSocket;
    private final String MULTICAST_IP = "228.5.6.7";
    private InetAddress MULTICAST_GROUP;
    private final int MULTICAST_PORT = 6789;
    
    private DatagramSocket ucSocket;
    
    Encryption crypto;
    
    public Communication()
    {
        Thread multicastListener, unicastListener;
        
        
        multicastListener = new Thread()
        {
            @Override
            public void run()
            {
		try 
                {                       
			while(true)
                        {
                            byte[] buffer = new byte[1000];
                            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                            mcSocket.receive(messageIn);
                            System.out.println("Received:" + new String(messageIn.getData()));
                        }	
		}
                catch (SocketException e)
                {
                    System.out.println("Socket: " + e.getMessage());
		}
                catch (IOException e)
                {
                    System.out.println("IO: " + e.getMessage());
		}
                finally 
                {
                    if(mcSocket != null) 
                        mcSocket.close();
                }
            }
        };
        multicastListener.start();
        
        unicastListener = new Thread()
        {
            @Override
            public void run()
            {
		try
                {
                    byte[] buffer;
                    while(true)
                    {
                        buffer = new byte[1000];
                        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                        ucSocket.receive(request);     
                    }
		}
                catch (SocketException e)
                {
                    System.out.println("Socket: " + e.getMessage());
		}
                catch (IOException e) 
                {
                    System.out.println("IO: " + e.getMessage());
		}
                finally 
                {
                    if(ucSocket != null) 
                        ucSocket.close();
                }
            }
        };
        unicastListener.start();
    }
    
    
    @Override
    public void run()
    {
        for(int i = 0; i < 10; i++)
        {
            sleep(100);
            //Send hello message
            this.sendMulticast("0;" + this.getName() + ";" + 
                    this.getIp(1) + ";" + this.getPort() + ";");
        }
        //sleep(1000);
        //verifica se é o de maior prioridade, se for, send server
        if(isHighestPriority())
            this.sendMulticast("1;" + this.getPort() + ";");
        
        //wait for a server to be elected
        int dt = 0;
        while(getServer() == null && dt < 1000)
        {
            sleep(10);
            dt+=10;
        }

    }
    public void initializeSockets()
    {
        try 
        {
            //config sockets
            MULTICAST_GROUP = InetAddress.getByName(MULTICAST_IP);
            mcSocket = new MulticastSocket(MULTICAST_PORT);
            mcSocket.joinGroup(MULTICAST_GROUP);
            ucSocket = new DatagramSocket(myself.getPort());
        }
        catch (UnknownHostException ex) 
        {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * When a package is received on the multicast thread, this "event" is 
     * called, it process the message and take the appropriate approach. 
     * @param in
     */
    public void onMulticastMessage(DatagramPacket in)
    {
        String[] ins = new String(crypto.decryptMsg(p.getPublicKey(),in.getData())).split(";");
        
        switch(Integer.parseInt(ins[0]))
        {
            case 0:
                msgHelloEvent(ins); break;
            case 1:
                break;
        }
        
    }
    
    public void msgHelloEvent(String[] msg)
    {
        if(this.getPeerByPort(Integer.parseInt(msg[1])) == null)
        {
            Peer p = new Peer(msg[2], msg[3], Integer.parseInt(msg[1]), false);
            peers.add(p);
        }
        else if(this.getPeerByPort(Integer.parseInt(msg[1])).getServer().getPort() == Integer.parseInt(msg[1]))
        {
            //msg de hello do servidor, que faz?
            
        }
    }
    
    
    public void sendMulticast(String msg)
    {
        byte [] m = msg.getBytes();
        DatagramPacket out;
        out = new DatagramPacket(m, m.length, MULTICAST_GROUP, MULTICAST_PORT);
        try 
        {
            mcSocket.send(out);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**     
     * When a package is received on the unicast thread, this "event" is 
     * called, it process the message and take the appropriate approach.
     * @param in
     */
    public void onUnicastMessage(DatagramPacket in)
    {
        String[] ins = new String(in.getData()).split(";");
        
        switch(Integer.parseInt(ins[0]))
        {
            case 10:
                msgBid(ins); break;
            case 11:
                msgAuctionBook(ins); break;
            case 12:
                msgEndAuction(ins); break;
        }
    }
    

    
    
    public void sendUnicast(Peer p, String msg)
    {
        byte [] m = msg.getBytes();
        DatagramPacket out;
        out = new DatagramPacket(m, m.length, p.getIp(), p.getPort());
        try 
        {
            ucSocket.send(out);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sleep(int ms)
    {
        try 
        {
            Thread.sleep(ms);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Peer> getPeers()
    {
        return peers;
    }
    
    public Peer getPeerByPort(int port)
    {
        for(Peer p : this.getPeers())
        {
            if(p.getPort() == port)
                return p;
        }
        return null;
    }

    
    public class Server
    {
        //books in the auction
        private final ArrayList<Book> auctionbooks;
        Calendar calendar;
        int ids = 0;

        public Server()
        {
            auctionbooks = new ArrayList<>();
            
            for(Peer p : peers)
            {
                msgAskPublicKey(p);                
            }
            
            
        }

        public void msgAskPublicKey(Peer p)
        {
            sendUnicast(p, "17;");
        }
        
        
        
        public void msgBid(String[] msg)
        {
            this.registerBid(Integer.parseInt(msg[2]), Integer.parseInt(msg[1]), 
                    Double.parseDouble(msg[3]));
        }

        public void msgAuctionBook(String[] msg)
        {

            this.registerBook(ids, msg[2], msg[4],Double.parseDouble(msg[3]), 
                     Integer.parseInt(msg[5]), Integer.parseInt(msg[1]));
            ids++;
        }

        public void msgEndAuction(String[] msg)
        {
            Book b = this.getBookById(Integer.parseInt(msg[2]));
            if(b.getOwnerId() == Integer.parseInt(msg[1]))
            {
                b.endAuction();
                //send msg para todos os que estão seguindo o livro - acabou
               
            }
        }

        /**
         * Envia uma atualização do livro para os seguidores desse livro
         * @param b
         */
        public void sendToFollowers(Book b)
        {
        
        }
        
        /**
         * envia uma atualização do livro para quem botou ele a venda
         * @param b
         */
        public void sendToAuctioneer(Book b)
        {
        
        }
        
        /**
         *Quando alguem deseja ver todos os livros no servidor, envia tudo
         */
        public void sendAll()
        {
        
        }      
        
        /**
         * no Run de cmm, depois do procedimento normal, se esse Peer for o server,
         * a cada x s, ele chama esse metodo, que verifica se já deve encerrar o
         * leilão desse livro por tempo
         */
        public void updateList()
        {
            for(Book b : auctionbooks)
            {
                if(Calendar.getInstance().after(b.getEndTimeAuction()))
                    b.endAuction();
            }
                
        }
        
        
        public Book getBookById(int id)
        {
            for(Book b : this.auctionbooks)
            {
                if(b.getId() == id)
                    return b;
            }
            return null;
        }

        public void registerBook(int bid, String name, String desc, 
                double startingBid, int auctionTime, int ownerId)
        {
            calendar = Calendar.getInstance();
            Book b;
            b = new Book();
            b.setId(bid);
            b.setName(name);
            b.setDesc(desc);
            b.setStartingBid(startingBid);
            b.setAuctionTime(auctionTime);
            b.setOwnerId(ownerId);

            calendar.add(Calendar.SECOND, b.getAuctionTime());

            b.setEndTimeAuction(calendar.getTime());

            auctionbooks.add(b);

            System.out.println(b.toString(1));
        }


        public void registerBid(int bookId, int clientId, double bid)
        {
           for(Book b : auctionbooks)
               if(b.getId() == bookId)
               {
                   if(b.getCurrentBid() < bid && b.inAuction())
                   {
                       b.setCurrentBid(bid);
                       b.getBids().add(new Bids(clientId, bid));
                       //Envia msg para todos os que estão seguindo esse livro para att os valores
                   }
               }
        }
    }
    
    
}
/**
 Padrao de mensagens:
 * tipo;id;conteudo
 * 
 * multicast
 * Tipos de mensagem:
 * 0 - hello - multicast, manda para a rede suas infos
 *          senderport;sendername;senderip;
 * 1 - helloServer - multicast, server avisa que continua ativo
 * 2 - 
 * 
 * 
 * unicast
 * 10 - bid - 10;senderport;idbook;value
 * 11 - registerbook - 11;senderport;bookname;value;description;time
 * 12 - endeauction - 12;senderport;bookid;
 * 13 - allbooksrequest - 13;senderport
 * 14 - sendbookA - 14;bookname;value;description;time
 * 15 - sendbookF - 15;bookname;value;description;time
 * 16 - sendbookM - 16;bookname;value;description;time
 * 17 - askPK - 17;serverport;
 * 18 - receivePK - 18;port;key
 **/