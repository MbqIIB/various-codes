Exemple 1.6 : BlockingQueueTest.java

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
 
public class BlockingQueueTest
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      System.out.print("Entrez le r�pertoire de base 
                       (par ex. /usr/local/jdk5.0/src) : ");
      String directory = in.nextLine();
      System.out.print("Entrez le mot cl� (par ex. volatil) : ");
      String keyword = in.nextLine();     
 
      final int FILE_QUEUE_SIZE = 10;
      final int SEARCH_THREADS = 100;
 
      BlockingQueue<File> queue = new 
                 ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);
 
      FileEnumerationTask enumerator = new FileEnumerationTask(queue, 
                 new File(directory));
      new Thread(enumerator).start();
      for (int i = 1; i <= SEARCH_THREADS; i++)
         new Thread(new SearchTask(queue, keyword)).start();
   }
}
 
/**
   Cette t�che recense tous les fichiers d'un r�pertoire et de 
   ses sous-r�pertoires.
*/
class FileEnumerationTask implements Runnable
{
   /**
      Construit un FileEnumerationTask.
      @param queue la file d'attente de blocage � laquelle sont ajout�s
                   les fichiers recens�s
      @param startingDirectory le r�pertoire dans lequel commencer 
                   le recensement
   */
   public FileEnumerationTask(BlockingQueue<File> queue, 
                   File startingDirectory)
   {
      this.queue = queue;
      this.startingDirectory = startingDirectory;      
   }
 
   public void run()
   {
      try
      {
         enumerate(startingDirectory);
         queue.put(DUMMY); 
      }
      catch (InterruptedException e) {}
   }
 
   /**
      Recense tous les fichiers d'un r�pertoire donn� et de 
      ses sous-r�pertoires 
      @param directory le r�pertoire o� commencer
   */
   public void enumerate(File directory) throws InterruptedException
   {     
      File[] files = directory.listFiles();
      for (File file : files)      {
         if (file.isDirectory()) enumerate(file);
         else queue.put(file); 
      }
   }
 
   public static File DUMMY = new File("");
 
   private BlockingQueue<File> queue;
   private File startingDirectory;
}
 
/**
   Cette t�che recherche les fichiers pour un mot cl� donn�.
*/
class SearchTask implements Runnable
{
   /**
      Construit un SearchTask.
      @param queue la file � partir de laquelle prendre les fichiers
      @param keyword le mot cl� � rechercher
   */
   public SearchTask(BlockingQueue<File> queue, String keyword)
   {
      this.queue = queue;
      this.keyword = keyword;
   }
 
   public void run()
   {
      try
      {
         boolean done = false;
         while (!done)
         {
            File file = queue.take();
            if (file == FileEnumerationTask.DUMMY) { queue.put(file); 
                            done = true; }
            else search(file);
         }
      }
      catch (IOException e) { e.printStackTrace(); }
      catch (InterruptedException e) {}
   }
 
   /**
      Recherche un fichier pour un mot cl� donn� et affiche 
      toutes les lignes correspondantes.
      @param file le fichier � rechercher
   */
   public void search(File file) throws IOException
   {     
      Scanner in = new Scanner(new FileInputStream(file));
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         lineNumber++;         
         String line = in.nextLine();
         if (line.contains(keyword))
            System.out.printf("%s:%d:%s%n", file.getPath(), 
                         lineNumber, line);
      }
      in.close();
   }
 
   private BlockingQueue<File> queue;
   private String keyword;
}
