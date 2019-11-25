package com.apachescribe.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Base64;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.security.Signature;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.security.cert.X509Certificate;

public class PKI {

    // get digital signature
    public String getDigitalSignature(String string) throws Exception {

        String digitalSignature = null;

        byte[] data = string.getBytes("UTF8");

        // load keystore
        char[] keyPassword = "changeit".toCharArray();

        KeyStore keyStore = KeyStore.getInstance("JKS");

        InputStream keyStoreData = new FileInputStream("/home/system/apps/java/UmemeIntegration/app/keystore.jks");
        keyStore.load(keyStoreData, keyPassword);

        Key key = keyStore.getKey("localhost", keyPassword);

        Certificate cert = keyStore.getCertificate("localhost");
        PublicKey publicKey = cert.getPublicKey();
        KeyPair keyPair = new KeyPair(publicKey, (PrivateKey) key);
        Signature sig = Signature.getInstance("SHA256withRSA");

        sig.initSign(keyPair.getPrivate());
        sig.update(data);
        byte[] signatureBytes = sig.sign();

        sig.initVerify(keyPair.getPublic());
        sig.update(data);

        if (sig.verify(signatureBytes)) {
            digitalSignature = Base64.getEncoder().encodeToString(signatureBytes);
        }

        return digitalSignature;
    }

    // disable ssl verification
    public static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            } };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

}