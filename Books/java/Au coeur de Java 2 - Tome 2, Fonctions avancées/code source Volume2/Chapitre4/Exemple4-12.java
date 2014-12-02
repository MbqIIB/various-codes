Exemple 4.12 : WarehouseServer.java

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;

/**
   Ce programme serveur instancie un objet d'un entrep�t
   distant, l'enregistre aupr�s du service de nom et attend
   que les clients invoquent les m�thodes.
*/
public class WarehouseServer
{
   public static void main(String[] args)
   {
      try
      {
         System.out.println
            ("Construction des impl�mentations du serveur...");

         WarehouseImpl w = new WarehouseImpl();
         w.add(new ProductImpl("Grille-pain Moulinex", Product.BOTH, 
               18, 200, "Electrom�nager"));
         w.add(new ProductImpl("Micro-ondes Philips", Product.BOTH, 
               18, 200, "Electrom�nager"));
         w.add(new ProductImpl("Pelle � vapeur M�caTerrassement", 
               Product.MALE, 20, 60, "Jardinage"));
         w.add(new ProductImpl("D�sherbant U238", Product.BOTH, 
               20, 200, "Jardinage"));
         w.add(new ProductImpl("Fragrance Java persistante", 
               Product.FEMALE, 15, 45, "Beaut�"));
         w.add(new ProductImpl("Souris informatique BlackRongeur", 
               Product.BOTH, 6, 40, "Ordinateurs"));
         w.add(new ProductImpl("Mon premier Expresso", Product.FEMALE, 
               6, 10, "Electrom�nager"));
         w.add(new ProductImpl("Eau de Cologne JavaJungle", Product.MALE, 
               15, 45, "Beaut�"));
         w.add(new ProductImpl("Machine � Expresso FireWire", 
               Product.BOTH, 20, 50, "Ordinateurs"));
         w.add(new ProductImpl("Livre Les mauvaises habitudes de Java en 
               21 jours", Product.BOTH, 20, 200, "Ordinateurs"));

         System.out.println
            ("Liaison des impl�mentations du serveur � la base de registres...");
         Context namingContext = new InitialContex();
         namingContext.bind("rmi:central_warehouse", w);

         System.out.println
            ("Attente des invocations des clients...");
      }
      catch (Exception e)
      {
         e.printlnStackTrace();
      }
   }
}
