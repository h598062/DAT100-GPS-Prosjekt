package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	// conversion factor m/s to miles per hour
	public static  double     MS     = 2.236936;
	private static double     WEIGHT = 80.0;
	private        GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length; i++) {
			if (i != gpspoints.length - 1) {

				distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			}
		}
		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;
		double prev      = gpspoints[0].getElevation();

		for (GPSPoint gpspoint : gpspoints) {
			double e = gpspoint.getElevation();
			if (prev < e) {
				elevation += e - prev;
			}
			prev = e;
		}
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		// Tid ved sluttpunkt - tid ved startpunkt = tid brukt
		return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene
	public double[] speeds() {
		double[] speedTab = new double[gpspoints.length - 1];
		for (int i = 0; i < gpspoints.length - 1; i++) {
			double d = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			int    t = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
			// gjennomsnittsfart = distanse / tid
			// fart fra m/s til km/h = fart * 3.6
			speedTab[i] = d / t * 3.6;
		}
		return speedTab;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	public double maxSpeed() {
		// findMax funksjonen finner den verdien i en tabell som er størst
		return GPSUtils.findMax(this.speeds());
	}

	public double averageSpeed() {
		double d = this.totalDistance();
		int    t = this.totalTime();
		return d / t * 3.6;
	}

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double[] met      = {4.0, 6.0, 8.0, 10.0, 12.0, 16.0};
		double   speedmph = speed * MS;
		double   hr       = (double) secs / 3600;
		if (speedmph < 10) {
			kcal = met[0] * hr * weight;
		} else if (speedmph < 12) {
			kcal = met[1] * hr * weight;
		} else if (speedmph < 14) {
			kcal = met[2] * hr * weight;
		} else if (speedmph < 16) {
			kcal = met[3] * hr * weight;
		} else if (speedmph < 20) {
			kcal = met[4] * hr * weight;
		} else {
			kcal = met[5] * hr * weight;
		}
		return kcal;
	}

	public double totalKcal(double weight) {
		// bruk total tid og gjennomsnittshastighet
		return kcal(weight, this.totalTime(), this.averageSpeed());
	}

	public void displayStatistics() {

		System.out.println("==============================================");
		System.out.printf("%-15s:", "Total Time");
		System.out.println(GPSUtils.formatTime(this.totalTime()));
		System.out.printf("%-15s:", "Total distance");
		System.out.println(GPSUtils.formatDouble(this.totalDistance() / 1000) + " km");
		System.out.printf("%-15s:", "Total elevation");
		System.out.println(GPSUtils.formatDouble(this.totalElevation()) + " m");
		System.out.printf("%-15s:", "Max speed");
		System.out.println(GPSUtils.formatDouble(this.maxSpeed()) + " km/t");
		System.out.printf("%-15s:", "Average speed");
		System.out.println(GPSUtils.formatDouble(this.averageSpeed()) + " km/t");
		System.out.printf("%-15s:", "Energy");
		System.out.println(GPSUtils.formatDouble(this.totalKcal(WEIGHT)) + " kcal");
		System.out.println("==============================================");
	}

}
