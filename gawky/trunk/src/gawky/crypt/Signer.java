package gawky.crypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.apache.commons.lang.SystemUtils;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class Signer {

	public static void main(String[] args) throws Exception {
		
		// Parameter Only
		
		/*
		 * 

		http://pswdf009:1080/ContentServer/ContentServer.dll?get&pVersion
			=0045&contRep=K1&docId=361A524A3ECB5459E0000800099245EC&accessMod
			e=r&authId=pawdf054_BCE_26&expiration=19981104091537


ContRep = K1
DocId = 361A524A3ECB5459E0000800099245EC
AccessMode = r
AuthId = pawdf054_BCE_26
Expiration = 19981104091537


K1361A524A3ECB5459E0000800099245ECrpawdf054_BCE_2619981104091537

g3AhQg== -> g3AhQg%3D%3D


http://pswdf009:1080/ContentServer/ContentServer.dll?get&pVersion
=0045&contRep=K1&docId=361A524A3ECB5459E0000800099245EC&accessMod
e=r&authId=pawdf054_BCE_26&expiration=19981104091537&secKey=g3AhQ
g%3D%3D




		*/	
		
		String data = "K1361A524A3ECB5459E0000800099245ECrpawdf054_BCE_2619981104091537";
		
		System.out.println(new String(Base64.encode(new Signer().sign(data.getBytes()))));
	}
	
	
	  public KeyStore getKeystore(char[] password) throws GeneralSecurityException, IOException {
		    KeyStore keystore = KeyStore.getInstance("jks");
		    InputStream input = new FileInputStream(SystemUtils.USER_HOME + File.separator + ".keystore");
		    try {
		      keystore.load(input, password);
		    } catch (IOException e) {
		    } finally {
		      input.close();
		    }
		    return keystore;
		  }
	  
	
	public byte[] sign(byte[] data) throws Exception 
	{
	  Security.addProvider(new BouncyCastleProvider());
	  CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
	  generator.addSigner(getPrivateKey(), 
			              (X509Certificate) getCertificate(pass),
	                      CMSSignedDataGenerator.DIGEST_SHA1);
	  generator.addCertificatesAndCRLs(getCertStore());
	  CMSProcessable content = new CMSProcessableByteArray(data);

	  CMSSignedData signedData = generator.generate(content, false, "BC");
	  return signedData.getEncoded();
	}

	private CertStore getCertStore() throws Exception 
	{
	  KeyStore keystore = getKeystore(pass.toCharArray());
		
	  ArrayList<Certificate> list = new ArrayList<Certificate>();
	  Certificate[] certificates = keystore.getCertificateChain(this.alias);
	  for (int i = 0, length = certificates == null ? 0 : certificates.length; i < length; i++) {
	    list.add(certificates[i]);
	  }
	  return CertStore.getInstance("Collection", new CollectionCertStoreParameters(list), "BC");
	}
		    
	
	PrivateKey privateKey;
	String alias = "SAP";
	String pass = "login96";
	
	//KeyStore keystore;
	
	private PrivateKey getPrivateKey() throws Exception 
	{
	  if (this.privateKey == null) {
	     this.privateKey = initalizePrivateKey();
	  }
	  return this.privateKey;
	}

	private PrivateKey initalizePrivateKey() throws Exception 
	{
	   KeyStore keystore = getKeystore(pass.toCharArray());
	   return (PrivateKey) keystore.getKey(this.alias, pass.toCharArray());
	}
	
	private Certificate getCertificate(String pass) throws Exception 
	{
	   KeyStore keystore = getKeystore(pass.toCharArray());
	   return keystore.getCertificate(this.alias);
	}
}
