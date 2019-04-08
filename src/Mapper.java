import java.util.ArrayList;
import java.util.Hashtable;

public class Mapper extends Thread{
	private ArrayList<String> list;
	private Hashtable<String, Integer> table = new Hashtable<String, Integer>();
	private String name;
	private Thread thread;
	
	/**
	 * Constructor initializes the initial arraylist to be mapped
	 * @param arrayList The arraylist to be mapped
	 */
	public Mapper(ArrayList<String> arrayList, int num) {
		list = arrayList;
		name = "Mapper: " + num;
	}
	
	/**
	 * Maps the arraylist to a hashtable
	 */
	public void map() {
		for (String string : list) {
			table.put(string, 0);
		}
		System.out.println(name + ": Map\n" + table.toString());
	}
	
	/**
	 * Adds the word count to the table
	 */
	public void mapCount() {
		for (String string : list) {
			if(table.containsKey(string)) {
				table.put(string, table.get(string)+1);
			} else {
				table.put(string, 0);
			}
		}
		System.out.println(name + ": Map Count\n" + table.toString());
	}
	
	/**
	 * Shuffles the four maps together
	 * @param t1 the first map (Chapter 1)
	 * @param t2 the second map (Chapter 2)
	 * @param t3 the third map (Chapter 3)
	 * @param t4 the fourth map (Chapter 4)
	 * @return a hashtable containing the shuffled results of the four maps
	 */
	public static Hashtable<String, ArrayList<Integer>> shuffle(Hashtable<String, Integer> t1, Hashtable<String, Integer> t2, Hashtable<String, Integer> t3, Hashtable<String, Integer> t4){
		//Create HashTable with ch1 in it
		Hashtable<String, ArrayList<Integer>> shuffleTable = new Hashtable<String, ArrayList<Integer>>();
		//Add chapter 1
		t1.forEach((string, integer) -> {
			ArrayList<Integer> arrayList = new ArrayList<>();
			arrayList.add(t1.get(string));
			shuffleTable.put(string, arrayList);
		});
		//Add chapter 2
		t2.forEach((string, integer) -> {
			if(shuffleTable.containsKey(string)) {
				ArrayList<Integer> arrayList = shuffleTable.get(string);
				arrayList.add(t2.get(string));
				shuffleTable.put(string, arrayList);
			} else {
				ArrayList<Integer> arrayList = new ArrayList<>();
				arrayList.add(t2.get(string));
				shuffleTable.put(string, arrayList);
			}
		});
		//Add chapter 3
		t3.forEach((string, integer) -> {
			if(shuffleTable.containsKey(string)) {
				ArrayList<Integer> arrayList = shuffleTable.get(string);
				arrayList.add(t3.get(string));
				shuffleTable.put(string, arrayList);
			} else {
				ArrayList<Integer> arrayList = new ArrayList<>();
				arrayList.add(t3.get(string));
				shuffleTable.put(string, arrayList);
			}
		});
		//Add chapter 4
		t4.forEach((string, integer) -> {
			if(shuffleTable.containsKey(string)) {
				ArrayList<Integer> arrayList = shuffleTable.get(string);
				arrayList.add(t4.get(string));
				shuffleTable.put(string, arrayList);
			} else {
				ArrayList<Integer> arrayList = new ArrayList<>();
				arrayList.add(t4.get(string));
				shuffleTable.put(string, arrayList);
			}
		});
		return shuffleTable;
	}
	
	/**
	 * Gets the arraylist being mapped
	 * @return the arraylist being mapped
	 */
	public ArrayList<String> getList() {
		return list;
	}
	
	/**
	 * Gets the thread the mapper creates
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	/**
	 * Gets the hashtable the arraylist was mapped to
	 * @return the hashtable the arraylist is mapped to
	 */
	public Hashtable<String, Integer> getTable() {
		return table;
	}
	
	public void start() {
		System.out.println("Starting " + name);
	    if ( thread == null){
	        thread = new Thread (this, name);
	        thread.start();
	   }
	}
	
	public void run(){ //Runs the thread
        System.out.println("Running " + name);
        map();
        mapCount();
		System.out.println(name + " has finished.");
    }
}
