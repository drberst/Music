package music;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

class Measure {
	//	Chord[] groups;
	Note[][] Notes;
	int curtime;
	int curpart;
	int parts;
	List<Attribute> attributes;
	int measureNumber;
	
	static int width = 6;
	static int beatsperbar = 4;
	static int notesperbeat = 4;
	
	public Measure() {
//		quartervalue = 4;
//		notesperbar = quartervalue * 4;
		parts = 1;
		Notes = new Note[parts][beatsperbar()];
//
		measureNumber = 1;
		curtime = 0;
		curpart = 0;
	}
	public int beatsperbar() {
		return notesperbeat*beatsperbar;
	}
	static Measure parse(Element e) {
		Measure m = new Measure();
		m.measureNumber = Integer.parseInt(e.attributeValue("number"));

		if (m.measureNumber == 1) {
			System.err.println("FIRST!!");
			notesperbeat = Integer.parseInt(e.element("attributes").elementText("divisions"));
		}
		
//		timesigTop = timesigBot * 4;
//		System.err.println("subdiv is:"+quartervalue);
		//		m.groups = new Chord[subdiv * 4];
		//		m.parts = new ArrayList<>[subdiv*4];

		m.curtime = 0;
		m.curpart = 0;
		m.Notes = new Note[5][m.beatsperbar()];
		m.parseNotes(e);
//		m.trim();
		return m;
	}

	void parseNotes(Element measure) {
		for (Iterator<Element> itNote = measure.elementIterator(); itNote.hasNext();) {
			Element note = itNote.next();
			String type = note.getName();
			//			note.getName()
			//			System.out.println(type);
			if (type.equals("backup")) {
				this.curtime = 0;
				this.curpart++;
			} else if (type.equals("note")) {
				Note n = Note.parseElement(note);
				if (n.toString().length() > width)
					width = n.toString().length();
				if (n.chord) {
					this.curtime = 0;
					this.curpart++;
				} else {
					curpart = 0;
				}
				put(n, curpart, curtime);
				curtime += n.duration;
			}
			//System.out.printf("note=%s, x=%.0f y=%.0f,\tval=%s\n", pitchname, x, y / 5 + 3, noteVal(pitchname));
			//System.out.printf("%d\n");
		}
	}

	void append(Note n, int aPart) {
		
	}
	
	int maxPart;
	int maxTime;
	void put(Note n, int aPart, int aTime) {
		try {
			//			if (Notes[aPart] == null)
			//				Notes[aPart] = new Note[subdiv * 4];
			Notes[aPart][aTime] = n;
			if (aPart > maxPart)
				maxPart = aPart;
			if (aTime > maxTime)
				maxTime = aTime;
			//			aTime += n.duration;
		} catch (Exception e) {
			e.printStackTrace();
			//			System.err.println(this.toString());
		}

	}

	void trim() {
		Note[][] temp = new Note[maxPart + 1][maxTime + 1];

		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = Notes[i][j];
			}
		}
		Notes = temp;
		//		System.out.println("DONE");
	}

	static String header() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		//		StringBuilder sb2 = new StringBuilder();
		int len = notesperbeat*beatsperbar;
		int tempw = width;
		for (int i = 0; i < len; i++) {
			//			sb.append(i+1);
//			sb.append("|");
			if(i%notesperbeat==0)
				sb.append(String.format("b%-" + tempw + "d", i/notesperbeat+1));
			else
//				sb.append(".");
				sb.append(String.format(".%-" + tempw + "s", ""));
			//			sb2.append("+     ");
			
		}
		sb.append("|\n");
		//		sb2.append("\n");
		//		sb.append(sb2.toString());
		return sb.toString();
	}
	public String toString2() {
		return String.format("Measure [sig=%d/%d, Notes=%s, curtime=%s, curpart=%s, measureNumber=%s, maxPart=%s, maxTime=%s]", beatsperbar,notesperbeat,Arrays.toString(Notes[0]), curtime, curpart, measureNumber,
				maxPart, maxTime);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(toString2());
		sb.append(header());
		for (int iPart = 0; iPart < Notes.length; iPart++) {
			sb.append("|");
			for (int iBeat = 0; iBeat < Notes[0].length; iBeat++) {
				Note n = Notes[iPart][iBeat];
//				sb.append("                    ".substring(0, width));

				if (n == null)
					sb.append(String.format("%-" + width + "s", ""));
				else
					sb.append(String.format("%-" + width + "s", n));
				sb.append("|");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	//
	//	@Override
	//	public String toString() {
	//		String result = "";
	//		for (int i = 0; i < groups.length; i++) {
	//			String line = "\n" + (i+1) + ":";
	//			if (groups[i] != null)
	//				line += groups[i];
	//			result += line;
	//		}
	//		return String.format("Measure %d=%s", number, result);
	//	}

}
