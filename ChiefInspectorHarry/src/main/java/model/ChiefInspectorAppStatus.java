package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;

@Startup
@Singleton
public class ChiefInspectorAppStatus {


	List<ContainerInspection> inspections = new ArrayList<>();

	VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);

	public List<ContainerInspection> getInspections() {
		return inspections;
	}


	public VisualRecognition getService() {
		return service;
	}


	public ChiefInspectorAppStatus() {
		super();
		service.setApiKey(<api-key>);
	}
		                      

	public ContainerInspection getLatestInspection() {
		return inspections != null && !inspections.isEmpty()
				? inspections.get(inspections.size()-1)
				:startNewInspection(0);
	}
	
	private ContainerInspection startNewInspection(int i) {
		ContainerInspection containerInspection = new ContainerInspection();
		containerInspection.setId( i );
		inspections.add(containerInspection);
		return containerInspection;
	}


	public Integer getLatestInspectionId(){
		return Optional.ofNullable(getLatestInspection()).map(ContainerInspection::getId).orElse(0);
	}
	
	public ContainerInspection startNewInspection(){
		return startNewInspection(getLatestInspectionId() + 1);
	}
	
	
}
