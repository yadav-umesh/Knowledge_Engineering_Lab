import java.util.*;
import java.io.*;
public class Apriori_2 {
	static boolean isSubList(List<String> l1,List<String> l2) {
		boolean flag=true;
		for(int i=0;i<l2.size();i++) {
			if(!l1.contains(l2.get(i))) {
				flag=false;
			}
		}
		return flag;
	}
	static List<String> returnList(List<String> l1,List<String> l2) {
		List<String> jl=new ArrayList<String>();
		if(l1.size()==1) {
			jl.add(l1.get(0));
			jl.add(l2.get(0));
			return jl;
		}
		int i=0;
		for(i=0;i<l1.size()-1;i++) {
			if(l1.get(i)!=l2.get(i)) {
				break;
			}
		}
		if(i==l1.size()-1)
		{
			jl.addAll(l1);
			jl.add(l2.get(l2.size()-1));
		    return jl;
		}
		return null;
	}
	static LinkedHashMap<List<String>, Integer> getCandiCount(List<List<String>> trans,LinkedHashMap<List<String>,Integer> C1) {
		for(List<String>  ll: C1.keySet()) {
			int count=0;
			for(int t=0;t<trans.size();t++) {
				if(isSubList(trans.get(t),ll)) {
					count++;
				}
			}
			C1.put(ll,count);
		}
		return C1;
	}
	static void displayC_Form_L(LinkedHashMap<List<String>, Integer> C1, List<List<String>> L1, int h,int min_sup) {
		if(!C1.isEmpty())
			System.out.println("Condidate items C"+h);
		for(List<String>  ll: C1.keySet()) {
			System.out.println(ll+" : "+C1.get(ll).intValue());
			if(C1.get(ll).intValue()>=min_sup) {
				L1.add(ll);
				
			}
		}
	}
	static int dispayL(List<List<String>> L1,int h) {
		if(!L1.isEmpty())
			System.out.println("List of frequent  itemset L"+(h++));
			for(int i=0;i<L1.size();i++) {
				System.out.println(">"+L1.get(i));
		}
		return h;
	}
	public static void main(String[] args) throws Exception {
		int min_sup=2;
		List<List<String>> trans=new ArrayList<>();
		LinkedHashMap< List<String>,Integer> C1=new LinkedHashMap<>();
		List<List<String>> L1=new ArrayList<>();
		Scanner sc=new Scanner(System.in);		
		System.out.print("Enter file name(csv file):");
		String fname=sc.nextLine();
		FileReader fr=new FileReader(fname);
		BufferedReader br=new BufferedReader(fr);
		String str="";
		String ite[]=br.readLine().split(",");
		for(String s:ite) {
			List<String> l=new ArrayList<>();
			l.add(s);
			C1.put(l, 0);
		}
		while((str=br.readLine())!=null) {
			String tr[]=str.split(",");
			int p=0;
			List<String> li=new ArrayList<>();
			for(String s:tr) {
				if(s.equals("")) {
					p++;
				}
				else {
					li.add(ite[p++]);
				}
			}
			trans.add(li);
		}
		br.close();
		for(int i=0;i<trans.size();i++) {
			int j=i+1;
			System.out.println("T"+j+":"+trans.get(i));
		}		
		System.out.print("Enter minimum support count:");
		min_sup=sc.nextInt();
		int h=1;
		C1=getCandiCount(trans, C1);
		displayC_Form_L(C1,L1,h,min_sup);
		h=dispayL(L1, h);
		while(!C1.isEmpty()||!L1.isEmpty()) {
			C1.clear();
			for(int i=0;i<L1.size();i++) {
				for(int j=i+1;j<L1.size();j++) {
					List<String> l=returnList(L1.get(i), L1.get(j));
					if(l!=null)
						C1.put(l,0);
				}
			}
			L1.clear();
			C1=getCandiCount(trans, C1);
			displayC_Form_L(C1,L1,h,min_sup);
			h=dispayL(L1, h);
		}
	}
}
