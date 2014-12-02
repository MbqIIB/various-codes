Exemple 1.8 : ThreadPoolTest.java

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
 
public class ThreadPoolTest
{
   public static void main(String[] args) throws Exception
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Entrez le r�pertoire de base 
                        par ex. /usr/local/jdk5.0/src): ");
      String directory = in.nextLine();
      System.out.print("Entrez le mot cl� (par ex. volatile) : ");
      String keyword = in.nextLine();     
 
      ExecutorService pool = Executors.newCachedThreadPool();
 
      MatchCounter counter = new MatchCounter(
                      new File(directory), keyword, pool);           
      Future<Integer> result = pool.submit(counter);
 
      try
      {
         System.out.println(result.get() + " fichiers concordants.");
      }
      catch (ExecutionException e)
      {
         e.printStackTrace();
      }
      catch (InterruptedException e) {}
      pool.shutdown();
 
      int largestPoolSize = ((ThreadPoolExecutor) 
                      pool).getLargestPoolSize();
      System.out.println("taille de pool la plus grande =" + 
                      largestPoolSize);
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
      @param directory Le r�pertoire dans lequel commencer la recherche
      @param keyword Le mot cl� � rechercher
      @param pool Le pool de threads pour envoyer les sous-t�ches
   */
   public MatchCounter(File directory, String keyword, 
                     ExecutorService pool)
   {
      this.directory = directory;      
      this.keyword = keyword;
      this.pool = pool;
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
               MatchCounter counter = new MatchCounter(
                     file, keyword, pool);           
               Future<Integer> result = pool.submit(counter);
               results.add(result);
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
   private ExecutorService pool;
   private int count;
}
