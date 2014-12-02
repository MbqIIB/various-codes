Exemple 3.8 : CompoundInterest.java

public class CompoundInterest
{
   public static void main(String[] args)
   {
      final int STARTRATE = 10;
      final int NRATES = 6;
      final int NYEARS = 10;

      // d�finir les taux d'int�r�t de 10 � 15%
      double[] interestRate = new double[NRATES];
      for (int j = 0; j < interestRate.length; j++)
         interestRate[j] = (STARTRATE + j) / 100.0;

      double[][] balance = new double[NYEARS][NRATES];

      // d�finir les soldes initiaux � 10 000
      for (int j = 0; j < balance[0].length; j++)
         balance[0][j] = 10000;

      // calculer l'int�r�t des ann�es � venir
      for (int i = 1; i < balance.length; i++)
      {
         for (int j = 0; j < balance[i].length; j++)
         {
            // r�cup. solde ann�e pr�c�dente de la ligne pr�c�dente
            double oldBalance = balance[i - 1][j];

            // calculer l'int�r�t
            double interest = oldBalance * interestRate[j];

            // calculer le solde de l'ann�e
            balances[i][j] = oldBalance + interest;
         }
      }

      // imprimer une ligne de taux d'int�r�t

      for (int j = 0; j < interestRate.length; j++)
         System.out.printf("%9.0f%%", 100 * interestRate[j]));

      System.out.println();

      // imprimer la table des soldes
      for (double[] row : balances)
      {
         // imprimer une ligne de la table 
         for (double b : row)
            System.out.printf("%10.2f", b);

         System.out.println();
      }
   }
}
