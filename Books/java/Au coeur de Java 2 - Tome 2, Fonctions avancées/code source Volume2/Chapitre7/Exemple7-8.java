Exemple 7.8 : AuthTest.java

import java.security.*;
import javax.security.auth.*;
import javax.security.auth.login.*;

/**
   Ce programme authentifie un utilisateur via un identifiant 
   personnalis� et ex�cute SysPropAction 
   avec les privil�ges de l'utilisateur. 
*/
public class AuthTest
{
   public static void main(final String[] args)
   {
      try 
      {
         System.setSecurityManager(new SecurityManager());

         LoginContext context = new LoginContext("Login1");
         context.login();
         System.out.println("Authentification r�ussie.");
         Subject subject = context.getSubject();
         System.out.println("subject=" + subject);
         PrivilegedAction action = new SysPropAction("user.home");
         Object result = Subject.doAsPrivileged(subject, action, null);
         System.out.println(result);
         context.logout();
      } 
      catch (LoginException e) 
      {
         e.printStackTrace();
      }      
   }
}
