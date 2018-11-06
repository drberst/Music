package music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;


class Noteg extends Note {
	int fret;
	int str;
	int index;

	Noteg(Note aNote, int aStr, int aFret) {
		this.CopyFrom(aNote);
		fret = aFret;
		str = aStr;
	}

	@Override
	public String toString() {
		return String.format("%s(%d,%d)", fullName(), str, fret);//+"."+index;
	}
}

public class Guitar {
	//	static Note[] strs = { new Note("E4"), new Note("B3"), new Note("G3"), new Note("D3"), new Note("A2"), new Note("E2") };
	static Note[] strs = { new Note("E2"), new Note("A2"), new Note("D3"), new Note("G3"), new Note("B3"), new Note("E4") };
	Noteg[][] fretboard;
	int frets = 13;
	//	int 
	//	Integer[][] help;

	LinkedList<Noteg> notes;
	static int min = 28;
	static int max = 64;

	public Guitar() {
		super();
		fretboard = new Noteg[6][frets];
		//		help = new Integer[6][13];
		notes = new LinkedList<Noteg>();
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Guitar g = new Guitar();
		//		g.set(77, new Note("G7"));
		//		g.fill();
		//		g.add(new Note("C"));
		//		g.add(new Note("C4"));
		//		g.add(new Note("E"));
		//		g.add(new Note("G"));
		System.out.println(g.fullBoard());
		//		System.out.println(g.get(26));
		//		System.out.println("list:"+g.findAll(new Note("A3")));
		//		System.out.println(g.findAll(new Note("A3")));
		Guitar notes = new Guitar();
		notes.addAll2(new Note("C"));
		notes.addAll2(new Note("E"));
		notes.addAll2(new Note("G"));
		
		System.out.println(notes);
		HashMap<String, Tab> m = new HashMap<String, Tab>();
		int last = 0;
		int count;
		boolean hasitbeenasecond = false;;
		System.out.println("Starting random, time is "+(System.currentTimeMillis() - start));
		for (count = 0; !hasitbeenasecond; count++) {
			Tab t = Tab.genRandom(notes.notes);
//			System.out.println(notes.notes);
			//			if(m.get(t.simple()!=null)
			//			System.out.println(t.simple());
			if (m.put(t.simple(), t) == null) {
//				System.err.println("Found a new one! m="+m.size());
				last = count;
			} else {
//				System.err.println((i-last)+" without incident");
			}
//			if (count - last >= 100) {
			
			if(count > 0 && count%1000==0) {
				long time = System.currentTimeMillis() - start;
				hasitbeenasecond = time >= 1000;
				if (time%1000 <10)
				System.out.println(time);
//				System.err.println("dang this is too hard");
//				break;
			}
		}
		
		//		Tabl
		Tab[] all = m.values().toArray(new Tab[m.size()]);

		Arrays.sort(all, new Comparator<Tab>() {
			public int compare(Tab h1, Tab h2) {
				return h1.hardness().compareTo(h2.hardness());
			}
		});
		System.out.printf("WOW THERE'S %d of them\n", all.length);

		if (all.length < 5) {
			for (int i = 0; i < all.length; i++) {
				System.out.println(all[i].simple());
			}
		} else {
			System.out.println("best 10");
			for (int i = 0; i < 5; i++) {
				System.out.println(all[i].simple());
			}
			System.out.println("\nworst 10");

			for (int i = 0; i < 5; i++) {
				System.out.println(all[all.length - 5 + i].simple());
			}
		}
		System.out.printf("we done, count was %d, total time was %d ms\n", count,System.currentTimeMillis() - start);
	}
	public static Noteg noteOnString(int str, int interval) {
		Note root = strs[5-str];
		Noteg result = new Noteg(root.transpose(interval), str, interval);
		return result;
	}
	LinkedList<Noteg> findAll(Note aNote) {
		//		int lowest = aNote.value-min;
		//		int lowest = 
		Guitar x = new Guitar();
		x.addAll(aNote);
		System.out.println(x);
		//		x.fill();
		//		x.fretboard = new Noteg[6][13];
		//		for (int str = 0; str < fretboard.length; str++) {
		////			System.out.print((str + 1) + "|");
		//			for (int fret = 0; fret < fretboard[0].length; fret++) {
		//				Note n = strs[str].transpose(fret);
		//		
		////				System.out.printf("%s vs %s\n", aNote,n );
		//				if(aNote.name.equals(n.name)) 
		//					x.add(aNote);
		//			}
		//		}

		//		while(fret >= 0) {
		//			res.add(pos);
		//			fret-=5;
		//			lowest-=0;
		//			lowest-=5;
		//			pos+=8;
		//			fret = lowest%fretboard[0].length;

		//			z++;
		//			System.out.println(fret);
		//		}
		//		res.add(aNote.value-28);
		//		res.add(aNote.value-28+8);
		//		res.add(aNote.value-28+8+8);
		//		res.add(aNote.value-28+8+8+8);

		//		System.out.println("GONNA GO AHEAD AND PREDICT: "+res.toString());
		//		for (int i = 0; i < 78; i++) {
		//			Note n = get(i);
		//			if(n.value == aNote.value) {
		//				System.out.printf("%s at %d (%d,%d)\n",n,i,i/fretboard[0].length,i%fretboard[0].length);
		//				x.add(n);
		////				res.add(i);
		//			}
		//		}
		//		for (Note n : strs) {
		//			
		//		}
		//		return res;
		return x.notes;
	}

