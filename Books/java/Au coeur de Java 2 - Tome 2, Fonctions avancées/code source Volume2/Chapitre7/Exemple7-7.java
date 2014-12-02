Exemple 7.7 : WordCheckSecurityManager.java

import java.io.*;
import java.security.*;
import java.util.*;

/**
   Ce gestionnaire de s�curit� v�rifie si les mots interdits
   sont rencontr�s � la lecture d'un fichier.
*/
public class WordCheckSecurityManager extends SecurityManager
{
   public void checkPermission(Permission p)
   {
      if (p instanceof FilePermission
         && p.getActions().equals("read"))
      {
         if (inSameManager())
            return;
         String fileName = p.getName();
         if (containsBadWords(fileName))
            throw new SecurityException("Mots interdits dans " 
               + fileName);
      }
      else super.checkPermission(p);
   }

   /**
      Renvoie true si ce gestionnaire est appel� alors qu'un autre appel 
      est en attente vers lui-m�me.
      @return true s'il y a plusieurs appels � ce gestionnaire
   */
   public boolean inSameManager()
   {
      Class[] cc = getClassContext();
   
      // sauter le jeu courant d'appel � ce gestionnaire 
      int i = 0;
      while (i < cc.length && cc[0] == cc[i]) 
         i++;
         
      // v�rifier si d'autres appels � ce gestionnaire 
      while (i < cc.length)
      {
         if (cc[0] == cc[i]) return true;
         i++;
      }
      return false;
   }

   /**
      V�rifie si un fichier contient des mots interdits.
      @param fileName le nom du fichier
      @return true si le nom de fichier se termine par .txt et 
      qu'il contient au moins un mot interdit.
   */
   boolean containsBadWords(String fileName)
   {
      if (!fileName.toLowerCase().endsWith(".txt")) return false;
         // ne v�rifier que les fichiers texte
      Scanner in;
      try
      {
         in = new Scanner(new FileReader(fileName));
      }
      catch (IOException e)
      {
         return false;
      }
      while (in.hasNext())
         if (badWords.contains(in.next().toLowerCase()))
         {
             in.close();
             System.out.println(fileName);
             return true;
         }
      return false;
   }

   private List badWords = Arrays.asList(new String[] {
      "sexe", "drogues", "c++" });
}
