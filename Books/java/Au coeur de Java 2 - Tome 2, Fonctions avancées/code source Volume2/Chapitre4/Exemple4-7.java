Exemple 4.7 : Warehouse.java

import java.rmi.*;
import java.util.*;

/**
   L'interface distante d'un entrep�t avec des produits.
*/
public interface Warehouse extends Remote
{
   /**
      R�cup�re les produits correspondants � la demande du client.
      @param c le client demandeur
      @return une liste de tableau des produits correspondants
   */
  ArrayList<Product> find(Customer c) throws RemoteException;
}
