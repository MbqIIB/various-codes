Exemple 7.4 : PermissionTest.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import javax.swing.*;

/**
   Cette classe montre la permission personnalis�e WordCheckPermission.
*/
public class PermissionTest
{
   public static void main(String[] args)
   {
      System.setSecurityManager(new SecurityManager());
      JFrame f = new PermissionTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient un champ de texte pour ins�rer des mots
   dans une zone de texte prot�g�e des "mots interdits".
*/
class PermissionTestFrame extends JFrame
{
   public PermissionTestFrame()
   {
      setTitle("PermissionTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      textField = new JTextField(20);
      JPanel panel = new JPanel();
      panel.add(textField);
      JButton openButton = new JButton("Ins�rer");
      panel.add(openButton);
      openButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               insertWords(textField.getText());
            }
         });

      add(panel, BorderLayout.NORTH);

      textArea = new WordCheckTextArea();
      add(new JScrollPane(textArea), BorderLayout.CENTER);
   }

   /**
      Essaie d'ins�rer des mots dans la zone de texte.
      Affiche une bo�te de dialogue si la tentative a �chou�.
      @param words les mots � ins�rer
   */
   public void insertWords(String words)
   {
      try
      {
         textArea.append(words + "\n");
      }
      catch (SecurityException e)
      {
         JOptionPane.showMessageDialog(this,
            "D�sol�, c'est interdit.");
      }
   }

   private JTextField textField;
   private WordCheckTextArea textArea;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 300;
}

/**
   Une zone de texte dont la m�thode append cr�e une 
   v�rification de s�curit� afin qu'aucun mot interdit ne 
   soit ajout�.
*/
class WordCheckTextArea extends JTextArea
{
   public void append(String text)
   {
      WordCheckPermission p
         = new WordCheckPermission(text, "insert");
      SecurityManager manager = System.getSecurityManager();
      if (manager != null) manager.checkPermission(p);
      super.append(text);
   }
}
