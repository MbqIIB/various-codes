Exemple 4.21 : SysPropClient.cpp

#include <iostream>

#include "SysProp.hh"

using namespace std;

CORBA::Object_ptr getObjectReference(CORBA::ORB_ptr orb,
   const char serviceName[])
{
   CosNaming::NamingContext_var rootContext;
  
   try
   {
      // Obtient une r�f�rence au contexte racine du service Name:
      CORBA::Object_var initServ;
      initServ = orb->resolve_initial_references("NameService");

      // Restreint l'objet renvoy� par resolve_initial_references()
      // � un objet CosNaming::NamingContext object
      rootContext = CosNaming::NamingContext::_narrow(initServ);
      if (CORBA::is_nil(rootContext)) 
      {
          cerr << "Echec dans la restriction du contexte de d�finition de noms." << endl;
         return CORBA::Object::_nil();
      }
   }
   catch(CORBA::ORB::InvalidName&)
   {
      cerr << "Le service de d�finition de noms n'existe pas." << endl;
      return CORBA::Object::_nil();
   }

   // Cr�e un objet name, contenant le nom corejava/SysProp:
   CosNaming::Name name;
   name.length(1);

   name[0].id   = serviceName;
   name[0].kind = "Object";
  
   CORBA::Object_ptr obj;
   try
   {
      // R�sout le nom � une r�f�rence d'objet, et y assigne la r�f�rence
      // renvoy�e � un CORBA::Object:
      obj = rootContext->resolve(name);
   }
   catch(CosNaming::NamingContext::NotFound&)
   {
      // Cette exception est d�clench�e si l'un des composants
      // du chemin [contextes ou l'objet] n'est pas trouv� :
      cerr << "Contexte non trouv�." << endl;
      return CORBA::Object::_nil();
   }
   return obj;
}

int main (int argc, char *argv[]) 
{
   CORBA::ORB_ptr orb = CORBA::ORB_init(argc, argv, "omniORB4");

   CORBA::Object_var obj = getObjectReference(orb, "SysProp");

   if (CORBA::is_nil(sysProp))
   { 
      cerr << "Impossible d'invoquer une r�f�rence d'objet nulle." 
         << endl;
      return 1;
   }

   CORBA::String_var key = "java.vendor";
   CORBA::String_var value = sysProp->getProperty(key);

   cerr << key << "=" << value << endl;

   return 0;
}


