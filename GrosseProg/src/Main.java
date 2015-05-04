import java.io.File;
import java.util.ArrayList;


public class Main {
	private Rechnung rechnung;
	private File file;
	
	public Main(File file){
		this.rechnung = new Rechnung();
		this.file = file;
		einlesen();
	}
	
	public void ausgeben(){
		System.out.println(rechnung.toString());
	}
	
	private void einlesen(){
		ArrayList<String> t1 = new ArrayList<String>();
		t1.add("% Test1");
		t1.add("15 30 20");
		rechnung.einlesen(t1);
	}
	
	public static void main(String[] args) {
		Main main = new Main(new File(""));
		main.ausgeben();
	}
}
