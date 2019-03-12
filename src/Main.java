import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//Import all chapters
		ArrayList<String> ch1 = readChapter(new File("src/mobydick1.txt"));
		ArrayList<String> ch2 = readChapter(new File("src/mobydick2.txt"));
		ArrayList<String> ch3 = readChapter(new File("src/mobydick3.txt"));
		ArrayList<String> ch4 = readChapter(new File("src/mobydick4.txt"));
		//Create mappers
		Mapper ch1Map = new Mapper(ch1);
		Mapper ch2Map = new Mapper(ch2);
		Mapper ch3Map = new Mapper(ch3);
		Mapper ch4Map = new Mapper(ch4);
		//Map chapters
		ch1Map.map();
		ch2Map.map();
		ch3Map.map();
		ch4Map.map();
		//Combine mappers
		ch1Map.mapCount();
		ch2Map.mapCount();
		ch3Map.mapCount();
		ch4Map.mapCount();
		//Print out combined mappers
		System.out.println("Ch1 Word Count");
		System.out.println(ch1Map.getTable().toString());
		System.out.println("Ch2 Word Count");
		System.out.println(ch2Map.getTable().toString());
		System.out.println("Ch3 Word Count");
		System.out.println(ch3Map.getTable().toString());
		System.out.println("Ch4 Word Count");
		System.out.println(ch4Map.getTable().toString());
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
		//Print reduced result
		String result = "Reducer 1 Output: " + reducer1.getResult() + "\nReducer 2 Output: " + reducer2.getResult();
		printResult(result);
		/*
		System.out.println(reducer1.getResult());
		System.out.println(reducer2.getResult());
		*/
		
	}
	/**
	 * Prints to a file, overwriting what was there previously
	 * @param result The text to print
	 */
	private static void printResult(String result) {
		try {
			FileWriter writer = new FileWriter("src/output.txt", false);
			PrintWriter printer = new PrintWriter(writer);
			printer.print(result);
			printer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: File does not exist!");
		}
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
