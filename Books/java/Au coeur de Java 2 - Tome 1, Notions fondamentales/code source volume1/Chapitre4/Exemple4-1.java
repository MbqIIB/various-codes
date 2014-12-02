Exemple 4.1 : CalendarTest.java

import java.util.*;

public class CalendarTest
{  
   public static void main(String[] args)
   {  
      // construire d comme la date courante
      GregorianCalendar d = new GregorianCalendar();

      int today = d.get(Calendar.DAY_OF_MONTH);
      int month = d.get(Calendar.MONTH);

      // attribuer � d le premier jour du mois
      d.set(Calendar.DAY_OF_MONTH, 1);

      int weekday = d.get(Calendar.DAY_OF_WEEK);

      // imprimer l'en-t�te 
      System.out.println("Sun Mon Tue Wed Thu Fri Sat");

      // indenter la premi�re ligne du calendrier 
      for (int i = Calendar.SUNDAY; i < weekday; i++ )
         System.out.print("    ");

      do
      {  
         // imprimer la date
         int day = d.get(Calendar.DAY_OF_MONTH);
         System.out.printf("%3d", day);

         // marquer la date du jour avec un *
         if (day == today)
            System.out.print("*");
         else
            System.out.print("  ");

         // sauter � la ligne apr�s chaque samedi
         if (weekday == Calendar.SATURDAY)
            System.out.println();

         // incr�menter d 
         d.add(Calendar.DAY_OF_MONTH, 1);
         weekday = d.get(Calendar.DAY_OF_WEEK);
      } 
      while (d.get(Calendar.MONTH) == month);
      // sortir de la boucle si d est le premier jour du mois suivant 

      // imprimer derni�re fin de ligne si n�cessaire
      if (weekday != Calendar.SUNDAY)
         System.out.println();
   }
}
