Exemple 1.7 : FutureTest.java

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
 
public class FutureTest
{
   public static void main(String[] args) 
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Entrez le r�pertoire de base 
                      (par ex. /usr/local/jdk5.0/src): ");
      String directory = in.nextLine();
      System.out.print("Entrez le mot cl� (par ex. volatile): ");
      String keyword = in.nextLine();     
 
      MatchCounter counter = new MatchCounter(
                   new File(directory), keyword);           
      FutureTask<Integer> task = new FutureTask<Integer>(counter);
      Thread t = new Thread(task);
      t.start();         
      try
      {
         System.out.println(task.get() + " fichiers concordants.");
      }
      catch (ExecutionException e)
      {
         e.printStackTrace();
      }
      catch (InterruptedException e) {}
   }
}
 
/**
   Cette t�che compte les fichiers d'un r�pertoire et de ses 
   sous-r�pertoires qui contiennent un mot cl� donn�.
*/
class MatchCounter implements Callable<Integer>
{
   /**
      Construit un MatchCounter.
      @param directory le r�pertoire dans lequel commencer la recherche
      @param keyword le mot cl� � rechercher
   */
   public MatchCounter(File directory, String keyword)
   {
      this.directory = directory;      
      this.keyword = keyword;
   }
 
   public Integer call()
   {
      count = 0;
      try
      {
         File[] files = directory.listFiles();
         ArrayList<Future<Integer>> results = 
                      new ArrayList<Future<Integer>>();
 
         for (File file : files)      
            if (file.isDirectory()) 
            {               
               MatchCounter counter = new MatchCounter(file, keyword);           
               FutureTask<Integer> task = 
                          new FutureTask<Integer>(counter);
               results.add(task);
               Thread t = new Thread(task);
               t.start();         
            }
            else 
            {
               if (search(file)) count++;
            }
 
         for (Future<Integer> result : results)
            try
            {
               count += result.get();
            }
            catch (ExecutionException e)
            {
               e.printStackTrace();
            }
      }
      catch (InterruptedException e) {}
      return count;
   }
 
   /**
      Recherche un mot cl� donn� dans un fichier.
      @param file Le fichier � parcourir
      @return true si le mot cl� figure dans le fichier
   */
   public boolean search(File file)
   {     
      try
      {
         Scanner in = new Scanner(new FileInputStream(file));
         boolean found = false;
         while (!found && in.hasNextLine())
         {
            String line = in.nextLine();
            if (line.contains(keyword)) found = true;
         }
         in.close();
         return found;
      }
      catch (IOException e)
      {
         return false;
      }
   }
 
   private File directory;
   private String keyword;
   private int count;
}
