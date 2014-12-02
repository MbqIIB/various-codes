Exemple 9.1 : Calculator.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator
{
   public static void main(String[] args)
   {  
      CalculatorFrame frame = new CalculatorFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre avec un panneau pour une calculatrice.
*/
class CalculatorFrame extends JFrame
{
   public CalculatorFrame()
   {
      setTitle("Calculator");
      CalculatorPanel panel = new CalculatorPanel();
      add(panel);
      pack();
   }
}

/**
   Un panneau avec les boutons de la calculatrice 
   et l'affichage des r�sultats.
*/
class CalculatorPanel extends JPanel
{  
   public CalculatorPanel()
   {  
      setLayout(new BorderLayout());

      result = 0;
      lastCommand = "=";
      start = true;

      // ajouter la zone d'affichage

      display = new JButton("0");
      display.setEnabled(false);
      add(display, BorderLayout.NORTH);

      ActionListener insert = new InsertAction();
      ActionListener command = new CommandAction();

      // ajouter les boutons dans une grille 4 x 4 

      panel = new JPanel();
      panel.setLayout(new GridLayout(4, 4));

      addButton("7", insert);
      addButton("8", insert);
      addButton("9", insert);
      addButton("/", command);

      addButton("4", insert);
      addButton("5", insert);
      addButton("6", insert);
      addButton("*", command);

      addButton("1", insert);
      addButton("2", insert);
      addButton("3", insert);
      addButton("-", command);

      addButton("0", insert);
      addButton(".", insert);
      addButton("=", command);
      addButton("+", command);

      add(panel, BorderLayout.CENTER);
   }

   /**
      ajoute un bouton au panneau central.
      @param label Le libell� du bouton
      @param listener L'�couteur du bouton
   */
   private void addButton(String label, ActionListener listener)
   {  
      JButton button = new JButton(label);
      button.addActionListener(listener);
      panel.add(button);
   }

   /**
      Cette action ins�re la cha�ne d'action du bouton 
      � la fin du texte d'affichage.
   */
   private class InsertAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         String input = event.getActionCommand();
         if (start) 
         {
            display.setText("");
            start = false;
         }
         display.setText(display.getText() + input);
      }
   }

   /**
      Cette action ex�cute la commande indiqu�e par la cha�ne 
      d'action du bouton.
   */
   private class CommandAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {  
         String command = event.getActionCommand();

         if (start)
         {  
            if (command.equals("-")) 
            { 
               display.setText(command); 
               start = false; 
            }
            else 
               lastCommand = command;
         }
         else
         {  
            calculate(Double.parseDouble(display.getText()));
            lastCommand = command;
            start = true;
         }
      }
   }

   /**
      Ex�cute le calcul en attente. 
      @param x La valeur � cumuler avec le r�sultat pr�c�dent.
   */
   public void calculate(double x)
   {
      if (lastCommand.equals("+")) result += x;
      else if (lastCommand.equals("-")) result -= x;
      else if (lastCommand.equals("*")) result *= x;
      else if (lastCommand.equals("/")) result /= x;
      else if (lastCommand.equals("=")) result = x;
      display.setText("" + result);
   }

   private JButton display;
   private JPanel panel;
   private double result;
   private String lastCommand;
   private boolean start;
}
