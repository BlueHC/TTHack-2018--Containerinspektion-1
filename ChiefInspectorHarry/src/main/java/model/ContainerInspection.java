package model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ContainerInspection implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String description;

	private Timestamp starttime;

	private List<InspectionImage> images = new ArrayList<InspectionImage>();

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImages(List<InspectionImage> images) {
		this.images = images;
	}

	public ContainerInspection() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public List<InspectionImage> getImages() {
		return this.images;
	}

	public List<InspectionImage> getEvaluatedImages() {
		return this.images != null
				? this.images.stream().filter(InspectionImage::isEvaluated).collect(Collectors.toList())
				:new ArrayList<>();
	}

	public List<InspectionImage> getOriginalImages() {
		return this.images != null
				? this.images.stream().filter(InspectionImage::isOriginal).collect(Collectors.toList())
				:new ArrayList<>();
	}

	public void addImage(InspectionImage groundplanimage) {
		this.images.add(groundplanimage);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

}