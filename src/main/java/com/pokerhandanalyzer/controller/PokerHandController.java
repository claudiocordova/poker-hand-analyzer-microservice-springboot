package com.pokerhandanalyzer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pokerhandanalyzer.model.Card;
import com.pokerhandanalyzer.model.PokerHandAnalysis;
import com.pokerhandanalyzer.service.PokerHandService;

@RestController
public class PokerHandController {
	
	@Autowired
	private PokerHandService pokerHandService;

	
	@RequestMapping(method=RequestMethod.POST, value ="/isstraight")
	public PokerHandAnalysis submitPokerHand(@RequestBody List<Card> pokerHand) {
		return pokerHandService.submitPokerHand(pokerHand);
	}
	
	@RequestMapping(method=RequestMethod.GET, value ="/health")
	public String health() {
		return "ok";
	}
	
	@RequestMapping(method=RequestMethod.GET, value ="/test")
	public PokerHandAnalysis test() {
		PokerHandAnalysis r = new PokerHandAnalysis("1","hello V5");
		return r;
	}
	
	
	
	
}
