Exemple 3.7 : LotteryDrawing.java

import java.util.*;

public class LotteryDrawing
{  
   public static void main(String[] args)
   {  
      Scanner in = new Scanner(System.in);

      System.out.print("How many numbers do you need to draw? ");
      int k = in.nextInt();

      System.out.print("What is the highest number you can draw? ");
      int n = in.nextInt();

      // remplir un tableau avec les nombres 1 2 3 . . . n
      int[] numbers = new int[n];
      for (int i = 0; i < numbers.length; i++)
         numbers[i] = i + 1;

      // tirer k nombres et les mettre dans un second tableau
      int[] result = new int[k];
      for (int i = 0; i < result.length; i++)
      {  
         // cr�er un indice al�atoire entre 0 et n - 1
         int r = (int)(Math.random() * n);

         // choisir l'�l�ment � cet emplacement al�atoire
         result[i] = numbers[r];

         // d�placer le dernier �l�ment vers l'emplac. al�atoire 
         numbers[r] = numbers[n - 1];
         n--;
      }

      // imprimer le tableau tri�
      Arrays.sort(result);
      System.out.println
         ("Bet the following combination It'll make you rich!");
      for (int r : result)
         System.out.println(r);
   }
}
