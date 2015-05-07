package grosseProg2015;

/**
 * Soll geworfen werden, wenn die Strategie nicht den definierten Richtlienien
 * entspricht
 * 
 * @author td
 *
 */
public class StrategieVerarbeitungsException extends Exception {
	/**
	 * Erzeugt eine neue StrategieFormatException.
	 * 
	 * @param s
	 *            Fehlerstring, der zur Ausgabe verwendet werden kann
	 */
	public StrategieVerarbeitungsException(String s) {
		super(s);
	}

}
