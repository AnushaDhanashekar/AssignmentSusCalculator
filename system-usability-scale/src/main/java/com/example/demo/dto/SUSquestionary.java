package com.example.demo.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Data
@Entity
public class SUSquestionary {
	@Id
	private int id;
	private String questionary;

}
