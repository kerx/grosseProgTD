import java.util.ArrayList;
import java.util.Iterator;


public class Terminplan implements Iterable<Integer>{
	private ArrayList<Integer> dauer;
	private String name;
	private double wz;
	private double mwz;
	private double lz;
	private double bs;
	private boolean[] geschlossen;
	
	public Terminplan(ArrayList<Integer> dauer, String name, boolean[] geschlossen){
		this.dauer = (ArrayList<Integer>) dauer.clone();
		this.name = name;
		this.geschlossen = geschlossen.clone();
	}
	public double getWz() {
		return wz;
	}
	public void setWz(double wz) {
		this.wz = wz;
		setBs();
	}
	public double getMwz() {
		return mwz;
	}
	public void setMwz(double mwz) {
		this.mwz = mwz;
		setBs();
	}
	public double getLz() {
		return lz;
	}
	public void setLz(double lz) {
		this.lz = lz;
		setBs();
	}
	public double getBs() {
		return bs;
	}
	public String toString(){
		return dauer.toString()+" wz: "+wz+"; mwz: "+mwz+"; lz: "+lz+" bs: "+bs;
	}
	private String dauerZuZeitpunkt(){
		return "";
	}
	private void setBs(){
		this.bs = this.wz+(0.1*this.mwz)+5*lz;
	}
	@Override
	public Iterator<Integer> iterator() {
		Iterator<Integer> iter = this.dauer.iterator();
		return iter;
	}
}
