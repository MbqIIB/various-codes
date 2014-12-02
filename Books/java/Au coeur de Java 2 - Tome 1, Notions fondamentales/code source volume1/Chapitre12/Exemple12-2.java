

Exemple 12.2 : DataFileTest.java

import java.io.*;
import java.util.*;

public class DataFileTest
{
   public static void main(String[] args)
   {
      Employee[] staff = new Employee[3];

      staff[0] = new Employee("Carl Cracker", 75000,
         1987, 12, 15);
      staff[1] = new Employee("Harry Hacker", 50000,
         1989, 10, 1);
      staff[2] = new Employee("Tony Tester", 40000,
         1990, 3, 15);

      try
      {
         // enregistrer tous les dossiers employ� dans le 
         // fichier employee.dat
         PrintWriter out = new PrintWriter(new
            FileWriter("employee.dat"));
         writeData(staff, out);
         out.close();

         // r�cup�rer tous les enregistrements dans un nouveau tableau 
         BufferedReader in = new BufferedReader(new
            FileReader("employee.dat"));
         Employee[] newStaff = readData(in);
         in.close();

         // afficher les enregistrements employ� nouvellement lus
         for (int i = 0; i < newStaff.length; i++)
            System.out.println(newStaff[i]);
      }
      catch(IOException exception)
      {
         exception.printStackTrace();
      }
   }

   /**
      Ecrit tous les employ�s dans un tableau vers un printWriter
      @param e Un tableau d'employ�s
      @param out Un printWriter
   */
   static void writeData(Employee[] e, PrintWriter out)
      throws IOException
   {
      // �crire le nombre d'employ�s
      out.println(e.length);

      for (int i = 0; i < e.length; i++)
         e[i].writeData(out);
   }

   /**
      Lit un tableau d'employ�s � partir d'un lecteur buff�ris�
      @param in Le lecteur buff�ris�
      @return Le tableau d'employ�s
   */
   static Employee[] readData(BufferedReader in)
      throws IOException
   {
      // r�cup�rer la taille du tableau
      int n = Integer.parseInt(in.readLine());

      Employee[] e = new Employee[n];
      for (int i = 0; i < n; i++)
      {
         e[i] = new Employee();
         e[i].readData(in);
      }
      return e;
   }
}

class Employee
{
   public Employee() {}

   public Employee(String n, double s,
      int year, int month, int day)
   {
      name = n;
      salary = s;
      GregorianCalendar calendar
         = new GregorianCalendar(year, month - 1, day);
         // GregorianCalendar utilise 0 = janvier
      hireDay = calendar.getTime();
   }

   public String getName()
   {
      return name;
   }

   public double getSalary()
   {
      return salary;
   }

   public Date getHireDay()
   {
      return hireDay;
   }

   public void raiseSalary(double byPercent)
   {
      double raise = salary * byPercent / 100;
      salary += raise;
   }

   public String toString()
   {
      return getClass().getName()
         + "[name=" + name
         + ",salary=" + salary
         + ",hireDay=" + hireDay
         + "]";
   }

   /**
      Ecrit les donn�es des employ�s dans un PrintWriter
      @param out Le PrintWriter
   */
   public void writeData(PrintWriter out) throws IOException
   {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(hireDay);
      out.println(name + "|"
         + salary + "|"
         + calendar.get(Calendar.YEAR) + "|"
         + (calendar.get(Calendar.MONTH) + 1) + "|"
         + calendar.get(Calendar.DAY_OF_MONTH));
   }

   /**
      Lit les donn�es des employ�s depuis un lecteur buff�ris�
      @param in Le lecteur buff�ris�
   */
   public void readData(BufferedReader in) throws IOException
   {
      String s = in.readLine();
      StringTokenizer t = new StringTokenizer(s, "|");
      name = t.nextToken();
      salary = Double.parseDouble(t.nextToken());
      int y = Integer.parseInt(t.nextToken());
      int m = Integer.parseInt(t.nextToken());
      int d = Integer.parseInt(t.nextToken());
      GregorianCalendar calendar
         = new GregorianCalendar(y, m - 1, d);
         // GregorianCalendar utilise 0 = janvier
      hireDay = calendar.getTime();
   }

   private String name;
   private double salary;
   private Date hireDay;
}
