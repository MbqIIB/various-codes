Exemple 6.5 : TitlePositionEditor.java

package com.horstmann.corejava;

import java.beans.*;

/**
   Un �diteur personnalis� pour la propri�t� titlePosition du 
   ChartBean. L'�diteur permet � l'utilisateur de choisir entre
   Left, Center et Right
*/
public class TitlePositionEditor
   extends PropertyEditorSupport
{
   public String[] getTags() { return options; }
   private String[] options = { "Gauche", "Centr�", "Droite" };
   public String getJavaInitializationString() { return "" + getValue(); 

   public String getAsText()
   {
      int value = (Integer) getValue();
      return options[value];
   }

   public void setAsText(String s)
   {
      for (int i = 0; i < options.length; i++)
      {
         if (options[i].equals(s))
         {
            setValue(i);
            return;
         }
      }
   }
}

