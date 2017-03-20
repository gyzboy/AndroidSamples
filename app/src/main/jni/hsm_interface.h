#ifndef HSM_INTERFACE_H_
#define HSM_INTERFACE_H_

#ifdef __cplusplus
extern "C" {
#endif

/* hsm objetc data type */
typedef enum Object_data_type
{
	HSM_OBJECT_DATA_TYPE_pem,
	HSM_OBJECT_DATA_TYPE_der,
	HSM_OBJECT_DATA_TYPE_p7b,
	HSM_OBJECT_DATA_TYPE_pfx
}HSM_OBJECT_DATA_TYPE;

/* hsm object type */
typedef enum object_type_
{
	HSM_OBJECT_TYPE_private_key,
	HSM_OBJECT_TYPE_public_key,
	HSM_OBJECT_TYPE_cert
}HSM_OBJECT_TYPE;

/* hsm object property */
typedef struct object_proptery_
{
	unsigned char strID[32];		/* id */
	unsigned char strLabel[32];		/* label */
	unsigned char strPassword[32];	/* password NULL */
	HSM_OBJECT_TYPE nObjectType;
}HSM_OBJECT_PROPERTY;

/**
 * Open the safe module.
 * return value  >= 0 : success (suggest 0)
 *	              < 0 : error code
 */
typedef int (*HSM_OSM_OPEN)(void);

/**
 * close the safe module
 * return value  >= 0 : success (suggest 0)
 *	              < 0 : error code
 */
typedef int (*HSM_OSM_CLOSE)(void);

/**
 * Inject the certificate of the existing key pair.
 * @param pObjectProperty[in] : the HSM_OBJECT_PROPERTY object
 * @param pObjectData[in] : the data of the certificate.
 * @param nDataLength[in] : the length of the data buffer.
 * @param nDataType[in] : the format of the buffer, Currently, only "HSM_OBJECT_DATA_TYPE_pem" is supported.
 * return value >= 0 : success (suggest 0)
 *               < 0 : error code
 */
//typedef int (*HSM_OSM_SAVE_ROOTCERT)(HSM_OBJECT_PROPERTY* pObjectProperty, unsigned char* pObjectData, unsigned int nDataLength, HSM_OBJECT_DATA_TYPE nDataType);

/**
 * Inject the certificate of the existing key pair.
 * @param pObjectProperty[in] : the HSM_OBJECT_PROPERTY object
 * @param pKeyId[in] : the alias of the certificate.
 * @param pObjectData[in] : the data of the certificate.
 * @param nDataLength[in] : the length of the data buffer.
 * @param nDataType[in] : the format of the buffer, Currently, only "HSM_OBJECT_DATA_TYPE_pem" is supported.
 * return value >= 0 : success (suggest 0)
 *               < 0 : error code
 */
//typedef int (*HSM_OSM_SAVE_PUBCERT)(HSM_OBJECT_PROPERTY* pObjectProperty, unsigned char* pKeyId, unsigned char* pObjectData, unsigned int nDataLength, HSM_OBJECT_DATA_TYPE nDataType);

typedef int (*HSM_OSM_SAVE_OBJECT)(HSM_OBJECT_PROPERTY* pObjectProperty, unsigned char* pObjectData, unsigned int nDataLength, HSM_OBJECT_DATA_TYPE nDataType);

/**
 * Remove the certificate of the given alias.
 * The OWNER certificate can't be removed.
 * @param pObjectProperty[in] : the HSM_OBJECT_PROPERTY object
 * @param pPIN[in] : the password of the certificate
 * @param nPINLength[in] : the password length
 * return value >=0 : success (suggest 0)
 *               <0 : error code
 */
typedef int (*HSM_OSM_DELETE_OBJECT)(HSM_OBJECT_PROPERTY* pObjectProperty, char* pPIN, unsigned int nPINLength);

typedef int (*HSM_OSM_DELETE_ALL)(char* pPIN, unsigned int nPINLength);

/**
 * Get the certificate data.
 * @param nIndex[in] : slot id, usually we use "0"
 * @param ObjectProperty[in] : the HSM_OBJECT_PROPERTY object
 * @param byteData[out] : the output buffer to store the certificate PEM data.
 * @param nDataLength[in] : the max length of the result buffer.
 * @param nDataType : the format of the buffer, Currently, only "HSM_OBJECT_DATA_TYPE_pem" is supported.
 * return value >=0 : the length of the certificate PEM data.
 *               <0 : error code
 */
typedef int (*HSM_OSM_LOAD_OBJECT)(unsigned int nIndex, HSM_OBJECT_PROPERTY* ObjectProperty, unsigned char* byteData, unsigned int nDataLength, HSM_OBJECT_DATA_TYPE nDataType);

/**
 * Get the real random buffer from safe module.
 * @param out[out] : the buffer to store random bytes.
 * @param nDataLength[in] : the length of the buffer.
 * return value >= 0 : success (suggest 0)
 *                <0 : error code
 */
typedef int (*HSM_OSM_RANDOM)(unsigned char *out, size_t nDataLength);

/**
 * Request security module to generate a key pair inside the module.
 * @param pKeyId[in] : the alias of the private key.
 * @param nAlgorithm[in] : the algorithm of the key pair. Currently, only ALGORITHM_RSA is supported.
 * @param nKeySize[in] : the bit size of the key. Currently, only 2048 is supported.
 * return value >= 0 : success (suggest 0)
 *               < 0 : error code
 */
//typedef int (*HSM_OSM_GEN_KEYPAIR)(unsigned char* pKeyId, unsigned int nAlgorithm, unsigned int nKeySize);

/**
 * Remove the key pair of the given alias.
 * @param pKeyId[in] : the alias of the private key.
 * return value >=0 : success (suggest 0)
 *               <0 : error code
 */
//typedef int (*HSM_OSM_DEL_KEYPAIR)(unsigned char* pKeyId);

/**
 * Generate the CSR for given private key.
 * @param pKeyId[in] : the alias of the private key
 * @param pCsrName[in] : the commonName of the CSR
 * @param pBufOut[out] : the buffer to store the CSR data.
 * @param nDataMaxLength[in] : the max length of the result buffer.
 * return value >=0 : success, with the valid length of the bufResult.
 *               <0 : error code
 */
//typedef int (*HSM_OSM_GENCSR)(unsigned char* pKeyId, unsigned char* pCsrName, unsigned char* pBufOut, int nDataMaxLength);

/**
 * Do encryption by the given private key. The result data is in PKCS#1 padding format.
 * This method requires SAFE_MODULE permission.
 * @param pKeyId[in] : the alias of the given private key.
 * @param pBufIn[in] : the buffer of the plain data. 
 * @param pBufOut[out] : the buffer for the output cipher data.
 * @param nDataMaxLength[in] : the max length of the output buffer.
 * return value >=0 : encrypt success and return the length of the bufResult.
 *               <0 : error code.
 */
//typedef int (*HSM_OSM_RSA_ENCRYPT)(unsigned char* pKeyId, unsigned char* pBufIn, unsigned char* pBufOut, int nDataMaxLength);

/**
 * Check the security module is tampered or not. If the security module is tampered, 
 * all data in the security module should not be trusted.
 * return value == 0 : Not tampered
 *              == 1 : Tampered
 */
//typedef int (*HSM_IS_TAMPERED)(void);

typedef int (*HSM_OSM_GET_VER)(unsigned char *nVersion);

typedef int (*HSM_STORE_3DESKEY)(unsigned char *des_key, unsigned int keylen, unsigned int cryptflag, unsigned int id);

typedef int (*HSM_COMPUTE_3DES)(unsigned char *desdata, unsigned int datalen, unsigned char *out, unsigned int cryptflag, unsigned int id);

/////////////////////////////////////////////// CPOS SECURITY INTERFACE //////////////////////
typedef struct 
{
    int certType;      //type
    char szAlias[256]; //alias
}HSM_CERT_INFO;

/**
 * <br>incert root cert to security module</br>
 * @param certType[in]: cert type
 * @param bufCert:cert data
 * @param certLen: cert datalength
 * @param alias:cert alias,end by '\0'
 * @param formart: cert format (PEM/DER)
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_INJECT_ROOT_CERT)(int certType, unsigned char* bufCert, int certLen, char* alias, int format);


/**
 * <br>Inject the certificate of the existing key pair
 * @param bufCert: cert data
 * @param certLen: cert datalength
 * @param alias: cert alias,end by '\0'
 * @param aliasPrivateKey: Private pair alias,end by '\0'
 * @param formart: cert format (PEM/DER)
 * @return >= 0 success < 0  fail
 */
 typedef int (*HSM_INJECT_PUBLICKEY_CERT)(unsigned char* bufCert, int certLen, char* alias, char* aliasPrivateKey, int format);

 
/**
 * <br>load cert
 * @param bufCert: cert data
 * @param certLen: cert datalength
 * @param alias: cert alias,end by '\0'
 * @param aliasPrivateKey: Private pair alias,end by '\0'
 * @param formart: cert format (PEM/DER)
 * @return >= 0 success < 0  fail
 */
 typedef int (*HSM_GET_CERT)(unsigned char* bufCert, int* certLen, int certType, char* alias, int format);


/**
 * <br>delete cert
 * @param certType: cert type
 * @param alias: cert alias,end by '\0'
 * @return >= 0 success < 0  fail
 * Notice: do not support for now
 */
typedef int (*HSM_DELET_CERT)(int certType, char* alias);


/**
 * <br>delete key pair
 * @param aliasPrivateKey: key pair cert alias,end by '\0'
 * @return >= 0 success < 0  fail
 * Notice: do not support for now
 */
typedef int (*HSM_DELET_KEY_PAIR)(char* aliasPrivateKey);


/**
 * <br> generate key  pair
 * @param alias: key pair alias
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_GENERATE_KEY_PAIR)(char* alias);


/**
 * <br>Generate the CSR(Certificate Signing Request  for given private key
 * @param bufCSR: <out> CSR buf
 * @param csrLen: <in> bufCSR size  <out> 
 * @param aliasPrivateKey: PrivateKey alias
 * @param commonName: the DN of the commonName 
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_GENERATE_CSR)(unsigned char* bufCSR, int* csrLen, char* aliasPrivateKey, char* commName);


/**
 * <br>RSA encrypt
 * @param bufCipher: <out> encrypt data
 * @param cipherLen: <out> encrypt data length
 * @param aliasPrivateKey: PrivateKey pairs alias
 * @param bufPlain: buf
 * @param plainLen: buf length
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_RSA_ENCRYPT)(unsigned char* bufCipher, int* cipherLen, char* aliasPrivateKey, unsigned char* bufPlain, int plainLen);


/**
 * <br>RSA decrypt
 * @param bufPlain: <out> buf
 * @param plainLen: <out> bufPlain size  <
 * @param aliasPrivateKey: PrivateKey pairs alias
 * @param bufCipher:encrypt data
 * @param cipherLen: encrypt data length
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_RSA_DECRYPT)(unsigned char* bufPlain, int* plainLen, char* aliasPrivateKey, unsigned char* bufCipher, int cipherLen);


/**
 * Get the real random buffer from safe module.
 * @param out[out] : the buffer to store random bytes.
 * @param nDataLength[in] : the length of the buffer.
 * return value >= 0 : success (suggest 0) <0 : error code
 */
typedef int (*HSM_GET_RANDOM)(unsigned char* bufRandom, int dataLen);


/**
 * <br>query private key alias 
 * @param bufAlias: <out> private key alias 
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_QUERY_PRIVATEKEY_LABELS)(unsigned char* bufAlias);


/**
 * <br>query specify type cert alias
 * @param bufAlias: <out> :cert alias
 * @param certType:cert type
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_QUERY_CERT_LABELS)(unsigned char* bufAlias, int certType);

/**
 * <br>query specify type cert type
 * @param alias: cert alias
 * @return >= 0 cert type  < 0  fail
 */
typedef int (*HSM_QUERY_CERT_TYPE)(char* alias);

/**
 * <br>query private key count
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_QUERY_PRIVATE_KEY_COUNT)();


/**
 * <br>query specify type cert count
 * @param certType : cert type  OWNER(1),PUBLIC_KEY(2),APP_ROOT(3),COMMUNICATE(4)
 * @return >= 0 success < 0  fail
 */
typedef int (*HSM_QUERY_CERT_COUNT)(int certType);

/**
* <br> Enumeration cert information 
* @param lstCertsInfo : HSM_CERT_INFO
* @param iLstSize: buf length 
* @ return: >=0 cert count  < 0 error code.
*/
typedef int (*HSM_OSM_ENUMERATE_CERTS)(HSM_CERT_INFO* lstCertsInfo, int iLstSize);


#ifdef __cplusplus
}
#endif

#endif /* HSM_INTERFACE_H_ */
