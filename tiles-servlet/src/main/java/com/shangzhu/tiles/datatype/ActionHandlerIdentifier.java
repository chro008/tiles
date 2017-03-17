package com.shangzhu.tiles.datatype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface ActionHandlerIdentifier {
	public String path() default "";	
}
