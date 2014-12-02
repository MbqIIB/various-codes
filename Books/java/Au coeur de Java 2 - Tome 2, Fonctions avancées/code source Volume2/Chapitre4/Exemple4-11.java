Exemple 4.11 : WarehouseImpl.java

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.locks.*;

/**
   Cette classe correspond � l'impl�mentation de l'interface
   distante de l'entrep�t.
*/
public class WarehouseImpl
   extends UnicastRemoteObject
   implements Warehouse
{
   /**
      Construit une impl�mentation d'entrep�t.
   */
   public WarehouseImpl()
      throws RemoteException
   {
     products = new ArrayList<ProductImpl>();
     add(new ProductImpl("Core Java Book",
         0, 200, Product.BOTH, "Ordinateurs"));
   }

   /**
      Ajoute un produit � l'entrep�t. Ceci est une m�thode locale.
      @param p Le produit � ajouter
   */
   public void add(ProductImpl p)
   {  
      Lock wlock = rwlock.writeLock();
      wlock.lock();
      try 
      {
         products.add(p);
      }
      finally
      {
         wlock.unlock();
      }
   }

   public ArrayList<Product> find(Customer c)
      throws RemoteException
   {
      Lock rlock = rwlock.readLock();
      rlock.lock();
      try 
      {
      ArrayList<Product> result = new ArrayList<Product>();
      //ajoute tous les produits correspondants
      for (ProductImpl p : products)
      {
         if (p.match(c)) result.add(p);
      }
      //ajoute le produit correspondant � tous, une copie de CoreJava
      if (!result.contains(products.get(0)))
         result.add(products.get(0));

      //nous r�initialisons c simplement pour montrer que c est
      //une copie de l'objet client
      c.reset();
      return result;
      }
      finally
      {
         rlock.unlock();
      }
   }

   private ArrayList<ProductImpl> products;
   private ReadWriteLock rwlock = new ReentrantReadWriteLock();
}
