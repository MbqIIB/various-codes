Exemple 11.7 : Item.java

public class Item
{ 
   /**
      Construit un �l�ment.
      @param aDescription La description de l'�l�ment
      @param aPartNumber Le num�ro de pi�ce de l'�l�ment
   */
   public Item(String aDescription, int aPartNumber)
   {  
      description = aDescription;
      partNumber = aPartNumber;
   }

   /**
      R�cup�re la description de cet �l�ment.
      @return La description
   */
   public String getDescription()
   {  
      return description;
   }

   public String toString()
   {  
      return "[description=" + description
         + ", partNumber=" + partNumber + "]";
   }

   @LogEntry(logger="global") public boolean equals(Object otherObject)
   {  
      if (this == otherObject) return true;
      if (otherObject == null) return false;
      if (getClass() != otherObject.getClass()) return false;
      Item other = (Item) otherObject;
      return description.equals(other.description)
         && partNumber == other.partNumber;
   }

   @LogEntry(logger="global") public int hashCode()
   {  
      return 13 * description.hashCode() + 17 * partNumber;
   }

   private String description;
   private int partNumber;
}
