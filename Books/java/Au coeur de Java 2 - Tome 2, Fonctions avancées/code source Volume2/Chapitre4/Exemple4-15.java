Exemple 4.15 : ProductActivator.java

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.activation.*;
import java.util.*;
import javax.naming.*;

/**
   Ce programme serveur active deux objets distants et
   les enregistre aupr�s du service de noms.
*/
public class ProductActivator
{  
   public static void main(String args[])
   {  
      try
      {  
         System.out.println
            ("Construction des descripteurs d'activation...");

         Properties props = new Properties();
         // utilise le fichier server.policy du r�pertoire courant
         props.put("java.security.policy", 
            new File("server.policy").getCanonicalPath());
        ActivationGroupDesc group = new ActivationGroupDesc(props, null);
         ActivationGroupID id 
            = ActivationGroup.getSystem().registerGroup(group);
         MarshalledObject p1param 
            = new MarshalledObject("Grille-pain Moulinex");
         MarshalledObject p2param 
            = new MarshalledObject("Micro-ondes Philips");

         String classDir = ".";
         // transforme le r�pertoire de classe en URL de fichier
         // pour cette d�mo, nous supposons que les classes sont dans le 
         // r�p. Courant
         // Nous utilisons toURI pour que les espaces et autres 
         // caract�res sp�ciaux des noms de fichiers soient ignor�s
         String classURL 
            = new File(classDir).getCanonicalFile().toURI().toString();

         ActivationDesc desc1 = new ActivationDesc
                      (id, "ProductImpl", classURL, p1param);
         ActivationDesc desc2 = new ActivationDesc
                      (id, "ProductImpl", classURL, p2param);
         
         Product p1 = (Product) Activatable.register(desc1);
         Product p2 = (Product) Activatable.register(desc2);

         System.out.println
            ("Liaison des impl�mentations activables � la base de registre...");
         Context namingContext = new InitialContext();
         namingContext.bind("rmi:grille-pain", p1);
         namingContext.bind("rmi:micro-ondes", p2);

         System.out.println ("Fermeture...");
      }
      catch(Exception e)
      {  
         e.printStackTrace();
      }
   }
}
