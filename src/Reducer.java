import java.util.ArrayList;
import java.util.TreeMap;

public class Reducer extends Thread{
	private TreeMap<String, ArrayList<Integer>> tableToReduce = new TreeMap<>();
	private int num;
	private String result = "";
	private String name;
	private Thread thread;
	
	/**
	 * Creates a reducer
	 * @param input The TreeMap to be reduced
	 * @param num The number of the reducer
	 */
	public Reducer(TreeMap<String, ArrayList<Integer>> input, int num) {
		// TODO Auto-generated constructor stub
		tableToReduce = input;
		this.num = num;
		name = "Reducer: " + num;
	}
	
	/**
	 * Reduces the data put into the reducer and adds it to a result string
	 */
	private void generateResult() {
		tableToReduce.forEach((string, arrayList) -> {
			result += "[Key: \"" + string + "\" Value: " + getTotal(arrayList) + " Hash Code: " + string.hashCode() + " reducer: " + num + "\n";
		});
	}
	
	/**
	 * Gets the total values of each key in the TreeMap
	 * @param arrayList The arraylist containing the values to be added together
	 * @return all values added together
	 */
	private int getTotal(ArrayList<Integer> arrayList) {
		int total = 0;
		for(int i = 0; i<arrayList.size(); i++) {
			total += arrayList.get(i);
		}
		return total;
	}
	
	/**
	 * Returns the final result String
	 * @return The final result string
	 */
	public String getResult() {return result;}
	
	public void start() {
		System.out.println("\nStarting " + name);
	    if ( thread == null){
	        thread = new Thread (this, name);
	        thread.start();
	   }
	}
	
	/**
	 * Gets the thread the Reducer creates
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}
	
	public void run(){ //Runs the thread
        System.out.println("Running " + name);
		generateResult();
        System.out.println("Printing " + name);
		System.out.println(result);
		System.out.println(name + " has finished.");
    }
}
