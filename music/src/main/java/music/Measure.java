package music;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

class Measure {
	//	Chord[] groups;
	static int width = 5;
	Note[][] Notes;
	int time;
	int part;
	List<Attribute> attributes;
	int number;
	static int subdiv;
	static int notesperbar;

	static Measure parse(Element e) {
		Measure m = new Measure();
		if (subdiv == 0)
			subdiv = Integer.parseInt(e.element("attributes").elementText("divisions"));
		notesperbar = subdiv*4;
		//		m.groups = new Chord[subdiv * 4];
		//		m.parts = new ArrayList<>[subdiv*4];

		m.number = Integer.parseInt(e.attributeValue("number"));
		m.time = 0;
		m.part = 0;
		m.Notes = new Note[3][16];
		m.parseNotes(e);
		m.trim();
		return m;
	}

	void parseNotes(Element measure) {
		for (Iterator<Element> itNote = measure.elementIterator(); itNote.hasNext();) {
			Element note = itNote.next();
			String type = note.getName();
			//			note.getName()
			//			System.out.println(type);
			if (type.equals("backup")) {
				this.time = 0;
				this.part++;
			} else if (type.equals("note")) {
				Note n = Note.parseElement(note);
				if(n.toString().length() > width) width = n.toString().length();
				if (n.chord)
					time = 0;
				put(n, part, time);
				time += n.duration;
			}
			//System.out.printf("note=%s, x=%.0f y=%.0f,\tval=%s\n", pitchname, x, y / 5 + 3, noteVal(pitchname));
			//System.out.printf("%d\n");
		}
	}
	int maxPart;
	int maxTime;
	void put(Note n, int aPart, int aTime) {
		try {
			//			if (Notes[aPart] == null)
			//				Notes[aPart] = new Note[subdiv * 4];
			Notes[aPart][aTime] = n;
			if(aPart > maxPart) maxPart = aPart;
			if(aTime > maxTime) maxTime = aTime;
			//			aTime += n.duration;
		} catch (Exception e) {
			e.printStackTrace();
			//			System.err.println(this.toString());
		}

	}
	void trim() {
		int h = 0;
		int w = 0;
		Note[][] temp = new Note[maxPart+1][maxTime+1];

		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				temp[i][j] = Notes[i][j];
			}
		}
		Notes = temp;
//		System.out.println("DONE");
	}
	static String header(int len) {
		StringBuilder sb = new StringBuilder();
//		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < len; i++) {
//			sb.append(i+1);
			sb.append(String.format("b%-"+width+"d", i+1));
//			sb2.append("+     ");
		}
		sb.append("\n");
//		sb2.append("\n");
//		sb.append(sb2.toString());
		return sb.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(header(notesperbar));
		for (int iPart = 0; iPart < Notes.length; iPart++) {
			sb.append("|");
			for (int iBeat = 0; iBeat < Notes[0].length; iBeat++) {
				Note n = Notes[iPart][iBeat];

				if (n == null)
					sb.append("                    ".substring(0, width));
				else
					sb.append(String.format("%-"+width+"s", n));
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
