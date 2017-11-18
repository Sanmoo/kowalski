package com.app.kowalski.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.kowalski.project.Project;

@Entity
@Table(name = "activity")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer activityId;

	private String name;
	private String description;
	private String status;
	private Date startDate;
	private Date endDate;

	@ManyToOne
    @JoinColumn(name="project_projectId")
    private Project project;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Activity() {}

	public Activity convertToActivity(ActivityDTO activityDTO) {
		this.name = activityDTO.getName();
		this.description = activityDTO.getDescription();
		this.status = activityDTO.getStatus();
		try {
			this.startDate = sdf.parse(activityDTO.getStartDate());
			this.endDate = sdf.parse(activityDTO.getEndDate());
		} catch (ParseException e) {}


		return this;
	}

	/**
	 * @return the id
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * @param id the id to set
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
