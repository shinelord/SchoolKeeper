/**
 * SchoolKeeper  
 * com.schoolkeeper.utils.file.FileUtils 
 */
package com.schoolkeeper.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 *
 * @Title: FileNio.java
 * @Package com.schoolkeeper.utils.file
 * @Description: TODO(文件操作类,支持BIO,NIO等方式)
 * @author 王年明
 * @date 2015年9月26日 下午7:19:49
 * @version V1.0
 */
public class FileUtils {

	@Test
	public void testFileUtils() throws Exception {
		int bufSize = 100;
		File fin = new File("D:\\workspace\\test\\usagetracking.log");
		File fout = new File("D:\\workspace\\test\\usagetracking2.log");
		FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
		ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);

		FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
		ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);

		readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer);

		System.out.print("OK!!!");
	}

	public void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				out.write(buffer[i]);
		}
		in.close();
		out.close();
	}

	public static void readFileByLine(int bufSize, FileChannel fcin,ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer) {
		String enterStr = "\n";
		try {
			byte[] bs = new byte[bufSize];
			int size = 0;
			StringBuffer strBuf = new StringBuffer("");
			// while((size = fcin.read(buffer)) != -1){
			while (fcin.read(rBuffer) != -1) {
				int rSize = rBuffer.position();
				rBuffer.rewind();
				rBuffer.get(bs);
				rBuffer.clear();
				String tempString = new String(bs, 0, rSize);
				// System.out.print(tempString);
				// System.out.print("<200>");

				int fromIndex = 0;
				int endIndex = 0;
				while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
					String line = tempString.substring(fromIndex, endIndex);
					line = new String(strBuf.toString() + line);
					// System.out.print(line);
					// System.out.print("</over/>");
					// write to anthone file
					writeFileByLine(fcout, wBuffer, line);

					strBuf.delete(0, strBuf.length());
					fromIndex = endIndex + 1;
				}
				if (rSize > tempString.length()) {
					strBuf.append(tempString.substring(fromIndex,
							tempString.length()));
				} else {
					strBuf.append(tempString.substring(fromIndex, rSize));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer,
			String line) {
		try {
			// write on file head
			// fcout.write(wBuffer.wrap(line.getBytes()));
			// wirte append file on foot
			fcout.write(wBuffer.wrap(line.getBytes()), fcout.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}