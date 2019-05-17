package com.weather.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

/**
 * Created by sskrishn on 03/18/2019.
 */
@Entity
@Table(name = "weatherdates")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt", "id", "tmin", "tmax", "date" }, allowGetters = false)
@ApiModel(description = "Class representing a weather date tracked by the application.")
public class WeatherDate implements Comparable<WeatherDate> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyyMMdd")
	@JsonProperty("DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
	@ApiModelProperty(notes = "Date in the format 'yyyymmdd'", example = "20130101", required = true, position = 0)
	private Date date;

	@JsonProperty("TMAX")
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(notes = "Maximum Temperature", example = "66", required = true, position = 1)
	private Double TMAX;

	@JsonProperty("TMIN")
	@ApiModelProperty(notes = "Minimum Temperature", example = "37", required = true, position = 2)
	@Column(nullable = false)
	@NotNull
	private Double TMIN;

	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDATE() {
		return date;
	}

	public void setDATE(Date dATE) {
		date = dATE;
	}

	public Double getTMAX() {
		return TMAX;
	}

	public void setTMAX(Double tMAX) {
		TMAX = tMAX;
	}

	public Double getTMIN() {
		return TMIN;
	}

	public void setTMIN(Double tMIN) {
		TMIN = tMIN;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int compareTo(WeatherDate u) {
		if (getDATE() == null || u.getDATE() == null) {
			return 0;
		}
		return getDATE().compareTo(u.getDATE());
	}
}
