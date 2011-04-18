package net.mcbat.MobBounty.Utils;

public class Time {
	public static String Day = "Day";
	public static String Sunset = "Sunset";
	public static String Night = "Night";
	public static String Sunrise = "Sunrise";
	
	public static long ToDay = 0;
	public static long ToSunset = 10000;
	public static long ToNight = 11500;
	public static long ToSunrise = 18500;
	
	public static long DayLength = 10000;
	public static long SunsetLength = 1500;
	public static long NightLength = 7000;
	public static long SunriseLength = 1500;
	
	public static long getTo(String timePeriod) {
		if (timePeriod == Time.Sunset) return Time.ToSunset;
		else if (timePeriod == Time.Night) return Time.ToNight;
		else if (timePeriod == Time.Sunrise) return Time.ToSunrise;
		
		return 0;
	}

	public static long getLength(String timePeriod) {
		if (timePeriod == Time.Day) return Time.DayLength;
		else if (timePeriod == Time.Sunset) return Time.SunsetLength;
		else if (timePeriod == Time.Night) return Time.NightLength;
		else if (timePeriod == Time.Sunrise) return Time.SunriseLength;
		
		return 0;
	}
	
	public static String getTimeOfDay(long time) {
		if (time >= Time.ToDay && time < Time.ToSunset)
			return Time.Day;
		else if (time >= Time.ToSunset && time < Time.ToNight)
			return Time.Sunset;
		else if (time >= Time.ToNight && time < Time.ToSunrise)
			return Time.Night;
		
		return Time.Sunrise;
	}
}
