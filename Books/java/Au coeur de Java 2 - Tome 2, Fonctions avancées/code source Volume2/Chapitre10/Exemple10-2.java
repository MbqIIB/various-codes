Exemple 10.2 : GridBagTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   Ce programme montre comment utiliser un fichier XML pour d�crire
   une disposition de grille.
*/
public class GridBagTest
{  
   public static void main(String[] args)
   {  
      String filename = args.length == 0 ? "fontdialog.xml" : args[0];
      JFrame frame = new FontFrame(filename);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient une bo�te de dialogue pour la s�lection des polices
   d�crites par un fichier XML.
   @param filename Le fichier contenant les composants de l'interface
   utilisateur pour la bo�te de dialogue
*/
class FontFrame extends JFrame
{  
   public FontFrame(String filename)
   {  
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      setTitle("GridBagTest");

      gridbag = new GridBagPane(filename);
      add(gridbag);

      face = (JComboBox) gridbag.get("�il de caract�re");
      size = (JComboBox) gridbag.get("Taille");
      bold = (JCheckBox) gridbag.get("Gras");
      italic = (JCheckBox) gridbag.get("Italique");

      face.setModel(new DefaultComboBoxModel(new Object[]
         {
            "Serif", "SansSerif", "Monospaced",
            "Dialog", "DialogInput"
         }));
         
      size.setModel(new DefaultComboBoxModel(new Object[]
         {
            "8", "10", "12", "15", "18", "24", "36", "48" 
         }));

      ActionListener listener = new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
               { setSample(); }
         };

      face.addActionListener(listener);
      size.addActionListener(listener);
      bold.addActionListener(listener);
      italic.addActionListener(listener);

      setSample();
   }

   /**
      Cette m�thode applique la police s�lectionn�e � l'�chantillon de 
      texte.
   */
   public void setSample()
   {
      String fontFace = (String) face.getSelectedItem();
      int fontSize = Integer.parseInt(
         (String) size.getSelectedItem());
      JTextArea sample = (JTextArea) gridbag.get("sample");
      int fontStyle 
         = (bold.isSelected() ? Font.BOLD : 0) 
         + (italic.isSelected() ? Font.ITALIC : 0);
      
      sample.setFont(new Font(fontFace, fontStyle, fontSize));
      sample.repaint();      
   }

   private GridBagPane gridbag;
   private JComboBox face;
   private JComboBox size;
   private JCheckBox bold;
   private JCheckBox italic;
   private static final int DEFAULT_WIDTH = 400;
   private static final int DEFAULT_HEIGHT = 400;
}
