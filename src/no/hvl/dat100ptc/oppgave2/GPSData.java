package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	protected int        antall = 0;
	private   GPSPoint[] gpspoints;

	public GPSData(int n) {
		this.gpspoints = new GPSPoint[n];
		antall         = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;

		if (antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;
			antall++;
			inserted = true;
		}

		return inserted;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint;

		gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);

		return insertGPS(gpspoint);
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");
		for (GPSPoint gpspoint : gpspoints) {
			System.out.println(gpspoint.toString());
		}
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
