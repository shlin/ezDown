import java.io.*;
import java.net.*;

public class Downloader extends Thread {
	private boolean isFreeDRM = true;

	private Song song = null;
	private URL downURL = null;
	private OutputStream out = null;
	private URLConnection conn = null;
	private InputStream in = null;

	public Downloader() {
	}

	public void setTarget(Song s) {
		this.song = s;
	}

	public void setFreeDRM(boolean is) {
		this.isFreeDRM = is;
	}

	public void run() {
		System.out.println(song.getName() + " started.");
		try {
			downURL = new URL(song.getURL().trim());
			out = new FileOutputStream(song.getName() + ".wma");

			conn = downURL.openConnection();
			in = conn.getInputStream();
			song.setSize(conn.getContentLength());

			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			song.isErr(false);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				song.isDone(true);
			} catch (IOException ioe) {
			}
		}

		if (isFreeDRM) {
			DeDRM de = new DeDRM();
			de.setFile(song.getName() + ".wma");
			de.remove();
		}

		System.out.println(song.getName() + " done.");
		TManager.addDownCount(-1);
	}
}