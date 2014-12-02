Exemple 2.2 : ImageViewer.java

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
   Un programme permettant d'afficher des images.
*/
public class ImageViewer
{
   public static void main(String[] args)
   {
      JFrame frame = new ImageViewerFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec une �tiquette permettant d'afficher une image.
*/
class ImageViewerFrame extends JFrame
{
   public ImageViewerFrame()
   {
      setTitle("ImageViewer");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

// utiliser une �tiquette pour afficher les images
      label = new JLabel();     
      add(label);

// configurer le s�lecteur de fichiers
      chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));

// configurer la barre de menus
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      JMenu menu = new JMenu("File");
      menuBar.add(menu);

      JMenuItem openItem = new JMenuItem("Open");
      menu.add(openItem);
      openItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               // montrer la bo�te de dialogue du s�lecteur
               int result = chooser.showOpenDialog(null);

               // en cas de s�lection d'un fichier, d�finir comme ic�ne
               // de l'�tiquette
               if (result == JFileChooser.APPROVE_OPTION)
               {
                  String name = chooser.getSelectedFile().getPath();
                  label.setIcon(new ImageIcon(name));
               }
            }
         });

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

   private JLabel label;
   private JFileChooser chooser;
   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 400;
}
