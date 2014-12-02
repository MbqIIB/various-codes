Exemple 3.1 : TestDB.java

import java.sql.*;
import java.io.*;
import java.util.*;

/**
   Ce programme teste si la base de donn�es et le pilote 
   JDBC sont correctement configur�s.
*/
class TestDB
{  
   public static void main (String args[])
   {  
      try
      {  
         runTest();
      }
      catch (SQLException ex)
      {  
         while (ex != null)
         {  
            ex.printStackTrace();
            ex = ex.getNextException();
         }
      }
      catch (IOException ex)
      {  
         ex.printStackTrace();
      }
   }

   /**
      Ex�cute un test en cr�ant un tableau, en ajoutant une valeur, en 
      affichant le contenu du tableau, puis en supprimant le tableau.
   */
   public static void runTest()
      throws SQLException, IOException
   {
      Connection conn = getConnection();
      try
      {
         Statement stat = conn.createStatement();

         stat.execute("CREATE TABLE Salutations (Message CHAR(20))");
         stat.execute("INSERT INTO Salutations VALUES ('Bonjour')");

         ResultSet result = stat.executeQuery("
                      SELECT * FROM Salutations");
         result.next();
         System.out.println(result.getString(1));
         stat.execute("DROP TABLE Salutations");      
      }
      finally
      {
         conn.close();
      }
   }

   /**
      Obtient une connexion � partir des propri�t�s sp�cifi�es
      dans le fichier database.properties
      @return la connexion � la base de donn�es
   */
   public static Connection getConnection()
      throws SQLException, IOException
   {  
      Properties props = new Properties();
      FileInputStream in = new FileInputStream("database.properties");
      props.load(in);
      in.close();

      String drivers = props.getProperty("jdbc.drivers");
      if (drivers != null)
         System.setProperty("jdbc.drivers", drivers);
      String url = props.getProperty("jdbc.url");
      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");

      return DriverManager.getConnection(url, username, password);
   }
