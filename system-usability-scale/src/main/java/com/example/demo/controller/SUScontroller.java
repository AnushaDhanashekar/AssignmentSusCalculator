package com.example.demo.controller;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.QuestionaryResultsRepo;
import com.example.demo.dao.SusRepo;
import com.example.demo.dto.GlobalScore;
import com.example.demo.dto.QuitionaryResults;
import com.example.demo.dto.SusComputedResult;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("/sus")
public class SUScontroller {
	public static JSONObject susScore = new JSONObject();
	@Autowired
	SusRepo susRepo;
	@Autowired
	QuestionaryResultsRepo questionaryResultsRepo;
	@Autowired 
	public SusComputedResult susComputedResult;
	@Autowired
	public GlobalScore globalScore;
	
	static {
		
		susScore.put("stronglydisagree", 0);
		susScore.put("disagree", 1);
		susScore.put("unsure", 2);
		susScore.put("agree", 3);
		susScore.put("stronglyagree", 4);
		
	
	}
	
	@GetMapping("/susWindow")
	public String susWindow(Model model) {
		 model.addAttribute("susWindow", susRepo.findAll());
		return "susWindow";
		
	}
	//SUSquestionaryForm sUSquestionary
	@PostMapping(value="/saveCompute")
	public @ResponseBody String saveCompute(@RequestBody List<QuitionaryResults> quitionaryResults, HttpServletRequest request) {
		// = request.getSession().getId();
		/*
		 * To calculate the SUS score, first sum the score contributions from each item.
		 * Each item's score contribution will range from 0 to 4. For items 1,3,5,7,and
		 * 9 the score contribution is the scale position minus 1. For items 2,4,6,8 and
		 * 10, the contribution is 5 minus the scale position. Multiply the sum of the
		 * scores by 2.5 to obtain the overall value of SU.
		 */
		susComputedResult.setSessionId(request.getSession().getId());
		
		double finalScore = 0;
		for(QuitionaryResults itemScore : quitionaryResults){
			int chosenOption = (int) susScore.get(itemScore.getOption().toLowerCase());
			if(chosenOption == -1)continue;
			if(itemScore.getId().equals("1") || itemScore.getId().equals("3") || itemScore.getId().equals("5") || itemScore.getId().equals("7") || itemScore.getId().equals("9")) {			
				finalScore += chosenOption -1;
				
				if(itemScore.getId().equals("1"))susComputedResult.setQue_1(chosenOption);
				else if(itemScore.getId().equals("3"))susComputedResult.setQue_3(chosenOption);
				else if(itemScore.getId().equals("5"))susComputedResult.setQue_5(chosenOption);
				else if(itemScore.getId().equals("7"))susComputedResult.setQue_7(chosenOption);
				else if(itemScore.getId().equals("9"))susComputedResult.setQue_9(chosenOption);
			}
			else {
				finalScore += 5 -chosenOption;
				
				if(itemScore.getId().equals("2"))susComputedResult.setQue_2(chosenOption);
				else if(itemScore.getId().equals("4"))susComputedResult.setQue_4(chosenOption);
				else if(itemScore.getId().equals("6"))susComputedResult.setQue_6(chosenOption);
				if(itemScore.getId().equals("8"))susComputedResult.setQue_8(chosenOption);
				if(itemScore.getId().equals("10"))susComputedResult.setQue_10(chosenOption);
			}
		}
		susComputedResult.setTimestamp(LocalDateTime.now());
		susComputedResult.setSusScore(finalScore * 2.5);
		questionaryResultsRepo.save(susComputedResult);		
		
		request.getSession(false).invalidate();
		return "success";
	}
	
	@GetMapping("/susGlobal")
	public String susGlobal(Model model) {
		long totalNumberOfRows = questionaryResultsRepo.count();
		Iterable<SusComputedResult> globalResultSet = questionaryResultsRepo.findAll();
		long aboveAvg = StreamSupport.stream(globalResultSet.spliterator(), false).filter(greater -> greater.getSusScore() > 68).count();
		long belowAvg = totalNumberOfRows- aboveAvg;
		globalScore.setTotalSessions(totalNumberOfRows);
		globalScore.setAboveAvgScore(aboveAvg);
		globalScore.setBelowAvgScore(belowAvg);
		 model.addAttribute("susGlobal", globalScore);
		return "susGlobal";
		
	}
	
	/*
	 * @GetMapping("/sus") public String sus(Model model) {
	 * model.addAttribute("message", "System Usability Scale"); return "sus";
	 * 
	 * }
	 * 
	 * @GetMapping("/style") public String style() { return "add-css-js-demo"; }
	 * 
	 * @GetMapping("/bootstrap") public String bootstrap() { return "add-bootstrap";
	 * }
	 */

}
