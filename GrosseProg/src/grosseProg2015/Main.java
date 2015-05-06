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
public class Main {
	private Rechnung rechnung;
	private ArrayList<String> eingabeListe;
	private final static boolean TEST = true;

	/**
	 * Erzeugt ein Main-Objekt, dass für die Ein- und Ausgabe zuständig ist. Die
	 * angegebene Datei wird direkt verarbeitet.
	 * 
	 * @param file
	 *            einzulesende Daten
	 */
	public Main(File file) {
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
	public Main(ArrayList<String> list) {
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

	/**
	 * Programmstart
	 * 
	 * @param args
	 *            Name/Pfad einer Datei
	 */
	public static void main(String[] args) {
		if (TEST) {
			System.out.println("Test an!");
			ArrayList<String> list = creatTestList();
			long vorher = System.currentTimeMillis();
			Main main = new Main(list);
			main.ausgeben();
			long nachher = System.currentTimeMillis();
			System.out.println("Zeit benötigt: " + (double) (nachher - vorher)
					/ 1000. + " Sekunden");
		} else {
			if (args.length == 1) {
				Main main = new Main(new File(args[0]));
				main.ausgeben();
			} else {
				System.out
						.println("Bitte einen existierenden Dateipfad beim Aufruf mit übergeben.");
			}
		}
	}

	/**
	 * Generiert Testeingaben
	 * 
	 * @return Liste von Testeingaben
	 */
	private static ArrayList<String> creatTestList() {
		ArrayList<String> list = new ArrayList<String>();
		// Testwerte definieren
		//int[] werte = new int[]{15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
		int[] werte = new int[]{0,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
		// ales durchlaufen
		for (int i = 0; i < werte.length; i++) {
			for (int j = 0; j < werte.length; j++) {
				for (int k = 0; k < werte.length; k++) {
					if (werte[i] == 0 && werte[j] == 0 && werte[k] == 0) {
						// Fehlerfall überspringen
						continue;
					}
					list.add("% " + werte[i] + " " + werte[j] + " " + werte[k]);
					list.add(werte[i] + " " + werte[j] + " " + werte[k]);
				}
			}
		}
		return list;
	}
}
