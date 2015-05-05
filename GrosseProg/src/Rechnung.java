import java.util.ArrayList;

public class Rechnung {
	private ArrayList<Terminplan> terminplaene;

	public Rechnung() {
		this.terminplaene = new ArrayList<Terminplan>();
	}

	/**
	 * Methode um verarbeite zu verkleinern und den Code übersichtlicher zu
	 * gestalten
	 * 
	 * @param input
	 */
	public void einlesen(ArrayList<String> input) {
		String name = "";
		for (String str : input) {
			// über alle Zeilen
			if (name.equals("") && str.startsWith("%")) {
				// Kommentarzeile (Name der Strategie
				name = str;
			} else if (!name.equals("")) {
				// Name gesetzt: Also kommen jetzt drei Zahlen
				String[] split = str.split(" ");
				if (split.length == 3) {
					// schonmal richtige Länge
					int[] split_int = new int[3];
					for (int i = 0; i < 3; i++) {
						// in Integer umwandeln
						try {
							split_int[i] = Integer.parseInt(split[i]);
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException(
									"Nicht genau 3 Zahlen als Strategie angegeben");
						}
					}
					// Strategie verarbeiten
					verarbeite(name, split_int);
					name = "";
				}
			} else {
				// Entweder 2 Zahlen oder Kommentarzeilen hintereinander
				throw new IllegalArgumentException(
						"Mehrere Kommentar- oder Strategiezeilen am Stück");
			}
		}
	}

	private void verarbeite(String name, int[] strategie) {
		int sum = 0;
		ArrayList<Integer> terminListe = new ArrayList<Integer>();
		int[] abschnittslaengen = new int[] { 60, 180, 240 };
		for (int i = 0; i < 3; i++) {
			if (strategie[i] <= 0) {
				// freie Abschnitt für den Arzt
				sum = abschnittslaengen[i];
			} else {
				// richtiger Arbeitsabschnitt
				while (sum < abschnittslaengen[i]) {
					// solange wir uns noch in dem i-ten Abschnitt befinden
					sum += strategie[i];
					if (sum <= 240) {
						// Nur wenn der Endtermin (Praxis geschlossen) nicht
						// überschritten wird
						terminListe.add(strategie[i]);
					}
				}
			}
		}
		Terminplan tp = new Terminplan(terminListe, name, strategie);
		terminplaene.add(tp);
		berechneZeit(0, 0, 0, 0, 0, terminListe, tp);
		tp.finish();
	}

	/**
	 * TODO diff? mwz? rekursion falsch!
	 * 
	 * @param count
	 * @param wz
	 * @param mwz
	 * @param mwz_sum
	 * @param lz
	 * @param terminlaenge
	 * @param termin
	 * @return
	 */
	private void berechneZeit(int count, int wz, int mwz, int lz, int verzug,
			ArrayList<Integer> terminlaenge, Terminplan termin) {
		if (terminlaenge.isEmpty()) {
			// keine Elemente mehr vorhanden
			termin.addWz(wz < 0 ? 0. : ((double) wz / (double) count));
			termin.addMwz((double) mwz);
			termin.addLz(Math.abs(lz));
		} else {
			// noch Elemente zum verabeiten
			int[] termindauern = new int[] { 15, 20,30};
			for (int dauer : termindauern) {
				int terminlaenge_save = terminlaenge.get(0);
				int diff = dauer - terminlaenge_save;
				int wz_new = wz + verzug;
				int mwz_new = mwz > verzug ? mwz : verzug;
				int verzug_new = (verzug + diff) > 0 ? verzug + diff : 0;
				int lz_new = lz;
				if (verzug_new == 0 && diff < 0) {
					lz_new += (verzug+diff);
				}
				// behandeltes Element entfernen
				terminlaenge.remove(0);
				// nächster Aufruf
				berechneZeit(count + 1, wz_new, mwz_new, lz_new, verzug_new,
						(ArrayList<Integer>) terminlaenge.clone(), termin);
				terminlaenge.add(0,terminlaenge_save);
			}
		}
	}

	private String findeBesten() {
		return "";
	}

	public String toString() {
		return terminplaene.toString();
	}
}
