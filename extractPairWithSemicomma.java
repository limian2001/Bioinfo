
import java.io.*;
import java.util.*;


public class extractPairWithSemicomma {
	public static ArrayList<String> readFileByLines(String fileName){
		File file = new File(fileName);
		BufferedReader reader = null;
		//HashSet<String> tmp = new HashSet<String>();
		ArrayList<String> res = new ArrayList<String>();
		try {
			//System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			//int line = 1;
			//一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null){
				String[] A = tempString.split("\t");
				String m = "";
				if(A[2].compareToIgnoreCase(A[3]) <= 0)
					m = A[2].toLowerCase()+ ";" +A[3].toLowerCase();
				else
					m = A[3].toLowerCase()+ ";" +A[2].toLowerCase();
				res.add(m);
				//System.out.println(m);
				
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null){
				try {
					reader.close();
				} catch (IOException e1) {
					}
			}
		}
		HashSet<String> set = new HashSet<String>(); //去掉res里面重复的
		for(String s : res){
			set.add(s);
		}
		res.clear();
		for(String s : set){
			res.add(s);
		}
		return res;
		
	}
	public static void main(String args[]){
		ArrayList<String> res = readFileByLines("/Users/LM/Documents/workspace/BioInfo/src/task2/DrugBankTrainCorpus.txt");
		
		for(String s : res){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/LM/Documents/workspace/BioInfo/src/task2/DrugBank_Out2.txt", true)))) {
		    out.println(s);
		}catch (IOException e) {
		   // exception handling left as an exercise for the reader
		}
		}
	}
}
