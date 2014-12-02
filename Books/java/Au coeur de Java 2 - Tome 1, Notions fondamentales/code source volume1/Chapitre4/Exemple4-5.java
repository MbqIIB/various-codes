Exemple 4.5 : ConstructorTest.java

import java.util.*;

public class ConstructorTest
{
   public static void main(String[] args)
   {
      // remplir le tableau staff avec 3 objets Employee 
      Employee[] staff = new Employee[3];

      staff[0] = new Employee("Harry", 40000);
      staff[1] = new Employee(60000);
      staff[2] = new Employee();

      // afficher les informations concernant 
      // tous les objets Employee 
      for (Employee e : staff)
         System.out.println("name=" + e.getName()
            + ",id=" + e.getId()
            + ",salary=" + e.getSalary());
   }
}

class Employee
{
   // trois constructeurs surcharg�s
   public Employee(String n, double s)
   {
      name = n;
      salary = s;
   }

   public Employee(double s)
   {
      // appelle le constructeur Employee(String, double) 
      this("Employee #" + nextId, s);
   }

   // le constructeur par d�faut 
   public Employee()
   {
      // name initialis� � ""--voir plus bas
      // salary non d�fini explicitement--initialis� � 0
      // id initialis� dans le bloc d'initialisation 
   }
 
   public String getName()
   {
      return name;
   }

   public double getSalary()
   {
      return salary;
   }

   public int getId()
   {
      return id;
   }

   private static int nextId;

   private int id;
   private String name = ""; // initialisation champ d'instance 
   private double salary;

   // bloc d'initialisation statique
   static
   {
      Random generator = new Random();
      // d�finir nextId � un nombre al�atoire 
      // entre 0 et 9999
      nextId = generator.nextInt(10000);
   }

   // bloc d'initialisation d'objet 
   {
      id = nextId;
      nextId++;
   }
}
