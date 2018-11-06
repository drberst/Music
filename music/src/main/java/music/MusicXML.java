package music;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MusicXML {

	public static void main(String[] args) throws Exception {
		String filename = "foo.xml";
//				filename = "rival.xml";
//		filename = "GameOverSimple.xml";
		Document document = parse(filename);

		toNoteList(document);
	}

	public static void main2(String[] args) throws Exception {
		String filename = "zelda.tab";
		List<String> linelist = readFileInList(filename);
		String[] lines = linelist.toArray(new String[6]);
//		int i =5;
		int offset = 4;
		int len = lines[0].length();
		int dashesperquarter = 2;
		int r=0;
		int smallestR = len;
		
		Measure.width = 8;
		int beat = 0;
		Measure m = new Measure();
		int mNumber = 1;

		for (int pos = offset; pos < lines[0].length(); pos++) {
//			Note = Guitar.strs[]
			Noteg gg = null;

			for (int str = 0; str < lines.length; str++) {
				int interval = Character.getNumericValue(lines[str].charAt(pos));
				if (interval >= 0) {
					gg = Guitar.noteOnString(str, interval);
					gg.duration = r;
					m.put(gg, 0, beat);
					beat+=r;
					if(beat > Measure.beatsperbar) {
						System.out.println("Measure: "+mNumber+++"\n"+m.toString());
						m=new Measure();
						beat = 0;
					}
					if (r> 0 && r < smallestR) smallestR = r;
					r=0;
				}
				System.out.print(lines[str].charAt(pos));
			}
			if (gg==null) System.out.println(r++);
			else System.out.println(" Note="+gg+"//"+gg.duration+"."+gg.subdiv);
		}
		System.out.println("Smallest r is "+smallestR);
		m.trim();
		System.out.println(m);
	}
	
	
	public static List<String> readFileInList(String filename) {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private static void toNoteList(Document document) {
		Element part = document.getRootElement().element("part");
		int mNumber = 1;
		for (Iterator<Element> itMeas = part.elementIterator("measure"); itMeas.hasNext();) {
			Element measure = itMeas.next();
			Measure m = Measure.parse(measure);
//			System.out.println(mNumber + " / / / / / / / / / / / /");
			System.out.println(m.measureNumber + " / / / / / / / / / / / /");

			System.out.println(m);
			mNumber++;
		}
	}

	public static void edit(Document document) throws IOException {

		Element root = document.getRootElement();
		for (Iterator<Element> it = root.element("part").elementIterator("measure"); it.hasNext();) {
			Element measure = it.next();
			for (Iterator<Element> itNote = measure.elementIterator("note"); itNote.hasNext();) {
				Element note = itNote.next();
				System.out.println("note:" + note.asXML());
				note.element("stem").setText("up");
			}
		}

		FileWriter out = new FileWriter("foo.xml");
		document.write(out);
		out.close();
	}

	public static Document parse(String url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

}
