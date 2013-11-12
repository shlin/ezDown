public class Song {
	private int songNum = 0;
	private int totalSize = 0;
	private String url = null;
	private String name = null;
	private boolean isDone = false;
	private boolean isErr = false;

	public Song(int num, String name, String urlLine) {
		this.songNum = num;
		this.name = name;
		this.url = urlLine;

		convertSongName();
	}

	private void convertSongName() {
		// Special charset convert
		name = name.replaceAll("\\?", "�H");
		name = name.replaceAll("\\\\", "��");
		name = name.replaceAll("\\/", "��");
		name = name.replaceAll(":", "�G");
		name = name.replaceAll("\"", "��");
		name = name.replaceAll("<", "��");
		name = name.replaceAll(">", "��");
		name = name.replaceAll("\\|", "�U");
	}

	public void setSize(int size) {
		this.totalSize = size;
	}

	public void isDone(boolean done) {
		this.isDone = done;
	}

	public void isErr(boolean err) {
		this.isErr = err;
	}

	public int getNum() {
		return this.songNum;
	}

	public int getSize() {
		return this.totalSize;
	}

	public String getURL() {
		return this.url;
	}

	public String getName() {
		return this.name;
	}

	public boolean isDone() {
		return this.isDone;
	}

	public boolean isErr() {
		return this.isErr;
	}
}
