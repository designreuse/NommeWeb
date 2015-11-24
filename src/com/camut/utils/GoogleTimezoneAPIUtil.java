package com.camut.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONObject;

public class GoogleTimezoneAPIUtil {
	
	public static TimeZone getTimeZoneFromGeoLocation(double latitude, double longitude) {
		Calendar calendar = Calendar.getInstance();
		TimeZone timezone = calendar.getTimeZone();

		// Convert milliseconds to second (UNIX time stamp)
		int timestamp = (int) (calendar.getTimeInMillis() / 1000.0);

		// TODO: Add API key in case access is denied by google. Right now, we
		// do not use API key and it still works.
		String urlString = String.format(
				"https://maps.googleapis.com/maps/api/timezone/json?location=%f,%f&timestamp=%d&sensor=false", latitude,
				longitude, timestamp);

		URL url = null;
		URLConnection connection = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			Log4jUtil.error(e);
		}
		if (url != null) {
			try {
				connection = url.openConnection();
			} catch (IOException e) {
				Log4jUtil.error(e);
			}

			if (connection != null) {
				BufferedReader streamReader = null;
				try {
					streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					Log4jUtil.error(e);
				} catch (IOException e) {
					Log4jUtil.error(e);
				}

				if (streamReader != null) {
					StringBuilder responseStrBuilder = new StringBuilder();
					String inputStr;
					try {
						while ((inputStr = streamReader.readLine()) != null)
							responseStrBuilder.append(inputStr);
					} catch (IOException e) {
						Log4jUtil.error(e);
					}
					JSONObject obj = new JSONObject(responseStrBuilder.toString());

					String status = obj.getString("status");
					if (status.equals("OK")) {
						String timeZoneIdString = obj.getString("timeZoneId");
						return TimeZone.getTimeZone(timeZoneIdString);
					} else {
						Log4jUtil.info("Cannot get timezone. Google Timezone API returned: " + status
								+ System.lineSeparator() + "JSON:" + obj.toString());
					}
				}
			}
		}

		Log4jUtil.info("Something went wrong with getting timezone, setting machine timezone: " + timezone.getID());
		return timezone;
	}
	
	public static DateTime getLocalDateTime(double latitude, double longitude) {
		TimeZone timezone = GoogleTimezoneAPIUtil.getTimeZoneFromGeoLocation(latitude, longitude);
		DateTime localTime = DateTime.now(DateTimeZone.forID(timezone.getID()));
		return localTime;
	}

}
