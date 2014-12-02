Exemple 1.3 : UnsynchBankTest.java



/**
   Ce programme montre la corruption de donn�es lorsque
   plusieurs threads acc�dent � une structure de donn�es.
*/
public class UnsynchBankTest
{ 
   public static void main(String[] args)
   {  
      Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
      int i;
      for (i = 0; i < NACCOUNTS; i++)
      {  
         TransferRunnable r = new TransferRunnable(b, i,
            INITIAL_BALANCE);
         Thread t = new Thread(r);
         t.start();
      }
   }

   public static final int NACCOUNTS = 100;
   public static final double INITIAL_BALANCE = 1000;
}

/**
   Une banque avec plusieurs comptes bancaires
*/
class Bank
{ 
   /**
      Construit la banque
      @param n le nombre de comptes
      @param initialBalance le solde initial 
      pour chaque compte
   */
   public Bank(int n, int initialBalance)
   {  
      accounts = new double[n];
      for (int i = 0; i < accounts.length; i++)
         accounts[i] = initialBalance;
   }

   /**
      Transf�re l'argent d'un compte � l'autre
      @param from le compte d'origine du transfert
      @param to le compte vers lequel effectuer le transfert
      @param amount la somme � transf�rer
   */
   public void transfer(int from, int to, double amount)
   {  
      if (accounts[from] < amount) return;
      System.out.print(Thread.currentThread());
      accounts[from] -= amount;
      System.out.printf(" %10.2f de %d � %d", amount, from, to);
      accounts[to] += amount;
      System.out.printf(" Solde total : %10.2f%n", getTotalBalance());
   }

   /**
      R�cup�re la somme de tous les soldes.
      @return le solde
   */
   public double getTotalBalance()
   {
       double sum = 0;

       for (double a : accounts)
          sum += a;
 
       return sum;
   }

   /**
      Obtient le nombre de comptes dans la banque
      @return le nombre de comptes
   */
   public int size()
   {  
      return accounts.length;
   }

   private final double[] accounts;
}

/**
   Un ex�cutable qui transf�re l'argent d'un compte � l'autre
   dans une banque
*/
class TransferRunnable implements Runnable
{  
   /**
      Construit un ex�cutable de transfert
      @param b la banque dont les comptes sont concern�s par le transfert
      @param from le compte d'origine du transfert
      @param max la somme maximum d'argent pour chaque transfert 
   */
   public TransferRunnable(Bank b, int from, double max)
   {  
      bank = b;
      fromAccount = from;
      maxAmount = max;
   }

   public void run()
   {  
      try
      {  
         while (true)
         {  
            int toAccount = (int) (bank.size() * Math.random());
            double amount = maxAmount * Math.random();
            bank.transfer(fromAccount, toAccount, amount);
            Thread.sleep((int) (DELAY * Math.random()));
        }
      }
      catch(InterruptedException e) {}
  }

  private Bank bank;
  private int fromAccount;
  private double maxAmount;
  private int DELAY = 10;
}
