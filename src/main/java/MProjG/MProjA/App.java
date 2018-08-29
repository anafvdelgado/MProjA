package MProjG.MProjA;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.utils.Utils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        //System.out.println( "Hello World!" );
        
        final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("1234567890123456"),"AES");
		final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));
		
		final String transform = "AES/CBC/PKCS5Padding";
		Properties properties = new Properties();
		properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.OPENSSL.getClassName());
		CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
		
		final String hello = "Hello Word!";
		
		System.out.println("String: " + hello);
		
		byte[] input = getUTF8Bytes(hello);
		byte[] output = new byte[32];
		
		encipher.init(Cipher.ENCRYPT_MODE, key, iv);
		int updateBytes = encipher.update(input, 0, input.length, output, 0);
		int finalBytes = encipher.doFinal(input, 0, 0, output, updateBytes);
		
		System.out.println(finalBytes);
		encipher.close();
		
		System.out.println(Arrays.toString(Arrays.copyOf(output, updateBytes+finalBytes)));
        
    }
    
    private static byte[] getUTF8Bytes(String input) {
		return input.getBytes(StandardCharsets.UTF_8);
		
	}
}
