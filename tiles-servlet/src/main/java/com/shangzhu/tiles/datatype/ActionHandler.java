package com.shangzhu.tiles.datatype;

import java.lang.reflect.Method;

public class ActionHandler {
	private Object handler_object;
	private Method handler_method;
	
	public ActionHandler(Object handler_object, Method handler_method) {
		super();
		this.handler_object = handler_object;
		this.handler_method = handler_method;
	}
	
	public Object getHandler_object() {
		return handler_object;
	}
	
	public void setHandler_object(Object handler_object) {
		this.handler_object = handler_object;
	}
	
	public Method getHandler_method() {
		return handler_method;
	}
	
	public void setHandler_method(Method handler_method) {
		this.handler_method = handler_method;
	}
	
}
