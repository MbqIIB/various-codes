Exemple 11.6 : EntryLogger.java

import java.lang.annotation.*;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.io.*;
import java.util.*;

import org.apache.bcel.*;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.*;
import org.apache.bcel.classfile.FieldOrMethod;
import org.apache.bcel.generic.*;
import org.apache.bcel.util.*;

/**
   Ajouter des entr�es de journal � toutes les m�thodes d'une classe qui 
   poss�de l'annotation LogEntry.
*/
public class EntryLogger
{
   /**
      Ajoute le code d'entr�e de journal � la classe donn�e
      @param args Le nom du fichier de classe � coller
   */
   public static void main(String[] args)
   {
      try
      {
         if (args.length == 0) 
            System.out.println("USAGE: java EntryLogger classname");
         else
         {
            Attribute.addAttributeReader("RuntimeVisibleAnnotations", 
               new AnnotationsAttributeReader());
            JavaClass jc = Repository.lookupClass(args[0]);
            ClassGen cg = new ClassGen(jc);
            EntryLogger el = new EntryLogger(cg);
            el.convert();
            File f = new File(
               Repository.lookupClassFile(cg.getClassName()).getPath());
            cg.getJavaClass().dump(f.getPath());
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
      Construit un EntryLogger qui ins�re une consignation dans les
      m�thodes annot�es d'une classe donn�e. 
      @param cg La classe
   */
   public EntryLogger(ClassGen cg)
   {
      this.cg = cg;
      cpg = cg.getConstantPool();
   }

   /**
      Convertit la classe en ins�rant les appels de consignation.
   */
   public void convert() throws IOException
   {
      for (Method m : cg.getMethods())
      {
         AnnotationsAttribute attr 
            = (AnnotationsAttribute) getAttribute(m, 
               "RuntimeVisibleAnnotations");
         if (attr != null)
         {
            LogEntry logEntry = attr.getAnnotation(LogEntry.class);
            if (logEntry != null)
            {
               String loggerName = logEntry.logger();
               if (loggerName == null) loggerName = "";
               cg.replaceMethod(m, insertLogEntry(m, loggerName));               
            }
         }
      }
   }

   /**
      Ajoute un appel "entering" au d�but d'une m�thode.
      @param m La m�thode
      @param loggerName Le nom du consignateur � appeler
   */
   private Method insertLogEntry(Method m, String loggerName)
   {
      MethodGen mg = new MethodGen(m, cg.getClassName(), cpg);
      String className = cg.getClassName();
      String methodName = mg.getMethod().getName();
      System.out.printf("Ajout d'instructions de consignation � %s.%s%n", 
         className, methodName);


      int getLoggerIndex = cpg.addMethodref(
            "java.util.logging.Logger",
            "getLogger",
            "(Ljava/lang/String;)Ljava/util/logging/Logger;");
      int enteringIndex = cpg.addMethodref(
            "java.util.logging.Logger",
            "entering",
            "(Ljava/lang/String;Ljava/lang/String;)V");

      InstructionList il = mg.getInstructionList();
      InstructionList patch = new InstructionList();
      patch.append(new PUSH(cpg, loggerName));
      patch.append(new INVOKESTATIC(getLoggerIndex));
      patch.append(new PUSH(cpg, className));
      patch.append(new PUSH(cpg, methodName));
      patch.append(new INVOKEVIRTUAL(enteringIndex));
      InstructionHandle[] ihs = il.getInstructionHandles();
      il.insert(ihs[0], patch);

      mg.setMaxStack();
      return mg.getMethod();
   }

   /**
      R�cup�re l'attribut d'un champ ou d'une m�thode avec le nom donn�.
      @param fm Le champ ou la m�thode
      @param name Le nom de l'attribut
      @return L'attribut ou null si aucun attribut n'est trouv� 
      avec le nom donn�
   */
   public static Attribute getAttribute(FieldOrMethod fm, String name)
   {
      for (Attribute attr : fm.getAttributes())
      {
         int nameIndex = attr.getNameIndex();
         ConstantPool cp = attr.getConstantPool();
         String attrName = cp.constantToString(
            cp.getConstant(nameIndex));
         if (attrName.equals(name))
            return attr;
      }
      return null;
   }

   private ClassGen cg;
   private ConstantPoolGen cpg;
}

/**
   Lecteur � int�grer pour un attribut d'annotation dans le
   cadre de la BCEL.
*/
class AnnotationsAttributeReader implements org.apache.bcel.classfile.AttributeReader
{
   public Attribute createAttribute(int nameIndex, 
         int length, DataInputStream in,
      ConstantPool constantPool)
   {
      AnnotationsAttribute attribute = new AnnotationsAttribute(
         nameIndex, length, constantPool);
      try 
      {
         attribute.read(in, constantPool);
         return attribute;
      }
      catch (IOException e)
      {
         e.printStackTrace();
         return null;
      }
   }
}

/**
   Cet attribut d�crit un jeu d'annotations. 
   Seuls les attributs d'annotation en cha�ne sont pris en charge.
*/
class AnnotationsAttribute extends Attribute
{
   /**
      Lit cette annotation.
      @param nameIndex L'indice pour le nom de cet attribut
      @param length Le nombre d'octets de cet attribut
      @param cp Le pool constant
   */
   public AnnotationsAttribute (int nameIndex, 
      int length, ConstantPool cp)
   {
      super(Constants.ATTR_UNKNOWN, nameIndex, length, cp);
      annotations = new HashMap<String, Map<String, String>>();
   }

   /**
      Lit cette annotation.
      @param in Le flux d'entr�e
      @param cp Le pool constant
   */
   public void read(DataInputStream in, ConstantPool cp)
      throws IOException
   {
      short numAnnotations = in.readShort();
      for (int i = 0; i < numAnnotations; i++) 
      {
         short typeIndex = in.readShort();
         String type = cp.constantToString(cp.getConstant(typeIndex));
         Map<String, String> nvPairs = new HashMap<String, String>();
         annotations.put(type, nvPairs);
         short numElementValuePairs = in.readShort();
         for (int j = 0; j < numElementValuePairs; j++) 
         {
            short nameIndex = in.readShort();
            String name = cp.constantToString(cp.getConstant(nameIndex));
            byte tag = in.readByte();
            if (tag == 's') 
            {
               short constValueIndex = in.readShort();
               String value = cp.constantToString(
                  cp.getConstant(constValueIndex));
               nvPairs.put(name, value);
            }
            else
               throw new UnsupportedOperationException(
                 "Ne peut g�rer que les attributs String");
         }                        
      }
   }

   public void dump(DataOutputStream out)
      throws IOException
   {
      ConstantPoolGen cpg = new ConstantPoolGen(getConstantPool());

      out.writeShort(getNameIndex());
      out.writeInt(getLength());
      out.writeShort(annotations.size());
      for (Map.Entry<String, Map<String, String>> entry : 
        annotations.entrySet()) 
      {
         String type = entry.getKey();
         Map<String, String> nvPairs = entry.getValue();
         out.writeShort(cpg.lookupUtf8(type));         
         out.writeShort(nvPairs.size());
         for (Map.Entry<String, String> nv : nvPairs.entrySet()) 
         {
            out.writeShort(cpg.lookupUtf8(nv.getKey()));
            out.writeByte('s');
            out.writeShort(cpg.lookupUtf8(nv.getValue()));
         }
      }
   }

   /**
      R�cup�re une annotation de ce jeu d'annotations.
      @param annotationClass La classe de l'annotation � r�cup�rer
      @return L'objet annotation ou null si aucune annotation 
      correspondante n'est pr�sente
   */
   public <A extends Annotation> A getAnnotation(
      Class<A> annotationClass)
   {
      String key = "L" + annotationClass.getName() + ";";
      final Map<String, String> nvPairs = annotations.get(key);
      if (nvPairs == null) return null;

      InvocationHandler handler = new
         InvocationHandler()
         {
            public Object invoke(Object proxy, 
                  java.lang.reflect.Method m, Object[] args) 
               throws Throwable
            {
               return nvPairs.get(m.getName());
            }
         };

      return (A) Proxy.newProxyInstance(
         getClass().getClassLoader(), 
         new Class[] { annotationClass }, 
         handler);
   }

   public void accept(org.apache.bcel.classfile.Visitor v)
   {
      throw new UnsupportedOperationException();
   }

   public Attribute copy(ConstantPool cp)
   {
      throw new UnsupportedOperationException();
   }

   public String toString ()
   {
      return annotations.toString();
   }

   private Map<String, Map<String, String>> annotations;
}
