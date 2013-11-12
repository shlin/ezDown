import java.io.*;
import java.util.Scanner;

public class DeDRM {
	private Process proc = null;
	private InputStream in = null;
	private Scanner input = null;

	private String file = null;
	private String cmd = "./bin/convert.exe";

	public DeDRM() {
	}

	public void setFile(String f) {
		this.file = f;
	}

	public void remove() {
		try {
			proc = Runtime.getRuntime().exec(cmd + " \"" + file + "\"");
			in = proc.getInputStream();
			input = new Scanner(in);
			while (input.hasNext())
				System.out.println(input.nextLine());
			proc.destroy();
			in.close();
			input.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
