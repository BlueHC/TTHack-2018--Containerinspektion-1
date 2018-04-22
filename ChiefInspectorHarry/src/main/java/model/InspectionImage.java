package model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;

import java.sql.Timestamp;
import java.util.List;


public class InspectionImage implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private InspectionImageType type;
	
	private String filename;

	private String name;
	
	private String description;

	public InspectionImageType getType() {
		return type;
	}

	public void setType(InspectionImageType type) {
		this.type = type;
	}

	public ContainerInspection getInspection() {
		return inspection;
	}

	public void setInspection(ContainerInspection inspection) {
		this.inspection = inspection;
	}

	private ContainerInspection inspection;

	private Timestamp savedtime;

	public boolean isEvaluated() {
		return this.type != null && this.type == InspectionImageType.EVALUATED;
	}
	
	public boolean isOriginal() {
		return this.type != null && this.type == InspectionImageType.ORIGINAL;
	}

	public boolean isUndefined() {
		return this.type == null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public InspectionImage() {
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getSavedtime() {
		return this.savedtime;
	}

	public void setSavedtime(Timestamp savedtime) {
		this.savedtime = savedtime;
	}

	@JsonIgnore
	/*public List<Planingsession> getPlaningsessions() {
		return this.planingsessions;
	}

	public void setPlaningsessions(List<Planingsession> planingsessions) {
		this.planingsessions = planingsessions;
	}

	public Planingsession addPlaningsession(Planingsession planingsession) {
		getPlaningsessions().add(planingsession);
		planingsession.setGroundplanimage(this);

		return planingsession;
	}

	public Planingsession removePlaningsession(Planingsession planingsession) {
		getPlaningsessions().remove(planingsession);
		planingsession.setGroundplanimage(null);

		return planingsession;
	}*/

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}