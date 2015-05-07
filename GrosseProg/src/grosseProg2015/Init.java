package grosseProg2015;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main-, Ein- und Ausgabeklasse
 * 
 * @author td
 *
 */
public class Init {
	private Rechnung rechnung;
	private ArrayList<String> eingabeListe;

	/**
	 * Erzeugt ein Main-Objekt, dass für die Ein- und Ausgabe zuständig ist. Die
	 * angegebene Datei wird direkt verarbeitet.
	 * 
	 * @param file
	 *            einzulesende Daten
	 */
	public Init(File file) {
		this.rechnung = new Rechnung();
		this.eingabeListe = new ArrayList<String>();
		einlesen(file);
		try {
			rechnung.einlesen(eingabeListe);
		} catch (StrategieFormatException | StrategieVerarbeitungsException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Konstruktor für Tests
	 * 
	 * @param list
	 *            Liste von Namen und deren Strategien
	 */
	public Init(ArrayList<String> list) {
		this.rechnung = new Rechnung();
		this.eingabeListe = (ArrayList<String>) list.clone();
		try {
			rechnung.einlesen(eingabeListe);
		} catch (StrategieFormatException | StrategieVerarbeitungsException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Gibt die Liste aller Strategien und derer Bewertungen zurück
	 */
	public void ausgeben() {
		System.out.println(rechnung.getStrategienString());
		System.out.println(rechnung.getTerminplaeneString());
		System.out.println(rechnung.findeBesten());
	}

	/**
	 * Liest die Datei file ein und lässt die Daten verarbeiten
	 */
	private void einlesen(File file) {
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				eingabeListe.add(sc.nextLine());
			}

		} catch (FileNotFoundException e) {
			System.out
					.println("Die Datei wurde in diesem Pfad nicht gefunden.");
			System.exit(0);
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
}
