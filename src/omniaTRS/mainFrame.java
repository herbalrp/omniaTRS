package omniaTRS;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.ListSelectionModel;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainFrame {

  private JFrame frmOmniatrs;
  private JTextField textField;
  private JTable table;
  private JTextArea textArea; 
  private JLabel lblWPorzdku;
  private JLabel lblOstrzeenie;
  private JLabel lblNewLabel;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          mainFrame window = new mainFrame();
          window.frmOmniatrs.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public mainFrame() {
    initialize();
   /* DataBaseConnector baza = new DataBaseConnector();
    if(baza.OpenConnection())
    {
      System.out.println("DZIA£A");
      double[][] tabY = new double[50][2];
      double[][] tabX = new double[50][2];
      double[] n = new double[50];
      for(int i=0; i<50; i++)
      {
        Random rand = new Random();
        double j = rand.nextDouble();
      
        double k = 0;
        if(j>0.5) {k=1;};
        double cena = rand.nextDouble()*100+1;
        tabY[i][0] = k;
        tabX[i][0] = 1;
        tabX[i][1] = cena;
        n[i] = 1;
        System.out.println(k + " " + cena);
        
      }
      double[] xrange = {0, 101};
     LogisticRegression regresja = new LogisticRegression(2, 50, 1, tabY, tabX, n, xrange);
     regresja.compute();
      double[] beta = regresja.getBeta();
      for(int j=0; j<beta.length; j++)
      {
        System.out.println("BETA" + beta[j]);
      }
      int[] f = regresja.getExPostTable();
      for(int i=0; i<4; i++)
      {
        System.out.println("ExPost" + f[i]);
      }
      System.out.println("R_2" + (double) regresja.getR_2());
      
      ValueImbalance v = new ValueImbalance();
      v.setCustomerID(1000);
      v.setDataBase(baza);
      v.Compute();
      
    } else
    {
      System.out.println("NIE DZIA£A");
    }*/
      
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frmOmniatrs = new JFrame();
    frmOmniatrs.setTitle("omniaTRS");
    frmOmniatrs.setResizable(false);
    frmOmniatrs.setIconImage(Toolkit.getDefaultToolkit().getImage(mainFrame.class.getResource("/image/ikona.jpg")));
    frmOmniatrs.setBounds(100, 100, 1390, 864);
    frmOmniatrs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frmOmniatrs.getContentPane().setLayout(null);
    
    JPanel panel = new JPanel();
    panel.setBounds(0, 0, 1384, 884);
    frmOmniatrs.getContentPane().add(panel);
    
    textArea = new JTextArea();
    textArea.setForeground(Color.BLACK);
    textArea.setWrapStyleWord(true);
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
    textArea.setLineWrap(true);
    
    textField = new JTextField();
    textField.setText("1000");
    textField.setColumns(10);
    
    JButton btnNewButton = new JButton("New button");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        compute();
      }
    });
    
    JScrollPane scrollPane = new JScrollPane();
    
    lblNewLabel = new JLabel("ALERT ");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
    lblNewLabel.setForeground(Color.WHITE);
    lblNewLabel.setEnabled(false);
    lblNewLabel.setBackground(Color.RED);
    lblNewLabel.setVisible(false);
    
    lblOstrzeenie = new JLabel("OSTRZE\u017BENIE");
    lblOstrzeenie.setForeground(Color.WHITE);
    lblOstrzeenie.setFont(new Font("Tahoma", Font.BOLD, 20));
    lblOstrzeenie.setEnabled(false);
    lblOstrzeenie.setBackground(Color.RED);
    lblOstrzeenie.setVisible(false);
    
    lblWPorzdku = new JLabel("W PORZ\u0104DKU");
    lblWPorzdku.setForeground(Color.WHITE);
    lblWPorzdku.setFont(new Font("Tahoma", Font.BOLD, 20));
    lblWPorzdku.setVisible(false);
    lblWPorzdku.setEnabled(false);
    lblWPorzdku.setBackground(Color.RED);
    GroupLayout gl_panel = new GroupLayout(panel);
    gl_panel.setHorizontalGroup(
      gl_panel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel.createSequentialGroup()
          .addComponent(textArea, GroupLayout.PREFERRED_SIZE, 488, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(ComponentPlacement.RELATED)
          .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_panel.createSequentialGroup()
              .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(btnNewButton))
            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
          .addGap(80)
          .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
            .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblOstrzeenie, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
            .addComponent(lblWPorzdku, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
          .addGap(175))
    );
    gl_panel.setVerticalGroup(
      gl_panel.createParallelGroup(Alignment.LEADING)
        .addGroup(gl_panel.createSequentialGroup()
          .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel.createSequentialGroup()
              .addGap(38)
              .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
              .addGap(75)
              .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                .addComponent(btnNewButton)
                .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addGroup(gl_panel.createSequentialGroup()
              .addGap(6)
              .addComponent(textArea, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE))
            .addGroup(gl_panel.createSequentialGroup()
              .addGap(72)
              .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
              .addGap(18)
              .addComponent(lblOstrzeenie, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(ComponentPlacement.RELATED)
              .addComponent(lblWPorzdku, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
          .addContainerGap(434, Short.MAX_VALUE))
    );
    
    table = new JTable();
    
    table.setFont(new Font("Arial", Font.PLAIN, 18));
    scrollPane.setViewportView(table);
    table.setFillsViewportHeight(true);
    table.setEnabled(false);
    table.setModel(new DefaultTableModel(
      new Object[][] {
        {null, null, null},
      },
      new String[] {
        "Modu\u0142", "Waga", "Stopie\u0144 zagro\u017Cenia"
      }
    ) {
      Class[] columnTypes = new Class[] {
        String.class, String.class, Object.class
      };
      public Class getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
      }
      boolean[] columnEditables = new boolean[] {
        false, false, false
      };
      public boolean isCellEditable(int row, int column) {
        return columnEditables[column];
      }
    });
    table.getColumnModel().getColumn(0).setPreferredWidth(135);
    table.getColumnModel().getColumn(0).setMinWidth(30);
    table.getColumnModel().getColumn(1).setMinWidth(30);
    table.getColumnModel().getColumn(2).setPreferredWidth(180);
    table.getColumnModel().getColumn(2).setMinWidth(30);
    panel.setLayout(gl_panel);
  }

  protected boolean compute() {
    
    lblWPorzdku.setVisible(false);
    lblOstrzeenie.setVisible(false);
    lblNewLabel.setVisible(false);
    textArea.setText("");
    table.setDefaultRenderer(Object.class, new ColorRendererM(true));
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    for(int i =0; i<model.getRowCount(); i++)
    {
      model.removeRow(i);
    };
       
   
    
    DataBaseConnector baza = new DataBaseConnector();
    if(!baza.OpenConnection())
    {
            
      table.setModel(model);
      
      textArea.setText("B£¥D PO£¥CZENIA Z BAZ¥");
      return false;
    }
    
    try
    {
    long ID = Long.parseLong(textField.getText());
    DecisionMaker decisionMaker = new DecisionMaker(ID, baza);
    decisionMaker.compute();
    int decision = decisionMaker.getDecision();
    switch(decision)
    {
      case 0: lblWPorzdku.setVisible(true); break;
      case 1: lblOstrzeenie.setVisible(true); break;
      case 2: lblNewLabel.setVisible(true); break;
      default: break;
    }
    
    ArrayList<Subresult> results = decisionMaker.getResults();
    String info = textArea.getText();
  
    
    String label = "-";
    Color color;
    int j =0;
    for(Subresult a: results)
    {
      info=info + "\r\n" + a.getName() + a.getComment();
      if(a.getPriority()==2)
      {
        label = "wysoka";
      } else if(a.getPriority()==1)
      {
        label = "niska";
      }
      switch(a.getScore())
      {
        case 0: color = Color.WHITE; break;
        case 1: color = Color.green; break;
        case 2: color = Color.orange; break;
        case 3: color = Color.red; break;
        default: color = Color.WHITE; break;
      }
      model.addRow(new Object[] {a.getName(), label, color});
      
    }
    
    table.setModel(model);
    
    textArea.setText(info);
    
    }
        catch (NumberFormatException e)
    {
      
      textArea.setText("B£ÊDNE ID");
      return false;
    } /*catch (Exception e)
    {
      
      textArea.setText("Wyst¹pi³ b³¹d");
      return false;
    }*/
    
    
    

    
    return true;
  }
  
  public class ColorRendererM extends JLabel
  implements TableCellRenderer {

    public ColorRendererM(boolean f) {

      setVisible(true);
      setOpaque(true); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
   JTable table, Object color,boolean isSelected, boolean hasFocus,int row, int column) {
      if(column==2)
      {
        this.setText("");
        Color newColor = (Color)color;
        this.setBackground(newColor);
        return this;
      } else
      {
        this.setBackground(Color.white);
        this.setText((String) color);
         return this;
      }
      
}
    
   
}



}
