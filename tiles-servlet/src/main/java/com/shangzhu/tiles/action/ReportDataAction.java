package com.shangzhu.tiles.action;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;

import com.shangzhu.tiles.custom.MyCompleteAutoloadTilesInitializer;
import com.shangzhu.tiles.datatype.ActionHandler;
import com.shangzhu.tiles.datatype.ActionHandlerIdentifier;
import com.shangzhu.tiles.datatype.ModelView;
import com.shangzhu.tiles.datatype.ModelViewType;
import com.shangzhu.tiles.datatype.ServletActionContext;

public class ReportDataAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Map<String, Map<String, ActionHandler>> dataHandlerMap = new ConcurrentHashMap<String, Map<String, ActionHandler>>();
	static {
		initDataHandlerMap();
	}

	private static void initDataHandlerMap() {
		String packageName = "com.shangzhu.tiles.action";
		try {
			URL url = ReportDataAction.class.getResource("");
			String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
			findActionHandlerClass(packageName, filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void findActionHandlerClass(String packageName,
			String packagePath) {

		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}

		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory()
						|| (file.getName().endsWith(".class") && !file
								.getName().contains("$"));
			}
		});

		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findActionHandlerClass(packageName + "." + file.getName(),
						file.getAbsolutePath());
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					registClass(Class.forName(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void registClass(Class<?> clazz) {
		String path = null;
		for (Annotation t : clazz.getAnnotations()) {
			if (t instanceof ActionHandlerIdentifier) {
				path = ((ActionHandlerIdentifier) t).path();
				break;
			}
		}
		if (path != null && path.trim().length() > 0) {
			Class<?>[] temp = null;
			Map<String, ActionHandler> tempMap = null;
			Object handler_object;
			try {
				handler_object = clazz.newInstance();
				for (Method method : clazz.getDeclaredMethods()) {
					temp = method.getParameterTypes();
					if (temp != null && temp.length == 1
							&& temp[0] == ServletActionContext.class) {
						method.setAccessible(true);
						tempMap = dataHandlerMap.get(path);
						if (tempMap == null) {
							tempMap = new ConcurrentHashMap<>();
							dataHandlerMap.put(path, tempMap);
						}
						tempMap.put(method.getName(), new ActionHandler(
								handler_object, method));
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private ActionHandler getActionHandler(String path, String actionoperate) {
		Map<String, ActionHandler> temp = dataHandlerMap.get(path);
		if (temp == null) {
			return null;
		}
		return temp.get(actionoperate);
	}
	
	protected void doPost(HttpServletRequest req,HttpServletResponse res){
		process(req, res);
	}
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res){
		process(req,res);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse res) {
        ModelView view = getModelView(req, res);
        if(view==null){
        	return;
        }else{
        	String definition = view.getPath();
        	ApplicationContext applicationContext = org.apache.tiles.request.servlet.ServletUtil
                    .getApplicationContext(getServletContext());
            Request request = new ServletRequest(applicationContext,req, res);
        	if(view.getType()==ModelViewType.JSP){
        		renderJsp(definition,applicationContext,request);
        	}else if(view.getType()==ModelViewType.PDF){
        		renderPdf(definition,applicationContext,request);
        	}
        }
    }
	
	private void renderJsp(String definition,ApplicationContext applicationContext,Request request){
        TilesContainer container = TilesAccess.getContainer(applicationContext,
                MyCompleteAutoloadTilesInitializer.JSP_CONTAINER);
        TilesAccess.setCurrentContainer(request, MyCompleteAutoloadTilesInitializer.JSP_CONTAINER);
        container.render(definition, request);
	}
	
	private void renderPdf(String definition,ApplicationContext applicationContext,Request request){
        TilesContainer container = TilesAccess.getContainer(applicationContext,
                MyCompleteAutoloadTilesInitializer.PDF_CONTAINER);
        TilesAccess.setCurrentContainer(request, MyCompleteAutoloadTilesInitializer.PDF_CONTAINER);
        container.render(definition, request);
	}
	
	protected ModelView getModelView(HttpServletRequest request,HttpServletResponse response) {
		try {
			ServletActionContext context = ServletActionContext.getInst(request,response);
			String uri = request.getRequestURI();
			String path = uri.substring(uri.lastIndexOf("/") + 1,
					uri.indexOf(".do"));
			String action = request.getParameter("action");

			if (action != null && action.trim().length() > 0) {
				String methodname;
				String operate = request.getParameter("operate");
				if (operate != null && operate.trim().length() > 0) {
					methodname = action + "_" + operate;
				} else {
					methodname = action;
				}

				ActionHandler actionHandler = getActionHandler(path, methodname);
				if (actionHandler != null) {
					Object obj = actionHandler.getHandler_method().invoke(
							actionHandler.getHandler_object(), context);

					if (obj instanceof ModelView) {
						ModelView view = (ModelView) obj;
						Map<String, Object> tempMap = view.getModelMap();
						if (tempMap != null) {
							for (String key : tempMap.keySet()) {
								request.setAttribute(key, tempMap.get(key));
							}
						}
						
						if(view.getType()==null){
							view.setType(ModelViewType.JSP);
						}
						
						return view;
					}else{
						return null;
					}
				} else {
					System.out.println("method name:" + methodname
							+ " does not exists!");
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
    }

}
