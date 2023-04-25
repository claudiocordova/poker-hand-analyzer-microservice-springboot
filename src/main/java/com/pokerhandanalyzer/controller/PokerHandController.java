package com.pokerhandanalyzer.controller;


import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.pokerhandanalyzer.model.Card;
import com.pokerhandanalyzer.model.PokerHandAnalysis;
import com.pokerhandanalyzer.service.PokerHandService;


import com.amazonaws.xray.contexts.SegmentContextExecutors;
import com.amazonaws.xray.entities.Entity;
import com.amazonaws.xray.entities.Segment;
import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.entities.TraceID;


@RestController
@XRayEnabled
public class PokerHandController {
	
	@Autowired
	private PokerHandService pokerHandService;

	
	@RequestMapping(method=RequestMethod.POST, value ="/isstraight")
	public PokerHandAnalysis submitPokerHand(@RequestBody List<Card> pokerHand) {
		
		//Segment seg = AWSXRay.beginSegment("isstraight");
		//AWSXRay.getGlobalRecorder().setTraceEntity(seg);
		PokerHandAnalysis s = pokerHandService.submitPokerHand(pokerHand);
		//AWSXRay.endSegment();
		
		
		
		//com.amazonaws.xray.spring.aop.XRayTraced
		
		return s;
	}
	
	@RequestMapping(method=RequestMethod.GET, value ="/health")
	public String health() {
		
		//AWSXRay.beginSegment("health");
		//AWSXRay.endSegment();
		return pokerHandService.health();
	}
	
	@RequestMapping(method=RequestMethod.GET, value ="/test")
	public PokerHandAnalysis test() {
		
		
		//AWSXRay.beginSegment("test");
		PokerHandAnalysis r = new PokerHandAnalysis("1","hello V11");
		//AWSXRay.endSegment();
		return r;
	}
	
	@RequestMapping(method=RequestMethod.GET, value ="/test2")
	public PokerHandAnalysis test2() {
		

		PokerHandAnalysis r = new PokerHandAnalysis("1","hello V11");
	
		return r;
	}

	
	
	
	
}

