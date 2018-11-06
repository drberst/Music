package music;

import java.util.Formatter;
import java.util.LinkedList;
import java.util.Random;

public class Tab {
	static Note[] roots = { new Note("E2"), new Note("A2"), new Note("D3"), new Note("G3"), new Note("B3"), new Note("E4") };
	Noteg[] lines;
	int[] diffs;
	int openspots = 6;
	//	int hardness;
	double avg;
	int spread;

	public Tab() {
		lines = new Noteg[6];
	}

	static Tab genRandom(LinkedList<Noteg> notes) {
		Random r = new Random();
		Tab t = new Tab();
		//		int filled = 0;
		int i = 0;
		LinkedList<Noteg> temp = new LinkedList<Noteg>();
		for (Noteg n : notes) {
			temp.push(n);
		}
		while (t.openspots > 0 && notes.size() > 0) {
			int index = r.nextInt(temp.size());
//			System.out.println(temp);
			Noteg n = temp.get(index);
			if (t.lines[n.str] == null) {
//				notes.get(index);
				
				temp.remove(n);
//				used.push(n);
				t.put(n);
			}
			if (i > 100) {
				System.err.println("This is getting silly");
				break;
			}
			i++;
		}
		return t;
	}

	void put(Noteg aNote) {
		if (lines[aNote.str] != null) {
			System.err.println("THIS SHOULDN'T HAPPEN gonna go ahead and set openspots to 0");
			openspots = 0;
		} else {
			lines[aNote.str] = aNote;
			openspots--;
		}
	}

	Integer hardness() {
		int hardness = 0;
		//		double avg = 0;

		diffs = new int[6];

		for (int i = 1; i < lines.length; i++) {
			if (lines[i] == null)
				return 999;
			if (lines[i - 1] == null)
				return 999;
			int a = lines[i].fret;
			int b = lines[i - 1].fret;
			int len = Math.abs(a - b);
			//			avg+=len;
			diffs[i] = len;
			hardness += len;
			//			if(a==0) continue;
			if (a == 0 || b == 0) {
				//				System.err.println("THAT'S EASY");
//				len = 0;
			}
			if (len > 5) {
				hardness += 100;
				//				System.err.printf("%d to %d=%d\n", b, a, len);
			}

			//			System.out.printf("%d to %d=%d\n", b, a, len);

			//			System.out.println("Stretch is:"+stretch);
		}
		//		if (hardness < 100)
		//			System.err.println("avg:"+avg/array.length);
		//		avg/=6;

		return hardness;
	}

	String stats() {
		int count = 6;
		int sum = 0;
		int[] values = new int[6];
		int low = 100;
		int high = 0;
		for (int i = 0; i < lines.length; i++) {
			int val = lines[i].fret;
			values[i] = val;
			sum += val;
			if (val > high)
				high = val;
			if (val < low)
				low = val;

		}
		double avg = 1.0 * sum / count;
		double stddev = 0;
		for (int num : values) {
			stddev += Math.pow(num - avg, 2);
		}

		double stddone = Math.sqrt(stddev / (count - 1));
		return String.format("sum=%d, count=%d avg=%.3f, spr=%d, dev=%.3f", sum, count, avg, high - low, stddone);
	}

	public String simple() {
		StringBuilder sb = new StringBuilder();
		sb.append("h");
		sb.append(hardness());
		for (int i = 0; i < lines.length; i++) {
			Noteg line = lines[i];
			String s = (line == null) ? "-" : "" + lines[i].fret;
			sb.append(String.format("%3s", s));
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		Formatter f = new Formatter();
		//		f.format("  ");
		String fixedwidth = "%4s";
		Formatter names = new Formatter();
		Formatter vals = new Formatter();
		String info = String.format("hardness=%d", hardness());
		//		String info2 = String.format("%s", stats());

		int z = 1;
		for (Noteg n : lines) {
			System.out.println(n);
			String diff = (z < 6) ? diffs[z] + "" : "";
			names.format("%-3s%-3s", n.fullName(), diff);
			vals.format("%-6s", n.fret);
			//			names+=n.fullName()+" ";
			//			vals+=n.fret+" ";
			z++;
		}
		//		names=String.format("%2d %2d %2d %2d %2d %2d", lines[0],lines[1],lines[2],lines[3],lines[4],lines[5]);
		//		String s = f.toString();
		//		f.close();
		//		System.out.println(s);
		String line1 = names.toString() + info;
		String line2 = vals.toString() + stats();
		return String.format("Tab |%s\n  >>|%s", line1, line2);
		//		return String.format("Tab [lines=%s, hardness=%s] %s", vals, hardness(),names);
	}

}
