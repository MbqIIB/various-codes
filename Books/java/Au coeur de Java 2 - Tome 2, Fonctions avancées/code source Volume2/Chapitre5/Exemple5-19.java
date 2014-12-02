Exemple 5.19 : SplitPaneTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   Ce programme montre l'organisateur du composant s�parateur.
*/
public class SplitPaneTest
{
   public static void main(String[] args)
   {
      JFrame frame = new SplitPaneFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc est constitu� de deux volets imbriqu�s pour montrer 
   les images et les donn�es des plan�tes.
*/
class SplitPaneFrame extends JFrame
{
   public SplitPaneFrame()
   {
      setTitle("SplitPaneTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // d�finit les composants pour les noms des plan�tes, les images et 
      // les descriptions

      final JList planetList = new JList(planets);
      final JLabel planetImage = new JLabel();
      final JTextArea planetDescription = new JTextArea();

      planetList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent event)
            {  
               Planet value 
                  = (Planet)planetList.getSelectedValue();

               // met � jour l'image et la description

               planetImage.setIcon(value.getImage());
               planetDescription.setText(value.getDescription());
            }
         });
      
      // d�finit les s�parateurs

      JSplitPane innerPane 
         = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            planetList, planetImage);

      innerPane.setContinuousLayout(true);
      innerPane.setOneTouchExpandable(true);

      JSplitPane outerPane 
         = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            innerPane, planetDescription);

      add(outerPane, BorderLayout.CENTER);
   }
   
   private Planet[] planets =
      {
         new Planet("Mercure", 2440, 0),
         new Planet("V�nus", 6052, 0),
         new Planet("Terre", 6378, 1),
         new Planet("Mars", 3397, 2),
         new Planet("Jupiter", 71492, 16),
         new Planet("Saturne", 60268, 18),
         new Planet("Uranus", 25559, 17),
         new Planet("Neptune", 24766, 8),
         new Planet("Pluton", 1137, 1),
      };
   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 300;
}

/**
   D�crit une plan�te.
*/
class Planet
{
   /**
      Construit une plan�te.
      @param n le nom de la plan�te
      @param r le rayon de la plan�te
      @param m le nombre de lunes
   */
  public Planet(String n, double r, int m)
   {  name = n;
      radius = r;
      moons = m;
      image = new ImageIcon(name + ".gif");
   }
   
   public String toString()
   {
      return name;
   }
   
   /**
      Obtient une description de la plan�te.
      @return la description
   */
   public String getDescription()
   {
      return "Rayon: " + radius + "\nLunes: " + moons + "\n";
   }
   
   /**
      Obtient une image de la plan�te.
      @return l'image
   */
   public ImageIcon getImage()
   {
      return image;
   }

   private String name;
   private double radius;
   private int moons;
   private ImageIcon image;
}
