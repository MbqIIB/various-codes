Exemple 11.6 : EventTracer.java

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;

public class EventTracer
{
   public EventTracer()
   {
      // le gestionnaire pour tous les proxies d'�v�nement
      handler = new
         InvocationHandler()
         {
            public Object invoke(Object proxy,
               Method method, Object[] args)
            {
               System.out.println(method + ":" + args[0]);
               return null;
            }
         };
   }

   /**
      Ajoute des traceurs d'�v�nements pour tous les �v�nements 
      que ce composant et ses enfants peuvent �couter
      @param c Un composant
   */
   public void add(Component c)
   {
      try
      {
         // r�cup�rer tous les �v�nements que ce composant peut �couter
         BeanInfo info = Introspector.getBeanInfo(c.getClass());

         EventSetDescriptor[] eventSets
            = info.getEventSetDescriptors();
         for (EventSetDescriptor eventSet : eventSets)
            addListener(c, eventSet);
      }
      catch (IntrospectionException e) {}
      // ok pour ne pas ajouter d'�couteurs si l'exception est lanc�e

      if (c instanceof Container)
      {
         // r�cup�rer tous les enfants et appeler add 
         // de mani�re r�currente
         for (Component comp : ((Container) c).getComponents())
            add(comp);
      }
   }

   /**
      Ajouter un �couteur au jeu d'�v�nements donn�
      @param c Un composant
      @param eventSet Un descripteur d'une interface d'�couteur
   */
   public void addListener(Component c, EventSetDescriptor eventSet)
   {
      // cr�er l'objet proxy pour ce type d'�couteur 
      // et acheminer tous les appels au gestionnaire
      Object proxy = Proxy.newProxyInstance(null,
         new Class[] { eventSet.getListenerType() }, handler);

      // ajouter le proxy au composant sous forme d'�couteur 
      Method addListenerMethod = eventSet.getAddListenerMethod();
      try
      {
         addListenerMethod.invoke(c, proxy);
      }
      catch(InvocationTargetException e) {}
      catch(IllegalAccessException e) {}
      // ok pour ne pas ajouter d'�couteur si l'exception est lanc�e
   }

   private InvocationHandler handler;
}
