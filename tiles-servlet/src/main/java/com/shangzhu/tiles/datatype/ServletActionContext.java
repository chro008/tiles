package com.shangzhu.tiles.datatype;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletActionContext {
	private HttpServletRequest request;
	private HttpServletResponse response;
	

	private ServletActionContext(){}
	
	private void initLoginInfo(){
	}
	
	public static  ServletActionContext getInst(HttpServletRequest request,HttpServletResponse response){
		ServletActionContext sac = new ServletActionContext();
		sac.request = request;
		sac.response = response;	
		sac.initLoginInfo();
		return sac;
	}
	public static ServletActionContext getInst(HttpServletRequest request){
		ServletActionContext sac = new ServletActionContext();
		sac.request = request;
		sac.initLoginInfo();
		return sac;
	}
	
	

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
