Exemple 1.4 : SynchBankTest.java


import java.util.concurrent.locks.*;

/** 
   Ce programme montre comment plusieurs threads peuvent 
   acc�der en s�curit� � une structure de donn�es.
*/
public class SynchBankTest
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
   Une banque avec plusieurs comptes.
*/
class Bank
{ 
   /**
      Construit la banque.
      @param n le nombre de comptes
      @param initialBalance le solde initial 
      pour chaque compte
   */
   public Bank(int n, double initialBalance)
   {  
      accounts = new double[n];
      for (int i = 0; i < accounts.length; i++)
         accounts[i] = initialBalance;
      bankLock = new ReentrantLock();
      sufficientFunds = bankLock.newCondition();
   }

   /**
      Transf�re l'argent d'un compte � l'autre.
      @param from le compte d'origine
      @param to le compte de destination
      @param amount le montant � transf�rer
   */
   public void transfer(int from, int to, double amount)
      throws InterruptedException
   {  
      bankLock.lock();
      try
      {
         while (accounts[from] < amount)
            sufficientFunds.await();
         System.out.print(Thread.currentThread());      
         accounts[from] -= amount;
         System.out.printf(" %10.2f de %d � %d", amount, from, to);
         accounts[to] += amount;
         System.out.printf(" Solde Total : %10.2f%n", getTotalBalance());
         sufficientFunds.signalAll();
      }
      finally
      {
         bankLock.unlock();
      }
   }

   /**
      R�cup�re la somme de tous les soldes.
      @return le solde total 
   */
   public double getTotalBalance()
   {  
      bankLock.lock();
      try
      {      
         double sum = 0;

         for (double a : accounts)
            sum += a;

         return sum;
      }
      finally
      {
         bankLock.unlock();
      }
   }

   /**
      R�cup�re le nombre de comptes � la banque.
      @return le nombre de comptes
   */
   public int size()
   {
      return accounts.length;
   }

   private final double[] accounts;
   private Lock bankLock;
   private Condition sufficientFunds;
}

/**
   Un ex�cutable qui transf�re l'argent d'un compte � un 
   autre dans une banque.
*/
class TransferRunnable implements Runnable
{ 
   /**
      Construit un ex�cutable de transfert.
      @param b la banque dont l'argent est transf�r�
      @param from le compte de destination
      @param max montant maximum de chaque transfert 
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
      catch (InterruptedException e) {}
   }

   private Bank bank;
   private int fromAccount;
   private double maxAmount;
   private int repetitions;
   private int DELAY = 10;
}
