package com.igh.crud.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igh.crud.model.PlayList;
import com.igh.crud.service.PlayListService;

@RestController
@RequestMapping(path = "playlist")
public class PlayListController {

	@Autowired
	private PlayListService service;
	
	@PostMapping
	public ResponseEntity<Integer> insertar(@RequestBody PlayList playList){
		int rpta = 0;
		try {
			rpta = service.insertar(playList);
		} catch (Exception e) {
			rpta = 0;
			e.printStackTrace();
			return new ResponseEntity<Integer>(rpta,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);
	}
	@GetMapping(path = "getPlayList")
	public ResponseEntity<Map<String,Object>> getPlayList(@RequestParam(required = false,defaultValue = "0") int page, @RequestParam(required = false,defaultValue = "5") int size){
		Map<String, Object> map = null;
		try {
			map = service.getPlayList(page, size);
		} catch (Exception e) {
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
	}
	
	@PutMapping(path = "{id}")
	public ResponseEntity<Integer> updatePlayList(@Valid @RequestBody PlayList playlist,@PathVariable Integer id){
		int rpta = 0;
		try {
			rpta = service.update(playlist, id);
			
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);
	}
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Integer> deleteById(@PathVariable Integer id){
		int rpta = 0;
		try {
			rpta = service.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Integer>(rpta,HttpStatus.OK);
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<PlayList> getById(@PathVariable Integer id){
		PlayList playlist = null;
		try {
			playlist = service.findById(id);
		} catch (Exception e) {
			return new ResponseEntity<PlayList>(playlist,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<PlayList>(playlist,HttpStatus.OK);
	}
	
}
