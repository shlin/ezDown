package FreeMe2;

class Global {
	static int intMS_BN_LEN = 20;
	static int intMAXKEYPAIRS = 50;

	static boolean hasV1Header;
	static boolean hasV2Header;

	static int intVerbose;
	static int intEngine;
	static int intAskSid;
	static int intFileHeader;
	static int intPacketLen;
	static int intNumPackets;

	static String strOFName;
	static String strKid;
	static String strChecksum;

	static void errExit(String msg) {
		// System.out.println(msg);
		// System.out.println("\n   Press <ENTER> to acknowledge error.\n");
		System.err.println(msg);
		System.exit(1);
	}

	static void printWCS(String msg) {
		System.out.println(msg);
	}

}
