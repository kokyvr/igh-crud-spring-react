package com.igh.crud.service;

import java.util.Map;

import com.igh.crud.model.PlayList;

public interface PlayListService {

	public int insertar(PlayList playList);
	
	public int update(PlayList playList,Integer id);
	
	public int deleteById(Integer id);
	
	public PlayList findById(Integer id);
	
	public Map<String,Object> getPlayList(int page, int size);
}
