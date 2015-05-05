import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private Rechnung rechnung;
	private File file;

	public Main(File file) {
		this.rechnung = new Rechnung();
		this.file = file;
		einlesen();
	}

	public void ausgeben() {
		System.out.println(rechnung.getStrategienString());
		System.out.println(rechnung.getTerminplaeneString());
		System.out.println(rechnung.findeBesten());
	}

	private void einlesen() {
		// t1.add("% Test1");
		// t1.add("30 30 30");
		// t1.add("% Test2");
		// t1.add("15 30 20");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			ArrayList<String> list = new ArrayList<String>();
			while (sc.hasNextLine()) {
				list.add(sc.nextLine());
			}

			rechnung.einlesen(list);
		} catch (FileNotFoundException e) {
			System.out
					.println("Die Datei wurde in diesem Pfad nicht gefunden.");
			System.exit(0);
		} catch (StrategieFormatException|StrategieVerarbeitungsException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

	public static void main(String[] args) {
		if(args.length ==1){
		Main main = new Main(new File(args[0]));
		main.ausgeben();
		}else{
			System.out.println("Bitte ein Dateipfad beim Aufruf mit übergeben.");
		}
	}
}
