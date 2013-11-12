import java.util.*;
import java.util.regex.*;

public class main {
	public static HashMap<Integer, Song> SongMap = new HashMap<Integer, Song>();

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// Analyze task
		// String strUrl
		// ="http://www.mymusic.net.tw/index.gsp#!http://www.mymusic.net.tw/album/show/164901";
		// String strUrl =
		// "http://www.mymusic.net.tw/index.gsp#!http://www.mymusic.net.tw/album/show/165257";
		String strUrl = javax.swing.JOptionPane.showInputDialog("Input URL");
		Pattern p = Pattern.compile("(.+?)#!(.+)");
		Matcher m = p.matcher(strUrl);

		if (m.find()) {
			strUrl = m.group(2);
			ezAnalyzer tmp = new ezAnalyzer();
			tmp.setMap(SongMap);
			tmp.setURL(strUrl);

			tmp.start();
			tmp.join();
		}

		// Download task
		Collection a = SongMap.values();
		Iterator b = a.iterator();
		Song c = null;
		int listSize = 5;

		while (b.hasNext()) {
			if (TManager.getDownCount() < 5) {
				c = (Song) b.next();
				Downloader agent = new Downloader();
				agent.setFreeDRM(false);
				agent.setTarget(c);
				TManager.addDownCount(1);
				agent.start();
			}
			Thread.sleep(250);
		}

		while (Thread.activeCount() > 1)
			Thread.sleep(250);
		System.out.println("All done!");
		System.out.println(Thread.activeCount());
	}

}
