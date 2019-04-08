import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
		//Create mappers
		Mapper ch1Map = new Mapper(ch1, 1);
		Mapper ch2Map = new Mapper(ch2, 2);
		Mapper ch3Map = new Mapper(ch3, 3);
		Mapper ch4Map = new Mapper(ch4, 4);
		ch1Map.start();
		ch2Map.start();
		ch3Map.start();
		ch4Map.start();
		try {
			System.out.println("\nWaiting for mappers to finsih");
			ch1Map.getThread().join();
			ch2Map.getThread().join();
			ch3Map.getThread().join();
			ch4Map.getThread().join();
		} catch (InterruptedException e) {
			System.out.println("\nMapper join error");
			e.printStackTrace();
		}
		//Shuffle maps
		Hashtable<String, ArrayList<Integer>> shuffledTable = Mapper.shuffle(ch1Map.getTable(), ch2Map.getTable(), ch3Map.getTable(), ch4Map.getTable());
		//Print out shuffled map
		System.out.println("\nShuffled Map");
		System.out.println(shuffledTable.toString());
		//Create maps to reduce
		Hashtable<String, ArrayList<Integer>> oddMap = new Hashtable<String, ArrayList<Integer>>();
		Hashtable<String, ArrayList<Integer>> evenMap = new Hashtable<String, ArrayList<Integer>>();
		shuffledTable.forEach((string, arrayList) -> {
			if(string.hashCode()%2 == 1) {
				oddMap.put(string, shuffledTable.get(string));
			} else {
				evenMap.put(string, shuffledTable.get(string));
			}
		});
		//Create Reducers
		Reducer reducer1 = new Reducer(oddMap, 1);
		Reducer reducer2 = new Reducer(evenMap, 2);
		reducer1.start();
		reducer2.start();
		try {
			reducer1.getThread().join();
			reducer2.getThread().join();
		} catch (InterruptedException e) {
			System.out.println("\nReducer join error");
			e.printStackTrace();
		}
		System.out.println("The program has finished.");
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
