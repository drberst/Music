package music;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MusicXML {

	public static void main(String[] args) throws Exception {
		String filename = "foo.xml";
//		filename = "rival.xml";
		filename = "GameOverSimple.xml";
		Document document = parse(filename);
		
		toNoteList(document);
	}
	
	private static void toNoteList(Document document) {

		Element part = document.getRootElement().element("part");
		int mNumber = 1;
		for (Iterator<Element> itMeas = part.elementIterator("measure"); itMeas.hasNext();) {
			
			Element measure = itMeas.next();
			
//			if (mNumber == 3 || mNumber == 1) {
				Measure m = Measure.parse(measure);
				//			System.out.printf("------ Measure %s ------\n", m.number);
				//			LinkedHashMap<Integer, Cluster> bar = new LinkedHashMap<Integer, Cluster>();
				System.out.println(mNumber+" / / / / / / / / / / / /");
				System.out.println(m);
//			} 
			mNumber++;
		}
		//		return list;
	}
	public static void guitarNote() {
		Note n = new Note();
		n.alter = 0;
		n.name = "G";
		n.octave = 5;
		
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
