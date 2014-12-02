Exemple 11.3 : ActionListenerInstaller.java

import java.awt.event.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
 
public class ActionListenerInstaller
{
   /**
      Traite toutes les annotations ActionListenerFor 
      dans l'objet donn�.
      @param obj Un objet dont les m�thodes peuvent avoir des 
      annotations ActionListenerFor 
   */
   public static void processAnnotations(Object obj)
   {
      try
      {
         Class cl = obj.getClass();
         for (Method m : cl.getDeclaredMethods())
         {
            ActionListenerFor a = m.getAnnotation(
               ActionListenerFor.class);
            if (a != null) 
            {
               Field f = cl.getDeclaredField(a.source());
               f.setAccessible(true);
               addListener(f.get(obj), obj, m);
            }
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
 
   /**
      Ajoute un �couteur d'action qui appelle une m�thode donn�e.
      @param source La source de l'�v�nement auquel un �couteur
      d'action est ajout�
      @param param Le param�tre implicite de la m�thode que 
      l'�couteur appelle
      @param m La m�thode appel�e par l'�couteur
   */
   public static void addListener(Object source, 
      final Object param, final Method m)
      throws NoSuchMethodException, IllegalAccessException, 
      InvocationTargetException
   {
      InvocationHandler handler = new
         InvocationHandler()
         {
            public Object invoke(Object proxy, Method mm, Object[] args) 
            throws Throwable
            {
               return m.invoke(param);                    
            }
         };
 
      Object listener = Proxy.newProxyInstance(null,
         new Class[] { java.awt.event.ActionListener.class },
         handler);
      Method adder = source.getClass().getMethod("addActionListener", 
         ActionListener.class);
      adder.invoke(source, listener);
   }
}
