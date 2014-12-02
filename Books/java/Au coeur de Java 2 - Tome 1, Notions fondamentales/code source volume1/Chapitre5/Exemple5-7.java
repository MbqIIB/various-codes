Exemple 5.7 : Arraygrowtest.java

import java.lang.reflect.*;
import java.util.*;

public class ArrayGrowTest
{  
   public static void main(String[] args)
   {  
      int[] a = { 1, 2, 3 };
      a = (int[]) goodArrayGrow(a);
      arrayPrint(a);
 
      String[] b = { "Tom", "Dick", "Harry" };
      b = (String[]) goodArrayGrow(b);
      arrayPrint(b);

      System.out.println
         ("The following call will generate an exception.");
      b = (String[]) badArrayGrow(b);
   }

   /**
      Cette m�thode tente d'agrandir un tableau en allouant
      un nouveau tableau et en copiant tous les �l�ments. 
      @param a Le tableau � agrandir
      @return Un tableau plus grand contenant tous les �l�ments de a.
      Toutefois, le tableau renvoy� est du type Object[], 
      et non du m�me type que a
   */
   static Object[] badArrayGrow(Object[] a)
   {  
      int newLength = a.length * 11 / 10 + 10;
      Object[] newArray = new Object[newLength];
      System.arraycopy(a, 0, newArray, 0, a.length);
      return newArray;
   }

   /**
      Cette m�thode agrandit un tableau en allouant
      un nouveau tableau et en copiant tous les �l�ments. 
      @param a Le tableau � agrandir. Peut �tre un tableau object 
      ou un tableau du type fondamental 
      @return Un tableau plus grand contenant tous les �l�ments de a.

    */
   static Object goodArrayGrow(Object a)
   {  
      Class cl = a.getClass();
      if (!cl.isArray()) return null;
      Class componentType = cl.getComponentType();
      int length = Array.getLength(a);
      int newLength = length * 11 / 10 + 10;

      Object newArray = Array.newInstance(componentType,
         newLength);
      System.arraycopy(a, 0, newArray, 0, length);
      return newArray;
   }

    /**
      Une m�thode permettant d'afficher tous les �l�ments dans un tableau
      @param a Le tableau � afficher. Peut �tre un tableau object 
      ou un tableau du type fondamental 
   */
   static void arrayPrint(Object a)
   {  
      Class cl = a.getClass();
      if (!cl.isArray()) return;
      Class componentType = cl.getComponentType();
      int length = Array.getLength(a);
      System.out.print(componentType.getName()
         + "[" + length + "] = { ");
      for (int i = 0; i < Array.getLength(a); i++)
         System.out.print(Array.get(a, i) + " ");
      System.out.println("}");
   }
}

