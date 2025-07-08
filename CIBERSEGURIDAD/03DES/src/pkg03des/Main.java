
 package pkg03des;
 
 /**
  *
  * @author Alumno
  */
 //es para definir entradas y salidas del sistema
 //para el manejo de archivos
import javax.crypto.*;
import javax.crypto.spec.*;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    public static void main(String[] args) throws Exception {

        if(args.length != 1){
            mensajeAyuda();
            System.exit(1);
        }

        String nombreArchivo = args[0];

        try {
            FileReader archivo = new FileReader("hola.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String cadena;
            while((cadena = lector.readLine()) != null){
                System.out.println(cadena);
            }
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("1.- Generar las claves DES");
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        generadorDES.init(56);
        SecretKey clave = generadorDES.generateKey();

        System.out.println("La clave es: " + clave);
        mostrarBytes(clave.getEncoded());

        String claveBase64 = Base64.getEncoder().encodeToString(clave.getEncoded());
        FileWriter fw = new FileWriter("clave.key");
        fw.write(claveBase64);
        fw.close();

        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");

        System.out.println("2.- Cifrar un fichero con DES : " + nombreArchivo + " dejamos el resultado en: " + nombreArchivo + ".cifrado");
        cifrador.init(Cipher.ENCRYPT_MODE, clave);

        byte[] buffer = new byte[1000];
        byte[] buffercifrado;

        FileInputStream entrada = new FileInputStream(nombreArchivo);
        FileOutputStream salida = new FileOutputStream(nombreArchivo+".cifrado");

        int bytesleidos = entrada.read(buffer, 0, 1000);

        while(bytesleidos != -1){
            buffercifrado = cifrador.update(buffer, 0, bytesleidos);
            if (buffercifrado != null)
                salida.write(buffercifrado);
            bytesleidos = entrada.read(buffer, 0, 1000);
        }

        buffercifrado = cifrador.doFinal();
        if (buffercifrado != null)
            salida.write(buffercifrado);

        entrada.close();
        salida.close();

        System.out.println("3.- Descifrar un fichero con DES : " + nombreArchivo + ".cifrado " + nombreArchivo + ".descifrado");

        // Cargar la clave desde el archivo
        String claveLeidaBase64 = new String(Files.readAllBytes(Paths.get("clave.key")));
        byte[] claveBytes = Base64.getDecoder().decode(claveLeidaBase64);
        SecretKey claveLeida = new SecretKeySpec(claveBytes, "DES");

        cifrador.init(Cipher.DECRYPT_MODE, claveLeida);

        byte[] bufferdescifrado;
        entrada = new FileInputStream(nombreArchivo+".cifrado");
        salida = new FileOutputStream(nombreArchivo+".descifrado");

        bytesleidos = entrada.read(buffer, 0, 1000);

        while(bytesleidos != -1){
            bufferdescifrado = cifrador.update(buffer, 0, bytesleidos);
            if (bufferdescifrado != null)
                salida.write(bufferdescifrado);
            bytesleidos = entrada.read(buffer, 0, 1000);
        }

        bufferdescifrado = cifrador.doFinal();
        if (bufferdescifrado != null)
            salida.write(bufferdescifrado);

        entrada.close();
        salida.close();
    }

    private static void mensajeAyuda() {
        System.out.println("Ejemplo de un programa que sirve para cifrar y descifrar con DES");
        System.out.println("Favor de ingresar un archivo de texto plano, sino no funciona osea .txt");
    }

    private static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }
} 