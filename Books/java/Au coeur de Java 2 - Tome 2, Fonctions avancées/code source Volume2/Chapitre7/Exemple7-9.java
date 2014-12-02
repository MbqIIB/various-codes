Exemple 7.9 : SysPropAction.java

import java.security.*;

/**
   Cette action recherche une propri�t� syst�me.
*/
public class SysPropAction implements PrivilegeAction
{
   /**
      Construit une action pour rechercher une propri�t� donn�e.
      @param propertyName Le nom de la propri�t� (comme "user.home")
   */
   public SysPropAction(String propertyName) 
      { this.propertyName = propertyName; }

   public Object run()
   {
      return System.getProperty(propertyName);
   }

   private String propertyName;
}
