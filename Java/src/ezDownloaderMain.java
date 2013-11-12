import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.regex.*;

public class ezDownloaderMain extends JFrame implements ActionListener {
	private JPanel p1 = new JPanel();
	private JScrollPane p2 = new JScrollPane();
	private JPanel p3 = new JPanel();
	private JPanel p3_1 = new JPanel();
	private JPanel p3_2 = new JPanel();
	private JTable JT1 = new JTable();
	private JTextField JTFURL = new JTextField(20);
	private JTextField JTFDIR = new JTextField(30);
	private JButton JBut1 = new JButton("Submit");
	private JButton JBut2 = new JButton("Clear");
	private JButton JBut3 = new JButton("Download");
	private JButton JBut4 = new JButton("Browser");
	private JFileChooser jfc = new JFileChooser();
	private JProgressBar jpb = new JProgressBar();

	private Object[][] JTModelData = null;
	private String[] JTModelTitle = new String[] { "No.", "Title", "State",
			"URL" };
	private DefaultTableModel JTModel = new DefaultTableModel(JTModelData,
			JTModelTitle);

	public static HashMap<Integer, Song> SongMap = new HashMap<Integer, Song>();
	private URL url = null;
	private ArrayList<String> nums = new ArrayList<String>();
	private ArrayList<String> titles = new ArrayList<String>();
	private ArrayList<String> status = new ArrayList<String>();
	private ArrayList<String> urls = new ArrayList<String>();

	public static void main(String[] args) {
		ezDownloaderMain frame = new ezDownloaderMain();
	}

	ezDownloaderMain() {

		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("ezPeer URL:"));
		p1.add(JTFURL);
		p1.add(JBut1);
		p1.add(JBut2);

		JBut1.addActionListener(this);
		JBut2.addActionListener(this);
		JBut3.addActionListener(this);
		JBut4.addActionListener(this);

		JT1.setModel(JTModel);
		p2.setViewportView(JT1);

		jpb.setValue(0);
		jpb.setMaximum(100);
		jpb.setStringPainted(true);

		jfc.setDialogType(JFileChooser.SAVE_DIALOG);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JTFDIR.setText(jfc.getCurrentDirectory().getAbsolutePath().toString());

		JBut2.setEnabled(false);
		JBut3.setEnabled(false);
		JTFDIR.setEnabled(false);

		p3.setLayout(new GridLayout(2, 1));
		p3.add(p3_1);
		p3.add(p3_2);
		p3_1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3_1.add(new JLabel("Dir. : "));
		p3_1.add(JTFDIR);
		p3_1.add(JBut4);
		p3_2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3_2.add(new JLabel("Download State : "));
		p3_2.add(jpb);
		p3_2.add(JBut3);

		// JFrame Base
		this.setTitle("ezDown");
		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);
		this.setSize(600, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JButton s = (JButton) e.getSource();
		System.out.println(e.getActionCommand());

		// AnalyzeHTMLTask Thread1 = new AnalyzeHTMLTask();
		// DownloadFileTask Thread2 = new DownloadFileTask();
		ezAnalyzer Thread1 = new ezAnalyzer();
		Downloader Thread2 = new Downloader();

		if (s == JBut1) {
			String strUrl = JTFURL.getText();
			Pattern p = Pattern.compile("(.+?)#!(.+)");
			Matcher m = p.matcher(strUrl);

			if (m.find()) {
				strUrl = m.group(2);
				ezAnalyzer tmp = new ezAnalyzer();
				tmp.setMap(SongMap);
				tmp.setURL(strUrl);

				tmp.start();
				try {
					tmp.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			JBut3.setEnabled(true);
			this.setVisible(true);
		} else if (s == JBut2) {
			JTModelData = null;
			JTModel.setDataVector(JTModelData, JTModelTitle);
			JBut2.setEnabled(false);
		} else if (s == JBut3) {
			// Download Task

			Collection a = SongMap.values();
			Iterator b = a.iterator();
			Song c = null;
			int listSize = 5;

			while (b.hasNext()) {
				if (TManager.getDownCount() < 5) {
					c = (Song) b.next();
					Downloader agent = new Downloader();
					agent.setTarget(c);
					TManager.addDownCount(1);
					agent.start();
				}

				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			while (Thread.activeCount() > 1) {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			System.out.println("All done!");
			System.out.println(Thread.activeCount());
		} else if (s == JBut4) {
			int option = jfc.showDialog(null, null);
			if (option == JFileChooser.APPROVE_OPTION)
				JTFDIR.setText(jfc.getSelectedFile().toString());
		}
		DrawTableTask();

		JBut2.setEnabled(JT1.getRowCount() > 0 ? true : false);
		JBut3.setEnabled(JT1.getRowCount() > 0 ? true : false);
	}

	public void DrawTableTask() {
		JTModelData = new Object[urls.size()][4];

		Collection songs = SongMap.values();
		Iterator<Song> songIter = songs.iterator();

		int i = 0;
		while (songIter.hasNext()) {
			Song songData = songIter.next();
			JTModelData[i][0] = songData.getNum();
			JTModelData[i][1] = songData.getName();
			JTModelData[i][3] = songData.getURL();
		}

		JTModel.setDataVector(JTModelData, JTModelTitle);
	}

}