	String tab() {
		//		String[] tab = new String[6];
		//		int[] finger = new int[6];
		//		int filled = 0;
		//		for (int i = 0; i < tab.length; i++) {
		//			Note root = strs[5-i];
		//			
		//		}
		//		int tabi = 0;
		//		System.out.println(notes);
		int easiest = 100;
		int besti = 0;
		String[] result = new String[6];
		int size = 100;
		int[] hards = new int[size];
		String[] tabs = new String[size];
		Integer[][] all = new Integer[size][6];
		ArrayList<Tab> goodones = new ArrayList<Tab>();

		int count = 0;
		int delta = 0;
		for (int i = 0; i < all.length; i++) {
			String[] tab = new String[6];
			int filled = 0;
			Tab t = new Tab();

			while (filled < 6) {
				Random r = new Random();
				Noteg n = notes.get(r.nextInt(notes.size()));

				if (all[count][5 - n.str] == null) {
					//					t.lines[5 - n.str] = n;
					t.put(n);
					tab[5 - n.str] = String.format("%2d", n.fret);//n.fullName() + "\n" + n.fret;
					all[count][5 - n.str] = n.fret;

					filled++;
				}
			}

			int hard = t.hardness();//hardness(all[count]);
			String s = String.join("\n", tab);
			hards[count] = hard;
			tabs[count] = s;
			if (hard < easiest) {
				System.out.println("EASY! Only took me " + (count - delta) + "tries");
				easiest = hard;
				besti = count;
				result = tab;
				delta = count;
			}
			if (hard < 10)
				goodones.add(t);
			count++;
		}

		System.out.println("count: " + count);
		System.out.println("easiest: " + easiest);
		System.out.println("index of easy: " + besti);

		Formatter f = new Formatter();
		f.format("  ");
		String fixedwidth = "%4s";
		for (int i = 0; i < all.length; i++) {
			f.format(fixedwidth, i);
		}
		f.format("\n");
		f.format("h|");
		for (int i = 0; i < size; i++) {
			int hard = hards[i];
			if (hard < 10)
				f.format(fixedwidth, ">>" + hards[i]);
			else
				f.format(fixedwidth, hards[i]);
		}
		f.format("\n\n");
		for (int str = 0; str < all[0].length; str++) {
			f.format((4 - str + 1) + "|");
			for (int j = 0; j < all.length; j++) {
				f.format(fixedwidth, all[j][str]);
			}
			f.format("\n");
		}

		String s = f.toString();
		f.close();
		System.out.println(s);
		for (Tab t : goodones) {
			System.out.println(t);
		}
		return String.join("\n", result);
	}

