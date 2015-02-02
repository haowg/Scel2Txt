package data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * ����sogo�ʿ⹤����
 * 
 * 
 * **/
public class ParseSogo {

	public static void main(String[] args) throws Exception {
		if (args.length<2) {
			System.err.println("usage: java -jar Scel2Txt.jar scelpath(in) txtpath(out)");
			System.exit(-1);
		}
		String scelName = args[0];
		String txtName = args[1];
		if (!new File(scelName).isFile()) {
			System.err.println("can not find "+scelName+" please check it");
			System.exit(-1);
		}
		sogou(scelName, txtName, true);
//		sogou("E:\\�ʿ�\\����\\���վƵ�����.scel", "E:\\�ʿ�\\����\\��������������ȫ.txt", false);
	}

	/**
	 * ��ȡscel�Ĵʿ��ļ� ����txt��ʽ���ļ�
	 * 
	 * @param inputPath
	 *            ����·��
	 * @param outputPath
	 *            ���·��
	 * @param isAppend
	 *            �Ƿ�ƴ��׷�Ӵʿ����� true ����׷��,false�����ؽ�
	 * 
	 * **/
	private static void sogou(String inputPath, String outputPath,
			boolean isAppend) throws IOException {
		File file = new File(inputPath);
		if (!isAppend) {
			if (Files.exists(Paths.get(outputPath), LinkOption.values())) {
				System.out.println("�洢���ļ��Ѿ�ɾ��");
				Files.deleteIfExists(Paths.get(outputPath));

			}
		}
		RandomAccessFile raf = new RandomAccessFile(outputPath, "rw");

		int count = 0;
		SougouScelMdel model = new SougouScelReader().read(file);
		Map<String, List<String>> words = model.getWordMap(); // ��<ƴ��,��>
		Set<Entry<String, List<String>>> set = words.entrySet();
		Iterator<Entry<String, List<String>>> iter = set.iterator();
		while (iter.hasNext()) {
			Entry<String, List<String>> entry = iter.next();
			List<String> list = entry.getValue();
//			String pinyin = entry.getKey();
			int size = list.size();
			for (int i = 0; i < size; i++) {
				String word = list.get(i);

//System.out.println(word+"\t"+pinyin);
				raf.seek(raf.getFilePointer());
				raf.write((word + "\n").getBytes());// д��txt�ļ�
				count++;

			}
		}
		raf.close();
		System.out.println("����txt�ɹ���,�ܼ�д��: " + count + " �����ݣ�");
	}

}