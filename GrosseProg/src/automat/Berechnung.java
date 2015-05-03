package automat;

import java.util.Arrays;

public class Berechnung {
	private Ausgabe out = new Ausgabe();
	
	public boolean berechne(int betrag, Geldspeicher rueckgeld, Geldspeicher automat){
		if(betrag==0){
			out.ausgeben("rueck: "+Arrays.toString(rueckgeld.toArray()));
			out.ausgeben("auto: "+ Arrays.toString(automat.toArray()));
			return true;
		}else if(betrag>0){
			for(int i = 0;i<5;i++){
				if(automat.getBestand(i)>0){
					rueckgeld.setEuro(i,1);
					automat.setEuro(i,-1);
					if(berechne(betrag-automat.getEuro(i),rueckgeld, automat)){
						return true;
					}else{
						rueckgeld.setEuro(i, -1);
						automat.setEuro(i, 1);
					}
				}
			}
			System.out.println("war hier");
			return false;
		}else{
			System.out.println("oder auch hier");
			return false;
		}
	}
}
