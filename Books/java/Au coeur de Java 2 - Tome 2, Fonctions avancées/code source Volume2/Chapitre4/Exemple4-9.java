Exemple 4.9 : Customer.java

import java.io.*;

/**
   Description d'un client. Sachez que les objets customer ne sont pas 
   distants--la classe n'impl�mente pas d'interface distante.
*/
public class Customer implements Serializable
{
  /**
     Construits un client.
     @param theAge l'�ge du client
     @param theSex le sexe du client (MASCULIN ou FEMININ)
     @param theHobbies les loisirs du client
  */
  public Customer(int theAge, int theSex, String[] theHobbies)
   {
      age = theAge;
      sex = theSex;
      hobbies = theHobbies;
   }

   /**
      R�cup�re l'�ge du client.
      @return l'�ge
   */
   public int getAge() { return age; }

   /**
      R�cup�re le sexe du client
      @return MASCULIN ou FEMININ
   */
   public int getSex() { return sex; }

   /**
      Teste si ce client a un loisir particulier.
      @param aHobby le loisir � tester
      @return true si ce client a choisi le loisir
   */
   public boolean hasHobby(String aHobby)
   {
      if (aHobby == "") return true;
      for (int i = 0; i < hobbies.length; i++)
         if (hobbies[i].equals(aHobby)) return true;

      return false;
   }

   /**
      R�initialise cet enregistrement client sur ses valeurs par d�faut.
   */
   public void reset()
   {
      age = 0;
      sex = 0;
      hobbies = null;
   }

   public String toString()
   {
      String result = "Age: " + age + ", Sexe: ";
      if (sex == Product.MALE) result += "masculin";
      else if (sex == Product.FEMALE) result += "f�minin";
      else result += " masculin ou f�minin ";
      result += ", Hobbies:";
      for (int i = 0; i < hobbies.length; i++)
         result += " " + hobbies[i];
      return result;
   }

   private int age;
   private int sex;
   private String[] hobbies;
}
