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
 */

package FreeMe2;

public class ChunkSave {
	public static GUID guid;
	public static long length;
	public static short data;
	public ChunkSave next;
}
