package automat;

public class Eingabe {
	public static void main(String[] args) {
		Berechnung b = new Berechnung();
		b.berechne(360, new Geldspeicher(new int[]{0,0,0,0,0}), new Geldspeicher(new int[]{12,0,0,1,2}));
	}
}
