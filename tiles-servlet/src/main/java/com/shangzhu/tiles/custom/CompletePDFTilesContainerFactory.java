package com.shangzhu.tiles.custom;

import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.extras.complete.CompleteAutoloadTilesContainerFactory;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.ApplicationResource;

public class CompletePDFTilesContainerFactory extends CompleteAutoloadTilesContainerFactory{
	
	protected List<ApplicationResource> getSources(ApplicationContext applicationContext) {
		List<ApplicationResource> retValue = new ArrayList<ApplicationResource>(1);
        retValue.add(applicationContext.getResource("/WEB-INF/tiles_pdf.xml"));
        return retValue;
    }
}
