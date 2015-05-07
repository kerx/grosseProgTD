package grosseProg2015;

/**
 * Soll geworfen werden, wenn die Strategie nicht das richtige Format hat
 * 
 * @author td
 *
 */
public class StrategieFormatException extends Exception {
	/**
	 * Erzeugt eine neue StrategieFormatException.
	 * 
	 * @param s
	 *            Fehlerstring, der zur Ausgabe verwendet werden kann
	 */
	public StrategieFormatException(String s) {
		super(s);
	}

}
