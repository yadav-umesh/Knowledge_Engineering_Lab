import java.io.*;
	import java.util.*;
	public class NaiveBays {
		List<List<String>> dataset;
		public NaiveBays() {
			dataset=new ArrayList<>();
		}
		void readData(List<List<String>> dataset) throws Exception {
			System.out.print("enter csv file name:");
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			String fname=sc.nextLine();
			try{
				FileReader fr=new FileReader(fname);
				BufferedReader br=new BufferedReader(fr);
				String s="";
				while((s=br.readLine())!=null) {
					List<String> l=new ArrayList<>();
					String tuple[]=s.split(",");
					for(String str:tuple) {
						l.add(str);
					}
					dataset.add(l);
				}
				br.close();
			}
			catch(Exception e) {
				System.out.println("file does not exist");
				System.exit(0);
			}
		}
		float findCi(String clabel) {
			int count=0;
			int n=dataset.size();
			int col=dataset.get(0).size()-1;
			for(int i=1;i<dataset.size();i++) {
				if(dataset.get(i).get(col).equals(clabel))
					count++;
			}
			return ((float)count)/(n-1);
		}
		List<String> makeList(String s1,String s2){
			List<String> list=new ArrayList<>();
			list.add(s1);
			list.add(s2);
			return list;
		}
		boolean isSublist(List<String> tuple,List<String> set) {
			for(String s:set) {
				if(!tuple.contains(s)) {
					return false;
				}
			}
			return true;
		}
		float findXCi(String clabel,List<String> x) {
			int col=dataset.get(0).size()-1;
			float prob=1;
			for(int i=1;i<x.size();i++) {
				int count=0,denom=0;
				for(int j=0;j<dataset.size();j++) {
					if(dataset.get(j).get(i).equals(x.get(i))&&dataset.get(j).get(col).equals(clabel))
						count++;
					if(dataset.get(j).get(col).equals(clabel))
						denom++;
				}
				prob*=((float)count)/(denom);
			}
			return prob;
		}
		void displayData(List<List<String>> dataset) {
			for(List<String> list:dataset) {
				for(String str:list) {
					System.out.print(str+"\t");
				}
				System.out.println();
			}
		}
		void testData(List<List<String>> data) {
			System.out.println("\n\ntesting results");
	//		System.out.println("yes:"+findCi("yes")+"   no:"+findCi("no"));
			int ch=50-data.get(0).toString().length();
			System.out.print(data.get(0).toString());
			for(int i=0;i<ch;i++)
				System.out.print(" ");
			System.out.println("buys_computer");
			for(int i=1;i<data.size();i++) {
				System.out.print(data.get(i));
				ch=50-data.get(i).toString().length();
				for(int p=0;p<ch;p++)
					System.out.print(" ");

				if(findXCi("yes",data.get(i))*findCi("yes")>findXCi("no",data.get(i))*findCi("no")) {
					System.out.println("yes");
				}else {
					System.out.println("no");
				}
			}
		}
		public static void main(String[] args) throws Exception {
			NaiveBays nb=new NaiveBays();
			nb.readData(nb.dataset);
			nb.displayData(nb.dataset);
			List<List<String>> test_dta=new ArrayList<>();
			nb.readData(test_dta);
			nb.testData(test_dta);
		}
}
