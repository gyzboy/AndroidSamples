package com.gyz.androidsamples.security.xidingjce.jni;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import android.util.Log;

public class HsmInterface {
    static {
        System.loadLibrary("XidingJce");
    }
	
	private native int hsm_open();
	private native int hsm_close();
	private native int hsm_get_cert(byte[] bufCert, int certType, String alias, int dataFormat);
	private native int query_cert_type(String alias);
	
	public HsmInterface(){
		//Do nothing.
	}
	
	public int Open(){
		return hsm_open();
	}
	
	public int Close(){
		return hsm_close();
	}
	
	public Certificate GetCert(String alias) throws CertificateException{
		int certType = query_cert_type(alias);
		if(certType < 0){
			Log.i("HSM", "query_cert_type FAIL");
			return null;
		}

		byte [] buf = new byte[10240];
		
		int ret = hsm_get_cert(buf, certType, alias, 0);
		if(ret <= 0){
			Log.i("HSM", "hsm_get_cert FAIL");
			return null;
		}

		InputStream isData = new ByteArrayInputStream(buf); 

		CertificateFactory certFac = CertificateFactory.getInstance("X.509");
		Certificate objCert = certFac.generateCertificate(isData);

		return objCert;
	}
}
