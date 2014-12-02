Exemple 11.8 : RobotTest.java

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class RobotTest
{
   public static void main(String[] args)
   {  
      // cr�er un cadre avec un panneau de bouton

      ButtonFrame frame = new ButtonFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      // joindre un robot au p�riph�rique �cran

      GraphicsEnvironment environment 
         = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice screen 
         = environment.getDefaultScreenDevice();
      
      try
      {
         Robot robot = new Robot(screen);
         run(robot);
      }
      catch (AWTException e)
      {
         e.printStackTrace();
      }
   }

   /**
      Ex�cute un exemple de proc�dure de test 
      @param robot Le robot attach� au p�riph�rique �cran
   */
   public static void run(Robot robot)
   {
      // simuler une pression sur la barre espace
      robot.keyPress(' ');
      robot.keyRelease(' ');
      
      // simuler une touche de tabulation suivie d'une espace
      robot.delay(2000);
      robot.keyPress(KeyEvent.VK_TAB);
      robot.keyRelease(KeyEvent.VK_TAB);
      robot.keyPress(' ');
      robot.keyRelease(' ');
      
      // simuler un clic de souris sur le bouton droit
      robot.delay(2000);
      robot.mouseMove(200, 50);
      robot.mousePress(InputEvent.BUTTON1_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_MASK);
      
      // capturer l'�cran et afficher l'image
      robot.delay(2000);
      BufferedImage image = robot.createScreenCapture(
         new Rectangle(0, 0, 400, 300));
      
      ImageFrame frame = new ImageFrame(image);
      frame.setVisible(true);
   }
}

/**
   Un cadre pour afficher une capture d'�cran
*/
class ImageFrame extends JFrame
{
   /**
      @param image L'image � afficher
   */
   public ImageFrame(Image image)
   {
      setTitle("Capture");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      
      JLabel label = new JLabel(new ImageIcon(image));
      add(label);
   }

   public static final int DEFAULT_WIDTH = 450;
   public static final int DEFAULT_HEIGHT = 350;  
}
