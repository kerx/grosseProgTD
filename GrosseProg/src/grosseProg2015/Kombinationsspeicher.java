package grosseProg2015;

/**
 * Zwischenspeicher für die Rekusionswerte
 * 
 * @author td
 *
 */
public class Kombinationsspeicher {
	private double wz;
	private int mwz;
	private int lz;

	/**
	 * Gibt die absolute durchscnittliche Wartezeit zurück
	 * 
	 * @return absolute durchscnittliche Wartezeit
	 */
	public double getWz() {
		return wz;
	}

	/**
	 * Addiert die übergebene durchschnittliche Wartezeit auf die bisherige
	 * durchschnittliche Wartezeit auf
	 * 
	 * @param wz
	 *            durchschnittliche Wartezeit
	 */
	public void addWz(double wz) {
		this.wz += wz;
	}

	/**
	 * Gibt die absolute maximale Wartezeit zurück
	 * 
	 * @return absolute maximale Wartezeit
	 */
	public int getMwz() {
		return mwz;
	}

	/**
	 * Addiert die übergebene maximale Wartezeit auf die bisherige maximale
	 * Wartezeit auf
	 * 
	 * @param mwz
	 *            maximale Wartezeit
	 */
	public void addMwz(double mwz) {
		this.mwz += mwz;
	}

	/**
	 * Gibt die absolute Leerlaufzeit des Arztes zurück
	 * 
	 * @return absolute Leerlaufzeit
	 */
	public int getLz() {
		return lz;
	}

	/**
	 * Addiert die übergebene Leerlaufzeit auf die bisherige Leerlaufzeit auf
	 * 
	 * @param lz
	 *            maximale Leerlaufzeit
	 */
	public void addLz(double lz) {
		this.lz += lz;
	}

}
