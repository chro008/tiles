package com.shangzhu.tiles.custom;

import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.web.startup.AbstractTilesListener;

public class MyCompleteAutoloadTilesListener extends AbstractTilesListener{

	protected TilesInitializer createTilesInitializer() {
        return new MyCompleteAutoloadTilesInitializer();
    }
}
