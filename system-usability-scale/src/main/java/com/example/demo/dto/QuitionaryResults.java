package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuitionaryResults {
	private String id;
	private String option;
	
	public QuitionaryResults(String option, String id) {
		this.option = option;
		this.id = id;
	}

}
