package Control;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import java.awt.event.ActionListener;

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
    private boolean serverHasPk;
    private int timeOutCounter;
    private String ip;
    private int port;
    private PublicKey publicKey;
    
    
    private Server server;
    private ArrayList<Peer> peers;
    
    private Timer timerHelloServidor;
    private boolean HelloServidor =false;
    private boolean ServidorDown=false;
    
    private MulticastSocket mcSocket;
    private final String MULTICAST_IP = "228.5.6.7";
    private InetAddress MULTICAST_GROUP;
    private final int MULTICAST_PORT = 6789;
    
    private DatagramSocket ucSocket;
    Encryption crypto;
    
    
    public Peer(String name, String ip, int port, boolean main)
    {
        peers = new ArrayList<>();
        this.name = name;
        this.setIp(ip);
        this.setPort(port);
        this.serverHasPk = false;
        
        ActionListener action = new ActionListener() {  
            public void actionPerformed(@SuppressWarnings("unused") java.awt.event.ActionEvent e) {  
                if(Peer.this.HelloServidor==false)
                {
                    Peer.this.ServidorDown=true;
                    Peer.this.msgServerNotAvailable();
                   
                }  
            }  
        }; 
        this.timerHelloServidor = new Timer(1000,action); //ativa a cada 1000 (1 segundo)
        

        if(main)
        {
              initializeSockets();
              Thread multicastListener;


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
                                    onMulticastMessage(messageIn);
                                    System.out.println(getName() + " Received:" + new String(messageIn.getData()));
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
                            //if(mcSocket != null) 
                              //  mcSocket.close();
                        }
                    }
                };
                multicastListener.start();

 
        }
    }

    @Override
    public void run()
    {
        crypto = new Encryption();
        //--------------------------------------------------------------------
        //run comunication
        for(int i = 0; i < 2; i++)
        {
            sleep(100);
            //Send hello message
            this.sendMulticast("0;" + this.getPort() + ";" + this.getName() + ";" + 
                    this.getIp(1) + ";");
        }
        //sleep(1000);
        //verifica se é o de maior prioridade, se for, send server
        if(isHighestPriority())
        {
            this.sendMulticast("1;" + this.getPort() + ";");
            System.out.println(this.getName() + " Disse que é o server");
            server = new Server();
        }
            
        //wait for a server to be elected
        int dt = 0;
        while(this.getServer() == null && dt < 1000)
        {
            sleep(10);
            dt+=10;
        }
        if(dt>=1000)
            System.out.println(this.getName() + " Não achou o server");
        else
            System.out.println(this.getName() + " Achou o server");
        
        if(getServer() != getPeerByPort(this.port))
        {
            System.out.println("Abriu para " + this.getName());
            this.Client();
        }
        //pede chave publica
        
        
        if(getServer() == getPeerByPort(this.port))
        {
            server.msgAskPublicKey();
            this.Client();
        }
        //Send public key
        //Encryption crypto = new Encryption();
        //this.sendPublicKey(this.getServer(), crypto.getPublicKey().getEncoded());
        //--------------------------------------------------------------------
        

    }

    public void sendPublicKey(Peer p, byte[] m) 
    {
        DatagramPacket out;
        
        try 
        {
            //ucSocketSender = new DatagramSocket(this.getPort());
            out = new DatagramPacket(m, m.length, p.getIp(), p.getPort());
            ucSocket.send(out);
            System.out.println(this.getName() + " mandei pk ");
            this.serverHasPk = true;
        } 
        catch (IOException e) 
        {

        }
    }
    //Peers ops
    public boolean isHighestPriority()
    {
        int max = -1;
        int mbs = -1;
        for(Peer p : this.getPeers())
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
             for(Peer p : this.getPeers())
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
        for(Peer p : this.getPeers())
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
    
    public void initializeSockets()
    {
        try 
        {
            //config sockets
            MULTICAST_GROUP = InetAddress.getByName(MULTICAST_IP);
            mcSocket = new MulticastSocket(MULTICAST_PORT);
            mcSocket.joinGroup(MULTICAST_GROUP);
            ucSocket = new DatagramSocket(this.getPort());
        }
        catch (UnknownHostException ex) 
        {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * When a package is received on the multicast thread, this "event" is 
     * called, it process the message and take the appropriate approach. 
     * @param in
     */
    public void onMulticastMessage(DatagramPacket in)
    {
        String[] ins = new String(in.getData()).split(";");
        switch(Integer.parseInt(ins[0]))
        {
            case 0:
                msgHelloEvent(ins); break;
            case 1:
                msgHelloEvent(ins); break;
        }
        
    }
    
    public void msgHelloEvent(String[] msg)
    {
        if(this.getPeerByPort(Integer.parseInt(msg[1])) == null)
        {
            Peer p = new Peer(msg[2], msg[3], Integer.parseInt(msg[1]), false);
            peers.add(p);
        }
        if(Integer.parseInt(msg[0]) == 1)
        {
            if(this.getPeerByPort(Integer.parseInt(msg[1])).isServer == false)
            {
                this.getPeerByPort(Integer.parseInt(msg[1])).setAsServer();
                this.timerHelloServidor.start();
            }
            else
            {
            //seta a variavel em true
                this.HelloServidor=true;
            }
            
 
            
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
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**     
     * When a package is received on the unicast thread, this "event" is 
     * called, it process the message and take the appropriate approach.
     * @param in
     */
    public void onUnicastMessage(DatagramPacket in)
    {
        String[] ins;
        if(in.getPort()!=this.getServer().getPort())
        {
            ins = new String(in.getData()).split(";");
            if(Integer.parseInt(ins[0]) == 44)
            {
                //deu merda no servidor, me avisaram
            }
            return;
        }
        if(!serverHasPk)
            ins = new String(in.getData()).split(";");
        else
        {
            Peer p = getPeerByPort(in.getPort());
            ins = new String(crypto.decryptMsg(p.getPublicKey(),in.getData())).split(";");
        }
        if(ins.length>0)
        {
            switch(Integer.parseInt(ins[0]))
            {
                case 10:
                    //msgBid(ins); 
                    break;
                case 11:
                    //msgAuctionBook(ins); 
                    break;
                case 12:
                    //msgEndAuction(ins); 
                    break;
                case 17:
                    this.sendPublicKey(this.getServer(), crypto.getPublicKey().getEncoded()); break;
                default:
                    break;
            }
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
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Peer> getPeers()
    {
        return peers;
    }
    
    public Peer getPeerByPort(int port)
    {
        if(this.getPeers()!=null)
        {
            for(Peer p : this.getPeers())
            {
                if(p.getPort() == port)
                    return p;
            }
        }
        return null;
    }

    public void msgServerNotAvailable()
    {
        for(Peer p : peers)
        {
                sendUnicast(p, "44;");
        }            
    }
    
    public void Client()
    {
        Thread unicastListener;
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
                        System.out.println(getName() + " Received:" + new String(request.getData()));
                        onUnicastMessage(request);
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
                    {
                        System.out.println(this.getName() + " ucSocket closing ");
                    
                        ucSocket.close();
                    }
                }
            }
        };
        unicastListener.start();
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

        public PublicKey msgReceivePk()
        {
            byte[] buffer = new byte[165];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            try 
            {
                ucSocket.receive(messageIn);
                PublicKey pk;            

                try 
                {
                    pk = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(messageIn.getData()));
                    
                    return pk;
                } 
                catch (InvalidKeySpecException e) 
                {
                    System.out.println("InvalidKeySpecException: " + e.getMessage());
                } 
                catch (NoSuchAlgorithmException e) 
                {
                    System.out.println("NoSuchAlgorithmException: " + e.getMessage());
                }
            } 
            catch (IOException e) 
            {
                System.out.println("IOException: " + e.getMessage());
            }
            return null;
        }

        public void msgAskPublicKey()
        {
            for(Peer p : peers)
            {
                if(p.getPort()!=getPort())
                {
                    sendUnicast(p, "17;");
                    p.setPublicKey(msgReceivePk());
                }
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
 * 17 - sendPKtoServer - 17;port;key
 * 44 - erro de servidor 44;
 
 **/    
    

