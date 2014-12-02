Exemple 9.15 : GBC.java

/*
GBC � Une classe pour ma�triser le GridBagLayout

Copyright (C) 2002 Cay S. Horstmann (http://horstmann.com)

Ce programme est un logiciel libre. Vous pouvez le distribuer et/ou
le modifier aux termes des conditions du "GNU General Public License"
publi� par Free Software Foundation (version 2 de la Licence ou
(� votre choix) toute version ult�rieure).

Ce programme est distribu� dans l'espoir qu'il sera utile,
mais SANS AUCUNE GARANTIE, ni m�me la garantie implicite de
QUALITE MARCHANDE ou D'ADAPTATION A UN OBJECTIF PARTICULIER.
Voir le GNU General Public License pour en savoir plus.

Vous devriez avoir re�u une copie du GNU General Public License
avec ce programme ; dans le cas contraire, �crivez � Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
*/

import java.awt.*;

/**
   Cette classe simplifie l'utilisation de la classe GridBagConstraints.
*/
public class GBC extends GridBagConstraints 
{
   /**
      Construit un GBC avec une position gridx et gridy donn�e
      et toutes les autres valeurs de GridBagConstraints d�finies
      sur le param�tre par d�faut.
      @param gridx La position gridx
      @param gridy La position gridy
   */
   public GBC(int gridx, int gridy)
   {
      this.gridx = gridx;
      this.gridy = gridy;
   }

   /**
      Construit un GBC avec gridx, gridy, gridwidth, gridheight
      et toutes les autres valeurs de GridBagConstraints d�finies sur la 
      valeur par d�faut.
      @param gridx La position gridx 
      @param gridy La position gridy 
      @param gridwidth L'�tirement de cellule dans la direction x
      @param gridheight L'�tirement de cellule dans la direction y
   */
   public GBC(int gridx, int gridy, int gridwidth, int gridheight)
   {
      this.gridx = gridx;
      this.gridy = gridy;
      this.gridwidth = gridwidth; 
      this.gridheight = gridheight; 
   }

   /**
      D�finit l'ancrage.
      @param anchor La valeur de l'ancrage
      @return this Objet pour une future modification
   */
   public GBC setAnchor(int anchor) 
   { 
      this.anchor = anchor; 
      return this;
   }

   /**
      D�finit la direction de fill.
      @param fill La direction de fill
      @return this Objet pour une future modification
   */
   public GBC setFill(int fill) 
   { 
      this.fill = fill; 
      return this;
   }

   /**
      D�finit les poids de cellule.
      @param weightx Le poids de cellule dans la direction x
      @param weighty Le poids de cellule dans la direction y
      @return this Objet pour une future modification
   */
   public GBC setWeight(double weightx, double weighty) 
   { 
      this.weightx = weightx; 
      this.weighty = weighty; 
      return this;
   }

   /**
      D�finit les insets de cette cellule.
      @param distance L'espacement � utiliser dans toutes les directions
      @return this Objet pour une future modification
   */
   public GBC setInsets(int distance) 
   { 
      this.insets = new Insets(distance, distance, distance, distance);
      return this;
   }

   /**
      D�finit les insets de cette cellule.
      @param top L'espacement � utiliser en haut
      @param left L'espacement � utiliser � gauche
      @param bottom L'espacement � utiliser en bas
      @param right L'espacement � utiliser � droite
      @return this Objet pour une future modification
   */
   public GBC setInsets(int top, int left, int bottom, int right) 
   { 
      this.insets = new Insets(top, left, bottom, right);
      return this;
   }

   /**
      D�finit le remplissage interne
      @param ipadx Le remplissage interne dans la direction x
      @param ipady Le remplissage interne dans la direction y
      @return this Objet pour une future modification
   */
   public GBC setIpad(int ipadx, int ipady) 
   { 
      this.ipadx = ipadx; 
      this.ipady = ipady; 
      return this;
   }
}
