package pkg03des;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class CifradorDES {

    public static void cifrarArchivo(String rutaArchivo) throws Exception {
        // 1. Generar la clave
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        generadorDES.init(56);
        SecretKey clave = generadorDES.generateKey();

        // Guardar la clave en base64 en un archivo
        String claveBase64 = Base64.getEncoder().encodeToString(clave.getEncoded());
        FileWriter fw = new FileWriter("clave.key");
        fw.write(claveBase64);
        fw.close();

        // 2. Cifrar el archivo
        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, clave);

        FileInputStream entrada = new FileInputStream(rutaArchivo);
        FileOutputStream salida = new FileOutputStream(rutaArchivo + ".cifrado");

        byte[] buffer = new byte[1000];
        byte[] buffercifrado;
        int bytesleidos = entrada.read(buffer);

        while (bytesleidos != -1) {
            buffercifrado = cifrador.update(buffer, 0, bytesleidos);
            if (buffercifrado != null)
                salida.write(buffercifrado);
            bytesleidos = entrada.read(buffer);
        }

        buffercifrado = cifrador.doFinal();
        if (buffercifrado != null)
            salida.write(buffercifrado);

        entrada.close();
        salida.close();
    }

    public static void descifrarArchivo(String rutaArchivoCifrado) throws Exception {
        // 1. Cargar la clave
        String claveLeidaBase64 = new String(Files.readAllBytes(Paths.get("clave.key")));
        byte[] claveBytes = Base64.getDecoder().decode(claveLeidaBase64);
        SecretKey claveLeida = new SecretKeySpec(claveBytes, "DES");

        // 2. Descifrar el archivo
        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cifrador.init(Cipher.DECRYPT_MODE, claveLeida);

        FileInputStream entrada = new FileInputStream(rutaArchivoCifrado);
        FileOutputStream salida = new FileOutputStream(rutaArchivoCifrado + ".descifrado");

        byte[] buffer = new byte[1000];
        byte[] bufferdescifrado;
        int bytesleidos = entrada.read(buffer);

        while (bytesleidos != -1) {
            bufferdescifrado = cifrador.update(buffer, 0, bytesleidos);
            if (bufferdescifrado != null)
                salida.write(bufferdescifrado);
            bytesleidos = entrada.read(buffer);
        }

        bufferdescifrado = cifrador.doFinal();
        if (bufferdescifrado != null)
            salida.write(bufferdescifrado);

        entrada.close();
        salida.close();
    }
}
