/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;
import Control.Book;
import Control.Peer;
import Control.Communication;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan
 */
public class InterfaceUser extends javax.swing.JFrame {

    Peer P;
    public WatchListServer ws;
    DefaultTableModel modelFollow;
    /**
     * Creates new form InterfaceUserNew
     */
    ArrayList<Book> MyBooks;
    public InterfaceUser(Peer p,boolean server,String name) {
        initComponents();
        if(server){
            String titulo="Server: ";
            titulo+=name;
            this.setTitle(titulo);
        }
        else{
            this.setTitle(name);
        }
        modelFollow = (DefaultTableModel) this.JTFollowing.getModel();
        this.P=p;
        MyBooks = new ArrayList<Book>();
        ws =new WatchListServer(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPMyBooks = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTMyBooks = new javax.swing.JTable();
        JBMyBooks = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        LabelName = new javax.swing.JLabel();
        JTxName = new javax.swing.JTextField();
        LabelDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JTxDescription = new javax.swing.JTextArea();
        LabelInitialPrice = new javax.swing.JLabel();
        jTxInitialPrice = new javax.swing.JTextField();
        LabelTime = new javax.swing.JLabel();
        JTxTime = new javax.swing.JTextField();
        JBAdd = new javax.swing.JButton();
        JBServer = new javax.swing.JButton();
        JPFollowingBooks = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTFollowing = new javax.swing.JTable();
        LabelPriceFollow = new javax.swing.JLabel();
        JTxPriceFollow = new javax.swing.JTextField();
        JBBidFollow = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPMyBooks.setBorder(javax.swing.BorderFactory.createTitledBorder("My Books for auction"));

        JTMyBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Price", "Time", "Description", "Winner Bid Owner"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JTMyBooks.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(JTMyBooks);

        JBMyBooks.setText("End the auction");
        JBMyBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBMyBooksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPMyBooksLayout = new javax.swing.GroupLayout(JPMyBooks);
        JPMyBooks.setLayout(JPMyBooksLayout);
        JPMyBooksLayout.setHorizontalGroup(
            JPMyBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPMyBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(JPMyBooksLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(JBMyBooks)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        JPMyBooksLayout.setVerticalGroup(
            JPMyBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPMyBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(JBMyBooks)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Registration books"));

        LabelName.setText("Name:");

        LabelDescription.setText("Description:");

        JTxDescription.setColumns(20);
        JTxDescription.setRows(5);
        jScrollPane3.setViewportView(JTxDescription);

        LabelInitialPrice.setText("Initial price:");

        LabelTime.setText("Auction Time:");

        JBAdd.setText("Add");
        JBAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LabelName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTxName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelDescription)
                    .addComponent(jScrollPane3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelInitialPrice)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxInitialPrice))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LabelTime)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JTxTime))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(JBAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelName)
                    .addComponent(JTxName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelInitialPrice)
                    .addComponent(jTxInitialPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelDescription)
                    .addComponent(LabelTime)
                    .addComponent(JTxTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(JBAdd)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JBServer.setText("Book's Server List");
        JBServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBServerActionPerformed(evt);
            }
        });

        JPFollowingBooks.setBorder(javax.swing.BorderFactory.createTitledBorder("Following Books"));

        JTFollowing.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Price", "Time", "Description", "Winner Bid Owner"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JTFollowing.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(JTFollowing);

        LabelPriceFollow.setText("Bid $");
        LabelPriceFollow.setToolTipText("");

        JBBidFollow.setText("Bid");
        JBBidFollow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBBidFollowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPFollowingBooksLayout = new javax.swing.GroupLayout(JPFollowingBooks);
        JPFollowingBooks.setLayout(JPFollowingBooksLayout);
        JPFollowingBooksLayout.setHorizontalGroup(
            JPFollowingBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPFollowingBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPFollowingBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(JPFollowingBooksLayout.createSequentialGroup()
                        .addComponent(LabelPriceFollow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JTxPriceFollow, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JBBidFollow, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 40, Short.MAX_VALUE)))
                .addContainerGap())
        );
        JPFollowingBooksLayout.setVerticalGroup(
            JPFollowingBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPFollowingBooksLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPFollowingBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelPriceFollow)
                    .addComponent(JTxPriceFollow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JBBidFollow))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JPMyBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(JPFollowingBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JBServer, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(JPMyBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(JPFollowingBooks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JBServer, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Ação do botão de Verificar os livros do servidor, essa ação limpa os valores que possuiam antes
     * na interface e torna a interface visivel com os novos produtos em leilão
     * 
     * @param evt 
     */
    private void JBServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBServerActionPerformed
        // TODO add your handling code here:
        ws.LimparAndRequest();
        ws.setVisible(true);
    }//GEN-LAST:event_JBServerActionPerformed

    /**
     * Adiciona um livro para leilão, essa acão do botão adiciona pega os valores preenchidos
     * pelo usuario e os envia para o servidor usando o metodo unicast de cadastro de livros
     * 
     * @param evt 
     */
    private void JBAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBAddActionPerformed
       String name = this.JTxName.getText();
       String description = this.JTxDescription.getText();
       String time = this.JTxTime.getText();
       String value = this.jTxInitialPrice.getText();
       Book B = new Book();
       B.setName(name);
       B.setDesc(description);
       B.setStartingBid(Double.parseDouble(value));
       B.setAuctionTime(Integer.parseInt(time));
       Integer id=0;
       this.MyBooks.add(B);
       //DefaultTableModel model = (DefaultTableModel) this.JTMyBooks.getModel();
       //model.addRow(new Object[]{id,name,value,time,description});
       this.JTxName.setText("");
       this.JTxDescription.setText("");
       this.JTxTime.setText("");
       this.jTxInitialPrice.setText("");
       P.sendBookToServer(name, value, description, time);
    }//GEN-LAST:event_JBAddActionPerformed

    /**
     * Esse metodo é a ação do botao end auction ele finaliza o leilão do livro selecionado
     * caso esse leilão ainda estaja ativo
     * 
     * @param evt 
     */
    private void JBMyBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBMyBooksActionPerformed
        int selecionada = this.JTMyBooks.getSelectedRow();
        if(selecionada>-1){
            Integer bookid = (Integer) this.JTMyBooks.getValueAt(selecionada,0);
            String time =(String) this.JTMyBooks.getValueAt(selecionada,3);
            if(time.equals("Finalizado")){
                JOptionPane.showMessageDialog(this,"Este Leilão já foi finalizado","Leilão Encerrado",JOptionPane.ERROR_MESSAGE);
            }
            else{
                P.EndAuction(bookid);
            }
        }
    }//GEN-LAST:event_JBMyBooksActionPerformed

    
    /**
     * envia uma aposta em um dos livros que você esta acompanhando o leilão, caso o leilão ainda esteja
     * ativo, esse evento é ativado quando clicar no botão Bid
     * 
     * @param evt 
     */
    private void JBBidFollowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBBidFollowActionPerformed
        int selecionada = this.JTFollowing.getSelectedRow();
        if(selecionada>-1){
            Integer bookid = (Integer) this.JTFollowing.getValueAt(selecionada,0);
            String time = (String) this.JTFollowing.getValueAt(selecionada,3);
            String value = this.JTxPriceFollow.getText();
            this.JTxPriceFollow.setText("");
            if(time.equals("Finalizado")){
                JOptionPane.showMessageDialog(this,"Este Leilão já foi finalizado","Leilão Encerrado",JOptionPane.ERROR_MESSAGE);
            }
            else{
                this.P.sendBidToServer(bookid, value);
            }
        }
    }//GEN-LAST:event_JBBidFollowActionPerformed

    /**
     * Esse metodo serve para adicionar um novo livro nos livros seguidos, e para atualizar aqueles
     * que já estão sendo seguido O metodo é chamado pela classe peer quando recebe uma msg unicast do
     * servidor com alguma atualizacao do livro seguido, Caso o livro já esteja no painel, ele é removido
     * e sua atualizacao é adicionada
     * 
     * @param b 
     */
    public void AdicionaFollowing(Book b)
    {
      int rows = this.JTFollowing.getRowCount();
      for(int i=0;i<rows;i++){
          Integer bookid = (Integer) this.JTFollowing.getValueAt(i,0);
          if(bookid==b.getId()){
              this.modelFollow.removeRow(i);
              break;
          }
       }
      //System.out.println(">>>>>>>>>>>ADD follow");
      //System.out.println("nome " +b.getName() +"valor" +b.getWinnerValue());
      modelFollow.addRow(new Object[]{b.getId(),b.getName(),b.getCurrentBid(),b.getEndTimeAuction().toString(),b.getDesc(),P.getPeerByPort(b.getWinner()).getName()});
    }
    
    /**
     * Esse metodo é chamado pela classe peer qndo um leilão é finalizado, 
     * e ele muda o tempo do leilão para finalizado
     * Este metodo serve para a tabela My own books
     * @param b 
     */
    public void FinalizarMyBooks(Book b){
      DefaultTableModel model = (DefaultTableModel) this.JTMyBooks.getModel();
      int rows = this.JTMyBooks.getRowCount();
      for(int i=0;i<rows;i++){
          Integer bookid = (Integer) this.JTMyBooks.getValueAt(i,0);
          if(bookid==b.getId()){
              model.setValueAt("Finalizado", i , 3);
          }
       }
    }
    
    
     /**
     * Esse metodo é chamado pela classe peer qndo um leilão é finalizado, 
     * e ele muda o tempo do leilão para finalizado
     * Este metodo serve para a tabela My follow books
     * @param b 
     */
    public void FinalizarMyFollow(Book b){
      DefaultTableModel model = (DefaultTableModel) this.JTFollowing.getModel();
      int rows = this.JTFollowing.getRowCount();
      for(int i=0;i<rows;i++){
          Integer bookid = (Integer) this.JTFollowing.getValueAt(i,0);
          if(bookid==b.getId()){
              model.setValueAt("Finalizado", i , 3);
          }
       }
    }
    
    /**
     * Esse metodo mostra uma mensagem quando você ganha um leilão
     * 
     * @param b 
     */
    public void GanheiLeilao(Book b){
        String msg="Parabéns, você ganhou o leilão do livro: ";
        msg+=b.getName();
        JOptionPane.showMessageDialog(this,msg,"Ganhou leilão",JOptionPane.PLAIN_MESSAGE);
    }
    
    
    /**
     * Esse metodo serve para adicionar um novo livro nos meus livros, e para atualizar aqueles
     * quando um livro recebe um lance O metodo é chamado pela classe peer quando recebe uma msg unicast do
     * servidor com alguma atualizacao do livro , Caso o livro já esteja no painel, ele é removido
     * e sua atualizacao é adicionada
     * 
     * @param b 
     */
    public void AdicionaMyBooks(Book b)
    {
      DefaultTableModel model = (DefaultTableModel) this.JTMyBooks.getModel();
      int rows = model.getRowCount();
      System.out.println(rows +">>>>>>>>>>>>>>>>>");
      for(int i=0;i<rows;i++){
          System.out.println(i +">>>>>>>>>>>>>>>>>");
          Integer bookid = (Integer) model.getValueAt(i,0);
          System.out.println(bookid +">>>>>>>>>>>>>>>>>");
          if(bookid==b.getId()){
              model.removeRow(i);
              break;
          }
       }
      //System.out.println("nome " +b.getName() +"valor" +b.getWinnerValue());
      String winner;
      if(b.getWinner()==-1){
          winner="";
      }
      else{
         winner=P.getPeerByPort(b.getWinner()).getName();
      }
      model.addRow(new Object[]{b.getId(),b.getName(),b.getCurrentBid(),b.getEndTimeAuction().toString(),b.getDesc(),winner});
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new InterfaceUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBAdd;
    private javax.swing.JButton JBBidFollow;
    private javax.swing.JButton JBMyBooks;
    private javax.swing.JButton JBServer;
    private javax.swing.JPanel JPFollowingBooks;
    private javax.swing.JPanel JPMyBooks;
    private javax.swing.JTable JTFollowing;
    private javax.swing.JTable JTMyBooks;
    private javax.swing.JTextArea JTxDescription;
    private javax.swing.JTextField JTxName;
    private javax.swing.JTextField JTxPriceFollow;
    private javax.swing.JTextField JTxTime;
    private javax.swing.JLabel LabelDescription;
    private javax.swing.JLabel LabelInitialPrice;
    private javax.swing.JLabel LabelName;
    private javax.swing.JLabel LabelPriceFollow;
    private javax.swing.JLabel LabelTime;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTxInitialPrice;
    // End of variables declaration//GEN-END:variables
}
