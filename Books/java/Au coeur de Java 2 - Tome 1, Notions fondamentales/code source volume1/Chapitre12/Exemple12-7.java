Exemple 12.7 : FindDirectories.java

import java.io.*;

public class FindDirectories
{  
   public static void main(String[] args)
   {
      // si aucun argument n'est fourni, commencer au r�pertoire parent
      if (args.length == 0) args = new String[] { ".." };

      try
      {  
         File pathName = new File(args[0]);
         String[] fileNames = pathName.list();

         // �num�rer tous les fichiers du r�pertoire
         for (int i = 0; i < fileNames.length; i++)
         {  
            File f = new File(pathName.getPath(), 
               fileNames[i]);

            // si le fichier est � nouveau un r�pertoire, appeler 
            // la m�thode main de mani�re r�currente
            if (f.isDirectory())
            {  
               System.out.println(f.getCanonicalPath());
               main(new String [] { f.getPath() });
            }
         }
      }
      catch(IOException e)
      {  
         e.printStackTrace(); 
      }
   }
}
