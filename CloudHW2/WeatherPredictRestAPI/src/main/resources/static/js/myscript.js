/**
 * 
 */
jQuery(document).ready(function($) {

	$("#cusBlk" ).hide();
	$("#ncusBlk" ).hide();

	$('#pred').click(function() {

		$("#cusBlk" ).hide();
		$("#ncusBlk" ).hide();

		let isValidDate = Date.parse($( "#forDate" ).val());
		if (isNaN(isValidDate)) {
		  // when is not valid date logic
		  alert("Please select valid date");
		  return;
		}

		var a= $( "#forDate" ).val().replace("-", "").replace("-", "");
		console.log(a);
		var urlTmp="/WeatherPredictRestAPI/api/forecast/"+a;

		$.ajax({
			url: urlTmp,
			type: "get", 
			success: function (data, textStatus, xhr) {

				console.log(data)
				$("#cusBlk" ).show();
				//$("#ncusBlk" ).show(); //have to remove
				var c=0;
				var dat="#cusDat";
				var mx="#cusmax";
				var mn="#cusmin";
				for (x in data){
					c=c+1;
					var month = new Array();
					month[0] = "Jan";
					month[1] = "Feb";
					month[2] = "Mar";
					month[3] = "Apr";
					month[4] = "May";
					month[5] = "Jun";
					month[6] = "Jul";
					month[7] = "Aug";
					month[8] = "Sept";
					month[9] = "Oct";
					month[10] = "Nov";
					month[11] = "Dec";

					var datTmp=dat+c;
					var mxTmp=mx+c;
					var mnTmp=mn+c;
					var dateString  = data[x].DATE;
					var year        = dateString.substring(0,4);
					var month1       = dateString.substring(4,6);
					var day         = dateString.substring(6,8);
					var dateTmp        = new Date(year, month1-1, day);
					var date= dateTmp.getDate()+" "+month[dateTmp.getMonth()]+" "+dateTmp.getFullYear();
					var tmx=data[x].TMAX;
					var tmn=data[x].TMIN;
					$(datTmp).text(date);
					$(mxTmp).text("\u2191 "+tmx.toFixed(2)+" \u2109");
					$(mnTmp).text("\u2193 "+tmn.toFixed(2)+" \u2109"); 	 	
				}

				startThirdParty();
			},
			error: function (xhr, textStatus, errorThrown) {
				alert("error in custom API"+xhr);
				startThirdParty();

			}
		});
	});


	function startThirdParty() {
		var lat   = 39.1015,
		long      = -84.5125;
		$("#ncusBlk" ).show();	
		var dateString  = $( "#forDate" ).val().replace("-", "").replace("-", "");
		var year        = dateString.substring(0,4);
		var month1       = dateString.substring(4,6);
		var day         = dateString.substring(6,8);
		var future        = new Date(year, month1-1, day);
		var i;
		for(i=0;i<7;i++){
			if(i!=0){	
				future.setDate(future.getDate() + 1); // add 7 days
			}
			var finalDate = future.getFullYear()+"-" + ((future.getMonth() + 1) < 10 ? '0' : '') + (future.getMonth() + 1) +"-"+ ((future.getDate()) < 10 ? '0' : '')+future.getDate();
			var a= finalDate+"T12:00:00";
			if(lat && long !== '') {
				weatherReport(lat, long,a,i);
			}
		}
	}

	function weatherReport(latitude, longitude, forcdate,i) {
		var apiKey       = '3bc1845c1199700ece90f9295098c1b7',
		//url          = 'https://cors-anywhere.herokuapp.com/https://api.darksky.net/forecast/',
		url          = 'https://api.darksky.net/forecast/',
		lati         = latitude,
		longi        = longitude,
		dt			= forcdate;
		api_call     = url + apiKey + "/" + lati + "," + longi + "," + dt + "?exclude=currently,flags,minutely,hourly,alerts";
		console.log(api_call);
		var days = [
			'Sunday',
			'Monday',
			'Tuesday',
			'Wednesday',
			'Thursday',
			'Friday',
			'Saturday'
			];
		// Celsius button check. Is it toggled or not?
		//var isCelsiusChecked = $('#celsius:checked').length > 0;
		$.ajax({
			url: api_call,
			type: "get", 
			dataType: 'jsonp',

			success: function (forecast, textStatus, xhr) {
				console.log("success")
				console.log(forecast)
				var c=0;
				console.log(forecast.daily.data[0].time)
				var date     = new Date(forecast.daily.data[0].time * 1000),
				day      = days[date.getDay()],
				tempMax = Math.round(forecast.daily.data[0].temperatureMax);
				tempMin = Math.round(forecast.daily.data[0].temperatureMin);

				// If Celsius is checked then convert degrees to celsius
				// for 24 hour forecast results
				/*if(isCelsiusChecked) {
					temp    = fToC(temp);
					tempMax = fToC(tempMax);
					temp = Math.round(temp);
					tempMax = Math.round(tempMax);
				}*/

				var dat="#ncusDat";
				var mx="#ncusmax";
				var mn="#ncusmin";
				c=1+i;

				var datTmp=dat+c;
				var mxTmp=mx+c;
				var mnTmp=mn+c;

				var date= date.toLocaleDateString()
				var tmx=tempMax;
				var tmn=tempMin;
				$(datTmp).text(date);
				$(mxTmp).text("\u2191 "+tmx.toFixed(2)+" \u2109");
				$(mnTmp).text("\u2193 "+tmn.toFixed(2)+" \u2109"); 	 	

			}
		,
		error: function (xhr, textStatus, errorThrown) {
			alert("error in ThirdParty API"+xhr)
		}
		});
	}

	// convert degrees to celsius
	function fToC(fahrenheit) {
		var fTemp  = fahrenheit,
		fToCel = (fTemp - 32) * 5 / 9;

		return fToCel;
	}


});
