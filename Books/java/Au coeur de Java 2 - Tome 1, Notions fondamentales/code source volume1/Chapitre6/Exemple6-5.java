Exemple 6.5 : AnonymousInnerClassTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class AnonymousInnerClassTest
{    
   public static void main(String[] args)
   {  
      TalkingClock clock = new TalkingClock();
      clock.start(1000, true);

      // laisse le programme fonctionner jusqu'� ce que l'utilisateur 
      // clique sur "OK"
      JOptionPane.showMessageDialog(null, "Quit program?");
      System.exit(0);
   }
}

/**
   Une horloge qui affiche l'heure � intervalles r�guliers.
*/
class TalkingClock
{  
   /**
      D�marre l'horloge.
      @param interval l'intervalle entre les messages 
      (en milli�mes de seconde)
      @param beep true si l'horloge doit �mettre un bip
   */
   public void start(int interval, final boolean beep)
   {
      ActionListener listener = new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {  
               Date now = new Date();
               System.out.println("At the tone, the time is " + now);
               if (beep) Toolkit.getDefaultToolkit().beep();
            }
         };
      Timer t = new Timer(interval, listener);
      t.start();
   }
}

