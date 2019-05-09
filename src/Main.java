import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Counter counters = new Counter();
		//Import all chapters
		ArrayList<String> ch1 = readChapter(new File("src/mobydick1.txt"));
		ArrayList<String> ch2 = readChapter(new File("src/mobydick2.txt"));
		ArrayList<String> ch3 = readChapter(new File("src/mobydick3.txt"));
		ArrayList<String> ch4 = readChapter(new File("src/mobydick4.txt"));
		ArrayList<ArrayList<String>> files = new ArrayList<>();
		ch1.sort(String::compareTo);
		ch2.sort(String::compareTo);
		ch3.sort(String::compareTo);
		ch4.sort(String::compareTo);
		files.add(ch1);
		files.add(ch2);
		files.add(ch3);
		files.add(ch4);
		MapReduce mReduce = new MapReduce(files, 4, 2);
		mReduce.MR_RUN();
	}
	
	/**
	 * Reads a file into an arraylist after cleaning the text
	 * @param FileName Name of the file to be read
	 * @return An arraylist of strings where each element is a seperate word
	 */
	private static ArrayList<String> readChapter(File fileName) {
		Scanner fileReader = null;
		ArrayList<String> list = new ArrayList<String>();
		Scanner lineReader = null;
		try {
			fileReader = new Scanner(fileName);
			while(fileReader.hasNextLine()) {
				lineReader = new Scanner(fileReader.nextLine().replaceAll("—", " ").replaceAll("-", " ").replaceAll("[^A-Za-z0-9 ]", ""));
				while(lineReader.hasNext()) {
					list.add(lineReader.next().toLowerCase());
				}
				lineReader.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found: " + fileName);
			e.printStackTrace();
		}
		fileReader.close();
		return list;
	}
}
