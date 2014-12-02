Exemple 2.4 : PriorityQueueTest.java

import java.util.*;

/**
   Ce programme pr�sente une queue de priorit�.
*/
public class PriorityQueueTest
{  
   public static void main(String[] args)
   {  
      PriorityQueue<GregorianCalendar> pq = 
              new PriorityQueue<GregorianCalendar>();
      pq.add(new GregorianCalendar(1906, Calendar.DECEMBER, 9)); 
              // G. Hopper
      pq.add(new GregorianCalendar(1815, Calendar.DECEMBER, 10)); 
              // A. Lovelace
      pq.add(new GregorianCalendar(1903, Calendar.DECEMBER, 3)); 
              // J. von Neumann
      pq.add(new GregorianCalendar(1910, Calendar.JUNE, 22)); 
              // K. Zuse

      System.out.println("It�ration sur les �l�ments...");
      for (GregorianCalendar date : pq)
         System.out.println(date.get(Calendar.YEAR));
      System.out.println("Suppression d'�l�ments...");
      while (!pq.isEmpty())
         System.out.println(pq.remove().get(Calendar.YEAR));
   }
}
