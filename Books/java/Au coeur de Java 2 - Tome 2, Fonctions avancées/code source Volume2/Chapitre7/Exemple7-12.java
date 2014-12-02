Exemple 7.12 : SimplePrincipal.java

import java.security.*;

/**
   Un principal avec une valeur nomm�e (comme "role=HR" ou
   "username=harry").
*/
public class SimplePrincipal implements Principal
{
   /**
      Construit un SimplePrincipal pour contenir une description 
      et une valeur.
      @param roleName Le nom du r�le
   */
   public SimplePrincipal(String descr, String value) { 
      this.descr = descr; this.value = value; }

   /**
      Renvoie le nom du r�le de ce principal
      @return Le nom du r�le
   */
   public String getName() { return descr + "=" + value; }

   public boolean equals(Object otherObject) 
   {
      if (this == otherObject) return true;
      if (otherObject == null) return false;
      if (getClass() != otherObject.getClass()) return false;
      SimplePrincipal other = (SimplePrincipal) otherObject;
      return getName().equals(other.getName());
   }
   public int hashCode() { return getName().hashCode(); }

   private String descr;
   private String value;
}
