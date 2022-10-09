package no.hvl.dat100ptc.oppgave1;

import no.hvl.dat100ptc.TODO;

public class GPSPoint {

	// Objektvariabler
	private int    time;
	private double latitude;
	private double longitude;
	private double elevation;

	// Konstruktør funksjon
	public GPSPoint(int time, double latitude, double longitude, double elevation) {
		this.time      = time;
		this.latitude  = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
	}

	// Hent/sett-metoder
	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	// Til tekststreng metode
	public String toString() {
		return this.time + " (" + this.latitude + "," + this.longitude + ") " + this.elevation + "\n";
	}
}
