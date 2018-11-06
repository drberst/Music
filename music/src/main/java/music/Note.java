package music;

import java.util.Scanner;

import org.dom4j.Element;

class Note {
	static int subdiv;
	static String[] names = { "C", "C♯/Db", "D", "D♯/Eb", "E", "F", "F♯/Gb", "G", "G♯/Ab", "A", "A♯/Bb", "B" };
	static String base = "C0";
	static boolean preferSharp = false;
	int duration;
	boolean chord;

	String name;
	int value;
	int octave;
	int alter;
	//	int x, y;

	public static void main(String[] args) {
		//		Note a = new Note("E4");
		//		System.out.println(a);
		for (int i = 0; i < 100; i++) {
			Note b = new Note(i);
			System.out.printf("%s\tp%d\tg%d\tm%d\n",b,b.val(1),b.val(2),b.val(3));
		}
		//		a.octave = 4;
		//		Note b = new Note("C");
		//		b.octave = 5;
		//		System.out.printf("%s, %s: %s\n",a,b,Note.semitones(a, b));
	}

	Note() {
		super();
	}

	Note(String aName) {
		duration = 4;
		//		name = aName;
		octave = 3;
		alter = 0;
		char[] temp = aName.toCharArray();
		for (int i = 0; i < aName.length(); i++) {
			char c = temp[i];
			if (c == '#')
				alter = 1;
			if (Character.isLetter(c)) {
				if (c == 'b' && i != 0)
					alter = -1;
				else
					name = String.valueOf(c).toUpperCase();
			}
			if (Character.isDigit(c))
				octave = Character.getNumericValue(c);
		}
		if (aName.equals(base))
			value = 1;
		else
			value = semitones();
	}

	Note(Integer aValue) {
		int oct = aValue / 12;
		int nam = aValue % 12;
		duration = 4;
		octave = oct;
		name = names[nam];
		if (name.length() > 1) {
			if (preferSharp) {
				name = name.split("/")[0].substring(0, 1);
				alter = 1;
			} else {
				name = name.split("/")[1].substring(0, 1);
				alter = -1;
			}
		}
		value = semitones();
	}

	Note transpose(int amount) {
		return new Note(value + amount);
	}

	static Note parseElement(Element note) {
		Note n = new Note();
		//		n.x = (int) Double.parseDouble(note.attributeValue("default-x"));
		//		n.y = (int) Double.parseDouble(note.attributeValue("default-y")) / 5;

		n.name = note.element("pitch").elementText("step");
		n.octave = Integer.parseInt(note.element("pitch").elementText("octave"));
		n.duration = Integer.parseInt(note.elementText("duration"));
		n.alter = toInt(note.element("pitch").elementText("alter"));
		//		n.value = n.y + 4 - 7 * (n.octave - 5);
		n.value = n.semitones();
		//		Element c = note.element("chord");
		n.chord = note.element("chord") != null;
		//		if(n.value > 7)
		return n;
	}

	static int toInt(String s) {
		if (s == null)
			return 0;
		else
			return Integer.parseInt(s);
	}

	int semitones() {
		Note root = new Note(base);
		return semitones(root, this);
	}

	int semitones(Note b) {
		return semitones(this, b);
	}

	static int semitones(Note a, Note b) {
		int ai = 12 * a.octave + a.alter, bi = 12 * b.octave + b.alter;
		for (int i = 0; i < names.length; i++) {
			//			System.out.println(i);
			if (names[i].equals(a.name))
				ai += i;
			if (names[i].equals(b.name))
				bi += i;
		}
		//		System.out.printf("%s=%d,%s=%d\n",a, ai,b,bi);
		return bi - ai;
		//		return (ai > bi) ? ai-bi : bi-ai;
		//		System.out.println("DONE");
	}

	public static void inputloop() {
		Scanner s = new Scanner(System.in);
		String input = "meme";
		int i = 0;
		while (!input.equals("")) {
			System.out.print("Note name?");
			input = s.nextLine();
			Note n = new Note(input);
			System.out.println(n);
		}
	}

	public String fullName() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		if (alter == -1)
			builder.append("b");
		if (alter == 1)
			builder.append("#");
		builder.append(octave);
		return builder.toString();
	}
	/**
	 * 
	 * @param pgm 1=piano 2=guitar 3=midi
	 * @return
	 */
	public int val(int pgm) {
//		String old = valtype;
//		valtype = s;
		if (pgm==1) { // piano
			return value - 9;
		} else if (pgm==2) { //guitar
			return value-28;
		} else if (pgm==3) { // midi
			return value +12;
		} else {	
			return value; //default
		}
	}
	public void CopyFrom(Note aNote) {
		this.duration = aNote.duration;
		this.chord = aNote.chord;
		this.value = aNote.value;
		this.name = aNote.name;
		this.octave = aNote.octave;
		this.alter = aNote.alter;
	}
	@Override
	public String toString() {
		//		String octaves = "++++";
		//		int count = octave - 5;
		//		String attachment = "";
		//		if (count > 0)
		//			attachment += octaves.substring(0, count);
		//		if (count < 0)
		//			attachment += octaves.substring(0, -1 * count).replace('+', '-');
		//		String attachment = octaves.substring(0,octave-5); 
		//		if(octave > 5)
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		if (alter == -1)
			builder.append("b");
		if (alter == 1)
			builder.append("#");
		//		if(alter==0) 
		//			builder.append(" ");
		builder.append(octave);

		builder.append("_");
		//		System.out.println(toTab());
		builder.append(val(2));
		return builder.toString();
		//		return String.format("%-6s", builder.toString());
		//		return String.format("%s%s", name, alt,octave);

		//		return String.format("%s/%s%s", name, value, attachment);
		//				return String.format("Note=%s value=%s%s",name,value,attachment);
		//		return String.format("Note [name=%s, x=%s, y=%2s, v=%s, o=%s]", name, x, y,value,octave);
	}

}
