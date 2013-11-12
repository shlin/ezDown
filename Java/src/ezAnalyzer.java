import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class ezAnalyzer extends Thread {
	private HashMap<Integer, Song> SongMap = null;

	private URL url = null;
	private InputStream input = null;
	private Scanner infile = null;

	private boolean stateEnable = false;
	private String srcLine = null;
	private String dstLine = null;
	private String ezHost = "http://drm.ezpeer.com/";

	private Pattern pStart = Pattern.compile("songlist");
	private Pattern pSongID = Pattern.compile("(.+?)-(.+?)-(.+?)-(.+?)-(.+?)$");
	private Pattern pSongSrc = Pattern.compile("song_id=([^\"]+?)\">(.+?)</a>");

	private Matcher mStart = null;
	private Matcher mSongID = null;
	private Matcher mSongSrc = null;
	private int songNum = 0;

	// <a href="/song/show?song_id=p000002-a0164901-s058008-t001-c7">笨蛋</a>
	// http://drm.ezpeer.com/p000012/s000005/a0080563/stm_p000012-a0080563-s000005-t003-c0.wma

	public ezAnalyzer() {
	}

	public void run() {
		try {
			input = url.openStream();
			infile = new Scanner(input);
			while (infile.hasNext()) {
				srcLine = new String(infile.nextLine().getBytes("UTF-8"));
				// System.out.println(srcLine);
				mStart = pStart.matcher(srcLine);

				if (mStart.find() || stateEnable) {
					// System.out.println(srcLine);

					mSongID = pSongID.matcher(srcLine);
					if (mSongID.find()) {
						System.out.println(srcLine);
						mSongSrc = pSongSrc.matcher(srcLine);
						if (mSongSrc.find()) {
							songNum = Integer.parseInt(mSongID.group(4)
									.replaceFirst("t", ""));

							mSongID = pSongID.matcher(mSongSrc.group(1));
							if (mSongID.find()) {
								dstLine = ezHost + mSongID.group(1) + "/"
										+ mSongID.group(3) + "/"
										+ mSongID.group(2) + "/stm_"
										+ mSongID.group(0) + ".wma";
							}
							// System.out.println(songNum + " " +
							// mSongSrc.group(1) + " " + mSongSrc.group(2) + " "
							// + dstLine);
							SongMap.put(songNum,
									new Song(songNum, mSongSrc.group(2),
											dstLine));
						}
					}
					stateEnable = true;
				}

			}
		} catch (IOException er) {
			System.out.println(er.getMessage());
		} finally {
			if (infile != null)
				infile.close();
		}
	}

	public void setURL(String strURL) {
		try {
			url = new URL(strURL.trim());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void setMap(HashMap<Integer, Song> Map) {
		this.SongMap = Map;
	}
}
