import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;



public class CalculateTPFP {
	public static void main(String args[]){
		ArrayList<String> res1 = CreateEntityPair.readFileByLines("/Users/LM/Documents/workspace/BioInfo/src/SemRepOutput_DrugBankTrain.txt");
		ArrayList<String> res2 = extractPairWithSemicomma.readFileByLines("/Users/LM/Documents/workspace/BioInfo/src/task2/DrugBankTrainCorpus.txt");
		ArrayList<String> tp = new ArrayList<String>();
		ArrayList<String> fn = new ArrayList<String>();
		ArrayList<String> fp = new ArrayList<String>();
		System.out.println("Total pairs in SemRep is "+res1.size());//8953
		System.out.println("Total pairs in DrugBankCorpus is "+res2.size());//2964
		HashSet<String> set1 = new HashSet<String>(); 
		for(String s : res1){
			set1.add(s);
		}
		
		HashSet<String> set2 = new HashSet<String>(); 
		for(String s : res2){
			set2.add(s);
		}
		int TP = 0,FN=0;
		for(String k : res1){
			if(set2.contains(k)){
				TP++;
				tp.add(k);
			}
			else{
				FN++;
				fn.add(k);
			}
		}
		for(String s : tp){
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/LM/Documents/workspace/BioInfo/src/task2/tp.txt", true)))) {
			    out.println(s);
			}catch (IOException e) {
			   // exception handling left as an exercise for the reader
			}
		}
		for(String s : fn){
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/LM/Documents/workspace/BioInfo/src/task2/fn.txt", true)))) {
				 out.println(s);
			}catch (IOException e) {
				   // exception handling left as an exercise for the reader
			}
		}
		
		System.out.println("TP is "+TP);
		System.out.println("FN is "+FN);
		int FP=0;
		for(String k : res2){
			if(!set1.contains(k)){
				fp.add(k);
				FP++;
			}
		}
		for(String s : fp){
			try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/LM/Documents/workspace/BioInfo/src/task2/fp.txt", true)))) {
				 out.println(s);
			}catch (IOException e) {
				   // exception handling left as an exercise for the reader
			}
		}
		System.out.println("FP is "+FP);
	}
}
