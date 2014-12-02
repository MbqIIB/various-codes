Exemple 6.7 : ProxyTest.java

import java.lang.reflect.*;
import java.util.*;

public class ProxyTest
{  
   public static void main(String[] args)
   {  
      Object[] elements = new Object[1000];

      // remplir les �l�ments avec des proxies de 1 � 1000
      for (int i = 0; i < elements.length; i++)
      {
        Integer value = i + 1;
        Class[] interfaces = value.getClass().getInterfaces();
        InvocationHandler handler = new TraceHandler(value);
        Object proxy = Proxy.newProxyInstance(null,
           interfaces, handler);
        elements[i] = proxy;
      }

      // construit un entier al�atoire
      Integer key = new Random().nextInt(elements.length) + 1;

      // recherche la cl�
      int result = Arrays.binarySearch(elements, key);

      // affiche la correspondance le cas �ch�ant
      if (result >= 0) System.out.println(elements[result]);
   }
}

/**
   Un gestionnaire d'invocation qui affiche le nom et les param�tres
   de la m�thode, puis appelle la m�thode initiale
*/
class TraceHandler implements InvocationHandler
{ 
   /**
      Construit un TraceHandler
      @param t le param�tre implicite de l'appel de m�thode
   */
   public TraceHandler(Object t)
   {  
      target = t;
   }

   public Object invoke(Object proxy, Method m, Object[] args) 
      throws Throwable
   {  
      // affiche l'argument implicite
      System.out.print(target);
      // affiche le nom de la m�thode
      System.out.print("." + m.getName() + "(");
      // affiche les arguments explicites
      if (args != null)
      {
         for (int i = 0; i < args.length; i++)
         {  
            System.out.print(args[i]);
            if (i < args.length - 1)
               System.out.print(", ");
         }
      }
      System.out.println(")");

      // appelle la m�thode r�elle
      return m.invoke(target, args);
   }

   private Object target;
}
