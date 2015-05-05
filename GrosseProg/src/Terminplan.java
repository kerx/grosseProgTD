import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;

public class Terminplan {
	private ArrayList<Integer> dauer;
	private String name;
	private double wz;
	private double mwz;
	private double lz;
	private double bs;
	private int[] strategie;

	public Terminplan(ArrayList<Integer> dauer, String name,
			int[] strategie) {
		this.dauer = (ArrayList<Integer>) dauer.clone();
		this.name = name;
		this.strategie = strategie.clone();
	}

	public void addWz(double wz) {
		this.wz += wz;
	}

	public void addMwz(double mwz) {
		this.mwz += mwz;
	}

	public void addLz(double lz) {
		this.lz += lz;
	}

	public void finish() {
		double variationen = Math.pow(3, dauer.size());
		wz = wz / variationen;
		mwz = mwz / variationen;
		lz = lz / variationen;
		setBs();

	}

	public String toString() {
		return dauerZuZeitpunkt() + " wz: " + wz + "; mwz: " + mwz + "; lz: "
				+ lz + " bs: " + bs;
	}

	private String dauerZuZeitpunkt() {
		if (dauer.isEmpty()) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			DecimalFormat deciFormat = new DecimalFormat("00");
			int index = 0;
			int hour = 8;
			int minute = 0;
			int[] abschnittwechsel = new int[] { 9, 11 };
			for (int value : this.dauer) {
				while (this.strategie[index] == 0) {
					hour = abschnittwechsel[index];
					sb.append("geschlossen  ");
					index++;
				}
				if (this.strategie[index] != value) {
					index++;
				} 
				sb.append(deciFormat.format(hour) + "." + deciFormat.format(minute % 60) + "  ");
				minute += value;
				hour += Math.floor(minute / 60);
				minute %= 60;
			}
			return sb.toString();
		}
	}

	private void setBs() {
		this.bs = this.wz + (0.1 * this.mwz) + 5 * lz;
	}

}
