Exemple 9.1 : HelloNative.h

/* NE PAS EDITER CE FICHIER - il est g�n�r� par la machine */
#include <jni.h>
/* En-t�te pour la classe HelloNative */

#ifndef _Included_HelloNative
#define _Included_HelloNative
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     HelloNative
 * Method:    greeting
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_HelloNative_greeting
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
