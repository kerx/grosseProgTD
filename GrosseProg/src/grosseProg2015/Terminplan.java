package grosseProg2015;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Terminplan {
	private final String NEWLINE = System.getProperty("line.separator");
	private ArrayList<Integer> dauer;
	private String name;
	private double wz;
	private double mwz;
	private double lz;
	private double bs;
	private int[] strategie;

	/**
	 * Erzeugt ein Terminplan-Objekt
	 * 
	 * @param dauer
	 *            Liste der geplanten Dauern der Termine
	 * @param name
	 *            Name der Strategie
	 * @param strategie
	 *            Array der Strategie zur späteren Ausgabe
	 */
	public Terminplan(ArrayList<Integer> dauer, String name, int[] strategie) {
		this.dauer = (ArrayList<Integer>) dauer.clone();
		this.name = name;
		this.strategie = strategie.clone();
	}

	/**
	 * Setzt alle Durchschnittswerte. Es müssen absolute Werte übergeben werden
	 * (also die Summe aller Werte der Kombinationen)
	 * 
	 * @param wz
	 *            absolute durchschnittliche Wartezeit
	 * @param mwz
	 *            absolute maximale Wartezeit
	 * @param lz
	 *            absoulte Leerlaufzeit des Arztes
	 */
	public void setWerte(double wz, int mwz, int lz) {
		double variationen = getMoeglichkeiten();
		this.wz = wz / variationen;
		this.mwz = (double) mwz / variationen;
		this.lz = (double) lz / variationen;
		setBs();

	}

	/**
	 * Gibt einen Terminplan zu der Strategie zurück
	 * 
	 * @return String des Terminplans
	 */
	public String getDauerString() {
		return dauerZuZeitpunkt();
	}

	/**
	 * Gibt Name und Strategie zurück
	 * 
	 * @return String mit Name und Strategie
	 */
	public String getStrategieString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + NEWLINE);
		for (int strat : this.strategie) {
			sb.append(strat + "\t");
		}
		return sb.toString();
	}

	/**
	 * Wandelt die Dauern-Liste in einen lesbaren Terminplan um
	 * 
	 * @return Terminplan
	 */
	private String dauerZuZeitpunkt() {
		if (dauer.isEmpty()) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			DecimalFormat df = new DecimalFormat("00");
			int index = 0;
			int stunde = 8;
			int minute = 0;
			int sum = 0;
			int[] abschnittwechsel = new int[] { 9, 11, 12 };
			int[] abschnittlaenge = new int[] { 60, 180, 240 };
			for (int value : this.dauer) {
				// geht alle Termindauern durch
				while (abschnittwechsel.length > index && strategie[index] <= 0) {
					// falls die Praxis zwischendurch/am Anfang geschlossen hat,
					// muss dies angezeigt werden
					if (stunde < abschnittwechsel[index]) {
						// wenn der letzte Termin nicht die geschlossene Zeit
						// überdauert
						stunde = abschnittwechsel[index];
						minute = 0;
					}
					sb.append("geschlossen  ");
					index++;
				}
				// für die Kontrolle, ob ein Abschnittwechsel stattfindet
				// wichtig
				sum += value;
				// Stunden und Minuten an den Terminplan anhängen
				sb.append(df.format(stunde) + "." + df.format(minute % 60)
						+ "  ");
				// neue Stunden/Minuten ausrechnen
				minute += value;
				stunde += Math.floor(minute / 60);
				minute %= 60;
				if (abschnittwechsel.length > index
						&& sum >= abschnittlaenge[index]) {
					// Ein Abschnitt wurde verlassen
					// Ist für das feststellen von geschlossene Praxis notwendig
					index++;
				}
			}
			while (abschnittwechsel.length > index && strategie[index] == 0) {
				// Falls am Ende die Praxis am Ende geschlossen ist
				sb.append("geschlossen  ");
				index++;
			}
			return sb.toString();
		}
	}

	/**
	 * Berechnet die Bewertung der Strategie
	 */
	private void setBs() {
		this.bs = this.wz + (0.1 * this.mwz) + 5 * lz;
	}

	/**
	 * Gibt den Strategienamen zurück
	 * 
	 * @return Strategiename
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die durchschnittliche Wartezeit zurück
	 * 
	 * @return durchschnittliche Wartezeit
	 */
	public double getWz() {
		return wz;
	}

	/**
	 * Gibt die durchschnittliche maximale Wartezeit zurück
	 * 
	 * @return durchschnittliche maximale Wartezeit
	 */
	public double getMwz() {
		return mwz;
	}

	/**
	 * Gibt die durchschnittliche Leerlaufzeit des Arztes zurück
	 * 
	 * @return durchschnittliche Leerlaufzeit
	 */
	public double getLz() {
		return lz;
	}

	/**
	 * Gibt die Bewertung der Strategie zurück <br>
	 * bs = wz + 0.1 * mwz + 5 * lz
	 * 
	 * @return Bewertung
	 */
	public double getBs() {
		return bs;
	}

	/**
	 * Gibt die Anzahl der möglichen Variationen zurück
	 * 
	 * @return Anzahl der Variationen
	 */
	public int getMoeglichkeiten() {
		return (int) Math.pow(3, dauer.size());
	}
}
