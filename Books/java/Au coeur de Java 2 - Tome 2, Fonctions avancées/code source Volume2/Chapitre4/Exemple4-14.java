Exemple 4.14 : ProductImpl.java

import java.rmi.*;
import java.rmi.server.*;

/**
   Ceci est la classe d'impl�mentation pour les objets du produit
   distant.
*/
public class ProductImpl
   extends UnicastRemoteObject
   implements Product
{ 
   /**
      Construit une impl�mentation de produit
      @param n le nom du produit 
   */
   public ProductImpl(String n) throws RemoteException
   {  
      name = n;
   }

   public String getDescription() throws RemoteException
   {  
      return "Je suis un " + name + ". Achetez-moi !";
   }

   private String name;
}
