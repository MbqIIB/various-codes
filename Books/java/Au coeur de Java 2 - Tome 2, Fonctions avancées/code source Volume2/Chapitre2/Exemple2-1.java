Exemple 2.1 : LinkedListTest.java

import java.util.*;

/**
   Ce programme pr�sente le fonctionnement des listes cha�n�es.
*/
public class LinkedListTest
{  
   public static void main(String[] args)
   {  
      List<String> a = new LinkedList<String>();
      a.add("Claire");
      a.add("Carl");
      a.add("Erica");

      List<String> b = new LinkedList<String>();
      b.add("Bob");
      b.add("Doug");
      b.add("Frances");
      b.add("Gloria");

      // fusionne les mots de b dans a

      ListIterator<String> aIter = a.listIterator();
      Iterator<String> bIter = b.iterator();

      while (bIter.hasNext())
      {  
         if (aIter.hasNext()) aIter.next();
         aIter.add(bIter.next());
      }

      System.out.println(a);

      // supprime un mot sur deux dans b

      bIter = b.iterator();
      while (bIter.hasNext())
      {  
         bIter.next(); // saute un �l�ment
         if (bIter.hasNext())
         {  
            bIter.next(); // saute un �l�ment
            bIter.remove(); // supprime cet �l�ment
         }
      }

      System.out.println(b);

      // Op�ration globale : supprime tous les mots dans b de a

      a.removeAll(b);

      System.out.println(a);
   }
}
