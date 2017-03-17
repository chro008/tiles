package com.shangzhu.tiles;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.DefinitionsUtil;
import org.apache.struts.tiles.NoSuchDefinitionException;
import org.apache.struts.tiles.TilesRequestProcessor;

public class MyTilesRequestProcessor extends TilesRequestProcessor {
	
	protected boolean processTilesDefinition(String definitionName,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// Do we do a forward (original behavior) or an include ?
		boolean doInclude = false;

		// Controller associated to a definition, if any
		Controller controller = null;

		// Computed uri to include
		String uri = null;

		ComponentContext tileContext = null;

		try {
			// Get current tile context if any.
			// If context exist, we will do an include
			tileContext = ComponentContext.getContext(request);
			doInclude = (tileContext != null);
			ComponentDefinition definition = null;

			
			// Process tiles definition names only if a definition factory
			// exist,
			// and definition is found.
			if (definitionsFactory != null) {
				// Get definition of tiles/component corresponding to uri.
				try {
					definition = definitionsFactory.getDefinition(
							definitionName, request, getServletContext());
				} catch (NoSuchDefinitionException ex) {
					// Ignore not found
					log.debug("NoSuchDefinitionException " + ex.getMessage());
				}
				
				if(definition==null){
					definition = getWildCardsDenifition(definitionName, request);
				}

				if (definition != null) { // We have a definition.
					// We use it to complete missing attribute in context.
					// We also get uri, controller.
					uri = definition.getPath();
					controller = definition.getOrCreateController();

					if (tileContext == null) {
						tileContext = new ComponentContext(
								definition.getAttributes());
						ComponentContext.setContext(tileContext, request);

					} else {
						tileContext.addMissing(definition.getAttributes());
					}
				}
			}
			
			// Process definition set in Action, if any.
			definition = DefinitionsUtil.getActionDefinition(request);
			if (definition != null) { // We have a definition.
				// We use it to complete missing attribute in context.
				// We also overload uri and controller if set in definition.
				if (definition.getPath() != null) {
					uri = definition.getPath();
				}

				if (definition.getOrCreateController() != null) {
					controller = definition.getOrCreateController();
				}

				if (tileContext == null) {
					tileContext = new ComponentContext(
							definition.getAttributes());
					ComponentContext.setContext(tileContext, request);
				} else {
					tileContext.addMissing(definition.getAttributes());
				}
			}

		} catch (java.lang.InstantiationException ex) {

			log.error("Can't create associated controller", ex);

			throw new ServletException("Can't create associated controller", ex);
		} catch (DefinitionsFactoryException ex) {
			throw new ServletException(ex);
		}

		// Have we found a definition ?
		if (uri == null) {
			return false;
		}

		// Execute controller associated to definition, if any.
		if (controller != null) {
			try {
				controller.execute(tileContext, request, response,
						getServletContext());

			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		// If request comes from a previous Tile, do an include.
		// This allows to insert an action in a Tile.
		if (log.isDebugEnabled()) {
			log.debug("uri=" + uri + " doInclude=" + doInclude);
		}

		if (doInclude) {
			doInclude(uri, request, response);
		} else {
			doForward(uri, request, response); // original behavior
		}

		return true;
	}
	
	private ComponentDefinition getWildCardsDenifition(String definitionName,HttpServletRequest request) throws DefinitionsFactoryException{
		ComponentDefinition tempDefinition;
		if(definitionName.matches("^[a-z]+[_|/|\\.][a-z]+$")){
			String[] wildCards = definitionName.split("[_|/|\\.]");
			
			ComponentDefinition wildCardsDenifition = definitionsFactory.getDefinition("*/*", request, getServletContext());
			tempDefinition = new ComponentDefinition(wildCardsDenifition);
			
			Map<String,Object> attributes = wildCardsDenifition.getAttributes();
			String value;
			for(Entry<String,Object> entry : attributes.entrySet()){
				value = entry.getValue().toString();
				if(value.matches(".*\\{[1-2]\\}.*")){
					value = value.replaceAll("\\{1\\}", wildCards[0]).replaceAll("\\{2\\}", wildCards[1]);
					
					tempDefinition.putAttribute(entry.getKey(), value);
					
				}
			}
			tempDefinition.setName(wildCards[0]+"/"+wildCards[1]);
			return tempDefinition;
		}else{
			return null;
		}
		
	}
	
}
