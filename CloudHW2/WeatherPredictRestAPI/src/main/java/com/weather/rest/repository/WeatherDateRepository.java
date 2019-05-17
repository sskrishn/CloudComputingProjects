package com.weather.rest.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weather.rest.model.WeatherDate;

/**
 * Created by sskrishn on 03/18/2019.
 */
@Repository
public interface WeatherDateRepository extends JpaRepository<WeatherDate, Long> {
	
	
	
	  @Query(value ="SELECT DATE_FORMAT(date,'%Y%m%d') as DATE from weatherdates;", nativeQuery=true) 
	  List<Object> findAllCus();
	 

	@Query(value ="SELECT * from weatherdates  WHERE Date(date) = :date", nativeQuery=true)
	public WeatherDate findByCusDate(@Param("date") String date);
	
	
	
	@Query(value ="select * from weatherdates WHERE date BETWEEN  :date AND DATE_ADD(:date, INTERVAL 6 DAY) GROUP BY DAY(date)", nativeQuery=true)
	public List<WeatherDate> checkForecastDates(@Param("date") String date);
	
	@Query(value ="select id,AVG(tmax) as tmax,AVG(tmin) as tmin, created_at,date,updated_at from weatherdates WHERE MONTH(date) = MONTH(:date) AND DAY(date) BETWEEN  DAY(:date) AND DAY(DATE_ADD(:date, INTERVAL 6 DAY)) GROUP BY DAY(date)", nativeQuery=true)
	public List<WeatherDate> findForecastDates(@Param("date") String date);
	
	@Query(value ="select id,AVG(tmax) as tmax,AVG(tmin) as tmin, created_at,date,updated_at from weatherdates WHERE MONTH(date) = MONTH(:date) AND DAY(date) = DAY(:date) GROUP BY DAY(date)", nativeQuery=true)
	public WeatherDate findForecastDatesSingle(@Param("date") String date);

   // List<WeatherDate> findAllByDate(Date date);


}
