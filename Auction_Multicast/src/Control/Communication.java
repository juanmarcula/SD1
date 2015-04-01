package Control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Communication(final int id, String name, final int port)
    {
        Thread multicastListener, unicastListener;
        peers = new ArrayList<>();        
		
        //Add info about myself
        myself = new Peer(id, name, "localhost", port, true);
        
        peers.add(myself);        
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
        sleep(1000);
        //Send hello message
        sendMulticast("0;" + myself.getId() + ";" + myself.getName() + ";" + 
                myself.getIp(1) + ";" + myself.getPort() + ";");
        
        sleep(1000);
        //verifica se é o de maior prioridade, se for, send server
        if(isHighestPriority())
                    sendMulticast("1;" + myself.getId() + ";");
        
        //wait for a server to be elected
        while(server == null);
        
        //Se for o leiloeiro, envia livros a vender
        
        
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
        String[] ins = new String(in.getData()).split(";");
        
        switch(Integer.parseInt(ins[0]))
        {
            case 0:
                break;
            case 1:
                break;
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
            case 0:
                break;
            case 1:
                break;
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
    
    
    //Peers ops
    public boolean isHighestPriority()
    {
        int max = -1;
        int mbs = -1;
        for(Peer p : peers)
            if(p.getPriority() > max)
                mbs = p.getId();
        
        if(mbs == myself.getId())
            return true;
        else
            return false;
    }
}
/**
 Padrao de mensagens:
 * tipo;id;conteudo
 * 
 * Tipos de mensagem:
 * 0 - hello - multicast, manda para a rede suas infos
 *          idsender;sendername;senderip;senderport
 * 1 - helloServer - multicast, server avisa que continua ativo
 * 1 - 
 **/