package automat;

public class Geldspeicher {
	private int[] geldspeicher;
	private int[] wertigkeit = new int[] { 200, 100, 50, 20, 10 };

	public Geldspeicher(int[] geldspeicher) {
		this.geldspeicher = geldspeicher.clone();
	}

	public int getBestand(int von) {
		return geldspeicher[von];
	}

	public int getEuro(int von) {
			return wertigkeit[von];
	}
	
	public void setEuro(int von, int menge){
		geldspeicher[von] += menge;
	}
	
	public int[] toArray(){
		return geldspeicher.clone();
	}
}