	String tab2() {
		System.out.println(notes);
		String[] result = new String[6];

		int count = 0;
		int easiest = 100;
		while (count < 1000) {
			Integer[] finger = new Integer[6];
			String[] tab = new String[6];
			int filled = 0;
			while (filled < 6) {
				Random r = new Random();
				Noteg n = notes.get(r.nextInt(notes.size()));
				if (finger[5 - n.str] == null) {
					//				System.out.printf("random note is %s\n",n);
					tab[5 - n.str] = n.fullName() + "=" + n.fret;
					finger[5 - n.str] = n.fret;
					filled++;
				}

			}
			int hard = -1;//hardness(finger);
			if (hard < easiest) {
				easiest = hard;
				result = tab;
			}
			count++;
			//			System.out.println("hardness:" + hardness(finger));
		}
		System.out.println("count: " + count);
		//		for (Noteg n : notes) {
		//			if (tab[5-n.str] == null) { 
		//				tab[5-n.str] = n.fullName()+"="+n.fret;
		//			}
		////			System.out.printf("%d\n", positionOf(n));
		//		}

		return String.join("\n", result);
	}

	String find(Note n) {
		int pos = n.value - 28;
		//		if (po)
		//		if(pos >= 19) {
		//			pos++;
		//		}

		int str = pos / fretboard[0].length;
		int fret = pos % fretboard[0].length;
		//		if(str==5) return 1000;
		//		System.out.printf("%s at %d (%d,%d)\n",n,pos,str,fret);

		//		if(fret > ) {
		//			
		//		}
		int lowest = pos;
		return "";
		//		return String.format("%s:%d,%d",n.fullName(),str,fret);
		//		while(lowest < 12)
		//		int highest = pos-12;
		//		return highest;
		//		return pos+12*str;
		//		if(str == 0) return 10000;
		//		return help[str][fret];
		//		System.out.printf("posOf[%s]=[%s]\n", n,get(pos));
		//		return help
		//		return pos;
	}

	Note get(int pos) {
		int str = pos / fretboard[0].length;
		int fret = pos % fretboard[0].length;
		Note n = fretboard[str][fret];
		//		n.value - 12 * (pos / 12)
		//		System.out.printf("pos=%d/[%d,%d] n=[%s] or [%s]\n", pos, str+1, fret, n, new Note(strs[str].value+fret));
		return n != null ? n : new Note(strs[str].value + fret);
		//		return n;
	}

	Noteg set(int str, int fret, Note aNote) {
		Noteg n = new Noteg(aNote, str, fret);
		//		n.index = pos;
		fretboard[str][fret] = n;
		return n;
	}

	Noteg set(int pos, Note aNote) {
		int str = pos / fretboard[0].length;
		int fret = pos % fretboard[0].length;
		Noteg n = new Noteg(aNote, str, fret);
		n.index = pos;
		fretboard[str][fret] = n;
		return n;
	}

	void fill() {
		int capacity = fretboard.length * fretboard[0].length;
		for (int i = 0; i < capacity; i++) {
			Note n = get(i);

			//			n.value = i+28;
			set(i, n);
			//			help[i/13][i%13] = i;
		}
		//		try {
		//			for (int i = 0; i < strs.length; i++) {
		//				Note root = strs[i];
		//				for (int j = 0; j < fretboard[0].length; j++) {
		//					Note n = new Note(root.value+j);
		////					add(n);
		//					fretboard[i][j] = n;
		////					System.out.printf("pos=%d n=[%s],%d,%d\n", pos, n, pos / 5, pos % 5);
		//				}
		//			}
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//			System.out.println(this);
		//		}
	}

	void clear() {
		fretboard = new Noteg[6][13];
	}

	void addAll2(Note aNote) {
		for (int i = 2; i < 6; i++) {
			aNote.octave = i;
			addAll(aNote);
		}
	}

	void addAll(Note aNote) {
		//		Noteg n = new Noteg()
		//		notes.add(aNote);
		for (int i = 0; i < strs.length; i++) {
			Note n = strs[i];
			int x = n.semitones(aNote);
			//			System.out.printf("[%s]-[%s]= %2d\n", n, aNote, x);
			if (0 <= x && x < fretboard[0].length)
				notes.add(set(i, x, aNote));
		}
	}

