package com.shangzhu.tiles.custom;

import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.extras.complete.CompleteAutoloadTilesContainerFactory;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.ApplicationResource;

public class MyCompleteAutoloadTilesContainerFactory extends CompleteAutoloadTilesContainerFactory{

	protected List<ApplicationResource> getSources(ApplicationContext applicationContext) {
		List<ApplicationResource> retValue = new ArrayList<ApplicationResource>(1);
		System.out.println("11111111111111111"+applicationContext.getInitParams().get("dltoken"));
        retValue.add(applicationContext.getResource("/WEB-INF/tiles.xml"));
        return retValue;
    }
}
