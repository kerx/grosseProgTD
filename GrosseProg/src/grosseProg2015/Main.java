package grosseProg2015;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programmstart
 * 
 * @author td
 *
 */
public class Main {
	private final static boolean TEST = false;

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
			Init init = new Init(list);
			init.ausgeben();
			long nachher = System.currentTimeMillis();
			System.out.println("Zeit benötigt: " + (double) (nachher - vorher)
					/ 1000. + " Sekunden");
		} else {
			if (args.length == 1) {
				Init init = new Init(new File(args[0]));
				init.ausgeben();
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
		// int[] werte = new
		// int[]{15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
		int[] werte = new int[] { 0, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30 };
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
