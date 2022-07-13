package com.igh.crud.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class GuardarArchivo {

	private GuardarArchivo() {
		
	}
	
	public static String guardarArchivo(MultipartFile multiPart, String ruta) throws IOException {  
		// Obtenemos el nombre original del archivo.
				String nombreOriginal = multiPart.getOriginalFilename();
				// Reemplazamos en el nombre de archivo los espacios por guiones.
				nombreOriginal = nombreOriginal.replace(" ", "-");
				// Agregamos al nombre del archivo 8 caracteres aleatorios para evitar duplicados.
				/*File convertFile = new File(ruta + nombreOriginal);
				convertFile.createNewFile();
				try (FileOutputStream fout = new FileOutputStream(convertFile)){
					// Formamos el nombre del archivo para guardarlo en el disco duro.
				    
					fout.write(multiPart.getBytes());
				    	
					  
					
					
				} catch (Exception e) {
						e.printStackTrace();
						return null;
				}*/
				
				File file = new File(ruta + multiPart.getOriginalFilename());
				try {
					OutputStream os = new FileOutputStream(file);
					os.write(multiPart.getBytes());
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
				return nombreOriginal;
	}
}
