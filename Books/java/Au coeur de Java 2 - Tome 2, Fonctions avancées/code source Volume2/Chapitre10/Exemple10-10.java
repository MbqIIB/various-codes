Exemple 10.10 : TransformTest.java

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
   Ce programme montre les transformations XSL. Il applique 
   une transformation � un ensemble d'enregistrements d'employ�s. Les
   enregistrements sont stock�s dans le fichier employee.dat et 
   transform�s au format XML. Sp�cifiez la feuille de style sur la ligne 
   de commande, par ex. java TransformTest makeprop.xsl
*/
public class TransformTest
{
   public static void main(String[] args) throws Exception
   {
      String filename;
      if (args.length > 0) filename = args[0];
      else filename = "makehtml.xsl";
      File styleSheet = new File(filename);
      StreamSource styleSource = new StreamSource(styleSheet); 

      Transformer t = TransformerFactory
         .newInstance().newTransformer(styleSource);
      t.transform(new SAXSource(new EmployeeReader(), 
         new InputSource(new FileInputStream("employee.dat"))), 
         new StreamResult(System.out));      
   }
}

/**
   Cette classe lit le fichier uniforme employee.dat et signale les 
   �v�nements de l'analyseur SAX pour agir � la mani�re d'une analyse 
   d'un fichier XML.
*/
class EmployeeReader implements XMLReader
{
   public void parse(InputSource source) 
      throws IOException, SAXException
   {
      InputStream stream = source.getByteStream();
      BufferedReader in = new BufferedReader(
         new InputStreamReader(stream));
      String rootElement = "staff";
      AttributesImpl atts = new AttributesImpl();

      if (handler == null) 
         throw new SAXException("Pas de gestionnaire de contenu");
        
      handler.startDocument();      
      handler.startElement("", rootElement, rootElement, atts);            
      String line;
      while ((line = in.readLine()) != null) 
      {
         handler.startElement("", "employee", "employee", atts);      
         StringTokenizer t = new StringTokenizer(line, "|");

         handler.startElement("", "name", "name", atts);      
         String s = t.nextToken();
         handler.characters(s.toCharArray(), 0, s.length());
         handler.endElement("", "name", "name");      

         handler.startElement("", "salary", "salary", atts);
         s = t.nextToken();
         handler.characters(s.toCharArray(), 0, s.length());
         handler.endElement("", "salary", "salary");

         atts.addAttribute("", "year", "year", "CDATA", 
            t.nextToken());
         atts.addAttribute("", "month", "month", "CDATA", 
            t.nextToken());
         atts.addAttribute("", "day", "day", "CDATA", 
            t.nextToken());
         handler.startElement("", "hiredate", "hiredate", atts);
         handler.endElement("", "hiredate", "hiredate");
         atts.clear();

         handler.endElement("", "employee", "employee");
      }

      handler.endElement("", rootElement, rootElement);
      handler.endDocument();      
   }

   public void setContentHandler(ContentHandler newValue) 
      {handler = newValue;} 

   public ContentHandler getContentHandler() 
      { return handler; } 

   // les m�thodes suivantes sont simplement des impl�mentations 
   // inactives
   public void parse(String systemId)
      throws IOException, SAXException {}
   public void setErrorHandler(ErrorHandler handler) {}
   public ErrorHandler getErrorHandler() { return null; }
   public void setDTDHandler(DTDHandler handler) {}
   public DTDHandler getDTDHandler() { return null; }
   public void setEntityResolver(EntityResolver resolver) {}
   public EntityResolver getEntityResolver() { return null; }
   public void setProperty(String name, Object value) {} 
   public Object getProperty(String name) { return null; }
   public void setFeature(String name, boolean value) {}
   public boolean getFeature(String name) { return false; }  

   private ContentHandler handler;
}
