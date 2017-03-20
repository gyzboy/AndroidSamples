package com.gyz.androidsamples.security.cynovo.jce;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;


import android.util.Log;

import com.gyz.androidsamples.security.xidingjce.jni.HsmInterface;

public class CynovoKeyStoreImpl extends KeyStoreSpi {
	
    private HsmInterface hsm;

	@Override
	public Key engineGetKey(String alias, char[] password)
			throws NoSuchAlgorithmException, UnrecoverableKeyException {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineGetKey");
		return null;
	}

	@Override
	public Certificate[] engineGetCertificateChain(String alias) {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineGetCertificateChain");
		return null;
	}

	@Override
	public Certificate engineGetCertificate(String alias) {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineGetCertificate");
		
		try {
			return hsm.GetCert(alias);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Date engineGetCreationDate(String alias) {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineGetCreationDate");
		return null;
	}

	@Override
	public void engineSetKeyEntry(String alias, Key key, char[] password,
			Certificate[] chain) throws KeyStoreException {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineSetKeyEntry");
	}

	@Override
	public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain)
			throws KeyStoreException {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineSetKeyEntry");
	}

	@Override
	public void engineSetCertificateEntry(String alias, Certificate cert)
			throws KeyStoreException {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineSetCertificateEntry");
	}

	@Override
	public void engineDeleteEntry(String alias) throws KeyStoreException {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineDeleteEntry");
	}

	@Override
	public Enumeration<String> engineAliases() {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineAliases");
		return null;
	}

	@Override
	public boolean engineContainsAlias(String alias) {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineContainsAlias");
		return false;
	}

	@Override
	public int engineSize() {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineSize");
		return 0;
	}

	@Override
	public boolean engineIsKeyEntry(String alias) {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineIsKeyEntry");
		return false;
	}

	@Override
	public boolean engineIsCertificateEntry(String alias) {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineIsCertificateEntry");
		return false;
	}

	@Override
	public String engineGetCertificateAlias(Certificate cert) {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineGetCertificateAlias");
		return null;
	}

	@Override
	public void engineStore(OutputStream stream, char[] password)
			throws IOException, NoSuchAlgorithmException, CertificateException {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineStore");
	}

	@Override
	public void engineLoad(InputStream stream, char[] password)
			throws IOException, NoSuchAlgorithmException, CertificateException {
		// TODO Auto-generated method stub
		Log.i("XDKS", "called engineLoad");
		hsm = new HsmInterface();
		hsm.Open();
	}
}
