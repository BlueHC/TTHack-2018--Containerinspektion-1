package webservices;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import model.ChiefInspectorAppStatus;

@ApplicationPath("rest")
public class ChiefInspectorApp extends Application {

	@Override
	public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(ChiefInspectorService.class));
	}
	
	

}
