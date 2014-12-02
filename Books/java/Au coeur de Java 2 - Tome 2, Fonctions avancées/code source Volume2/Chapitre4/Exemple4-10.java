Exemple 4.10 : Warehouse.java

import java.rmi.*;
import java.util.*;

/**
   L'interface distante d'un entrep�t avec des produits.
*/
public interface Warehouse extends Remote
{
   /**
      R�cup�re les produits correspondant au client.
      @param c le client concern�
      @return une liste de tableau des produits correspondants
   */
  ArrayList<Product> find(Customer c) throws RemoteException;
}
