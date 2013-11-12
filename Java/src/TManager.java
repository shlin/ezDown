public class TManager {
	private static int DownCount = 0;

	public TManager() {
	}

	public static synchronized void addDownCount(int no) {
		DownCount += no;
		System.out.println("Count: " + DownCount);
	}

	public static synchronized int getDownCount() {
		return DownCount;
	}
}
