Exemple 9.5 : CheckBoxtTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckBoxTest
{
   public static void main(String[] args)
   {  
      CheckBoxFrame frame = new CheckBoxFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un label pour l'exemple de texte et des
      cases � cocher pour s�lectionner des attributs de police.
*/
class CheckBoxFrame extends JFrame 
{  
   public CheckBoxFrame()
   {  
      setTitle("CheckBoxTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter le label d'exemple de texte

      label = new JLabel("The quick brown fox jumps over the lazy dog.");
      label.setFont(new Font("Serif", Font.PLAIN, FONTSIZE));
      add(label, BorderLayout.CENTER);

      // Cet �couteur affecte � l'attribut de police du libell�
      // le statut de la case � cocher 

      ActionListener listener = new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               int mode = 0;
               if (bold.isSelected()) mode += Font.BOLD; 
               if (italic.isSelected()) mode += Font.ITALIC;
               label.setFont(new Font("Serif", mode, FONTSIZE));
            }
         };

      // ajouter les cases � cocher 

      JPanel buttonPanel = new JPanel();

      bold = new JCheckBox("Bold");    
      bold.addActionListener(listener);
      buttonPanel.add(bold);

      italic = new JCheckBox("Italic");    
      italic.addActionListener(listener);
      buttonPanel.add(italic);

      add(buttonPanel, BorderLayout.SOUTH);
   }
    
   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  

   private JLabel label;
   private JCheckBox bold;
   private JCheckBox italic;        

   private static final int FONTSIZE = 12;
}
