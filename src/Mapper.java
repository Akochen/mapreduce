import java.util.ArrayList;
import java.util.TreeMap;

public class Mapper extends Thread{
	private ArrayList<String> list;
	private TreeMap<String, Integer> table = new TreeMap<String, Integer>();
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
	 * Maps the arraylist to a TreeMap
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
	 * @return a TreeMap containing the shuffled results of the four maps
	 */
	public static TreeMap<String, ArrayList<Integer>> shuffle(ArrayList<TreeMap<String, Integer>> tableList){
		//Create TreeMap with ch1 in it
		TreeMap<String, ArrayList<Integer>> shuffleTable = new TreeMap<String, ArrayList<Integer>>();
		for (TreeMap<String, Integer> TreeMap : tableList) {
			TreeMap.forEach((string, integer) -> {
				ArrayList<Integer> arrayList = new ArrayList<>();
				arrayList.add(TreeMap.get(string));
				shuffleTable.put(string, arrayList);
			});
		}
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
	 * Gets the TreeMap the arraylist was mapped to
	 * @return the TreeMap the arraylist is mapped to
	 */
	public TreeMap<String, Integer> getTable() {
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
