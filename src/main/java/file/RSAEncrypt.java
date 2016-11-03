package file;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

public class RSAEncrypt {

	static KeyPairGenerator keyPairGen;

	static KeyPair keyPair;

	static RSAPrivateKey privateKey;

	static RSAPublicKey publicKey;
	
	static{
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(512);
			keyPair = keyPairGen.generateKeyPair();
			// Generate keys
			privateKey = (RSAPrivateKey) keyPair.getPrivate();
			publicKey = (RSAPublicKey) keyPair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		RSAEncrypt encrypt = new RSAEncrypt();
		File file = new File(
				"D:\\test\\a.txt");
		File newFile = new File(
				"D:\\test\\sdf1.txt");
		encrypt.encryptFile(encrypt, file, newFile);
		File file1 = new File(
				"D:\\test\\sdf1.txt");
		File newFile1 = new File(
				"D:\\test\\sdf2.txt");
		encrypt.decryptFile(encrypt, file1, newFile1);
	}

	public void encryptFile(RSAEncrypt encrypt, File file, File newFile) {
		try {
			InputStream is = new FileInputStream(file);
			OutputStream os = new FileOutputStream(newFile);

			byte[] bytes = new byte[53];
			while (is.read(bytes) > 0) {
				byte[] e = encrypt.encrypt(RSAEncrypt.publicKey, bytes);
				bytes = new byte[53];
				os.write(e, 0, e.length);
			}
			os.close();
			is.close();
			System.out.println("write success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void decryptFile(RSAEncrypt encrypt, File file, File newFile) {
		try {
			InputStream is = new FileInputStream(file);
			OutputStream os = new FileOutputStream(newFile);
			byte[] bytes1 = new byte[64];
			while (is.read(bytes1) > 0) {
				byte[] de = encrypt.decrypt(RSAEncrypt.privateKey, bytes1);
				bytes1 = new byte[64];
				os.write(de, 0, de.length);
			}
			os.close();
			is.close();
			System.out.println("write success");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * * Encrypt String. *
	 * 
	 * @return byte[]
	 */
	protected byte[] encrypt(RSAPublicKey publicKey, byte[] obj) {
		if (publicKey != null) {
			try {
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				return cipher.doFinal(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** */
	/**
	 * * Basic decrypt method *
	 * 
	 * @return byte[]
	 */
	protected byte[] decrypt(RSAPrivateKey privateKey, byte[] obj) {
		if (privateKey != null) {
			try {
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				return cipher.doFinal(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}