package com.igh.crud.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.igh.crud.model.Cancion;
import com.igh.crud.service.CancionService;
import com.igh.crud.util.GuardarArchivo;

@RequestMapping(path = "cancion")
@RestController
public class CancionController {

	@Autowired
	private CancionService service;

	private String carpeta = "c:/canciones/";

	private String nombreArchivo;

	@PostMapping("/audio")
	public ResponseEntity<?> uploadMp3(@RequestParam("file") MultipartFile mp3) {
		File file;

		if (mp3.isEmpty() != true) {
			nombreArchivo = GuardarArchivo.randomAlphaNumeric(5) + "-" + (mp3.getOriginalFilename()).toUpperCase();
			file = new File(carpeta + nombreArchivo);
			try {
				OutputStream os = new FileOutputStream(file);
				os.write(mp3.getBytes());
				os.close();
			} catch (Exception e) {
				this.nombreArchivo = null;
				return new ResponseEntity<>(nombreArchivo, HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Integer> insertar(@RequestBody Cancion cancion) {
		int rpta = 0;
		if (nombreArchivo == null) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.BAD_REQUEST);
		}

		try {
			cancion.setNombreArchivo(nombreArchivo);
			rpta = service.insertar(cancion);
			this.nombreArchivo = null;
		} catch (Exception e) {
			GuardarArchivo.deleteFile(carpeta + nombreArchivo);
			e.printStackTrace();
			return new ResponseEntity<Integer>(rpta, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	@PermitAll
	@GetMapping("/getCanciones")
	public ResponseEntity<Map<String,Object>> getCanciones(@RequestParam(required = false,defaultValue = "0") int page, @RequestParam(required = false,defaultValue = "5") int size){
		Map<String,Object> canciones = null;
		try {
			canciones = service.getCanciones(page, size);
		} catch (Exception e) {
			return new ResponseEntity<Map<String,Object>>(canciones, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Map<String,Object>>(canciones, HttpStatus.OK);
	}

}