	void add(Note aNote) {
		for (int i = 0; i < strs.length; i++) {
			Note n = strs[i];
			int x = n.semitones(aNote);
			//			System.out.printf("[%s]-[%s]= %2d\n", n, aNote, x);
			if (0 <= x && x < fretboard[0].length) {
				notes.add(set((i) * 13 + x, aNote));
				break;
			}
		}
	}

	public void printG2(Note[][] fretboard) {
		System.out.printf("\n  ");
		for (int i = 0; i < fretboard[0].length; i++) {
			System.out.printf("%-8d", i);
		}
		System.out.println();
		for (int str = 0; str < fretboard.length; str++) {
			System.out.print((str + 1) + "|");
			for (int fret = 0; fret < fretboard[0].length; fret++) {
				Note n = fretboard[str][fret];
				String s = (n == null) ? "-" : n.fullName();
				System.out.printf("%-8s", s);
			}
			System.out.println("endfret");
		}
	}

	public Note[][] printG(LinkedList<Note> someNotes) {
		Note[][] fb = new Note[6][15];
		//		Note a = notes.getFirst();
		// header
		System.out.printf("  ");
		for (int i = 0; i < fb[0].length; i++) {
			System.out.printf("%-8d", i);
		}
		System.out.println();
		// notes
		for (int i = 0; i < fb.length; i++) {
			Note open = strs[i];
			System.out.print((i + 1) + "|");
			for (int j = 0; j < fb[0].length; j++) {
				String s = "-";
				for (Note n : someNotes) {
					int pos = open.semitones(n);
					if (pos % 12 == j) {
						Note a = new Note(n.value - 12 * (pos / 12));
						s = n.fullName();
						//							s = String.format("[%s]_%d*%d,%d", n.value,pos,pos/12,-12*(pos/12));
						fb[i][j] = a;
					}
					//						s = String.format("%d", pos%12);
				}
				System.out.printf("%-8s", s);
			}
			System.out.println();
		}
		return fb;
	}

	public static String fullBoard() {
		Guitar g = new Guitar();
		g.fill();
		Formatter f = new Formatter();
		//		f.append()
		f.format("g object: %s\n", g.notes.toString());
		f.format("  ");
		String fixedwidth = "%-10s";
		for (int i = 0; i < g.fretboard[0].length; i++) {
			f.format(fixedwidth, i);
		}
		f.format("\n");
		for (int str = 0; str < g.fretboard.length; str++) {
			f.format((4 - str + 1) + "|");
			for (int fret = 0; fret < g.fretboard[0].length; fret++) {
				Note n = g.fretboard[5 - str][fret];
				//				String s = (n == null) ? "-" : String.format("%s.", n.toString(),help[5-str][fret]);//,find(n));
				String s = (n == null) ? "-" : String.format("%s", n.fullName());
				f.format(fixedwidth, s);
			}
			f.format("\n");
		}

		String s = f.toString();
		f.close();
		return s;
	}

	@Override
	public String toString() {
		Formatter f = new Formatter();
		//		f.append()
		f.format("g object: %s\n", notes.toString());
		f.format("  ");
		String fixedwidth = "%-10s";
		for (int i = 0; i < fretboard[0].length; i++) {
			f.format(fixedwidth, i);
		}
		f.format("\n");
		int i = 0;
		for (int str = 0; str < fretboard.length; str++) {
			f.format((4 - str + 1) + "|");
			for (int fret = 0; fret < fretboard[0].length; fret++) {
				Note n = fretboard[5 - str][fret];
				//				int num = 5;
				//				String s = (n == null) ? "-" : String.format("%s.", n.toString(),help[5-str][fret]);//,find(n));
				String s = (n == null) ? "-" : n.toString();
				f.format(fixedwidth, s);
			}
			f.format("\n");
		}

		String s = f.toString();
		f.close();
		return s;
	}
}
