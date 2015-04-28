package Control;

import Interface.InterfaceUser;
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
import java.util.Arrays;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
    boolean clientOn;
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
    private final String MULTICAST_IP = "228.5.6.10";
    private InetAddress MULTICAST_GROUP;
    private final int MULTICAST_PORT = 6789;
    
    private DatagramSocket ucSocket;
    Encryption crypto;
    
    public ArrayList<Book> following;
    public ArrayList<Book> myOwn;
    public ArrayList<Book> serverBooks;
    Interface.InterfaceUser in;
     Thread multicastListener;
             Thread unicastListener;
    public Peer(String name, String ip, int port, boolean main)
    {
        
        peers = new ArrayList<>();
        following = new ArrayList<>();
        myOwn = new ArrayList<>();
        serverBooks = new ArrayList<>();
        
        this.name = name;
        this.setIp(ip);
        this.setPort(port);

        if(main)
        {
              this.serverHasPk = false;
                ActionListener action = new ActionListener() {  

                public void actionPerformed(@SuppressWarnings("unused") java.awt.event.ActionEvent e) 
                {  
                    if(getServer().getPort() == getPort())
                    {
                        server.updateList();
                        sendMulticast("1;" + getPort() + ";");
                    }
                //System.out.println("tick");
                    sleeptc(100);
                    if(HelloServidor==false)
                    {
                        ServidorDown=true;
                        msgServerNotAvailable();
                        System.out.println("Caiu");

                    } 
                    HelloServidor = false;                 

                }  
                }; 
                this.timerHelloServidor = new Timer(3000,action); //ativa a cada 1000 (1 segundo)
                
              initializeSockets();
             


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
                                    //System.out.println(getName() + " Received:" + new String(messageIn.getData()));
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
        clientOn = false;
            
        this.Client();
        while(true)
        {
            System.out.println("###### STARTING ######");
            crypto = new Encryption();
            clientOn = false;
            
            //--------------------------------------------------------------------
            int dt=0;
            while(dt <= 500 || peers.size()<4)
            {
                sleeptc(10);
                //Send hello message
                this.sendMulticast("0;" + this.getPort() + ";" + this.getName() + ";" + 
                        this.getIp(1) + ";");
                dt++;
            }
            sleeptc(100);
            System.out.println(this.getName() + " Saiu loop");
            this.isServer = false;
            dt = 0;
            
            while(this.getServer() == null && dt < 10000)
            {
                if(isHighestPriority())
                {
                    for(int i = 0; i < 10; i++)
                    {
                        sleeptc(10);
                        this.sendMulticast("1;" + this.getPort() + ";");
                        System.out.println(this.getName() + " Disse que é o server");
                        server = new Server();
                        this.isServer = true;
                    }
                }
                else
                    sleeptc(100);
                sleeptc(10);
                dt+=1;
            }
            //wait for a server to be elected
            
                
            if(dt>=10000)
                System.out.println(this.getName() + " Não achou o server");
            else
                System.out.println(this.getName() + " Achou o server");

            if(getServer() != getPeerByPort(this.port))
            {
                System.out.println(this.getName() + " abriu listener para o server");
                //this.Client();
                this.clientOn = true;
            }
            //pede chave publica


            if(getServer() == getPeerByPort(this.port))
            {
                System.out.println("Pedindo as chaves!");
                server.msgAskPublicKey();
                //this.Client();
                System.out.println("Recebi as chaves!");
                clientOn = true;
                //if(unicastListener.isInterrupted())
                //    unicastListener.start();
                
            }
            
            in = new InterfaceUser(this,this.isServer,this.getName());
            in.setVisible(true);
            getPeerByPort(this.port).setPublicKey(crypto.getPublicKey());
            getPeerByPort(this.port).serverHasPk = true;
            //Send public key
            //Encryption crypto = new Encryption();
            //this.sendPublicKey(this.getServer(), crypto.getPublicKey().getEncoded());
            //--------------------------------------------------------------------

            while(!ServidorDown)
            {
                sleeptc(100);
                int tt = 0;
               if(ServidorDown==true)
                   break;
            }
            System.out.println("###### RESTARTING -SEVER DOWN ######");
            clientOn = false;
            peers = new ArrayList<>();
            following = new ArrayList<>();
            myOwn = new ArrayList<>();
            serverBooks = new ArrayList<>();
            in.setVisible(false);
            timerHelloServidor.stop();
            ServidorDown = false;
            setPublicKey(null);
            sleeptc(1000);
            //unicastListener.interrupt();
            
            /*ucSocket.close();
            mcSocket.close();
            initializeSockets();
            multicastListener.stop();
            unicastListener.stop();
            multicastListener.start();
            unicastListener.start();*/
        }

    }

    /**
     * metodo usado para enviar a chave publica para o servidor
     * 
     * @param p
     * @param m 
     */
    public void sendPublicKey(Peer p, byte[] m) 
    {
        DatagramPacket out;
        
        try 
        {
            //ucSocketSender = new DatagramSocket(this.getPort());
            out = new DatagramPacket(m, m.length, p.getIp(), p.getPort());
            ucSocket.send(out);
            System.out.println(this.getName() + " Enviando PublicKey");
            this.serverHasPk = true;
        } 
        catch (IOException e) 
        {

        }
    }
    /**
    * indica se o peer atual é o de maior prioridade
    * 
    * @return boolean 
    */
    public boolean isHighestPriority()
    {
        int mbs = -1;
                   
        for(Peer p : getPeers())
        {
            int po = p.getPort(); 
            if(po > mbs)
                mbs = p.getPort();
        //if(port >= 7905)
         //   System.out.println("Minha porta: " + port + " Estou vendo: " + p.getPort()
        //    + " O max é: " + mbs);
        }
       // if(port >= 7905)
        //    System.out.println("O MBS do " + port + " é: " + mbs);
        
        if(mbs == port)
            return true;
        else
            return false;
    }
    
    
    /**
     * metodo que retorna o peer que é o servidor
     * 
     * @return Peer - Instacia do servidor
     */
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

    /**
    * define esse peer como servidor
    * 
    */
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
     * @param port
     * @param isServer the isServer to set
     */
    public void setServer(int port) 
    {
        for(Peer p : this.getPeers())
            if(p.getPort() == port)
                p.setAsServer();
        
    }

    /**
    * retorna um livro de uma base de livros dado a sua id
    * @param bid
    * @param base
    * @return
    */
    public Book getBookById(int bid, ArrayList<Book> base)
    {
       for(Book b : base)
       {
           if(b.getId() == bid)
               return b;
       }
       return null;
   }

    /**
     * retorna o objeto da classe InetAddress com o Ip do peer
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
     * retorna seu endereco de IP por string
     * 
     * @param a
     * @return the ip
     */
    public String getIp(int a) 
    {
        return this.ip;
    }

    /**
     * Inicializa o endereco de IP com a string enviada
     * 
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * retorna a porta que o Peer está usando
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Incializa a variavel port com a porta especificada por parametro
     * 
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Retorna a sua propria public key
     * 
     * @return the publicKey
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Inicializa sua public key com o valor passado por parametro
     * 
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
    
    /**
     * Inicializa os objetos de sockets
     */
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
            case 3: 
                msgEndAuction(ins); break;
        }
        
    }
    
    /**
     * Trata a mensagem do tipo Hello. Se não conhecer quem mandou, adiciona
     * se for o servidor quem mandou, não da erro de inatividade do servidor
     * @param msg
     */
    public void msgHelloEvent(String[] msg)
    {
        if(this.getPeerByPort(Integer.parseInt(msg[1])) == null)
        {
            if(Integer.parseInt(msg[0]) == 0)
            {
                System.out.println(this.getName() + " " + Arrays.toString(msg));
                Peer p = new Peer(msg[2], msg[3], Integer.parseInt(msg[1]), false);
                peers.add(p);
                this.sendMulticast("0;" + this.getPort() + ";" + this.getName() + ";" + 
                            this.getIp(1) + ";");
                if(isServer)
                     this.sendMulticast("1;" + this.getPort() + ";");
            }
        }
        if(Integer.parseInt(msg[0]) == 1)
        {
            if(this.getPeerByPort(Integer.parseInt(msg[1])).isServer == false)
            {
                this.HelloServidor=true;
                this.getPeerByPort(Integer.parseInt(msg[1])).setAsServer();
                this.timerHelloServidor.start();
                this.sendMulticast("0;" + this.getPort() + ";" + this.getName() + ";" + 
                        this.getIp(1) + ";");
                if(isServer)
                                this.sendMulticast("1;" + this.getPort() + ";");
                
            }
            else
            {
            //seta a variavel em true
                //if(this.getPeerByPort(Integer.parseInt(msg[1])).isServer == true)
                    this.HelloServidor=true;
                    //System.out.println("ta rolando");
            }

        }
    }
    
    /**
     * Trata o recebimento de mensagem do tipo de fim de leilão, no caso 
     * se for o Peer vencendo chama o metodo da interface para mostrar o vencedor
     * se for um Follower, chama o metodo para finalizar o leilão na tabela da interface do usuario
     * se for um livro proprio, chama o metodo para indicar graficamente que o leilão desse livro acabou
     * 
     * @param msg 
     */
    public void msgEndAuction(String[] msg)
    {
            //3;idVencedor;nomevencedor;bookid;valor
        Book bc = getBookById(Integer.parseInt(msg[3].trim()), following);
        if(Integer.parseInt(msg[1].trim()) == this.port)
        {in.GanheiLeilao(bc);}    //ganhei
        Book b = getBookById(Integer.parseInt(msg[3].trim()), myOwn);
        if(b!=null)
        {in.FinalizarMyBooks(b);}
        b = getBookById(Integer.parseInt(msg[3].trim()), following);
        if(b!=null)
        {in.FinalizarMyFollow(b);}

    }
    
    /**
     * trata o recebimento de todos os livros que estão no servidor
     * Quando o usuario requsita todos os livros em leilão ativo no servidor
     * 
     * @param msg 
     */
    public void msgServerBooks(String [] msg)
    {
        // 14 - sendbookA - 14;bookid;bookName;WinnerId;value;description;time
        
        /*for(Book b : serverBooks)
        {
            if(b.getName().equals(msg[1]))
            {
                serverBooks.remove(b);
            }
        }*/
        Book b = new Book();
        b.setId(Integer.parseInt(msg[1].trim()));
        b.setName(msg[2]);
        b.setAuctionTime(Integer.parseInt(msg[6].trim()));
        b.setCurrentBid(Double.parseDouble(msg[4]));
        b.getBids().add(new Bids(Integer.parseInt(msg[3].trim()),Double.parseDouble(msg[4])));
        b.setDesc(msg[5]);
        serverBooks.add(b);
        in.ws.AdicionaLivroServer(b);
    }
    
    /**
     * trata o recebimento dos livros que estou interessado
     * 
     * @param msg 
     */
    public void msgFollowingBooks(String [] msg)
    {
        // 15 - sendbookF - 14;bookid;bookname;winnerId;value;description;time
        /*for(Book b : this.following)
        {
            if(b.getName().equals(msg[1]))
            {
                following.remove(b);
            }
        }*/
        Book b = new Book();
        b.setId(Integer.parseInt(msg[1].trim()));
        b.setName(msg[2]);
        b.setAuctionTime(Integer.parseInt(msg[6].trim()));
        b.setCurrentBid(Double.parseDouble(msg[4]));
        b.getBids().add(new Bids(Integer.parseInt(msg[3].trim()),Double.parseDouble(msg[4])));
        b.setDesc(msg[5]);
        following.add(b);
        in.AdicionaFollowing(b);
    }
    
    /**
     * Trata o recebimento de atualização dos livros que estou leiloando
     * 
     * @param msg 
     */
    public void msgMyOwnBooks(String [] msg)
    {
        // 15 - sendbookF - 14;bookid;bookname;winnerId;value;description;time
        /*for(Book b : this.myOwn)
        {
            if(b.getName().equals(msg[1]))
            {
                myOwn.remove(b);
            }
        }*/
        Book b = new Book();
        b.setId(Integer.parseInt(msg[1].trim()));
        b.setName(msg[2]);
        b.setAuctionTime(Integer.parseInt(msg[6].trim()));
        b.setCurrentBid(Double.parseDouble(msg[4]));
        b.getBids().add(new Bids(Integer.parseInt(msg[3].trim()),Double.parseDouble(msg[4])));
        b.setDesc(msg[5]);
        myOwn.add(b);
        in.AdicionaMyBooks(b);
    }
    
    /**
     * Envia a mensagem msg para o grupo multicast
     * @param msg
     */
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
     * trata o recebimento de pacotes unicast
     * @param in
     */
    public void onUnicastMessage(DatagramPacket in)
    {
        String[] ins = null;

        Peer p = getPeerByPort(in.getPort());
        System.out.println("Sou servidor? " + getPeerByPort(port).isServer);
        
        if(p != null && p.isServer)
        {
            try
            {
                String a;
                p = getPeerByPort(in.getPort());
                if(p.getPublicKey()!=null)
                    ins = new String(crypto.decryptMsg(p.getPublicKey(),in.getData())).split(";");
                else
                     a = ins[5];
                System.out.println(this.getName() + " " + Arrays.toString(ins));
            }
            catch(Exception e)
            {
                ins = new String(in.getData()).split(";");
                String a  = ins[0];
                System.out.println(this.getName() + " " + Arrays.toString(ins));
            }
        }
        else
        {
            p = getPeerByPort(in.getPort());
            if(p.getPublicKey()!=null && getPeerByPort(port).isServer)
                ins = new String(crypto.decryptMsg(p.getPublicKey(),in.getData())).split(";");
            else
                 ins = new String(in.getData()).split(";");
            System.out.println(this.getName() + " " + Arrays.toString(ins));
        }
        if(ins.length>0)
        {
            p = getPeerByPort(in.getPort());
           System.out.println(getName() + " Recebi de: " + p.getName());
            switch(Integer.parseInt(ins[0]))
            {
                case 10:
                    server.msgBid(ins); 
                    break;
                case 11:
                    server.msgAuctionBook(ins); 
                    break;
                case 12:
                    server.msgEndAuction(ins); 
                    break;
                case 13:
                    server.sendAll(in.getPort()); 
                    break;
                case 14:
                    msgServerBooks(ins); 
                    break;
                case 15:
                    msgFollowingBooks(ins); 
                    break;
                case 16:
                    msgMyOwnBooks(ins); 
                    break;
                case 17:
                    this.sendPublicKey(this.getServer(), crypto.getPublicKey().getEncoded()); break;
                default:
                    break;
            }
        }
    }
    
    /**
     * Envia mensagem unicast para um peer p, com a mensagem msg e diz se deve 
     * ou nao ser encriptada
     * @param p
     * @param msg
     * @param encry
     */
    public void sendUnicast(Peer p, String msg,boolean encry)
    {
        byte [] m = msg.getBytes();
        //System.out.println(Arrays.toString(m));
        if(encry){
            m=this.crypto.encryptMsg(m);
        }
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
    
    /**
     * Thread.sleep com trycatchs
     * @param ms
     */
    public void sleeptc(int ms)
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
    
    /**
     * Retorna todos os peers
     * @return
     */
    public ArrayList<Peer> getPeers()
    {
        return peers;
    }
    
    /**
     * Retorna um peer, dado sua id(porta)
     * @param port
     * @return
     */
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

    /**
     * Envia mensagem unicast avisando que o servidor caiu
     */
    public void msgServerNotAvailable()
    {
        for(Peer p : peers)
        {
                sendUnicast(p, "44;",false);
        }            
    }
    
    /**
     * Envia para o servidor a mensagem pedindo para encerrar o leilao
     * @param bookname
     */
    public void EndAuction(int bookId)
    {
        //12;senderport;bookid;
        String msg="12;";
        msg = msg.concat("" + this.getPort());
        msg = msg.concat(";");
        msg = msg.concat("" + bookId);
        sendUnicast(this.getServer(),msg,true);
    }
    
    /**
     * Envia a mensgaem contendo o livro pro servidor para ser leiloado
     * @param name
     * @param value
     * @param description
     * @param time
     */
    public void sendBookToServer(String name,String value,String description,String time)
    {
        //11;senderport;bookname;value;description;time
        String msg="11;";
        msg = msg.concat("" +this.getPort());
        msg = msg.concat(";");
        msg = msg.concat(name);
        msg = msg.concat(";");
        msg = msg.concat(value);
        msg = msg.concat(";");
        msg = msg.concat(description);
        msg = msg.concat(";");
        msg = msg.concat(time);
        sendUnicast(this.getServer(),msg,true);
             
    }
    
    /**
     * Envia a mensgaem contendo a aposta 
     * @param name
     * @param value
     * 
     */
    public void sendBidToServer(int bookId,String value)
    {
       //10 - bid - 10;senderport;idbook;value
        String msg="10;";
        msg = msg.concat("" + this.getPort());
        msg = msg.concat(";");
        msg = msg.concat("" + bookId);
        msg = msg.concat(";");
        msg = msg.concat(value);
        sendUnicast(this.getServer(),msg,true);
        
    }
    
    /**
     * Envia o pedido pro servidor enviar todos os livros
     */
    public void RequestAllBooks()
    {
        String msg="13;";
        msg = msg.concat("" +this.getPort());
        sendUnicast(this.getServer(),msg,true);
    }
    
    /**
     * Inicia a thread que ouve as mensagens unicast que os clientes recebem
     */
    public void Client()
    {
        unicastListener = new Thread()
        {
            @Override
            public void run()
            {
                int tct;
                try
                {
                    byte[] buffer;
                    while(true)
                    {
                        if(clientOn == true)
                        {
                            System.out.println("Cliente ouvindo " + getName());
                            buffer = new byte[128];
                            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                            ucSocket.receive(request);  
                            if(clientOn==true)
                            {
                                System.out.println(getName() + " Received:" + new String(request.getData()));
                                onUnicastMessage(request);
                            }
                        }
                        else
                            sleeptc(5);
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
                catch (Exception e) 
                {
                    System.out.println(getName() + " Exception: " + e.getMessage());
                }
               // finally
                //{
                  //  System.out.println("AHHHHHHHHHHHH");
            //    }
            }
        };
        unicastListener.start();
    }
    
    /**
    * Metodos que so servidor vai usar
    */
    public class Server
    {
        //books in the auction
        private final ArrayList<Book> auctionbooks;
        Calendar calendar;
        int ids = 0;

 
        public Server()
        {
            auctionbooks = new ArrayList<>();
            Thread sendhello;
            sendhello = new Thread()
                {
                    @Override
                    public void run()
                    {
                        sendMulticast("1;" + getPort() + ";");
                        updateList();
                        sleeptc(1000);
                        
                    }
                };
                sendhello.start();
            
        }

        /**
         * Recebe do onUnicastMsg um lance em um livro
         * @param msg
         */
        public void msgBid(String[] msg)
        {
            //10;senderport;bookid;value
            this.registerBid(Integer.parseInt(msg[2]), Integer.parseInt(msg[1]), 
                    Double.parseDouble(msg[3]));
        }

        /**
         * Recebe do onUnicastMsg um livro a ser leiloado
         * @param msg
         */
        public void msgAuctionBook(String[] msg)
        {
            // 11 - registerbook - 11;senderport;bookname;value;description;time
            this.registerBook(ids, msg[2], msg[4],Double.parseDouble(msg[3]), 
                     Integer.parseInt(msg[5]), Integer.parseInt(msg[1]));
            ids++;
        }
        
        /**
         * Envia o unicast com o vencedor do livro, quando acaba o leilao
         * @param b
         */
        public void msgWinner(Book b)
        {
            //3;idVencedor;nomevencedor;bookid;valor
            Peer p = getPeerByPort(b.getWinner());
            if(p != null)
                sendMulticast("3;" + b.getWinner() + ";" + p.getName() + ";" + b.getId()+ ";" + b.getWinnerValue());
            else
                sendMulticast("3;-1;nowinner;" + b.getId() + ";" + b.getWinnerValue());
        }
        
        /**
         * Encerra o leilao de um livro, se quem enviou for o dono
         * @param msg
         */
        public void msgEndAuction(String[] msg)
        {
            // * 12 - endeauction - 12;senderport;bookid;
            Book b = this.getBookById(Integer.parseInt(msg[2]));
            
            if(b.getOwnerId() == Integer.parseInt(msg[1]))
            {
                b.endAuction();
                msgWinner(b);
                //send msg para todos os que estão seguindo o livro - acabou
               
            }
        }

        /**
         * Recebe a chave publica de um cliente
         * @return
         */
        public PublicKey msgReceivePk(Peer p)
        {
            byte[] buffer = new byte[165];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            try 
            {
                ucSocket.receive(messageIn);
                //ucSocket.setSoTimeout(10);
                if(messageIn.getPort() == p.getPort())
                {
                    PublicKey pk;            

                    try 
                    {
                        pk = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(messageIn.getData()));

                        return pk;
                    } 
                    catch (InvalidKeySpecException e) 
                    {
                        System.out.println("InvalidKeySpecException: " + e.getMessage());
                        return null;
                    } 
                    catch (NoSuchAlgorithmException e) 
                    {
                        System.out.println("NoSuchAlgorithmException: " + e.getMessage());
                        return null;
                    }
                    catch (Exception e) 
                    {
                        System.out.println("Exception: " + e.getMessage());
                        return null;
                    }
                }
                else 
                    return null;
            } 
            catch (IOException e) 
            {
                System.out.println(getName() + " IOException: " + e.getMessage());
            }
            return null;
        }

        /**
         * Pede a chave publica a um cliente e espera a sua resposta
         */
        public void msgAskPublicKey()
        {
            for(Peer p : peers)
            {
                if(p.getPort()!=getPort())
                {
                    System.out.println("Pedindo a chave de: " + p.getName());
                    
                    PublicKey pk = null;
                    while(pk == null)
                    {
                        sendUnicast(p, "17;",false);
                        pk = msgReceivePk(p);
                    }
                    p.setPublicKey(pk);
                    System.out.println("Recebi a chave de: " + p.getName());
                    p.serverHasPk = true;
                }
            }
            
        }
        
        /**
         * Envia uma atualização do livro para os seguidores desse livro
         * @param b
         */
        public void sendToFollowers(Book b)
        {
            // * 15 - sendbookF - 15;bookId;bookname;value;description;time
            for(Bids bid : b.getBids())
            {
                Peer p = getPeerByPort(bid.getClientId());
            
                if(p!=null)
                    sendUnicast(p, "15;" + b.getId() + ";"  + b.getName() +";" +b.getWinner()  +";"  + b.getWinnerValue() +";" +b.getDesc()
                        + ";" + b.getRemainingAuctionTime(), false);
            }
        }
        
        /**
         * envia uma atualização do livro para quem botou ele a venda
         * @param b
         */
        public void sendToAuctioneer(Book b)
        {
            //16 - sendbookO - 16;bookname;value;description;time
            //for(int port : b.getFollowing())
            //{
                Peer p = getPeerByPort(b.getOwnerId());
                if(p!=null)
                    sendUnicast(p, "16;" + b.getId() + ";" + b.getName()+";" +b.getWinner() + ";"  + b.getWinnerValue() +";" +b.getDesc()
                        + ";" + b.getRemainingAuctionTime(), false);
            //}
        }
        
        /**
         *Quando alguem deseja ver todos os livros no servidor, envia tudo
         */
        public void sendAll(int pPort)
        {
            Peer p = getPeerByPort(pPort);
            if(p!=null)
            {
                for(Book b:this.auctionbooks)
                {
                    // 14 - sendbookA - 14;bookid;bookName;WinnerId;value;description;time
                    if(b.inAuction())
                    {
                        String msg ="14;";
                        msg = msg.concat("" + b.getId());
                        msg = msg.concat(";");
                        msg = msg.concat(b.getName());
                        msg = msg.concat(";");
                        msg = msg.concat("" +b.getWinner());
                        msg = msg.concat(";");
                        msg = msg.concat("" + b.getWinnerValue());
                        msg = msg.concat(";");
                        msg = msg.concat(b.getDesc());
                        msg = msg.concat(";");
                        msg = msg.concat("" + b.getRemainingAuctionTime());
                        sendUnicast(p, msg, false);
                    }
                }
            }
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
                
                Calendar cal = Calendar.getInstance();
                cal.setTime(b.getEndTimeAuction());
                //System.out.println("updateList: Now" + Calendar.getInstance().getTime().toString());
                //System.out.println("updateList: End" + cal.getTime().toString());
                if(Calendar.getInstance().after(cal) && b.inAuction())
                {
                    
                    b.endAuction();
                    msgWinner(b);
                }
            }
                
        }
        
        /**
         * retorna o livro dado o nome
         * @param name
         * @return
         */
        public Book getBookByName(String name)
        {
            for(Book b : this.auctionbooks)
            {
                if(b.getName().equals(name))
                    return b;
            }
            return null;
        }
        
         /**
         * retorna o livro dado a sua id
         * @param bid
         * @return
         */
        public Book getBookById(int bid)
        {
            for(Book b : this.auctionbooks)
            {
                if(b.getId() == bid)
                    return b;
            }
            return null;
        }

        /**
         * adiciona o livro na lista de sendo leiloados
         * @param bid
         * @param name
         * @param desc
         * @param startingBid
         * @param auctionTime
         * @param ownerId
         */
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
            this.sendToAuctioneer(b);
            System.out.println(b.toString(1));
        }

        /**
         *  Adiciona um lance ao livro
         * @param bookId
         * @param clientId
         * @param bid
         */
        public void registerBid(int bookId, int clientId, double bid)
        {
           for(Book b : auctionbooks)
               if(b.getId() == (bookId))
               {
                   if(b.getWinnerValue()< bid && b.inAuction())
                   {
                       b.setCurrentBid(bid);
                       b.getBids().add(new Bids(clientId, bid));
                       //Envia msg para todos os que estão seguindo esse livro para att os valores
                       sendToAuctioneer(b);
                       sendToFollowers(b);
                       System.out.println("VAIIIIIIIIIIIIIIIIIIIIIII");
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
 * 3 - fimdeleilão - 3;nomevencedor;bookname;valor
 * 
 * unicast
 * 10 - bid - 10;senderport;bookid;value
 * 11 - registerbook - 11;senderport;bookname;value;description;time
 * 12 - endeauction - 12;senderport;bookid;
 * 13 - allbooksrequest - 13;senderport
 * 14 - sendbookA - 14;bookname;value;description;time
 * 15 - sendbookF - 15;bookname;value;description;time
 * 16 - sendbookO - 16;bookname;value;description;time
 * 17 - sendPKtoServer - 17;port;key
 * 44 - erro de servidor 44;
 
 **/    
    

