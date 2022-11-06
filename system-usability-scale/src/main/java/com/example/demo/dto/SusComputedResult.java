package com.example.demo.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Data
@Component
@Getter
@Setter
@Entity
public class SusComputedResult {
	@Id
	private String sessionId;

	private double susScore;
	private int que_1;
	private int que_2;
	private int que_3;
	private int que_4;
	private int que_5;
	private int que_6;
	private int que_7;
	private int que_8;
	private int que_9;
	private int que_10;
	private LocalDateTime timestamp;
	
}
