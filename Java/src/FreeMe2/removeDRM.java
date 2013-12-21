/**
 * 	in C
 * 	1Byte     uint8_t
	2Byte     uint16_t
	4Byte     uint32_t
	8Byte     uint64_t
	
	in Java
	byte	=> 1Byte
	short	=> 2Byte
	int 	=> 4Byte
	long 	=> 8Byte
	
	使用 short 來實作 1 byte unsigned => uchar  運算時使用 0xff 來確認 1byte
	使用 int 來實作 2 byte unsigned => wchar_t 運算時使用 0xffff 來確認 2byte
 */
package FreeMe2;

/**
 * @author PStar
 * 
 */
import java.io.*;
import java.util.*;

public class removeDRM {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ChunkSave chunk = new ChunkSave();
		int more = 1;
		File ifp;
//		String fnamestart;
		String outputFileName = new String();
		String filePath = new String();
		Scanner input;
		
		GlobalInfo.verbose = 0;
		GlobalInfo.engine = 1;
		GlobalInfo.asksid = 0;
		GlobalInfo.fileheader = 0;
		GlobalInfo.kid = 0;
		GlobalInfo.checksum = 0;
		GlobalInfo.hasV1header = false;
		GlobalInfo.hasV2header = false;
		GlobalInfo.ofname = outputFileName;
		
		if(args.length < 1 || args.length > 2){
			System.err.println("Usage: FreeMe2 [-vs2] protectedfile");
			System.exit(1);
		}
		
//		ifp = new FileInputStream(args[0]);
		ifp = new File("testFile/渺小.wma");
		
		filePath = ifp.getParent();
		
		outputFileName = "Freed-" + ifp.getName();
		
		input = new Scanner(ifp);
		while(input.hasNextByte()){
			chunk.data = 0;
			
		}
	}
	
	public static int handle_chunk(File fp, ChunkSave chunk){
		
		return 0;
	}
}
