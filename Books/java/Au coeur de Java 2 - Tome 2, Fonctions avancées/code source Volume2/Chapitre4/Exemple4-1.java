Exemple 4.1 : ProductServer.java

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;

/**
   Ce programme de serveur instancie deux objets distants,
   les enregistre aupr�s du service de nom et attend les
   clients pour invoquer les m�thodes sur les objets distants.
*/
public class ProductServer
{
   public static void main(String args[])
   {
      try
      {
         System.out.println
            ("Construction des impl�mentations du serveur...");

         ProductImpl p1
            = new ProductImpl("grille-pain Moulinex");
         ProductImpl p2
            = new ProductImpl("micro-ondes Philips");

         System.out.println
            ("Liaison des impl�mentations du serveur � la base de registres...");
         Context namingContext = new InitialContext();
         namingContext.bind("rmi:grille-pain", p1);
         namingContext.bind("rmi:micro-ondes", p2);

         System.out.println
            ("Attente des invocations des clients...");
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}
