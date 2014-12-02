Exemple 9.21 : Win32RegKey.java

import java.util.*;

/**
   Un objet Win32RegKey peut �tre utilis� pour extraire et d�finir les 
   valeurs d'une cl� de registre dans la base de registre Windows.
*/
public class Win32RegKey
{
   /**
      Construit un objet de cl� de registre.
      @param theRoot, parmi HKEY_CLASSES_ROOT, HKEY_CURRENT_USER,
      HKEY_LOCAL_MACHINE, HKEY_USERS, HKEY_CURRENT_CONFIG,
      HKEY_DYN_DATA
      @param thePath le chemin de la cl� de registre
   */
   public Win32RegKey(int theRoot, String thePath) 
   {
      root = theRoot; 
      path = thePath; 
   }

   /**
      Enum�re tous les noms des entr�es de registre sous le chemin d�crit 
      par cet objet.
      @return une �num�ration recensant tous les noms d'entr�e
   */
   public Enumeration<String> names() 
   {
      return new Win32RegKeyNameEnumeration(root, path); 
   }

   /**
      Extrait la valeur d'une entr�e de registre.
      @param name le nom de l'entr�e
      @return la valeur associ�e
   */
   public native Object getValue(String name);

   /**
      D�finit la valeur d'une entr�e de registre.
      @param name le nom de l'entr�e
      @param value la nouvelle valeur
   */
   public native void setValue(String name, Object value);

   public static final int HKEY_CLASSES_ROOT = 0x80000000;
   public static final int HKEY_CURRENT_USER = 0x80000001;
   public static final int HKEY_LOCAL_MACHINE = 0x80000002;
   public static final int HKEY_USERS = 0x80000003;
   public static final int HKEY_CURRENT_CONFIG = 0x80000005;
   public static final int HKEY_DYN_DATA = 0x80000006;

   private int root;
   private String path;

   static
   {
      System.loadLibrary("Win32RegKey");
   }
}

class Win32RegKeyNameEnumeration implements Enumeration<String>
{
   Win32RegKeyNameEnumeration(int theRoot, String thePath) 
   {
      root = theRoot; 
      path = thePath; 
   }

   public native String nextElement();
   public native boolean hasMoreElements();

   private int root;
   private String path;
   private int index = -1;
   private int hkey = 0;
   private int maxsize;
   private int count;
}

class Win32RegKeyException extends RuntimeException
{
   public Win32RegKeyException() {}
   public Win32RegKeyException(String why) 
   {
      super(why);
   }
}
