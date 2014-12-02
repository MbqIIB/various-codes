Exemple 4.19 : EnvServer.cpp

#include <iostream>
#include <cstdlib>

#include "Env.hh"

using namespace std;

class EnvImpl :
   public POA_Env,
   public PortableServer::RefCountServantBase
{
public:
   virtual char* getenv(const char *name);
};

char* EnvImpl::getenv(const char *name)
{
   char* value = std::getenv(name);
   return CORBA::string_dup(value);
}

static void bindObjectToName(CORBA::ORB_ptr orb,
   const char name[], CORBA::Object_ptr objref)
{
   CosNaming::NamingContext_var rootContext;
  
   try
   {
      // Obtient une r�f�rence au contexte racine du service Name :
      CORBA::Object_var obj;
      obj = orb->resolve_initial_references("NameService");

      // Restreint la r�f�rence renvoy�e      
      rootContext = CosNaming::NamingContext::_narrow(obj);
      if(CORBA::is_nil(rootContext))
      {
         cerr << "Echec dans la restriction du contexte de nom." << endl;
         return;
      }
   }
   catch (CORBA::ORB::InvalidName& ex)
   {
      //Ceci ne devrait pas arriver !
      cerr << "Le service n'existe pas." << endl;
      return;
   }

   try
   {
      CosNaming::Name contextName;
      contextName.length(1);
      contextName[0].id   = (const char*) "corejava"; 
      contextName[0].kind = (const char*) "Context";     

      CosNaming::NamingContext_var corejavaContext;
      try
      {
         // Lie le contexte � la racine :
         corejavaContext 
            = rootContext->bind_new_context(contextName);
      }
      catch (CosNaming::NamingContext::AlreadyBound& ex)
      {
         // Si le contexte existe d�j�, cette exception sera d�clench�e.
         // Dans ce cas, il suffit de r�soudre le nom et d'assigner
         // testContext � l'objet retourn� :
         CORBA::Object_var obj;
         obj = rootContext->resolve(contextName);
         corejavaContext 
            = CosNaming::NamingContext::_narrow(obj);
         if( CORBA::is_nil(corejavaContext) ) 
         {
            cerr << "Echec dans la restriction du contexte de nom." 
               << endl;
            return;
         }
      }

      // Lie objref � name dans le contexte :
      CosNaming::Name objectName;
      objectName.length(1);
      objectName[0].id = name;   
      objectName[0].kind = (const char*) "Object"; 

   try
   {
     corejavaContext->bind(objectName, objref);
   }
   catch (CosNaming::NamingContext::AlreadyBound& ex)
   {  
       corejavaContext->rebind(objectName, objeref);
   }
}
catch (CORBA::COMM_FAILURE& ex)
   {
      cerr 
         << "Exception syst�me d�tourn�e COMM_FAILURE � impossible de "
         << "contacter le service de noms." << endl;
   }
   catch (CORBA::SystemException&) 
   {
      cerr << "D�tournement d'une CORBA::SystemException pendant "
         << "l'utilisation du service de nom." << endl;
   }
}

int main(int argc, char *argv[])
{
   cout << "Cr�ation et initialisation de l'ORB..." << endl;

   CORBA::ORB_var orb = CORBA::ORB_init(argc, argv, "omniORB4");

   CORBA::Object_var obj 
      = orb->resolve_initial_references("RootPOA");
   PortableServer::POA_var poa 
      = PortableServer::POA::_narrow(obj);
   poa->the_POAManager()->activate();

   EnvImpl* envImpl = new EnvImpl();
   poa->activate_object(envImpl);

   // R�cup�re une r�f�rence � l'objet et l'enregistre dans
   // le service de nom.
   obj = envImpl->_this();

   cout << orb->object_to_string(obj) << endl;
   cout << "Liaison des impl�mentations serveur � la base de registre..." 
      << endl;
   bindObjectToName(orb, "Env", obj);
   envImpl->_remove_ref();

   cout << "Attente des invocations des clients..." << endl;
   orb->run();

   return 0;
}
