Exemple 5.17 : ProgressMonitorTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.Timer;

/**
   Un programme qui teste un contr�leur de progression.
*/
public class ProgressMonitorTest
{
   public static void main(String [] args))
   {
      JFrame frame = new ProgressMonitorFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Un cadre qui contient un bouton pour lancer une activit� 
   simul�e et une zone de texte pour le r�sultat de l'activit�.
*/
class ProgressMonitorFrame extends JFrame
{
   public ProgressMonitorFrame()
   {
      setTitle("ProgressMonitorTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // cette zone de texte contient le r�sultat de l'activit�
      textArea = new JTextArea();

      // configurer un cadre de boutons
      JPanel panel = new JPanel();
      startButton = new JButton("D�marrer");
      panel.add(startButton);

      add(new JScrollPane(textArea), BorderLayout.CENTER);
      add(panel, BorderLayout.SOUTH);

      // configurer l'action du bouton

      startButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               // d�marrer l'activit�
               activity = new SimulatedActivity(1000);
               activityThread = new Thread(activity);
               activityThread.start();

               // lancer la bo�te de dialogue de progression
               progressDialog = new 
                  ProgressMonitor(ProgressMonitorFrame.this,
                  "En attente de l'activit� simul�e", null, 0, 
                  activity.getTarget());

               // d�marrer le minuteur
               activityMonitor.start();

               startButton.setEnabled(false);
            }         
         });

      // configurer l'action du minuteur

      activityMonitor = new Timer(500, new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               int current = activity.getCurrent();

               // afficher la progression
               textArea.append(current + "\n");
               progressDialog.setProgress(current);

               // v�rifier si la t�che est termin�e ou annul�e
               if (current == activity.getTarget() 
                  || progressDialog.isCanceled())
               {
                  activityMonitor.stop();
                  progressDialog.close();
                  activityThread.interrupt();
                  startButton.setEnabled(true);
               }
            }
         });
   }

   private Timer activityMonitor;
   private JButton startButton;
   private ProgressMonitor progressDialog;
   private JTextArea textArea;
   private Thread activityThread;
   private SimulatedActivity activity;

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;
}

/**
    Un ex�cutable d'activit� simul�e.
*/
class SimulatedActivity implements Runnable
{
   /**
      Construit l'objet thread de l'activit� simul�e. Le 
      thread incr�mente un compteur de 0 � une cible donn�e.
      @param t La valeur cible du compteur.
   */
   public SimulatedActivity(int t)
   {
      current = 0;
      target = t;
   }

   public int getTarget()
   {
      return target;
   }

   public int getCurrent()
   {
      return current;
   }

   public void run()
   {
      try
      {
         while (current < target)
         {
            Thread.sleep(100);
            current++;
         }
      }
      catch(InterruptedException e)
      {
      }
   }

   private volatile int current;
   private int target;
}

