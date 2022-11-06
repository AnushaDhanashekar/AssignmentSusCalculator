package com.example.demo.dto;


import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Component
@Getter
@Setter
public class GlobalScore {
	private long totalSessions;
	private long aboveAvgScore;
	private long belowAvgScore;
	

}
