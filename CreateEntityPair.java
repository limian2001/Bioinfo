

	




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.*;

/**
* 以行为单位读取文件，常用于读面向行的格式化文件
@param file1 SemRepOutput_DrugBankTrain.txt;
@param file2 SemRepOutput_MedlineTrain.txt;
这个代码用于处理这两个文件，每一行中如果含有Entity这个字段的，并且后面有orch或者phsu的，
把药名取出来。每句话之间用空行分开，同一句话内的所有药两两之间做一个排列，比如第一句话有A,B,C，
输出就是A-B, B-A, A-C, C-A, B-C, C-B
*/
	
public class CreateEntityPair {
		
	
	public static ArrayList<String> readFileByLines(String fileName){
		File file = new File(fileName);
		BufferedReader reader = null;
		HashSet<String> tmp = new HashSet<String>();
		ArrayList<String> res = new ArrayList<String>();
		try {
			//System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			//int line = 1;
			//一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null){
				if(tempString.equals("")){ //文件中空行分开每个句子，到空行排列组合一下
					processToRes(tmp,res);//遇到空行表示这句话结束，送去处理
					tmp.clear();
				}
				else{
					//System.out.println(tempString);
					//System.out.println("haha");
					String[] temp = tempString.split("\\|");//源文件用|分割单词
					//System.out.println(temp[5]);
					boolean rightword = false; //用来标记这行是不是要找的
					if(temp[5].equals("entity")){
						if((temp[8].equals("orch") || temp[8].equals("phsu")))
							rightword = true; //含有这两个词的任意一个就是要找的
						else{
							String[] tp = temp[8].split(",");//有时候会有多个属性用,分开
						    //System.out.println(temp[8]);
							for(int i =0;i<tp.length;i++){ //遍历数组，看看有没有那两个关键字
								//System.out.println(tp[i]);
								if(tp[i].equals("orch") || tp[i].equals("phsu")){
									rightword = true;
									break;
								}
							}
						}
					}
				    if(rightword){ //如果这句话是要找的，把这个位置的词拿出来[7]
				    	tmp.add(temp[7]);
				    	//System.out.println(temp[7]);
				    }
				}
				
				
				//System.out.println("line " + line + ": " + tempString);
				
				
				
				
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
	public static void processToRes(HashSet<String> tmp,ArrayList<String> res){
		//System.out.println("This is proc res");
		String[] a = new String[tmp.size()];
		int i = 0;
		for(String s : tmp){//转到数组，方便用index
			a[i] = s;
			i++;
		}
		//输出所有组合，用;分开
		for(int j=0;j<a.length;j++)
			for(int k=j+1;k<a.length;k++){
				if(j!=k){;
					String m = "";
					if(a[j].compareToIgnoreCase(a[k]) <= 0)
						m = a[j].toLowerCase()+ ";" +a[k].toLowerCase();
					else
						m = a[k].toLowerCase()+ ";" +a[j].toLowerCase();
					res.add(m);
					//System.out.println(m);
				}
			}
	}
	

	public static void main(String args[]){
		ArrayList<String> res = readFileByLines("/Users/LM/Documents/workspace/BioInfo/src/SemRepOutput_DrugBankTrain.txt");
		
		
		for(String s : res){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/LM/Documents/workspace/BioInfo/src/DrugBank_lowcase_onlycombin2.txt", true)))) {
		    out.println(s);
		}catch (IOException e) {
		   // exception handling left as an exercise for the reader
		}
		}
	}
	
}