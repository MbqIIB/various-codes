Exemple 9.8 : Printf2Test.java

class Printf2Test
{
   public static void main(String[] args)
   {
      double price = 44.95;
      double tax = 7.75;
      double amountDue = price * (1 + tax / 100);

      String s = Printf2.sprint("Montant d� = %8.2f", 
         amountDue);
      System.out.println(s);
   }
}
