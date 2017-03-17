package com.shangzhu.tiles.action.traffic;

import java.io.IOException;
import java.io.PrintWriter;

import com.shangzhu.tiles.datatype.ActionHandlerIdentifier;
import com.shangzhu.tiles.datatype.ModelView;
import com.shangzhu.tiles.datatype.ModelViewType;
import com.shangzhu.tiles.datatype.ServletActionContext;

@ActionHandlerIdentifier(path="traffic")
public class TrafficHomepageAction {

	public ModelView homepage(ServletActionContext context){
		ModelView view = new ModelView("traffic.homepage");
		if(context.getRequest().getParameter("pdf")!=null){
			view.setType(ModelViewType.PDF);
		}
		return view.addModel("name", "systest");
	}
	
	public void homepage_getHtml(ServletActionContext context){
		PrintWriter out = null;
		try {
			out = context.getResponse().getWriter();
			out.println(Math.random());
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
