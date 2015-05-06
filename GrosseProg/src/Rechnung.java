import java.text.DecimalFormat;
import java.util.ArrayList;

public class Rechnung {
	private ArrayList<Terminplan> terminplaene;
	private final String NEWLINE = System.getProperty("line.separator");

	public Rechnung() {
		this.terminplaene = new ArrayList<Terminplan>();
	}

	/**
	 * Liest die übergebene ArrayList ein, speichert die Werte ab und
	 * verarbeitet sie direkt
	 * 
	 * @param input
	 *            Liste mit abwechselnd "Name der Strategie" und "Strategie"
	 *            (z.B. 30 30 30) enthält
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
				String[] split = str.split(" +");
				if (split.length == 3) {
					// schonmal richtige Länge
					int[] split_int = new int[3];
					for (int i = 0; i < 3; i++) {
						// in Integer umwandeln
						try {
							split_int[i] = Integer.parseInt(split[i]);
						} catch (NumberFormatException e) {
							throw new StrategieFormatException(
									"Strategiebeschreibung fehlerhaft. Es dürfen nur Ganzzahlen verwendet werden (Zeile "
											+ rowCount + ").");
						}
					}
					// Strategie verarbeiten
					verarbeite(name, split_int);
					name = "";
				} else {
					// Eingabe hat falsches Format: hier sollten 3 Zahlen in der
					// Reihe stehen
					throw new StrategieFormatException(
							"Strategiebeschreibung fehlerhaft. Es können nur drei Tagesabschnitte definiert werden (Zeile "
									+ rowCount + ").");
				}
			} else {
				// Entweder 2 Zahlen oder Kommentarzeilen hintereinander
				throw new StrategieFormatException(
						"Doppelte Kommentar- oder Strategiezeilen am Stück(Zeile "
								+ rowCount + ").");
			}
		}
		if (terminplaene.isEmpty()) {
			throw new StrategieFormatException(
					"Es wurden keine Strategien übermittelt.");
		}
	}

	/**
	 * Verarbeitet und speichert die übergebenen Daten
	 * 
	 * @param name
	 *            Name der Strategie
	 * @param strategie
	 *            3-Dimensionales Array
	 * @throws StrategieVerarbeitungsException
	 */
	private void verarbeite(String name, int[] strategie)
			throws StrategieVerarbeitungsException {
		int sum = 0;
		int nullCounter = 0;
		ArrayList<Integer> terminListe = new ArrayList<Integer>();
		int[] abschnittslaengen = new int[] { 60, 180, 240 };
		for (int i = 0; i < 3; i++) {
			if (strategie[i] <= 0) {
				// freie Abschnitt für den Arzt
				sum = abschnittslaengen[i];
				nullCounter++;
			} else if (strategie[i] >= 15 && strategie[i] <= 30) {
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
			} else {
				throw new StrategieVerarbeitungsException(
						"Nur Eingaben zwischen 15-30 sind erlaubt (Strategiename \""
								+ name + "\").");
			}
		}
		if (nullCounter >= 3) {
			throw new StrategieVerarbeitungsException(
					"Eine durchgehend geschlossene Praxis kann nicht verarbeitet werden (Strategiename \""
							+ name + "\").");
		}
		Terminplan tp = new Terminplan(terminListe, name, strategie);
		terminplaene.add(tp);
		// starte Rekursiven Aufrug
		berechneZeit(terminListe, tp);
	}

	/**
	 * Beginnt den rekursiven Aufruf berechneZeit()<br>
	 * Rekursive Methode: Berechnet zu der Liste der geplanten Termindauern alle
	 * möglichen Variationen mit wirklichen Termindauern (15, 20, 30) und
	 * berechnet wichtige Durchschnittwerte zur Berwertung der Strategie
	 * 
	 * @param terminListe
	 *            Liste der geplanten Termine
	 * @param tp
	 *            entsprechendes Terminplan-Objekt
	 */
	private void berechneZeit(ArrayList<Integer> terminListe, Terminplan tp) {
		Kombinationsspeicher ks = new Kombinationsspeicher();
		berechneZeit(0, 0, 0, 0, 0, terminListe, ks);
		tp.setWerte(ks.getWz(), ks.getMwz(), ks.getLz());
	}

	/**
	 * <b>BITTE NICHT VERWENDEN<b> <br>
	 * Wird durch berechneZeit(ArrayList<Integer>, Terminplan) gestartet <br>
	 * Rekursive Methode: Berechnet zu der Liste der geplanten Termindauern alle
	 * möglichen Variationen mit wirklichen Termindauern (15, 20, 30) und
	 * berechnet wichtige Durchschnittwerte zur Berwertung der Strategie
	 * 
	 * @param count
	 *            Anzahl der rekusiven Durchläufe bis zum 1. Ergebnis
	 *            (entspricht Baumhöhe)
	 * @param wz
	 *            aufaddierte Wartezeit
	 * @param mwz
	 *            maximale Wartezeit
	 * @param lz
	 *            aufaddierte Leerlaufzeit
	 * @param verzug
	 *            Zeit, die der Arzt mit seinen Terminen im Verzug ist
	 * @param terminList
	 *            Liste der noch kommenden Termine
	 * @param ks
	 *            Zwischenspeicher der Werte der Kombinationen
	 */
	private void berechneZeit(int count, int wz, int mwz, int lz, int verzug,
			ArrayList<Integer> terminListe, Kombinationsspeicher ks) {
		if (terminListe.isEmpty()) {
			// keine Elemente mehr vorhanden
			// In Kombinationsobjekt eintragen
			ks.addWz(wz < 0 ? 0. : ((double) wz / (double) count));
			ks.addMwz(mwz);
			ks.addLz(Math.abs(lz));
		} else {
			// noch Elemente zum verabeiten
			int[] termindauern = new int[] { 15, 20, 30 };
			for (int dauer : termindauern) {
				// jede Termindauer an dieser Stelle einmal einsetzen
				// damit für die folgenden Schleifendurchläufe wieder die
				// gesamte Liste zur Verfügung steht
				ArrayList<Integer> terminListe_clone = (ArrayList<Integer>) terminListe
						.clone();
				int diff = dauer - terminListe_clone.get(0);
				int wz_new = wz + verzug;
				int mwz_new = mwz > verzug ? mwz : verzug;
				int verzug_new = (verzug + diff) > 0 ? verzug + diff : 0;
				int lz_new = lz;
				if (verzug_new == 0 && diff < 0) {
					lz_new += (verzug + diff);
				}
				// behandeltes Element aus dem Klon entfernen
				terminListe_clone.remove(0);
				// nächster Aufruf (eine Ebene tiefer in den Baum gehen)
				berechneZeit(count + 1, wz_new, mwz_new, lz_new, verzug_new,
						(ArrayList<Integer>) terminListe_clone, ks);
			}
		}
	}

	/**
	 * Sucht aus den eingelesen Strategien die beste raus
	 * 
	 * @return beste Strategie
	 */
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
				+ " die beste der eingelesenen Strategien und sollte deshalb bei der Terminvergabe gewählt werden.";
	}

	/**
	 * Gibt alle Strategien in Listenform zurück
	 * 
	 * @return Zeilenweise alle Strategien
	 */
	public String getStrategienString() {
		StringBuilder sb = new StringBuilder("Liste der Strategien\r");
		for (Terminplan t : terminplaene) {
			sb.append(t.getStrategieString() + NEWLINE);
		}
		return sb.toString();
	}

	/**
	 * Gibt alle errechneten Werte und den Terminplan der Strategien zurück
	 * 
	 * @return alle Durchschnittswerte und Terminplan
	 */
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
