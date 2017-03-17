package com.shangzhu.tiles.action.myapp;

import com.shangzhu.tiles.datatype.ActionHandlerIdentifier;
import com.shangzhu.tiles.datatype.ModelView;
import com.shangzhu.tiles.datatype.ServletActionContext;

@ActionHandlerIdentifier(path="myapp")
public class MyAPPHomepageAction {
	
	public ModelView homepage(ServletActionContext context){
		return new ModelView("traffic.homepage").addModel("name", "myapp");
	}

}
