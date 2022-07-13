package com.igh.crud.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.igh.crud.model.Cancion;
import com.igh.crud.service.CancionService;

@RequestMapping(path = "cancion")
@RestController
public class CancionController {

	@Autowired
	private CancionService service;
	
	private String nombreArchivo;
	
	@PostMapping("/audio")
	public ResponseEntity<?> uploadMp3(@RequestParam("file")MultipartFile mp3){
		File file;
		if(mp3.isEmpty() !=true) {
			file = new File("d://canciones/"+mp3.getOriginalFilename());
			try {
				OutputStream os = new FileOutputStream(file);
				os.write(mp3.getBytes());
				os.close();
				String nombreOriginal = mp3.getOriginalFilename();
				// Reemplazamos en el nombre de archivo los espacios por guiones.
				nombreOriginal = nombreOriginal.replace(" ", "-");
				nombreArchivo=nombreOriginal;
				
			} catch (Exception e) {
			
				return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}	
		}
		
	
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<Integer> insertar(@RequestBody Cancion cancion ){
		int rpta = 0;
		try {
			cancion.setNombreArchivo(nombreArchivo);
			rpta = service.insertar( cancion);
			
		} catch (Exception e) {
	
			return new ResponseEntity<Integer>(rpta,HttpStatus.BAD_REQUEST);
		}
		this.nombreArchivo = null;
		
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);
	}

}
