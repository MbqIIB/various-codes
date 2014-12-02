Exemple 9.21 : FileChooserTest.java

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

public class FileChooserTest
{
   public static void main(String[] args)
   {
      ImageViewerFrame frame = new ImageViewerFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un menu pour le chargement d'une image et
      une zone d'affichage pour l'image charg�e.
*/
class ImageViewerFrame extends JFrame
{
   public ImageViewerFrame()
   {
      setTitle("FileChooserTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // configurer la barre de menus
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      JMenu menu = new JMenu("File");
      menuBar.add(menu);

      JMenuItem openItem = new JMenuItem("Open");
      menu.add(openItem);
      openItem.addActionListener(new FileOpenListener());

      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(exitItem);
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });

      // utiliser un label pour afficher les images
      label = new JLabel();
      add(label);

      // configurer le s�lecteur de fichier      
      chooser = new JFileChooser();

      // accepter tous les fichiers images avec
      // l'extension .jpg, .jpeg, .gif
      final ExtensionFileFilter filter 
         = new ExtensionFileFilter();
      filter.addExtension("jpg");
      filter.addExtension("jpeg");
      filter.addExtension("gif");
      filter.setDescription("Image files");
      chooser.setFileFilter(filter);

      chooser.setAccessory(new ImagePreviewer(chooser));

      chooser.setFileView(new FileIconView(filter, 
         new ImageIcon("palette.gif")));
   }

   /**
      Ecouteur du menu File->Open.
   */
   private class FileOpenListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         chooser.setCurrentDirectory(new File("."));

         // afficher la bo�te de dialogue de s�lection de fichier
         int result 
            = chooser.showOpenDialog(ImageViewerFrame.this);

         // si le fichier image est accept�, le d�finir comme
         // ic�ne pour le label 
         if(result == JFileChooser.APPROVE_OPTION)
         {
            String name
               = chooser.getSelectedFile().getPath();
            label.setIcon(new ImageIcon(name));
         }
      }
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 400;  

   private JLabel label;
   private JFileChooser chooser;
}

/**
   Ce filtre recherche tous les fichiers ayant 
      un jeu d'extensions donn�.
*/
class ExtensionFileFilter extends FileFilter
{
   /**
      Ajoute une extension reconnue par ce filtre de fichier.
      @param extension Une extension fichier (telle que ".txt" ou "txt")
   */
   public void addExtension(String extension)
   {
      if (!extension.startsWith("."))
         extension = "." + extension;
      extensions.add(extension.toLowerCase());     
   }

   /**
      D�finit une description pour le jeu de fichiers 
         que ce filtre reconna�t.
      @param aDescription Une description pour le jeu de fichiers
   */
   public void setDescription(String aDescription)
   {
      description = aDescription;
   }

   /**
      Renvoie une description pour le jeu de fichiers que 
         ce filtre reconna�t.
      @return Une description pour le jeu de fichiers
   */
   public String getDescription()
   {
      return description; 
   }

   public boolean accept(File f)
   {
      if (f.isDirectory()) return true;
      String name = f.getName().toLowerCase();

      // v�rifier si le nom du fichier se termine par
      // l'une quelconque des extensions
      for (String extension : extensions)
         if (name.endsWith(extension)) 
            return true;
      return false;
   }
    
   private String description = "";
   private ArrayList<String> extensions = new ArrayList<String>();
}

/**
   Une vue du fichier qui affiche une ic�ne pour tous les
      fichiers qui correspondent � un filtre 
*/
class FileIconView extends FileView
{
   /** 
       Construit un FileIconView.
       @param aFilter Un filtre de fichier - tous les fichiers que
          ce filtre accepte s'afficheront avec l'ic�ne 
       @param anIcon L'ic�ne affich�e avec les fichiers reconnus.
   */
   public FileIconView(FileFilter aFilter, Icon anIcon)
   {
      filter = aFilter;
      icon = anIcon;
   }

   public Icon getIcon(File f)
   {
      if (!f.isDirectory() && filter.accept(f)) 
         return icon;
      else return null;
   }
    
   private FileFilter filter;
   private Icon icon;
}

/**
   Un accessoire de s�lecteur de fichier donnant un
       aper�u des images.
*/
class ImagePreviewer extends JLabel 
{
   /**
      Construit un ImagePreviewer.
      @param chooser Le s�lecteur de fichier dont la propri�t� change, 
         d�clenche un changement de l'image dans l'aper�u 
   */
   public ImagePreviewer(JFileChooser chooser) 
   {
      setPreferredSize(new Dimension(100, 100));
      setBorder(BorderFactory.createEtchedBorder());

      chooser.addPropertyChangeListener(new 
         PropertyChangeListener()
         {
            public void propertyChange(PropertyChangeEvent 
               event) 
            {
               if (event.getPropertyName() ==
                  JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)
               {
                  // l'utilisateur a s�lectionn� un nouveau fichier 
                  File f = (File)event.getNewValue();
                  if (f == null) { setIcon(null); return; }
                   
                  // r�cup�rer l'image sous forme d'ic�ne 
                  ImageIcon icon = new ImageIcon(f.getPath());

                  // si l'ic�ne est trop grande, la r�duire
                  if(icon.getIconWidth() > getWidth())
                     icon = new ImageIcon(
                        icon.getImage().getScaledInstance(
                           getWidth(), -1, Image.SCALE_DEFAULT));

                  setIcon(icon);
               }
         }
      });
   }
} 
