package com.shangzhu.tiles.custom;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.extras.complete.CompleteAutoloadTilesContainerFactory;
import org.apache.tiles.extras.complete.CompleteAutoloadTilesInitializer;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.request.ApplicationAccess;
import org.apache.tiles.request.ApplicationContext;

public class MyCompleteAutoloadTilesInitializer extends CompleteAutoloadTilesInitializer{
	
	private ApplicationContext applicationContext;
	
	public static final String PDF_CONTAINER =
	        "com.shangzhu.tiles.custom.MyCompleteAutoloadTilesInitializer.PDF_CONTAINER";
	
	public static final String JSP_CONTAINER =
	        "com.shangzhu.tiles.custom.MyCompleteAutoloadTilesInitializer.JSP_CONTAINER";
	
    /**
     * The initialized container.
     */
    private TilesContainer container;
	
	public void initialize(ApplicationContext applicationContext) {
        this.applicationContext = createTilesApplicationContext(applicationContext);
        ApplicationAccess.register(applicationContext);
        String[] keys = {PDF_CONTAINER,JSP_CONTAINER};
        for(String key : keys){
        	container = createContainer(this.applicationContext,key);
            TilesAccess.setContainer(this.applicationContext, container, key);
        }
    }
	
	protected TilesContainer createContainer(ApplicationContext context,String key) {
		AbstractTilesContainerFactory factory = createContainerFactory(context,key);
        return factory.createContainer(context);
    }
	
    protected AbstractTilesContainerFactory createContainerFactory( ApplicationContext context,String key) {
		switch(key){
		case PDF_CONTAINER: return new CompletePDFTilesContainerFactory();
		case JSP_CONTAINER: return new CompleteJSPTilesContainerFactory();
		default: return new CompleteAutoloadTilesContainerFactory();
    }
	
	
}
	

}
