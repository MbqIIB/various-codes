Exemple 3.4 : Retirement2.java

import java.util.*;

public class Retirement2
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);

      System.out.print("How much money will you contribute every year?");
      double payment = in.nextDouble();

      System.out.print("Interest rate in %:");
      double interestRate = in.nextDouble();

      double balance = 0;
      int year = 0;

      String input;

      // mettre � jour le solde du compte tant que l'utilisateur
      // n'est pas pr�t � prendre sa retraite
      do
      {
         // ajouter versements et int�r�ts de cette ann�e 
         balance += payment;
         double interest = balance * interestRate / 100;
         balance += interest;

         year++;

         // afficher le solde actuel
         System.out.println("After year %d, your balance is %,.2f%n",
            year, balance);

         // demander si pr�t pour la retraite
         System.out.print("Ready to retire? (Y/N)");
         input = in.next();
      }
      while (input.equals("N"));
   }
}
