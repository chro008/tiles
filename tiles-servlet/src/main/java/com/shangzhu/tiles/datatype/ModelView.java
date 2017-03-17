package com.shangzhu.tiles.datatype;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
	private String path;
	private Map<String,Object> modelMap = null;
	private ModelViewType type;
	
	public ModelView(String path){
		this(path,false);
	}
	
	public ModelView(String path,ModelViewType type){
		this(path,false);
		setType(type);
	}
	
	public ModelView(String path,boolean isdirect){
		this.path = path;
	}
	
	public static ModelView getForwardView(String path){
		return new ModelView(path, false);
	}
	
	public static ModelView getRedirectView(String path){
		return new ModelView(path, true);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public ModelView addModel(String name,Object model,Object... objects){
		if(modelMap==null){modelMap = new HashMap<String, Object>();}
		modelMap.put(name, model);
		for(int i=0;i<objects.length-1;i=i+2){			
			modelMap.put(objects[i].toString(), objects[i+1]);
		}
		return this;
	}

	public Map<String, Object> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, Object> modelMap) {
		this.modelMap = modelMap;
	}

	public ModelViewType getType() {
		return type;
	}

	public void setType(ModelViewType type) {
		this.type = type;
	}
	
}
