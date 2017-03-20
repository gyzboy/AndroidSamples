#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include <jni.h>

#include "hsm_jni_interface.h"

/*
 * Register several native methods for one class
 */
static int register_native_methods(JNIEnv* env, const char* strClassName, JNINativeMethod* pMethods, int nMethodNumber)
{
	jclass clazz;
	clazz = env->FindClass(strClassName);
	if(clazz == NULL)
		return JNI_FALSE;
	if(env->RegisterNatives(clazz, pMethods, nMethodNumber) < 0)
		return JNI_FALSE;
	return JNI_TRUE;
}

/*
 * Register native methods for all class
 *
 */
int register_com_gyz_androidsamples_security_xidingjce_jni_HsmInterface(JNIEnv* env)
{
	int nCount = 0;
	JNINativeMethod* pMethods = hsm_get_methods(&nCount);

	return register_native_methods(env,	hsm_get_class_name(), pMethods, nCount);
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
	JNIEnv* env = NULL;
	jint nResult = -1;

	if(vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK)
	{
		return -1;
	}
	assert(env != NULL);

	if(!register_com_gyz_androidsamples_security_xidingjce_jni_HsmInterface(env))
		return -1;

	return JNI_VERSION_1_4;
}
