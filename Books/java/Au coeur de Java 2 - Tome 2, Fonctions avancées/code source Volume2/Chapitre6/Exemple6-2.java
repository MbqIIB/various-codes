Exemple 6.2 : FileNameBean.java


package com.horstmann.corejava;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;

/**
   Un bean permettant de sp�cifier des noms de fichiers.
*/
public class FileNameBean extends JPanel
{
   public FileNameBean()
   {
      dialogButton = new JButton("...");
      nameField = new JTextField(30);

      chooser = new JFileChooser();
 
      chooser.setFileFilter(new
         javax.swing.filechooser.FileFilter()
         {
            public boolean accept(File f)
            {
               String name = f.getName().toLowerCase();
               return name.endsWith("." + defaultExtension)
                  || f.isDirectory();
            }
            public String getDescription()
            {
              return defaultExtension + " files";
            }
         });

      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 100;
      gbc.weighty = 100;
      gbc.anchor = GridBagConstraints.WEST;
      gbc.fill = GridBagConstraints.BOTH;
      gbc.gridwidth = 1;
      gbc.gridheight = 1;
      add(nameField, gbc);

      dialogButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               int r = chooser.showOpenDialog(null);
               if(r == JFileChooser.APPROVE_OPTION)
               {  
                  File f = chooser.getSelectedFile();
                  try
                  {
                     String name = f.getCanonicalPath();
                     setFileName(name);
                  }
                  catch (IOException e)
                  {
                  }
               }
            }
         });
      nameField.setEditable(false);
      gbc.weightx = 0;
      gbc.anchor = GridBagConstraints.EAST;
      gbc.fill = GridBagConstraints.NONE;
      gbc.gridx = 1;
      add(dialogButton, gbc);
   }

    /**
       D�finit la propri�t� fileName.
       @param newValue le nouveau nom de fichier
    */
   public void setFileName(String newValue)
   {
      String oldValue = nameField.getText();
      nameField.setText(newValue);
      firePropertyChange("fileName", oldValue, newValue);
   }

   /**
      R�cup�re la propri�t� fileName.
      @return le nom du fichier s�lectionn�
   */
   public String getFileName()
   {
     return nameField.getText();
   }

   /**
      D�finit la propri�t� defaultExtension.
      @param s la nouvelle extension par d�faut
   */   
   public void setDefaultExtension(String s)
   {
      defaultExtension = s;
   }

   /**
      R�cup�re la propri�t� defaultExtension.
      @return l'extension par d�faut du s�lecteur de fichier
   */
   public String getDefaultExtension()
   {  
      return defaultExtension;
   }

   public Dimension getPreferredSize()
   {  
      return new Dimension(XPREFSIZE, YPREFSIZE);
   }
   
   private static final int XPREFSIZE = 200;
   private static final int YPREFSIZE = 20;
   private JButton dialogButton;
   private JTextField nameField;
   private JFileChooser chooser;
   private String defaultExtension = "gif";
}
