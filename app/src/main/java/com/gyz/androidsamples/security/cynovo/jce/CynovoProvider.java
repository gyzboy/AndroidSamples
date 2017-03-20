package com.gyz.androidsamples.security.cynovo.jce;

//import java.io.IOException;
import java.security.Provider;
import java.security.AccessController;
import java.security.PrivilegedAction;


public final class CynovoProvider extends Provider {

	private static final long serialVersionUID = 1L;

	private static final String INFO = "Cynovo JCE Provider";

	public static final String NAME = "Cynovo";
	
	public CynovoProvider(){
		super(NAME, 0.1, INFO);
	
	    AccessController.doPrivileged(new PrivilegedAction<Object>() {
	        @Override
	        public Object run() {
	            put("KeyStore.CERT", "com.cynovo.jce.CynovoKeyStoreImpl");
	            put("Alg.Alias.KeyStore.UBER", "Cynovo");

                return null;
	        }
	      });
	}
}
