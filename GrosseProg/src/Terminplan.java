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

	public Terminplan(ArrayList<Integer> dauer, String name, int[] strategie) {
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
		double variationen = getMoeglichkeiten();
		wz = wz / variationen;
		mwz = mwz / variationen;
		lz = lz / variationen;
		setBs();

	}

	public String getDauerString() {
		return dauerZuZeitpunkt();
	}

	public String getStrategieString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + NEWLINE);
		for (int strat : this.strategie) {
			sb.append(strat + "\t");
		}
		return sb.toString();
	}

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
				while (abschnittwechsel.length > index && strategie[index] == 0) {
					if (stunde < abschnittwechsel[index]) {
						stunde = abschnittwechsel[index];
						minute = 0;
					}
					sb.append("geschlossen  ");
					index++;
				}
				sb.append(df.format(stunde) + "." + df.format(minute % 60) + "  ");
				minute += value;
				sum += minute;
				stunde += Math.floor(minute / 60);
				minute %= 60;
				if (abschnittwechsel.length > index
						&& sum >= abschnittlaenge[index]) {
					index++;
				}
			}
			while (abschnittwechsel.length > index && strategie[index] == 0) {
				sb.append("geschlossen  ");
				index++;
			}
			return sb.toString();
		}
	}

	private void setBs() {
		this.bs = this.wz + (0.1 * this.mwz) + 5 * lz;
	}

	public String getName() {
		return name;
	}

	public double getWz() {
		return wz;
	}

	public double getMwz() {
		return mwz;
	}

	public double getLz() {
		return lz;
	}

	public double getBs() {
		return bs;
	}

	public int getMoeglichkeiten() {
		return (int) Math.pow(3, dauer.size());
	}
}
