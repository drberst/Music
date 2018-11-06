package music;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

class bak1Measure {
	Chord[] groups;
	Note[][] parts;
	int pos;
	List<Attribute> attributes;
	int number;
	static int subdiv;

	
	static bak1Measure parse(Element e) {
		bak1Measure m = new bak1Measure();
		if (subdiv == 0)
			subdiv = Integer.parseInt(e.element("attributes").elementText("divisions"));
		m.groups = new Chord[subdiv * 4];
//		m.parts = new ArrayList<>[subdiv*4];
		
		m.number = Integer.parseInt(e.attributeValue("number"));
		m.pos = 0;
		
		m.parseNotes(e);
		return m;
	}
	
	void parseNotes(Element measure) {
		for (Iterator<Element> itNote = measure.elementIterator(); itNote.hasNext();) {
			Element note = itNote.next();
			String type = note.getName();
//			note.getName()
//			System.out.println(type);
			if (type.equals("backup")) {
				this.pos = 0;
			}
			else if (type.equals("note")) {
				Note n = Note.parseElement(note);
				put(n);
			}
			//System.out.printf("note=%s, x=%.0f y=%.0f,\tval=%s\n", pitchname, x, y / 5 + 3, noteVal(pitchname));
			//System.out.printf("%d\n");
		}
	}
	public void put(Note n) {
		//		Cluster c = groups[pos];
		try {
			if (n.chord)
				pos -= n.duration;
			if (groups[pos] == null)
				groups[pos] = new Chord();
			groups[pos].add(n);

			pos += n.duration;
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.err.println(this.toString());
		}

	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < groups.length; i++) {
			String line = "\n" + (i+1) + ":";
			if (groups[i] != null)
				line += groups[i];
			result += line;
		}
		return String.format("Measure %d=%s", number, result);
	}

}
