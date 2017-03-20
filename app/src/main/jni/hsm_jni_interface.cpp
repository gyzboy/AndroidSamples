//#define LOG_SWITCH "debug.loggable.safe"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <dlfcn.h>
#include <semaphore.h>
#include <unistd.h>
#include <errno.h>
#include <jni.h>
#include <time.h>
//#include <cutils/properties.h>
#include <android/log.h>
#include "hsm_interface.h"
#include "hsm_jni_interface.h"

const char* g_pJNIREG_CLASS = "com/gyz/androidsamples/security/xidingjce/jni/HsmInterface";

//#define LOG_TAG "XD-JCE-JNI"
//#define LOGI(fmt,args...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,fmt,##args)
//#define LOGE(fmt,args...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,fmt,##args)

typedef struct hsm_interface {
	HSM_OSM_OPEN 				hsm_osm_open;
	HSM_OSM_CLOSE 				hsm_osm_close;
	HSM_OSM_SAVE_OBJECT			hsm_osm_save_object;
	HSM_OSM_DELETE_OBJECT		hsm_osm_delete_object;
	HSM_OSM_DELETE_ALL			hsm_osm_delete_all;
	HSM_OSM_LOAD_OBJECT			hsm_osm_load_object;
	HSM_OSM_RANDOM				hsm_osm_random;
	HSM_OSM_GET_VER				hsm_osm_get_ver;
	HSM_STORE_3DESKEY			hsm_store_3deskey;
	HSM_COMPUTE_3DES			hsm_compute_3des;
	
	// CPOS SECURITY INTERFACE //
	HSM_INJECT_ROOT_CERT        hsm_inject_root_cert;
	HSM_INJECT_PUBLICKEY_CERT   hsm_inject_publickey_cert;
	HSM_GET_CERT                hsm_get_cert;
	HSM_DELET_CERT 				hsm_delete_cert;
	HSM_DELET_KEY_PAIR 			hsm_delete_key_pair;
	HSM_GENERATE_KEY_PAIR 		hsm_generate_key_pair;
	HSM_GENERATE_CSR            hsm_generate_csr;
	HSM_RSA_ENCRYPT             hsm_RSA_encrypt;
	HSM_RSA_DECRYPT             hsm_RSA_decrypt;
	HSM_GET_RANDOM 				hsm_get_random;
	HSM_QUERY_PRIVATEKEY_LABELS hsm_query_privatekey_labels;
	HSM_QUERY_CERT_LABELS 		hsm_query_cert_labels;
	HSM_QUERY_CERT_TYPE         hsm_query_cert_type;
	HSM_QUERY_PRIVATE_KEY_COUNT hsm_query_private_key_count;
	HSM_QUERY_CERT_COUNT        hsm_query_cert_count;
	HSM_OSM_ENUMERATE_CERTS     hsm_osm_enumerate_certs;

	void* 						pHandle;
} HSM_INSTANCE;

static HSM_INSTANCE* g_pHsmInstance = NULL;

/**
 * hsm module init
 * @return value 0 : succ
 *               <0 : error code
 */
