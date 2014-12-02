Exemple 9.22 : ColorChooserTest.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ColorChooserTest
{
   public static void main(String[] args)
   {
      ColorChooserFrame frame = new ColorChooserFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau s�lecteur de couleur  
*/
class ColorChooserFrame extends JFrame
{
   public ColorChooserFrame()
   {
      setTitle("ColorChooserTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // ajouter le panneau s�lecteur de couleur au cadre

      ColorChooserPanel panel = new ColorChooserPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  
}

/**
   Un panneau avec des boutons pour afficher 
      trois types de s�lecteurs de couleur 
*/
class ColorChooserPanel extends JPanel
{
   public ColorChooserPanel()
   {
      JButton modalButton = new JButton("Modal");
      modalButton.addActionListener(new ModalListener());
      add(modalButton);

      JButton modelessButton = new JButton("Modeless");
      modelessButton.addActionListener(new ModelessListener());
      add(modelessButton);

      JButton immediateButton = new JButton("Immediate");
      immediateButton.addActionListener(new ImmediateListener());
      add(immediateButton);
   }

   /**
      Cet �couteur affiche un s�lecteur de couleur modal 
   */
   private class ModalListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         Color defaultColor = getBackground();
         Color selected = JColorChooser.showDialog(
            ColorChooserPanel.this, 
            "Set background", 
            defaultColor);
         if (selected != null) setBackground(selected);
       }
   }

   /**
      Cet �couteur affiche un s�lecteur de couleur non modal.
         La couleur du panneau est chang�e lorsque l'utilisateur 
         clique sur le bouton OK.
   */
   private class ModelessListener implements ActionListener
   {
      public ModelessListener()
      {
         chooser = new JColorChooser();
         dialog = JColorChooser.createDialog(
            ColorChooserPanel.this,
            "Background color",
            false /* non modal */,
            chooser,
            new ActionListener() // �couteur bouton OK 
               {
                  public void actionPerformed(ActionEvent event)
                  {
                     setBackground(chooser.getColor());
                  }
               },
            null /* pas d'�couteur pour le bouton Annuler */);
      }

      public void actionPerformed(ActionEvent event)
      {
         chooser.setColor(getBackground());
         dialog.setVisible(true);
      }

      private JDialog dialog;
      private JColorChooser chooser;
   }

   /**
      Cet �couteur affiche un s�lecteur de couleur non modal.
         La couleur du panneau change d�s que l'utilisateur 
         s�lectionne une nouvelle couleur.
   */
   private class ImmediateListener implements ActionListener
   {
      public ImmediateListener()
      {
         chooser = new JColorChooser();
         chooser.getSelectionModel().addChangeListener(new
            ChangeListener()
            {
               public void stateChanged(ChangeEvent event)
               {
                  setBackground(chooser.getColor());
               }
            });

         dialog = new JDialog(
            (Frame)null, 
            false /* non modal */);
         dialog.add(chooser);
         dialog.pack();
      }

      public void actionPerformed(ActionEvent event)
      {
         chooser.setColor(getBackground());
         dialog.setVisible(true);
      }

      private JDialog dialog;
      private JColorChooser chooser;
   }
} 
