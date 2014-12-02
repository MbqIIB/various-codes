Exemple 9.20 : DataExchangeTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DataExchangeTest
{
   public static void main(String[] args)
   {  
      DataExchangeFrame frame = new DataExchangeFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un menu dont l'option Fichier->Connecter (File->Connect)
      affiche une bo�te de dialogue de mot de passe.
*/
class DataExchangeFrame extends JFrame 
{  
   public DataExchangeFrame()
   {
      setTitle("DataExchangeTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // construire un menu Fichier
       
      JMenuBar mbar = new JMenuBar();
      setJMenuBar(mbar);
      JMenu fileMenu = new JMenu("File");
      mbar.add(fileMenu);

      // ajouter les options Connecter et Quitter au menu 

      JMenuItem connectItem = new JMenuItem("Connect");
      connectItem.addActionListener(new ConnectAction());
      fileMenu.add(connectItem);

      // L'option Quitter permet de quitter le programme 

      JMenuItem exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });
      fileMenu.add(exitItem);

      textArea = new JTextArea();
      add(new JScrollPane(textArea), 
         BorderLayout.CENTER);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  

   private PasswordChooser dialog = null;
   private JTextArea textArea;

   /**
      L'option Connecter affiche la bo�te de dialogue mot de passe.
   */

   private class ConnectAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         // Si premi�re fois, construire la bo�te de dialogue

         if (dialog == null) 
            dialog = new PasswordChooser();
          
         // d�finir les valeurs par d�faut 
         dialog.setUser(new User("yourname", null));

         // afficher la bo�te de dialogue 
         if (dialog.showDialog(DataExchangeFrame.this, 
            "Connect"))
         {
            // si accept�, extraire l'entr�e de l'utilisateur 
            User u = dialog.getUser();
            textArea.append(
               "user name= " + u.getName()
               + ", password = " +  (new String(u.getPassword()))
               + "\n");
         }
      }
   }
}

/**
   Un s�lecteur de mot de passe affich� dans une bo�te de dialogue
*/
class PasswordChooser extends JPanel 
{  
   public PasswordChooser()
   {  
      setLayout(new BorderLayout());

      // construire un panneau avec les champs Nom d'utilisateur 
      // et Mot de passe 

       JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(2, 2));
      panel.add(new JLabel("User name :"));
      panel.add(username = new JTextField(""));
      panel.add(new JLabel("Password :"));
      panel.add(password = new JPasswordField(""));
      add(panel, BorderLayout.CENTER);

      // cr�er les boutons OK et Annuler pour 
      // fermer la bo�te de dialogue 
       
      okButton = new JButton("OK");
      okButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               ok = true;
               dialog.setVisible(false);
            }
         });

      JButton cancelButton = new JButton("Cancel");
      cancelButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               dialog.setVisible(false);
            }
         });

      // ajouter les boutons dans la zone sud 

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(okButton);
      buttonPanel.add(cancelButton);
      add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
      D�finit les valeurs par d�faut de la bo�te de dialogue.
      @param u Les informations utilisateur par d�faut 
   */
   public void setUser(User u)
   {
      username.setText(u.getName());
   }

   /**
      R�cup�re les entr�es dans la bo�te de dialogue.
      @return Un objet User dont l'�tat repr�sente 
         les entr�es dans la bo�te de dialogue 
   */
   public User getUser()
   {
      return new User(username.getText(), 
         password.getPassword());
   }

    /**
      Affiche le panneau s�lecteur dans une bo�te de dialogue 
      @param parent Un composant dans le cadre propri�taire ou null
      @param title Le titre de la bo�te de dialogue 
   */
   public boolean showDialog(Component parent, String title)
   {  
      ok = false;

      // localiser le cadre propri�taire 

      Frame owner = null;
      if (parent instanceof Frame)
         owner = (Frame) parent;
      else 
         owner = (Frame)SwingUtilities.getAncestorOfClass(
            Frame.class, parent);

      // si premi�re fois, ou si le propri�taire a chang�, 
      // cr�er une nouvelle bo�te de dialogue 
       
      if (dialog == null || dialog.getOwner() != owner) 
      {      
         dialog = new JDialog(owner, true);
         dialog.add(this);
         dialog.getRootPane().setDefaultButton(okButton);
         dialog.pack();
      }

      // d�finir le titre et afficher la bo�te de dialogue 

      dialog.setTitle(title);
      dialog.setVisible(true);
      return ok;
   }

   private JTextField username;
   private JPasswordField password;
   private JButton okButton;
   private boolean ok;
   private JDialog dialog;
}

/**
   Un utilisateur a un nom et un mot de passe. Pour des raisons
      de s�curit�, le mot de passe est stock� sous forme de 
      tableau de caract�res (char[]), au lieu d'une cha�ne.
*/
class User
{
   public User(String aName, char[] aPassword)
   {
      name = aName;
      password = aPassword;
   }
    
   public String getName() { return name; }
   public char[] getPassword() { return password; }

   public void setName(String aName) { name = aName; }
   public void setPassword(char[] aPassword) 
   { password = aPassword; }

   private String name;
   private char[] password;
}
