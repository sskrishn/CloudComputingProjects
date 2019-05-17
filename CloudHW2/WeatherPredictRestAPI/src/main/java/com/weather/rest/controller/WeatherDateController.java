package com.weather.rest.controller;

import com.weather.*;
import com.weather.rest.repository.WeatherDateRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Weather for dates.")
public class WeatherDateController {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	WeatherDateRepository weatherDateRepository;

	@GetMapping("/historical")
	@ApiOperation("Returns list of all weather dates in the system.")
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
	@ApiOperation("Creates a new record with weather information provided for give date. If it exist it will update.")
	public ResponseEntity<?> createWeatherDate(
			@ApiParam("Weather information for a date to be created/updated. Copy the json on right side example(ignore tmax,tmin,createdat,updatedat)") @Valid @RequestBody WeatherDate weatherDate,
			Errors error) {
		WeatherDate user;
		try {
			Date given = sdf.parse(sdf.format(weatherDate.getDATE()));

			System.out.println("DATE" + weatherDate.getDATE());
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
	@ApiOperation("Returns a specific weather info for particular date. 404 if does not exist.")
	public ResponseEntity<?> getWeatherDateById(
			@ApiParam(value = "Date(yyyymmdd format) cannot be empty to get particular weather info.", defaultValue = "20130404") @PathVariable(value = "date") String weatherDateId) {
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
	@ApiOperation("Deletes a weather date record from the system. 404 if the date is not found.")
	public ResponseEntity<?> deleteWeatherDate(
			@ApiParam(value = "Date(yyyymmdd format) cannot be empty to delete particular weather info.", defaultValue = "20130303") @PathVariable(value = "date") String weatherDateId) {

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
	@ApiOperation("Returns weather info which is predicted for 7 dates from the given date.")
	public ResponseEntity<?> getForecastWeatherDates(
			@ApiParam(value = "Date(yyyymmdd format) cannot be empty to forecast weather info for 7 days.", defaultValue = "20200303") @PathVariable(value = "date") String weatherDateId)
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
