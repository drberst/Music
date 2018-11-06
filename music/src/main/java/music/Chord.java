package music;

import java.util.LinkedList;

class Chord {
	private LinkedList<Note> notes;
//	int duration;
	
	public Chord() {
		notes = new LinkedList<Note>();
	}
	public static void main(String[] args) {
		Chord CM = new Chord();
		CM.add(new Note("C"));
		CM.add(new Note("E"));
		CM.add(new Note("G"));
		System.out.println(CM);
		Guitar g = new Guitar();
		Note[][] fretboard = g.printG(CM.notes);
		g.printG2(fretboard);
		System.out.println();
		for(Note n : CM.notes) g.add(n);
		System.out.println(g);
	}
	public void add(Note n) {
		notes.add(n);
	}
	
	@Override
	public String toString() {
		if (notes.isEmpty())
			return "";
		
		return String.format("c%s",notes);
	}
}

