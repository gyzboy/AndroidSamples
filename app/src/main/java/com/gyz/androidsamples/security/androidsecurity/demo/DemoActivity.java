package com.gyz.androidsamples.security.androidsecurity.demo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AlgorithmParameters;
import java.security.DigestInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Process;
import android.security.KeyChain;
import android.security.KeyChainAliasCallback;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.gyz.androidsamples.R;
import com.gyz.androidsamples.security.cynovo.jce.CynovoProvider;

import static android.util.Log.e;

public class DemoActivity extends Activity {

    public static final String TAG = "ASDemo";
    private static final String ENTRY_ALIAS = "My Key Chain";
    private static final String ENTRY_PASSWORD = "changeit";

    private static final String KEYCHAIN_FILE = "test-keychain.p12";
    private static final String PEM_FILE = "my-root-cert.pem";

    
    private static final String PROPERTY_FILE = "property";
    private static final String PROPERTY_KEY = "keyimported";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        
        //Add by George.
        Security.insertProviderAt(new CynovoProvider(), 1);

        initUI(new int[] { R.id.testProviderBtn, R.id.testKeyBtn,
                R.id.testCertificateBtn, R.id.testKeyStoreBtn, R.id.testMDBtn,
                R.id.testSignatureBtn, R.id.testCipherBtn, R.id.testImport,
                R.id.testJSSEBtn2, });
    }

    void initUI(int[] ids) {
        for (int id : ids) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    switch (id) {
                    case R.id.testProviderBtn:
                        testProvider();
                        break;
                    case R.id.testKeyBtn:
                        testKey();
                        break;
                    case R.id.testCertificateBtn:
                        testCertificate();
                        break;
                    case R.id.testKeyStoreBtn:
                        testKeyStore();
                        break;
                    case R.id.testMDBtn:
                        testMessageDigest();
                        break;
                    case R.id.testSignatureBtn:
                        testSignature();
                        break;
                    case R.id.testCipherBtn:
                        testCipher();
                        break;
                    case R.id.testImport:
                        testImport();
                        break;
                    case R.id.testJSSEBtn2:
                        testJSSE();
                        break;
                    default:
                        break;
                    }
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    String bytesToHexString(byte[] input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte one : input) {
            sb.append(String.format("%x", one));
        }
        return sb.toString();
    }
    boolean isOurKeyChainAvailabe() {
        SharedPreferences sharedPreferences = getSharedPreferences(PROPERTY_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PROPERTY_KEY, false);
    }

    private void startClient() {
        Thread clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    e(TAG, "==>prepare client truststore");
                    SocketFactory socketFactory = null;
                    if (isOurKeyChainAvailabe()) {
                        e(TAG, "we have installed key in the system");
                        socketFactory = SSLSocketFactory.getDefault();
                    } else {
                        e(TAG, "prepare truststore manually");
                        AssetManager assetManager = DemoActivity.this
                                .getAssets();
                        InputStream keyInputStream = assetManager
                                .open(KEYCHAIN_FILE);
                        TrustManagerFactory tmf = TrustManagerFactory
                                .getInstance(TrustManagerFactory
                                        .getDefaultAlgorithm());
                        KeyStore keyStore = KeyStore.getInstance("PKCS12");
                        keyStore.load(keyInputStream,
                                ENTRY_PASSWORD.toCharArray());
                        keyInputStream.close();
                        tmf.init(keyStore);

                        SSLContext sslContext = SSLContext.getInstance("TLS");
                        sslContext.init(null, tmf.getTrustManagers(), null);
                        socketFactory = sslContext.getSocketFactory();
                    }

                    e(TAG, "==>start client:");

                    InetAddress serverAddr = Inet4Address.getLocalHost();
                    Socket mySocket = socketFactory.createSocket(serverAddr,
                            1500);
                    OutputStream outputStream = mySocket.getOutputStream();
                    String data = "I am client";
                    e(TAG, "==>Client sent:" + data);
                    outputStream.write(data.getBytes());
                    mySocket.close();

                } catch (Exception e) {
                    e(TAG, " " + e.getMessage());
                }
                e(TAG, "==>client quit");
            }
        });
        clientThread.start();
    }

    private void startServer() {
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    e(TAG, "==>prepare keystore for server:");
                    ServerSocketFactory serverSocketFactory = null;

                    AssetManager assetManager = DemoActivity.this.getAssets();
                    InputStream keyInputStream = assetManager
                            .open(KEYCHAIN_FILE);
                    KeyStore serverKeyStore = KeyStore.getInstance("PKCS12");
                    serverKeyStore.load(keyInputStream,
                            ENTRY_PASSWORD.toCharArray());
                    keyInputStream.close();
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    KeyManagerFactory keyManagerFactory = KeyManagerFactory
                            .getInstance(KeyManagerFactory
                                    .getDefaultAlgorithm());
                    keyManagerFactory.init(serverKeyStore,
                            ENTRY_PASSWORD.toCharArray());

                    sslContext.init(keyManagerFactory.getKeyManagers(), null,
                            null);
                    e(TAG, "==>start server:");
                    serverSocketFactory = sslContext.getServerSocketFactory();

                    String[] supportedCiphers = ((SSLServerSocketFactory) serverSocketFactory)
                            .getSupportedCipherSuites();
                    e(TAG,
                            "==>supported cihpers:"
                                    + Arrays.toString(supportedCiphers));

                    InetAddress listenAddr = Inet4Address.getLocalHost();
                    ServerSocket serverSocket = serverSocketFactory
                            .createServerSocket(1500, 5, listenAddr);

                    startClient();

                    Socket clientSock = serverSocket.accept();
                    InputStream inputStream = clientSock.getInputStream();
                    byte[] readBuffer = new byte[1024];
                    int nread = inputStream.read(readBuffer);
                    e(TAG, "==>echo from Client:"
                            + new String(readBuffer, 0, nread));
                    clientSock.close();
                    serverSocket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                e(TAG, "==>server quit");
                e(TAG, "***End Test JSSE***");
            }
        });
        serverThread.start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(DemoActivity.this, "Key File Imported", Toast.LENGTH_SHORT).show();
        KeyChain.choosePrivateKeyAlias(this,
                new KeyChainAliasCallback() {
                    @Override
                    public void alias(String alias) {
                            Toast.makeText(DemoActivity.this, "Key access granted!", Toast.LENGTH_SHORT)
                            .show();
                            test();
                    }
                }, new String[] {}, null, "localhost", -1,ENTRY_ALIAS);
        super.onActivityResult(requestCode, resultCode, data);
    }

    void importPKCS12() {
        try {
            e(TAG, "Install Our Key chain");
            BufferedInputStream bis = new BufferedInputStream(getAssets().open(
                    KEYCHAIN_FILE));
            byte[] keychain = new byte[bis.available()];
            bis.read(keychain);

            Intent installIntent = KeyChain.createInstallIntent();
            installIntent.putExtra(KeyChain.EXTRA_PKCS12, keychain);
            installIntent.putExtra(KeyChain.EXTRA_NAME, ENTRY_ALIAS);
            startActivityForResult(installIntent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void test() {
        try {
            PrivateKey privateKey = KeyChain.getPrivateKey(DemoActivity.this,
                    ENTRY_ALIAS);
            e(TAG, "==> " + privateKey.getClass().getSimpleName());
            e(TAG, "==>Private key Alg:" + privateKey.getAlgorithm());
            X509Certificate[] certs = KeyChain.getCertificateChain(
                    DemoActivity.this,ENTRY_ALIAS);
            Boolean bKeyCorrect = testKeyCorrectByCipher(privateKey, getCertFromKeyChainFile());
            e(TAG, "==>is key match chain from p12 file ? " + bKeyCorrect);
            bKeyCorrect = testKeyCorrectBySignature(privateKey, certs[0]);
            e(TAG, "==>is key match chain from System ? " + bKeyCorrect);
            SharedPreferences property = getSharedPreferences("property",
                    Context.MODE_PRIVATE);
            if (bKeyCorrect && isOurKeyChainAvailabe() == false) {
                property.edit().putBoolean(PROPERTY_KEY, true).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void testImport() {
        e(TAG, "***Begin Test Import***");
        try {
            boolean needGetKeyChain = isOurKeyChainAvailabe() == false;
            if (needGetKeyChain) {
                importPKCS12();
            } else {
                new Thread(new Runnable() {
                    
                    @Override
                    public void run() {
                        test();
                        
                    }
                }).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        e(TAG, "***End Test Import***");
    }

    boolean testKeyCorrectBySignature(PrivateKey privateKey, Certificate cert) {
        try {
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            byte[] data = new byte[1024];
            int nread = 0;
            InputStream inputStreamToBeSigned = getAssets().open(PEM_FILE);
            while ((nread = inputStreamToBeSigned.read(data)) > 0) {
                signature.update(data, 0, nread);
            }
            byte[] sig = signature.sign();
            signature = null;
            inputStreamToBeSigned.close();
            inputStreamToBeSigned = getAssets().open(PEM_FILE);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(cert.getPublicKey());
            data = new byte[1024];
            nread = 0;
            while ((nread = inputStreamToBeSigned.read(data)) > 0) {
                signature.update(data, 0, nread);
            }
            boolean isSigCorrect = signature.verify(sig);
            inputStreamToBeSigned.close();
            return isSigCorrect;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean testKeyCorrectByCipher(PrivateKey privateKey, Certificate cert) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, cert.getPublicKey());
            String data = "test key is correct";
            byte[] encryped = cipher.doFinal(data.getBytes());

            Cipher cipher2 = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher2.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] x = cipher2.update(encryped);
            byte[] y = cipher2.doFinal();
            byte[] decryped = concateTwoBuffers(x, y);
            String data2 = new String(decryped);
            return data2.equals(data);
        } catch (Exception e) {
            e(TAG, " " + e.getMessage());
        }
        return false;
    }

    void testJSSE() {
        e(TAG, "***Begin Test JSSE***");
        Thread checkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        });
        checkThread.start();
    }

    void testCipher() {
        e(TAG, "***Begin test Cipher***");
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey key = keyGenerator.generateKey();

            String data = "This is our data";
            e(TAG, "==>Raw Data : " + data);
            e(TAG, "==>Raw Data in hex: " + bytesToHexString(data.getBytes()));
            Cipher encryptor = Cipher.getInstance("DES/CBC/PKCS5Padding");
            encryptor.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = encryptor.update(data.getBytes());
            byte[] encryptedData1 = encryptor.doFinal();
            byte[] finalEncrpytedData = concateTwoBuffers(encryptedData,
                    encryptedData1);
            e(TAG, "==>Encrypted Data : "
                    + bytesToHexString(finalEncrpytedData));

            byte[] iv = encryptor.getIV();
            e(TAG, "==>Initial Vector of Encryptor: " + bytesToHexString(iv));

            Cipher decryptor = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            decryptor.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

            byte[] decryptedData = decryptor.update(finalEncrpytedData);
            byte[] decryptedData1 = decryptor.doFinal();
            byte[] finaldecrpytedData = concateTwoBuffers(decryptedData,
                    decryptedData1);
            e(TAG, "==>Decrypted Data  in hex: "
                    + bytesToHexString(finaldecrpytedData));
            e(TAG, "==>Decrypted Data  : " + new String(finaldecrpytedData));

        } catch (Exception e) {
            e.printStackTrace();
        }

        e(TAG, "***End test Cipher***");
    }

    byte[] concateTwoBuffers(byte[] src1, byte[] src2) {
        int totalLen = (src1 != null ? src1.length : 0)
                + (src2 != null ? src2.length : 0);

        byte[] returnBuffer = new byte[totalLen];

        if (src1 != null)
            System.arraycopy(src1, 0, returnBuffer, 0, src1.length);

        if (src2 != null)
            System.arraycopy(src2, 0, returnBuffer, (src1 != null ? src1.length
                    : 0), src2.length);

        return returnBuffer;
    }

    void testSignature() {
        e(TAG, "***Begin test Signature***");
        try {
            AssetManager assetManager = this.getAssets();
            InputStream inputStream = assetManager.open(KEYCHAIN_FILE);

            KeyStore myKeyStore = KeyStore.getInstance("PKCS12");
            myKeyStore.load(inputStream, ENTRY_PASSWORD.toCharArray());

            Certificate cert = myKeyStore.getCertificate(ENTRY_ALIAS);
            PublicKey publicKey = cert.getPublicKey();

            PrivateKey privateKey = (PrivateKey) myKeyStore.getKey(ENTRY_ALIAS,
                    ENTRY_PASSWORD.toCharArray());
            inputStream.close();

            e(TAG, "==>start sign of file : " + PEM_FILE);
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            byte[] data = new byte[1024];
            int nread = 0;
            InputStream inputStreamToBeSigned = assetManager.open(PEM_FILE);
            while ((nread = inputStreamToBeSigned.read(data)) > 0) {
                signature.update(data, 0, nread);
            }
            byte[] sig = signature.sign();
            e(TAG, "==>Signed Signautre:" + bytesToHexString(sig));
            signature = null;

            inputStreamToBeSigned.close();
            e(TAG, "==>start verfiy of file : " + PEM_FILE);
            inputStreamToBeSigned = assetManager.open(PEM_FILE);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            data = new byte[1024];
            nread = 0;
            while ((nread = inputStreamToBeSigned.read(data)) > 0) {
                signature.update(data, 0, nread);
            }
            boolean isSigCorrect = signature.verify(sig);
            e(TAG, "==>Is Signature Correct :" + isSigCorrect);

            inputStreamToBeSigned.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        e(TAG, "***End test Signature***");
    }

    void testMessageDigest() {
        e(TAG, "***Begin test MessageDigest***");
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            String data = "This is a message:)";
            e(TAG, "==>Message is:" + data);
            messageDigest.update(data.getBytes());

            byte[] mdValue = messageDigest.digest();
            e(TAG, "==>MD Value is:" + bytesToHexString(mdValue));

            messageDigest.reset();

            AssetManager assetManager = this.getAssets();
            InputStream inputStream = assetManager.open(KEYCHAIN_FILE);
            e(TAG, "==>Message is a file:" + KEYCHAIN_FILE);
            DigestInputStream digestInputStream = new DigestInputStream(
                    inputStream, messageDigest);
            byte[] buffer = new byte[1024];
            while (digestInputStream.read(buffer) > 0) {
            }
            mdValue = messageDigest.digest();
            e(TAG, "==>MD Value is:" + bytesToHexString(mdValue));

            digestInputStream.close();
            inputStream.close();

            e(TAG, "==>Calcualte MAC");
            Mac myMac = Mac.getInstance("HmacSHA1");
            inputStream = assetManager.open(PEM_FILE);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(64);
            SecretKey key = keyGenerator.generateKey();
            myMac.init(key);

            buffer = new byte[1024];
            int nread = 0;
            while ((nread = inputStream.read(buffer)) > 0) {
                myMac.update(buffer, 0, nread);
            }
            byte[] macValue = myMac.doFinal();
            e(TAG, "==>MAC Value is:" + bytesToHexString(macValue));

            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        e(TAG, "***End test MessageDigest***");
    }

    PrivateKey getPrivateKeyFromKeyChainFile() {
        try {
            AssetManager assetManager = this.getAssets();
            InputStream inputStream = assetManager.open(KEYCHAIN_FILE);
            KeyStore myKeyStore = KeyStore.getInstance("PKCS12");
            myKeyStore.load(inputStream, ENTRY_PASSWORD.toCharArray());
            inputStream.close();
            Key myKey = myKeyStore.getKey(ENTRY_ALIAS,
                    ENTRY_PASSWORD.toCharArray());
            return (PrivateKey) myKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Certificate getCertFromKeyChainFile() {
        try {
            AssetManager assetManager = this.getAssets();
            InputStream inputStream = assetManager.open(KEYCHAIN_FILE);
            KeyStore myKeyStore = KeyStore.getInstance("PKCS12");
            myKeyStore.load(inputStream, ENTRY_PASSWORD.toCharArray());
            inputStream.close();
            Certificate cert = myKeyStore.getCertificate(ENTRY_ALIAS);
            return cert;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void testKeyStore() {
        e(TAG, "***Begin test KeyStore***");
    	
        KeyStore myKeyStore;
		try {
			e(TAG, "==== getInstance ====");
			Provider provider = Security.getProvider("Cynovo");
			myKeyStore = KeyStore.getInstance("CERT", provider);
			
			e(TAG, "==== initial ====");
			try {
				myKeyStore.load(null,"ddddd".toCharArray());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CertificateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e(TAG, "==== enumerate aliases ====");
			Enumeration<String> aliasEnum = myKeyStore.aliases();
			
			e(TAG, "==== get Certificate ====");
			Certificate appCert = myKeyStore.getCertificate("cloudpos_app_dev_root");
			
            X509Certificate myX509Cer = (X509Certificate)appCert;
			
            e(TAG, "==>Subjecte DN:" + myX509Cer.getSubjectDN().getName());
            e(TAG, "==>Issuer DN:" + myX509Cer.getIssuerDN().getName());
            e(TAG, "==>Public Key:" + bytesToHexString(myX509Cer.getPublicKey().getEncoded()));
			
			e(TAG, appCert.toString());

		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
                
//        try {
//            AssetManager assetManager = this.getAssets();
//            InputStream inputStream = assetManager.open(KEYCHAIN_FILE);
//            KeyStore myKeyStore = KeyStore.getInstance("PKCS12");
//            myKeyStore.load(inputStream, ENTRY_PASSWORD.toCharArray());
//            Enumeration<String> aliasEnum = myKeyStore.aliases();
//            while (aliasEnum.hasMoreElements()) {
//                String alias = aliasEnum.nextElement();
//                Certificate[] certificates = myKeyStore
//                        .getCertificateChain(alias);
//                boolean bCE = myKeyStore.isCertificateEntry(alias);
//                boolean bKE = myKeyStore.isKeyEntry(alias);
//                e(TAG, "==>Alias:" + alias + " is CE:" + bCE + " is KE:" + bKE);
//                for (Certificate cert : certificates) {
//                    X509Certificate myCert = (X509Certificate) cert;
//                    e(TAG, "==>I am a certificate:");
//                    e(TAG, "==>Subjecte DN:" + myCert.getSubjectDN().getName());
//                    e(TAG, "==>Issuer DN:" + myCert.getIssuerDN().getName());
//                    e(TAG, "==>Public Key:"
//                            + bytesToHexString(myCert.getPublicKey()
//                                    .getEncoded()));
//                }
//
//                Key myKey = myKeyStore.getKey(alias,
//                        ENTRY_PASSWORD.toCharArray());
//                if (myKey instanceof PrivateKey) {
//                    e(TAG,
//                            "==>I am a private key:"
//                                    + bytesToHexString(myKey.getEncoded()));
//                } else if (myKey instanceof SecretKey) {
//                    if (myKey instanceof PrivateKey) {
//                        e(TAG,
//                                "==>I am a secret key:"
//                                        + bytesToHexString(myKey.getEncoded()));
//                    }
//                }
//
//                testKeyCorrectBySignature((PrivateKey) myKey, certificates[0]);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        e(TAG, "***End test KeyStore***");
    }

    void testCertificate() {
        e(TAG, "***Begin test Certificates***");
        try {
            AssetManager assetManager = this.getAssets();
            InputStream inputStream = assetManager.open(PEM_FILE);

            CertificateFactory certificateFactory = CertificateFactory
                    .getInstance("X.509");
            X509Certificate myX509Cer = (X509Certificate) certificateFactory
                    .generateCertificate(inputStream);
            e(TAG, "==>Subjecte DN:" + myX509Cer.getSubjectDN().getName());
            e(TAG, "==>Issuer DN:" + myX509Cer.getIssuerDN().getName());
            e(TAG, "==>Public Key:"
                    + bytesToHexString(myX509Cer.getPublicKey().getEncoded()));
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        e(TAG, "***End test Certificates***");
    }

    void testKey() {
        e(TAG, "***Begin Test Keys***");
        try {
            {// secret key tests
                e(TAG, "==>secret key: generated it using DES");
                KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
                keyGenerator.init(64);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] keyData = secretKey.getEncoded();
                String keyInBase64 = Base64.encodeToString(keyData,
                        Base64.DEFAULT);
                e(TAG, "==>secret key: encrpted data = "
                        + bytesToHexString(keyData));
                e(TAG, "==>secrety key:base64code=" + keyInBase64);
                e(TAG, "==>secrety key:alg=" + secretKey.getAlgorithm());
                // Suppose you have send keyInBase64 to other people through
                // email...
                byte[] receivedKeyData = Base64.decode(keyInBase64,
                        Base64.DEFAULT);
                SecretKeySpec keySpec = new SecretKeySpec(receivedKeyData,
                        secretKey.getAlgorithm());
                SecretKeyFactory secretKeyFactory = SecretKeyFactory
                        .getInstance(secretKey.getAlgorithm());
                SecretKey receivedKeyObject = secretKeyFactory
                        .generateSecret(keySpec);
                byte[] encodedReceivedKeyData = receivedKeyObject.getEncoded();
                e(TAG, "==>secret key: received key encoded data = "
                        + bytesToHexString(encodedReceivedKeyData));
            }

            {// public/private key test
                e(TAG, "==>key pair: generated it using RSA");
                KeyPairGenerator keyPairGenerator = KeyPairGenerator
                        .getInstance("RSA");
                keyPairGenerator.initialize(1024);
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();

                e(TAG,
                        "==>publickey:"
                                + bytesToHexString(publicKey.getEncoded()));
                e(TAG,
                        "==>privatekey:"
                                + bytesToHexString(privateKey.getEncoded()));

                Class spec = Class
                        .forName("java.security.spec.RSAPublicKeySpec");
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                RSAPublicKeySpec rsaPublicKeySpec = (RSAPublicKeySpec) keyFactory
                        .getKeySpec(publicKey, spec);
                BigInteger modulus = rsaPublicKeySpec.getModulus();
                BigInteger exponent = rsaPublicKeySpec.getPublicExponent();

                e(TAG, "==>rsa pub key spec:modulus="
                        + bytesToHexString(modulus.toByteArray()));
                e(TAG, "==>rsa pub key spec:exponent="
                        + bytesToHexString(exponent.toByteArray()));

                // In case we receive modulus and exponent infomation about the
                // public key:
                byte[] modulusByteArry = modulus.toByteArray();
                byte[] exponentByteArry = exponent.toByteArray();
                RSAPublicKeySpec receivedKeySpec = new RSAPublicKeySpec(
                        new BigInteger(modulusByteArry), new BigInteger(
                                exponentByteArry));

                KeyFactory receivedKeyFactory = keyFactory.getInstance("RSA");
                PublicKey receivedPublicKey = receivedKeyFactory
                        .generatePublic(receivedKeySpec);
                e(TAG, "==>received pubkey:"
                        + bytesToHexString(receivedPublicKey.getEncoded()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        e(TAG, "***End Test Keys***\n\n");
    }

    void testProvider() {
        e(TAG, "***Begin Test Providers***");
        
        //Add by George 2015.8.7
        //Security.insertProviderAt(new com.xiding.jce.XidingKeyStore(),1);  //�ӵ���1��

        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            e(TAG, "Provider:" + provider + " info:");
            Set<Entry<Object, Object>> allKeyAndValues = provider.entrySet();
            Iterator<Entry<Object, Object>> iterator = allKeyAndValues
                    .iterator();
            while (iterator.hasNext()) {
                Entry<Object, Object> oneKeyAndValue = iterator.next();
                Object key = oneKeyAndValue.getKey();
                Object value = oneKeyAndValue.getValue();
                e(TAG, "===>" + "Key type=" + key.getClass().getSimpleName()
                        + " Key=" + key.toString());
                e(TAG,
                        "===>" + "Value type="
                                + value.getClass().getSimpleName() + " Value="
                                + value.toString());
            }
        }

        e(TAG, "***End Test Providers***");
    }

}