static int hsm_module_init()
{
	void* pHandle = NULL;
	
	if (g_pHsmInstance == NULL)
	{
//		LOGI("hsm_module_init\n");

		pHandle = dlopen("libPKCS11Wrapper.so", RTLD_LAZY);
		if (!pHandle)
		{
//			LOGE("load libPKCS11Wrapper.so failed\n");
			return -1;
		}
		
		g_pHsmInstance = new HSM_INSTANCE();
		
		g_pHsmInstance->hsm_osm_open = (HSM_OSM_OPEN)dlsym(pHandle, "hsm_osm_open");
		if(g_pHsmInstance->hsm_osm_open == NULL)
		{
//			LOGE("Load hsm_osm_open failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_osm_close = (HSM_OSM_CLOSE)dlsym(pHandle, "hsm_osm_close");
		if(g_pHsmInstance->hsm_osm_close == NULL)
		{
//			LOGE("load hsm_osm_close failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_osm_save_object = (HSM_OSM_SAVE_OBJECT)dlsym(pHandle, "hsm_osm_save_object");
		if(g_pHsmInstance->hsm_osm_save_object == NULL)
		{
//			LOGE("load hsm_osm_save_object failed\n");
			goto function_failed;
		}

		g_pHsmInstance->hsm_osm_delete_object = (HSM_OSM_DELETE_OBJECT)dlsym(pHandle, "hsm_osm_delete_object");
		if(g_pHsmInstance->hsm_osm_delete_object == NULL)
		{
//			LOGE("load hsm_osm_delete_object failed\n");
			goto function_failed;
		}

		g_pHsmInstance->hsm_osm_delete_all = (HSM_OSM_DELETE_ALL)dlsym(pHandle, "hsm_osm_delete_all");
		if(g_pHsmInstance->hsm_osm_delete_all == NULL)
		{
//			LOGE("load hsm_osm_delete_all failed\n");
			goto function_failed;
		}

		g_pHsmInstance->hsm_osm_load_object = (HSM_OSM_LOAD_OBJECT)dlsym(pHandle, "hsm_osm_load_object");
		if(g_pHsmInstance->hsm_osm_load_object == NULL)
		{
//			LOGE("load hsm_osm_load_object failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_osm_random = (HSM_OSM_RANDOM)dlsym(pHandle, "hsm_osm_random");
		if(g_pHsmInstance->hsm_osm_random == NULL)
		{
//			LOGE("load hsm_osm_random failed\n");
			goto function_failed;
		}

		g_pHsmInstance->hsm_osm_get_ver = (HSM_OSM_GET_VER)dlsym(pHandle, "hsm_osm_getVer");
		if(g_pHsmInstance->hsm_osm_get_ver == NULL)
		{
//			LOGE("load hsm_osm_getVer failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_store_3deskey = (HSM_STORE_3DESKEY)dlsym(pHandle, "hsm_store_3deskey");
		if(g_pHsmInstance->hsm_store_3deskey == NULL)
		{
//			LOGE("load hsm_store_3deskey failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_compute_3des = (HSM_COMPUTE_3DES)dlsym(pHandle, "hsm_compute_3des");
		if(g_pHsmInstance->hsm_compute_3des == NULL)
		{
//			LOGE("load hsm_compute_3des failed\n");
			goto function_failed;
		}
		// CPOS SECURITY INTERFACE //
		g_pHsmInstance->hsm_inject_root_cert = (HSM_INJECT_ROOT_CERT)dlsym(pHandle, "hsm_inject_root_cert");
		if(g_pHsmInstance->hsm_inject_root_cert == NULL)
		{
//			LOGE("load hsm_inject_root_cert failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_inject_publickey_cert = (HSM_INJECT_PUBLICKEY_CERT)dlsym(pHandle, "hsm_inject_publickey_cert");
		if(g_pHsmInstance->hsm_inject_publickey_cert == NULL)
		{
//			LOGE("load hsm_inject_publickey_cert failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_get_cert = (HSM_GET_CERT)dlsym(pHandle, "hsm_get_cert");
		if(g_pHsmInstance->hsm_get_cert == NULL)
		{
//			LOGE("load hsm_get_cert failed\n");
			goto function_failed;
		}

		g_pHsmInstance->hsm_delete_cert = (HSM_DELET_CERT)dlsym(pHandle, "hsm_delete_cert");
		if(g_pHsmInstance->hsm_delete_cert == NULL)
		{
//			LOGE("load hsm_delete_cert failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_delete_key_pair = (HSM_DELET_KEY_PAIR)dlsym(pHandle, "hsm_delete_key_pair");
		if(g_pHsmInstance->hsm_delete_key_pair == NULL)
		{
//			LOGE("load hsm_delete_key_pair failed\n");
			goto function_failed;
		}

		g_pHsmInstance->hsm_generate_key_pair = (HSM_GENERATE_KEY_PAIR)dlsym(pHandle, "hsm_generate_key_pair");
		if(g_pHsmInstance->hsm_generate_key_pair == NULL)
		{
//			LOGE("load hsm_generate_key_pair failed\n");
			goto function_failed;
		}		

		g_pHsmInstance->hsm_generate_csr = (HSM_GENERATE_CSR)dlsym(pHandle, "hsm_generate_csr");
		if(g_pHsmInstance->hsm_generate_csr == NULL)
		{
//			LOGE("load hsm_generate_csr failed\n");
			goto function_failed;
		}			
		
		g_pHsmInstance->hsm_RSA_encrypt = (HSM_RSA_ENCRYPT)dlsym(pHandle, "hsm_RSA_encrypt");
		if(g_pHsmInstance->hsm_RSA_encrypt == NULL)
		{
//			LOGE("load hsm_RSA_encrypt failed\n");
			goto function_failed;
		}	
		g_pHsmInstance->hsm_RSA_decrypt = (HSM_RSA_DECRYPT)dlsym(pHandle, "hsm_RSA_decrypt");
		if(g_pHsmInstance->hsm_RSA_decrypt == NULL)
		{
//			LOGE("load hsm_RSA_decrypt failed\n");
			goto function_failed;
		}
			
		g_pHsmInstance->hsm_get_random = (HSM_GET_RANDOM)dlsym(pHandle, "hsm_get_random");
		if(g_pHsmInstance->hsm_get_random == NULL)
		{
//			LOGE("load hsm_get_random failed\n");
			goto function_failed;
		}
		g_pHsmInstance->hsm_query_privatekey_labels = (HSM_QUERY_PRIVATEKEY_LABELS)dlsym(pHandle, "hsm_query_privatekey_labels");
		if(g_pHsmInstance->hsm_query_privatekey_labels == NULL)
		{
//			LOGE("load hsm_query_privatekey_labels failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_query_cert_labels = (HSM_QUERY_CERT_LABELS)dlsym(pHandle, "hsm_query_cert_labels");
		if(g_pHsmInstance->hsm_query_cert_labels == NULL)
		{
//			LOGE("load hsm_query_cert_labels failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_query_cert_type = (HSM_QUERY_CERT_TYPE)dlsym(pHandle, "hsm_query_cert_type");
		if(g_pHsmInstance->hsm_query_cert_type == NULL)
		{
//			LOGE("load hsm_query_cert_type failed\n");
			goto function_failed;
		}

		g_pHsmInstance->hsm_query_private_key_count = (HSM_QUERY_PRIVATE_KEY_COUNT)dlsym(pHandle, "hsm_query_private_key_count");
		if(g_pHsmInstance->hsm_query_private_key_count == NULL)
		{
//			LOGE("load hsm_query_private_key_count failed\n");
			goto function_failed;
		}
		
		g_pHsmInstance->hsm_query_cert_count = (HSM_QUERY_CERT_COUNT)dlsym(pHandle, "hsm_query_cert_count");
		if(g_pHsmInstance->hsm_query_cert_count == NULL)
		{
//			LOGE("load hsm_query_private_key_count failed\n");
			goto function_failed;
		}
		g_pHsmInstance->hsm_osm_enumerate_certs = (HSM_OSM_ENUMERATE_CERTS)dlsym(pHandle, "hsm_osm_enumerate_certs");
		if(g_pHsmInstance->hsm_osm_enumerate_certs == NULL)
		{
//			LOGE("load hsm_osm_enumerate_certs failed\n");
			goto function_failed;
		}
		

		g_pHsmInstance->pHandle = pHandle;
	}
	return 0;
	
function_failed:
	if(g_pHsmInstance != NULL)
	{
		delete g_pHsmInstance;
		g_pHsmInstance = NULL;
	}
	dlclose(pHandle);
	return -1;
}

/**
 * Open the safe module.
 * return value  >= 0 : success (suggest 0)
 *	              < 0 : error code
 */
int native_hsm_osm_open(JNIEnv * env, jclass obj)
{
	int nResult = -1;
	
	int initRs = hsm_module_init();
	if (initRs == -2 || initRs == -1)
		return initRs;

	nResult = g_pHsmInstance->hsm_osm_open();
	
	return nResult;
}

/**
 * close the safe module
 * return value  >= 0 : success (suggest 0)
 *	              < 0 : error code
 */
int native_hsm_osm_close(JNIEnv * env, jclass obj)
{
	int nResult = -1;
	if(g_pHsmInstance == NULL)
		return nResult;
		
	nResult = g_pHsmInstance->hsm_osm_close();
	dlclose(g_pHsmInstance->pHandle);
	delete g_pHsmInstance;
	g_pHsmInstance = NULL;

	return nResult;
}


int native_injectRootCertificate(JNIEnv * env, jclass obj, jint certType, jbyteArray bufCert, jint bufLength,  jstring alias,jint dataFormat)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	
	if(bufCert == NULL)
		return nResult;
	
	jbyte* pbCertContent = env->GetByteArrayElements(bufCert, NULL);
	const char* palias = env->GetStringUTFChars(alias, JNI_FALSE);

	nResult = g_pHsmInstance->hsm_inject_root_cert(certType, (unsigned char*)pbCertContent,bufLength,  (char*)palias,  dataFormat);
	
	env->ReleaseByteArrayElements(bufCert, pbCertContent, 0);
	if(palias)
		env->ReleaseStringUTFChars(alias, palias);	
	return nResult;	
}

int native_injectPublicKeyCertificates(JNIEnv * env, jclass obj, jbyteArray  bufCert, jint bufLength, jstring alias, jstring aliasPrivateKey, jint dataFormat)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	
	if(bufCert == NULL)
		return nResult;
	
	jbyte* pbCertContent = env->GetByteArrayElements(bufCert, NULL);
	const char* palias = env->GetStringUTFChars(alias, JNI_FALSE);
	const char* paliasPrivateKey = env->GetStringUTFChars(aliasPrivateKey, JNI_FALSE);
	

	nResult = g_pHsmInstance->hsm_inject_publickey_cert((unsigned char*)pbCertContent,bufLength, (char*)palias, (char*)paliasPrivateKey,dataFormat);
	
	env->ReleaseByteArrayElements(bufCert, pbCertContent, 0);
	
	if(paliasPrivateKey)
		env->ReleaseStringUTFChars(aliasPrivateKey, paliasPrivateKey);		
	if(palias)
		env->ReleaseStringUTFChars(alias, palias);	
	return nResult;	
}


int native_hsm_get_cert(JNIEnv * env, jclass obj, jbyteArray  bufCert, jint certType, jstring alias, jint dataFormat)
{
	int nResult = -1;
	int certLen = 0;
	
	if(g_pHsmInstance == NULL)
		return nResult;

	if(bufCert == NULL)
		return nResult;
	
	jbyte* pbCertContent = env->GetByteArrayElements(bufCert, NULL);
	const char* palias = env->GetStringUTFChars(alias, JNI_FALSE);	
	
	nResult = g_pHsmInstance->hsm_get_cert((unsigned char*)pbCertContent, &certLen, certType,(char*)palias,dataFormat);

	env->ReleaseByteArrayElements(bufCert, pbCertContent, 0);
	if(palias)
		env->ReleaseStringUTFChars(alias, palias);	
	if(nResult >= 0)
		return certLen;
	
	return nResult;	
}

int native_hsm_delete_cert(JNIEnv * env, jclass obj,jint certType, jstring alias)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	nResult = g_pHsmInstance->hsm_osm_delete_all((char*)"1234", 4);
	return nResult;	
}

int native_hsm_delete_key_pair(JNIEnv * env, jclass obj, jstring aliasPrivateKey)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	nResult = g_pHsmInstance->hsm_osm_delete_all((char*)"1234", 4);
	return nResult;	
}

int native_generateKeyPair(JNIEnv * env, jclass obj, jstring alias)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	
	const char* palias = env->GetStringUTFChars(alias, JNI_FALSE);	
	nResult = g_pHsmInstance->hsm_generate_key_pair((char*)palias);
	if(palias)
		env->ReleaseStringUTFChars(alias, palias);	

	return nResult;	
}

int native_generateCSR(JNIEnv * env, jclass obj, jbyteArray  bufCert, jstring aliasPrivateKey, jstring commName)
{
	int nResult = -1;
	int bufCertlen = 10240;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	if(bufCert == NULL)
		return nResult;	
	
	const char* paliasPrivateKey = env->GetStringUTFChars(aliasPrivateKey, JNI_FALSE);	
	const char* pcommName = env->GetStringUTFChars(commName, JNI_FALSE);	
	
	jbyte* pbCertContent = env->GetByteArrayElements(bufCert, NULL);
	
	nResult = g_pHsmInstance->hsm_generate_csr((unsigned char*)pbCertContent,&bufCertlen,(char*)paliasPrivateKey,(char*)pcommName);	

	env->ReleaseByteArrayElements(bufCert, pbCertContent, 0);
	
	if(paliasPrivateKey)
		env->ReleaseStringUTFChars(aliasPrivateKey, paliasPrivateKey);	
	if(pcommName)
		env->ReleaseStringUTFChars(commName, pcommName);	
	if(nResult >=0)
		return bufCertlen;
	
	return nResult;

}

int native_RSA_encrypt(JNIEnv * env, jclass obj, jbyteArray  bufCipher ,jstring aliasPrivateKey, jbyteArray bufPlain, int plainLen)
{
	int nResult = -1;
	int bufCipherlen = 10240;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	if(bufCipher == NULL || bufPlain == NULL )
		return nResult;	
	
	const char* paliasPrivateKey = env->GetStringUTFChars(aliasPrivateKey, JNI_FALSE);	
	jbyte* pbufCipher = env->GetByteArrayElements(bufCipher, NULL);
	jbyte* pbufPlain = env->GetByteArrayElements(bufPlain, NULL);
	
	
	nResult = g_pHsmInstance->hsm_RSA_encrypt((unsigned char*)pbufCipher,&bufCipherlen,(char*)paliasPrivateKey,(unsigned char*)pbufPlain,plainLen);	

	env->ReleaseByteArrayElements(bufCipher, pbufCipher, 0);
	env->ReleaseByteArrayElements(bufPlain, pbufPlain, 0);
	if(paliasPrivateKey)
		env->ReleaseStringUTFChars(aliasPrivateKey, paliasPrivateKey);		
	
	if(nResult >=0)
		return bufCipherlen;
	
	return nResult;
}

int native_RSA_decrypt(JNIEnv * env, jclass obj, jbyteArray  bufPlain  ,jstring aliasPrivateKey, jbyteArray bufCipher, int cipherLen)
{
	int nResult = -1;
	int bufPlainlen = 10240;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	if(bufCipher == NULL || bufPlain == NULL )
		return nResult;	
	
	const char* paliasPrivateKey = env->GetStringUTFChars(aliasPrivateKey, JNI_FALSE);	
	jbyte* pbufCipher = env->GetByteArrayElements(bufCipher, NULL);
	jbyte* pbufPlain = env->GetByteArrayElements(bufPlain, NULL);

	nResult = g_pHsmInstance->hsm_RSA_decrypt((unsigned char*)pbufPlain,&bufPlainlen,(char*)paliasPrivateKey,(unsigned char*)pbufCipher,cipherLen);	

	env->ReleaseByteArrayElements(bufCipher, pbufCipher, 0);
	env->ReleaseByteArrayElements(bufPlain, pbufPlain, 0);
	if(paliasPrivateKey)
		env->ReleaseStringUTFChars(aliasPrivateKey, paliasPrivateKey);		
	
	if(nResult >=0)
		return bufPlainlen;
	
	return nResult;
}

int native_getrandom(JNIEnv * env, jclass obj, jbyteArray  random  ,jint  length)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	if(random == NULL)
		return nResult;	

	jbyte* prandom = env->GetByteArrayElements(random, NULL);

	nResult = g_pHsmInstance->hsm_get_random((unsigned char*)prandom,length);	
	
	env->ReleaseByteArrayElements(random, prandom, 0);
		
	return  nResult;
}

int native_queryprivatekeylabels(JNIEnv * env, jclass obj, jbyteArray  keylabels)
{
	int nResult = -1;
	int maxlength = 1024;
	int i =0;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	if(keylabels == NULL)
		return nResult;	


	jbyte* pkeylabels = env->GetByteArrayElements(keylabels, NULL);
	nResult = g_pHsmInstance->hsm_query_privatekey_labels((unsigned char*)pkeylabels);	
	env->ReleaseByteArrayElements(keylabels, pkeylabels, 0);	
	
//	LOGI("native_queryprivatekeylabels  nResult = %d",nResult );
	if(nResult >=0){
		for(i = 0;i<maxlength;i++){
//			LOGI("native_queryprivatekeylabels	i = %d",i );
			
			if(pkeylabels[i] == 0)
				return i;
		}
	}	
	
	return  nResult;
}

int native_querycertlabels(JNIEnv * env, jclass obj, jbyteArray  certlabels,int certType)
{
	int nResult = -1;
	int maxlength = 1024;
	int i =0;
	
	if(g_pHsmInstance == NULL)
		return nResult;
	if(certlabels == NULL)
		return nResult;	

//	LOGI("querycertlabels certType = %d",certType );
	
	jbyte* pcertlabels = env->GetByteArrayElements(certlabels, NULL);	
	nResult = g_pHsmInstance->hsm_query_cert_labels((unsigned char*)pcertlabels,certType);	
	env->ReleaseByteArrayElements(certlabels, pcertlabels, 0);	
	
	if(nResult >=0){
		for(i = 0;i<maxlength;i++){
			if(pcertlabels[i] == 0)
				return i;
		}
	}	
	
	return  nResult;
}

int native_querycerttype(JNIEnv * env, jclass obj, jstring alias)
{
	int nResult = -1;
	if(g_pHsmInstance == NULL)
		return nResult;

	const char* palias = env->GetStringUTFChars(alias, JNI_FALSE);

	nResult = g_pHsmInstance->hsm_query_cert_type((char*)palias);

	if(palias)
		env->ReleaseStringUTFChars(alias, palias);

	return nResult;
}

int native_queryprivatecount(JNIEnv * env, jclass obj)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;

	nResult = g_pHsmInstance->hsm_query_private_key_count();	
	
	return  nResult;
}

int native_querycertcount(JNIEnv * env, jclass obj,jint certType)
{
	int nResult = -1;
	
	if(g_pHsmInstance == NULL)
		return nResult;

	nResult = g_pHsmInstance->hsm_query_cert_count(certType);	
	
	return  nResult;
}

static JNINativeMethod g_Methods[] =
{
	{"hsm_open",                    "()I",                                         (void*)native_hsm_osm_open},
	{"hsm_close",                   "()I",                                         (void*)native_hsm_osm_close},
//	{"injectRootCertificate",       "(I[BILjava/lang/String;I)I",                  (void*)native_injectRootCertificate},
//	{"injectPublicKeyCertificates",	"([BILjava/lang/String;Ljava/lang/String;I)I", (void*)native_injectPublicKeyCertificates},
	{"hsm_get_cert",                "([BILjava/lang/String;I)I",                   (void*)native_hsm_get_cert},
//	{"hsm_delete_cert",             "(ILjava/lang/String;)I",                      (void*)native_hsm_delete_cert},
//	{"hsm_delete_key_pair",         "(Ljava/lang/String;)I",                       (void*)native_hsm_delete_key_pair},
//	{"generateKeyPair",             "(Ljava/lang/String;)I",                       (void*)native_generateKeyPair},
//	{"generateCSR",                 "([BLjava/lang/String;Ljava/lang/String;)I",   (void*)native_generateCSR},
//	{"RSA_encrypt",                 "([BLjava/lang/String;[BI)I",                  (void*)native_RSA_encrypt},
//	{"RSA_decrypt",                 "([BLjava/lang/String;[BI)I",                  (void*)native_RSA_decrypt},
//	{"getrandom",                   "([BI)I",                                      (void*)native_getrandom},
//	{"queryprivatekeylabels",       "([B)I",                                       (void*)native_queryprivatekeylabels},
//	{"querycertlabels",             "([BI)I",                                      (void*)native_querycertlabels},
	{"query_cert_type",             "(Ljava/lang/String;)I",                       (void*)native_querycerttype},
//	{"queryprivatecount",           "()I",                                         (void*)native_queryprivatecount},
//	{"querycertcount",              "(I)I",                                        (void*)native_querycertcount},
};

const char* hsm_get_class_name()
{
	return g_pJNIREG_CLASS;
}

JNINativeMethod* hsm_get_methods(int* pCount)
{
	*pCount = sizeof(g_Methods) /sizeof(g_Methods[0]);
	return g_Methods;
}
