package no.hvl.dat100ptc.oppgave3;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	private static final int R         = 6371000; // jordens radius
	private static final int TEXTWIDTH = 10;

	public static double findMax(double[] da) {

		double max;

		max = da[0];

		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];

		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}

		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] lats = new double[gpspoints.length];
		for (int i = 0; i < gpspoints.length; i++) {
			lats[i] = gpspoints[i].getLatitude();
		}
		return lats;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longs = new double[gpspoints.length];
		for (int i = 0; i < gpspoints.length; i++) {
			longs[i] = gpspoints[i].getLongitude();
		}
		return longs;
	}

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		latitude1  = Math.toRadians(gpspoint1.getLatitude());
		latitude2  = Math.toRadians(gpspoint2.getLatitude());
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());

		double deltaLat  = latitude2 - latitude1;
		double deltaLong = longitude2 - longitude1;

		// formelen fra oppgaven:
		// (sin(deltaLat/2))^2 + cos(latitude1)*cos(latitude2) * (sin(deltaLong/2))^2
		double a = Math.pow(Math.sin(deltaLat / 2), 2) +
		           Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(deltaLong / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		d = R * c;
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int    secs;
		double speed;

		// tid brukt = sluttid - starttid
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		// formel for gjennomsnittsfart = distanse / tid brukt
		// for å gjøre om fra m/s til km/h så må man gange med 3.6
		speed = distance(gpspoint1, gpspoint2) / secs * 3.6;

		return speed;
	}

	public static String formatTime(int secs) {

		final String TIMESEP = ":";
		String timestr = String.format("%02d", secs / 3600) + TIMESEP +
		                 String.format("%02d", (secs % 3600) / 60) + TIMESEP +
		                 String.format("%02d", (secs % 3600) % 60);
		//                      "%10s"
		timestr = String.format("%" + TEXTWIDTH + "s", timestr);
		return timestr;
	}

	public static String formatDouble(double d) {

		String str = String.format("%.2f", d);
		//                  "%10s"
		str = String.format("%" + TEXTWIDTH + "s", str);
		return str;
	}
}
