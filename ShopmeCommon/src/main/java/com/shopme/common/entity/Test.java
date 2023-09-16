package com.shopme.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
	@OneToOne
	@JoinColumn(name = "tset_id")
	private Test testt;
}
