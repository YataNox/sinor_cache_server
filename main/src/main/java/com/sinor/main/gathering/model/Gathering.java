package com.sinor.main.gathering.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Gathering {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String createdAt;
	private String crewCount;
	private String dateYn;
	private String distance;
	private String address;
	private String newYn;
	private String originYn;
	private String name;
	private String thumbnail;

}
