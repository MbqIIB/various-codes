Exemple 6.16 : DamageReport.java

import java.awt.*;
import java.awt.geom.*;
import java.beans.*;
import java.util.*;
 
/**
   Cette classe d�crit un rapport de dommages � un v�hicule qui sera 
   sauvegard� et charg� avec un m�canisme de persistance � long terme.
*/
public class DamageReport
{
   public enum CarType { SEDAN, WAGON, SUV }
 
   // Cette propri�t� est sauvegard�e automatiquement
   public void setRentalRecord(String newValue)
   {
      rentalRecord = newValue;
   }
 
   public String getRentalRecord()
   {
      return rentalRecord;
   }
 
   // Cette propri�t� est sauvegard�e automatiquement
   public void setCarType(CarType newValue)
   {
      carType = newValue;
   }
 
   public CarType getCarType()
   {
      return carType;
   }
 
   // Cette propri�t� est d�finie comme transitoire
   public void setRemoveMode(boolean newValue)
   {
      removeMode = newValue;
   }
 
   public boolean getRemoveMode()
   {
      return removeMode;
   }
 
   public void click(Point2D p)
   {
      if (removeMode) 
      {
         for (Point2D center : points)
         {
            Ellipse2D circle = new Ellipse2D.Double(
               center.getX() - MARK_SIZE, center.getY() - MARK_SIZE, 
               2 * MARK_SIZE, 2 * MARK_SIZE);
            if (circle.contains(p))
            {
               points.remove(center);
               return;
            }
         }
      }
      else points.add(p);
   }
 
   public void drawDamage(Graphics2D g2)
   {
      g2.setPaint(Color.RED);
      for (Point2D center : points)
      {
         Ellipse2D circle = new Ellipse2D.Double(
            center.getX() - MARK_SIZE, center.getY() - MARK_SIZE, 
            2 * MARK_SIZE, 2 * MARK_SIZE);
         g2.draw(circle);
      }
   }   
 
   public void configureEncoder(XMLEncoder encoder)
   {
      // Cette �tape est n�cessaire pour sauvegarder les objets 
      // Point2D.Double
      encoder.setPersistenceDelegate(
         Point2D.Double.class,
         new DefaultPersistenceDelegate(new String[]{ "x", "y" }) );
 
      // Cette �tape est n�cessaire pour sauvegarder le type �num�r� 
      // CarType
      encoder.setPersistenceDelegate(CarType.class, new EnumDelegate());
 
      // Cette �tape est n�cessaire car la liste des points sous forme de 
      // tableau n'est pas (et ne doit pas �tre) expos�e 
      // comme une propri�t�
      encoder.setPersistenceDelegate(
         DamageReport.class, new
            DefaultPersistenceDelegate()
            {
               protected void initialize(Class type, 
                     Object oldInstance, Object newInstance, 
                  Encoder out) 
               {
                  super.initialize(type, oldInstance, newInstance, out);
                  DamageReport r = (DamageReport) oldInstance;
 
                  for (Point2D p : r. points)
                     out.writeStatement(
                        new Statement(oldInstance,"click", 
                        new Object[]{ p }) );
               }
            });      
 
   }
 
   // Cette �tape est n�cessaire pour rendre transitoire la propri�t� 
   // removeMode
   static 
   {
      try 
      {
         BeanInfo info = Introspector.getBeanInfo(DamageReport.class);         
         for (PropertyDescriptor desc : info.getPropertyDescriptors()) 
            if (desc.getName().equals("removeMode"))
               desc.setValue("transient", Boolean.TRUE);
      } 
      catch (IntrospectionException e) 
      { 
         e.printStackTrace(); 
      }
   }
 
   private String rentalRecord;
   private CarType carType;
   private boolean removeMode;
   private ArrayList<Point2D> points = new ArrayList<Point2D>();
 
   private static final int MARK_SIZE = 5;
}
