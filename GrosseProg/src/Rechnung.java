import java.text.DecimalFormat;
import java.util.ArrayList;

public class Rechnung {
	private ArrayList<Terminplan> terminplaene;
	private final String NEWLINE = System.getProperty("line.separator");

	public Rechnung() {
		this.terminplaene = new ArrayList<Terminplan>();
	}

	/**
	 * Methode um verarbeite zu verkleinern und den Code übersichtlicher zu
	 * gestalten
	 * 
	 * @param input
	 */
	public void einlesen(ArrayList<String> input)
			throws StrategieFormatException, StrategieVerarbeitungsException {
		String name = "";
		// für Fehlerausgaben
		int rowCount = 0;
		for (String str : input) {
			rowCount++;
			// über alle Zeilen
			if (name.equals("") && str.startsWith("%")) {
				// Kommentarzeile (Name der Strategie)
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
							throw new StrategieFormatException(
									"Strategiebeschreibung in Zeile "
											+ rowCount
											+ " fehlerhaft. Es dürfen nur Ganzzahlen verwendet werden.");
						}
					}
					// Strategie verarbeiten
					verarbeite(name, split_int);
					name = "";
				} else {
					// Eingabe hat falsches Format: hier sollten 3 Zahlen in der
					// Reihe stehen
					throw new StrategieFormatException(
							"Strategiebeschreibung in Zeile "
									+ rowCount
									+ " fehlerhaft. Es können nur drei Tagesabschnitte definiert werden.");
				}
			} else {
				// Entweder 2 Zahlen oder Kommentarzeilen hintereinander
				throw new StrategieFormatException(
						"Mehrere Kommentar- oder Strategiezeilen " + rowCount
								+ " am Stück.");
			}
		}
		if (terminplaene.isEmpty()) {
			throw new StrategieFormatException(
					"Es wurden keine Strategien übermittelt.");
		}
	}

	private void verarbeite(String name, int[] strategie)
			throws StrategieVerarbeitungsException {
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
		if (terminListe.size() > 16) {
			// würde zulange dauern
			throw new StrategieVerarbeitungsException(
					"Die Berechnung für "
							+ terminListe.size()
							+ " Termine würde zu lange dauern. Die Strategie wurde aus der Berechnung entfernt.");
		}
		Terminplan tp = new Terminplan(terminListe, name, strategie);
		terminplaene.add(tp);
		berechneZeit(0, 0, 0, 0, 0, terminListe, tp);
		tp.finish();
	}

	/**
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
			int[] termindauern = new int[] { 15, 20, 30 };
			for (int dauer : termindauern) {
				int terminlaenge_save = terminlaenge.get(0);
				int diff = dauer - terminlaenge_save;
				int wz_new = wz + verzug;
				int mwz_new = mwz > verzug ? mwz : verzug;
				int verzug_new = (verzug + diff) > 0 ? verzug + diff : 0;
				int lz_new = lz;
				if (verzug_new == 0 && diff < 0) {
					lz_new += (verzug + diff);
				}
				// behandeltes Element entfernen
				terminlaenge.remove(0);
				// nächster Aufruf
				berechneZeit(count + 1, wz_new, mwz_new, lz_new, verzug_new,
						(ArrayList<Integer>) terminlaenge.clone(), termin);
				terminlaenge.add(0, terminlaenge_save);
			}
		}
	}

	public String findeBesten() {
		String bestName = "";
		DecimalFormat df = new DecimalFormat("#0.0000");
		double bestBs = Double.MAX_VALUE;
		for (Terminplan t : terminplaene) {
			if (t.getBs() < bestBs) {
				bestBs = t.getBs();
				bestName = t.getName();
			}
		}
		return "Die Strategie \""
				+ bestName
				+ "\" ist mit einer Bewertung von "
				+ df.format(bestBs)
				+ " die beste der eingelesenen Stragtegien und sollte deshalb bei der Terminvergabe gewählt werden.";
	}

	public String getStrategienString() {
		StringBuilder sb = new StringBuilder("Liste der Strategien\r");
		for (Terminplan t : terminplaene) {
			sb.append(t.getStrategieString() + NEWLINE);
		}
		return sb.toString();
	}

	public String getTerminplaeneString() {
		StringBuilder sb = new StringBuilder();
		for (Terminplan t : terminplaene) {
			sb.append(t.getName() + NEWLINE);
			sb.append("Terminverteilung bei dieser Strategie:" + NEWLINE);
			sb.append(t.getDauerString() + NEWLINE);
			sb.append(NEWLINE);
			sb.append("Bei "
					+ t.getMoeglichkeiten()
					+ " Kombinationen der Behandlungsdauern ergeben sich folgende Zeiten:"
					+ NEWLINE);
			DecimalFormat df = new DecimalFormat("#0.0000");
			sb.append("durchschnittliche mittlere Wartezeit \t WZ \t= "
					+ df.format(t.getWz()) + NEWLINE);
			sb.append("durchschnittliche maximale Wartezeit \t MWZ \t= "
					+ df.format(t.getMwz()) + NEWLINE);
			sb.append("durchschnittliche LeerLaufzeit \t\t LZ \t= "
					+ df.format(t.getLz()) + NEWLINE);
			sb.append("Gesamtbewertung der Strategie \t\t BS \t= "
					+ df.format(t.getBs()) + NEWLINE);
			sb.append(NEWLINE);
		}
		return sb.toString();
	}
}
