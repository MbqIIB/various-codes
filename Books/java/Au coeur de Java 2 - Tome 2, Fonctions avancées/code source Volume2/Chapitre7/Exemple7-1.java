Exemple 7.1 : ClassLoaderTest.java 

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   Ce programme montre un chargeur de classe personnalis�
   qui d�crypte les fichiers de classe.
*/
public class ClassLoaderTest
{
   public static void main(String[] args)
   {
      JFrame frame = new ClassLoaderFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   Ce bloc contient deux champs de texte pour le nom et
   la classe � charger ainsi que la cl� de d�cryptage.
*/
class ClassLoaderFrame extends JFrame
{
   public ClassLoaderFrame()
   {
      setTitle("ClassLoaderTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      setLayout(new GridBagLayout());
      add(new JLabel("Classe"), new GBC(0, 0).setAnchor(GBC.EAST));
      add(nameField, new GBC(1, 0).setWeight(
         100, 0).setAnchor(GBC.WEST));
      add(new JLabel("Cl�"), new GBC(0, 1).setAnchor(BBC.EAST));
      add(keyField, new GBC(1, 1).setWeight(100, 0).setAnchor(GBC.WEST));
      JButton loadButton = new JButton("Charger");
      add(loadButton, new GBC(0, 2, 2, 1));
      loadButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               runClass(nameField.getText(), keyField.getText());
            }
         });
      pack();    
   }

   /**
      Lance la m�thode principale d'une classe donn�e.
      @param name le nom de la classe
      @param key la cl� de d�cryptage pour les fichiers de classe
   */
   public void runClass(String name, String key)
   {
      try
      {
         ClassLoader loader
            = new CryptoClassLoader(Integer.parseInt(key));
         Class c = loader.loadClass(name);
         String[] args = new String[] {};

         Method m = c.getMethod("main", args.getClass());
         m.invoke(null, (Object) args);
      }
      catch (Throwable e)
      {
         JOptionPane.showMessageDialog(this, e);
      }
   }

   private JTextField keyField = new JTextField("3", 4);
   private JTextField nameField = new JTextField(30);
   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 200;
}

/**
   Ce chargeur de classe charge des fichiers de classe crypt�s.
*/
class CryptoClassLoader extends ClassLoader
{
   /**
      Construit un chargeur de classe crypt�.
      @param k la cl� de d�cryptage
   */
   public CryptoClassLoader(int k)
   {
      key = k;
   }

   protected Class findClass(String name)
      throws ClassNotFoundException
   {
         byte[] classBytes = null;
         try
         {
           classBytes = loadClassBytes(name);
         }
         catch (IOException e)
         {
           throw new ClassNotFoundException(name);
         }
         Class cl = defineClass(name, classBytes,
            0, classBytes.length);
         if (cl == null)
            throw new ClassNotFoundException(name);
         return cl;
      }

   /**
      Charge et d�crypte les octets du fichier de classe.
      @param name le nom de classe
      @return un tableau avec les octets du fichier de classe
   */
   private byte[] loadClassBytes(String name)
      throws IOException   
   {
      String cname = name.replace('.', '/') +  ".caesar";
      FileInputStream in = null;
      in = new FileInputStream(cname);
      try
      {
         ByteArrayOutputStream buffer
            = new ByteArrayOutputStream();
         int ch;
         while ((ch = in.read()) != -1)
         {
            byte b = (byte) (ch - key);
            buffer.write(b);
         }
         in.close();
         return buffer.toByteArray();
      }
      finally
      {
            in.close();
      }
   }

   private int key;
}
