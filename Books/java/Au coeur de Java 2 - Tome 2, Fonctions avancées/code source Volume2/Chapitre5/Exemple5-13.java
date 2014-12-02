Exemple 5.13 : TableCellRenderTest.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
   Ce programme montre l'affichage et la modification de cellules
   dans un tableau.
*/
public class TableCellRenderTest
{
   public static void main(String[] args)
   {
      JFrame frame = new TableCellRenderFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);      
   }
}

/**
   Ce bloc contient un tableau des donn�es des plan�tes.
*/
class TableCellRenderFrame extends JFrame
{  
   public TableCellRenderFrame()
   {  
      setTitle("TableCellRenderTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      TableModel model = new PlanetTableModel();
      JTable table = new JTable(model);
      table.setRowSelectionAllowed(false);

      // configure les afficheurs et les �diteurs

      table.setDefaultRenderer(Color.class,
         new ColorTableCellRenderer());
      table.setDefaultEditor(Color.class,
         new ColorTableCellEditor());

      JComboBox moonCombo = new JComboBox();
      for (int i = 0; i <= 20; i++)
         moonCombo.addItem(i);
 
      TableColumnModel columnModel = table.getColumnModel();
      TableColumn moonColumn
         = columnModel.getColumn(PlanetTableModel.MOONS_COLUMN);
      moonColumn.setCellEditor(new DefaultCellEditor(moonCombo));
      moonColumn.setHeaderRenderer(
         table.getDefaultRenderer(ImageIcon.class));
      moonColumn.setHeaderValue(new ImageIcon("Moons.gif"));

      // affiche le tableau

      table.setRowHeight(100);
      add(new JScrollPane(table), BorderLayout.CENTER);
   }

   private static final int DEFAULT_WIDTH = 600;
   private static final int DEFAULT_HEIGHT = 400;
}

/** 
   Le mod�le du tableau des plan�tes sp�cifie les valeurs et les    
   propri�t�s d'affichage et de modification pour les donn�es des 
   plan�tes.
*/
class PlanetTableModel extends AbstractTableModel
{
   public String getColumnName(int c)
      { return columnNames[c]; }
   public Class getColumnClass(int c)
      { return cells[0][c].getClass(); }
   public int getColumnCount()
      { return cells[0].length; }
   public int getRowCount()
      { return cells.length; }
   public Object getValueAt(int r, int c)
      { return cells[r][c]; }
   public void setValueAt(Object obj, int r, int c)
      { cells[r][c] = obj; }
   public boolean isCellEditable(int r, int c)
   { 
      return c == PLANET_COLUMN
         || c == MOONS_COLUMN
         || c == GASEOUS_COLUMN
         || c == COLOR_COLUMN;
   }
   
   public static final int PLANET_COLUMN = 0;
   public static final int MOONS_COLUMN = 2;
   public static final int GASEOUS_COLUMN = 3;
   public static final int COLOR_COLUMN = 4;
   
   private Object[][] cells =  
      {
         { "Mercure", 2440.0, 0, false, Color.yellow, 
            new ImageIcon("Mercury.gif") },
         { "V�nus", 6052.0, 0, false, Color.yellow, 
            new ImageIcon("Venus.gif") },
         { "Terre", 6378.0, 1, false, Color.blue, 
            new ImageIcon("Earth.gif") },
         { "Mars", 3397.0, 2, false, Color.red, 
            new ImageIcon("Mars.gif") },
         { "Jupiter", 71492.0, 16, true, Color.orange, 
            new ImageIcon("Jupiter.gif") },
         { "Saturne", 60268.0, 18, true, Color.orange, 
            new ImageIcon("Saturn.gif") },
         { "Uranus", 25559.0, 17, true, Color.blue, 
            new ImageIcon("Uranus.gif") },
         { "Neptune", 24766.0, 8, true, Color.blue, 
            new ImageIcon("Neptune.gif") },
         { "Pluton", 1137.0, 1, false, Color.black, 
            new ImageIcon("Pluto.gif") }
      };
      
      private String[] columnNames =
         { "Plan�te", "Rayon", "Lunes", "Gazeuse", 
         "Couleur", "Image" };
}

/**
   Cet afficheur pr�sente une valeur de couleur sous forme de panneau 
   avec la couleur donn�e.
*/
class ColorTableCellRenderer extends JPanel implements TableCellRenderer
{
   public Component getTableCellRendererComponent(JTable table,
      Object value, boolean isSelected, boolean hasFocus,
      int row, int column)
   {
      setBackground((Color)value);
      if (hasFocus)
         setBorder(UIManager.getBorder(
            "Table.focusCellHighlightBorder"));
      else
         setBorder(null);
      return this;
   }
}

/**
   Cet �diteur affiche une bo�te de dialogue des couleurs pour modifier 
   la valeur d'une cellule.
*/
class ColorTableCellEditor extends ColorTableCellRenderer 
   implements TableCellEditor
{
   public ColorTableCellEditor()
   {
      panel = new JPanel();    
      // pr�pare la bo�te de dialogue de couleur 
   
      colorChooser = new JColorChooser();
      colorDialog = JColorChooser.createDialog(null,
         "Couleur de la plan�te", false, colorChooser, 
         new
           ActionListener() // �couteur du bouton OK
         {
            public void actionPerformed(ActionEvent event)
            { stopCellEditing(); }
         }, 
       new
         ActionListener() // �couteur du bouton Annuler
         {
            public void actionPerformed(ActionEvent event)
            { cancelCellEditing(); }
         });
      colorDialog.addWindowListener(new
         WindowAdapter()
         {
            public void windowClosing(WindowEvent event)
            { cancelCellEditing(); }
        });
   }
   
   public Component getTableCellEditorComponent(JTable table, 
      Object value, boolean isSelected, int row, int column)
   {
         /* c'est ici que nous r�cup�rons la valeur Couleur courante.
         Nous l'enregistrons dans la bo�te de dialogue au cas o�             
         l'utilisateur commencerait des modifications.
      */
      colorChooser.setColor((Color)value);
      return panel;
   }
   
   public boolean shouldSelectCell(EventObject anEvent)
   {
      // d�but des modifications
      colorDialog.setVisible(true);
      
      // indique � l'appelant qu'il peut s�lectionner cette cellule
      return true;
   }

   public void cancelCellEditing()
   {
      // les modifications sont annul�es, cacher la bo�te de dialogue 
      colorDialog.setVisible(false);
      super.cancelCellEditing();
   }
   
   public boolean stopCellEditing()
   {
      // les modifications sont termin�es, cacher la bo�te de dialogue
      colorDialog.setVisible(false);
      super.stopCellEditing();
      
      // indique � l'appelant qu'il peut utiliser la valeur de couleur 
      return true;
   }
   
   public Object getCellEditorValue()
   {
      return colorChooser.getColor();
   }
   
   private Color color;
   private JColorChooser colorChooser;
   private JDialog colorDialog;
   private JPanel panel;
}
