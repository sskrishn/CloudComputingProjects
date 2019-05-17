package com.weather.rest.controller;

import com.weather.*;
import com.weather.rest.repository.WeatherDateRepository;
import com.weather.rest.exception.ErrorMessageException;
import com.weather.rest.exception.ResourceNotFoundException;
import com.weather.rest.model.WeatherDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.ErrorType;
import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sskrishn on 03/18/2019.
 */
@RestController
@RequestMapping("/api")
public class WeatherDateController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	WeatherDateRepository weatherDateRepository;

	@GetMapping("/historical")
	public ResponseEntity<?> getAllWeatherDates() {
		List<Object> entityList = weatherDateRepository.findAllCus();
		if (entityList == null) {
			throw new ErrorMessageException("Sorry Resource not found.");
		}

		List<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
		for (Object o : entityList) {
			HashMap<String, String> a = new HashMap<String, String>();
			a.put("DATE", o.toString());
			newList.add(a);
		}
		return new ResponseEntity<List<HashMap<String, String>>>(newList, HttpStatus.OK);
	}

	@PostMapping("/historical")
	public ResponseEntity<?> createWeatherDate(@Valid @RequestBody WeatherDate weatherDate, Errors error) {
		WeatherDate user;
		try {
			Date given = sdf.parse(sdf.format(weatherDate.getDATE()));

			System.out.println("DATEEEEEEEEE" + weatherDate.getDATE());
			WeatherDate weatherDatenew = weatherDateRepository.findByCusDate(sdf.format(weatherDate.getDATE()));
			if (weatherDatenew == null) {
				user = weatherDateRepository.save(weatherDate);
				return new ResponseEntity<WeatherDate>(user, HttpStatus.CREATED);
			}
			weatherDatenew.setTMAX(weatherDate.getTMAX());
			weatherDatenew.setTMIN(weatherDate.getTMIN());
			user = weatherDateRepository.save(weatherDatenew);
		} catch (Exception e) {
			throw new ErrorMessageException("Sorry Exception Occured. (It may be due to invalid input)");
		}
		return new ResponseEntity<WeatherDate>(user, HttpStatus.CREATED);

	}

	@GetMapping("/historical/{date}")
	public ResponseEntity<?> getWeatherDateById(@PathVariable(value = "date") String weatherDateId) {
		try {
			Date given = sdf.parse(weatherDateId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new ErrorMessageException("Sorry Exception Occured. (It may be due to invalid input)");
		}
		WeatherDate user = weatherDateRepository.findByCusDate(weatherDateId);

		if (user == null) {
			throw new ResourceNotFoundException("Record", "date", weatherDateId);
		}
		return new ResponseEntity<WeatherDate>(user, HttpStatus.OK);
	}

	@DeleteMapping("/historical/{date}")
	public ResponseEntity<?> deleteWeatherDate(@PathVariable(value = "date") String weatherDateId) {

		try {
			Date given = sdf.parse(weatherDateId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new ErrorMessageException("Sorry Exception Occured. (It may be due to invalid input)");
		}
		WeatherDate weatherDatenew = weatherDateRepository.findByCusDate(weatherDateId);
		if (weatherDatenew == null) {
			throw new ResourceNotFoundException("WeatherDate", "date", weatherDateId);

		}
		weatherDateRepository.delete(weatherDatenew);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/forecast/{date}")
	public ResponseEntity<?> getForecastWeatherDates(@PathVariable(value = "date") String weatherDateId)
			throws ParseException {

		System.out.println(weatherDateId);
		List<WeatherDate> oldDates = weatherDateRepository.checkForecastDates(weatherDateId);
		Date given = sdf.parse(weatherDateId);

		if (oldDates == null) {
			throw new ResourceNotFoundException("WeatherDate", "date", weatherDateId);
		}

		List<String> notMisDates = new ArrayList<String>();

		for (WeatherDate w : oldDates) {
			notMisDates.add(sdf.format(w.getDATE()));
		}

		if (oldDates.size() == 7) {
			return new ResponseEntity<List<WeatherDate>>(oldDates, HttpStatus.OK);
		}

		else {
			List<String> allDates = new ArrayList<String>();
			LocalDate givenLo = given.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			List<LocalDate> daysRange = Stream.iterate(givenLo, date -> date.plusDays(1)).limit(7)
					.collect(Collectors.toList());
			for (LocalDate i : daysRange) {
				allDates.add(i.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			}

			List<String> misDates = new ArrayList<String>(allDates);
			misDates.removeAll(notMisDates);

			for (String weatherDate : misDates) {
				WeatherDate tmp = weatherDateRepository.findForecastDatesSingle(weatherDate);
				if (tmp != null) {
					tmp.getDATE().setYear(sdf.parse(weatherDate).getYear());
					oldDates.add(tmp);
				}
			}
			Collections.sort(oldDates);
			return new ResponseEntity<List<WeatherDate>>(oldDates, HttpStatus.OK);
		}
	}
}
