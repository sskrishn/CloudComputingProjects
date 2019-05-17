package com.krishn.spring.controller;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.krishn.spring.model.CalculatorPojo;




@Controller
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		return "Calculator";
	}
	
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		return "Calculator";
	}
	
		
	@RequestMapping(value = "/cal", method = RequestMethod.POST)
	public String cal(@Validated CalculatorPojo calP, Model model) {
		System.out.println("result Page Requested");
		model.addAttribute("calEquation", calP.getCalEquation());
		String s=calP.getCalEquation();
		System.out.println(s);

		String[] a=s.split("&&");
		
		System.out.println(a.length);
		try {
		int res=new Integer(a[0].toString());
		int i=0;
		while ( i < a.length-2)
	    {
			System.out.println(a[i+2]);
	      
			String tmp=a[i+1];
			int num2=new Integer(a[i+2].toString());
			
			if(tmp.equals("+"))
			{
				res=res+num2;
			}
			else if(tmp.equals("-")) {
				res=res-num2;
			}
			
			else if(tmp.equals("*")) {
				res=res*num2;
			}
			
			else if(tmp.equals("/")) {
				res=res/num2;
			}
		
			i=i+2;
	    }
		
		calP.setCalResult(String.valueOf(res));
		String modEq=calP.getCalEquation().replaceAll("\\&&","");
		//System.out.println(calP.getStyleName());
		model.addAttribute("calEquation",modEq);
		model.addAttribute("calResult",calP.getCalResult());
		
		  } catch(Exception e) {
		  
		  String modEq=calP.getCalEquation().replaceAll("\\&&","");
		  //System.out.println(calP.getStyleName());
		  model.addAttribute("calEquation",modEq); model.addAttribute(
		  "calResult","Sorry, Error occured at Server Side. (Reasons may be : Division by zero, Unknown request etc."
		  ); 
		  return "calRes"; } 
		finally { 
			return "calRes"; 
			}
		 
		}
	
	
	
}
