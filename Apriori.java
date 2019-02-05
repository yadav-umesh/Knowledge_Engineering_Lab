import java.util.*;
import java.io.*;
public class Apriori {
	
      static public List<String> selfJoin(List<String> lk)
	{
		List<String> ck=new ArrayList<String>();
		if(lk.size()==1)
			return lk;
		for(int i=0;i<lk.size()-1;i++)
		{
			int in=0;
			String s=lk.get(i);
			String str[]=new String[s.length()];
			StringTokenizer st=new StringTokenizer(s,",");
			while(st.hasMoreTokens())
			{
				str[in]=st.nextToken();
				in++;
			}
			for(int j=i+1;j<lk.size();j++)
			{
				int in2=0;
				String s1=lk.get(j);
				String str2[]=new String[s1.length()];
				StringTokenizer st2=new StringTokenizer(s1,",");
				while(st2.hasMoreTokens())
				{
					str2[in2]=st2.nextToken();
					in2++;
				}
				int x=0;
				String output="";
				if(in2==1)
					x=1;
				for(int u=0;u<in2-1;u++)
				{
					if(str[u].equals(str2[u]))
					{
						output=output+str[u]+",";
						x=1;
					}
					else
					{
						x=0;break;
					}
				}
				if(x==1)
				{
					if(str[in-1].compareTo(str2[in2-1])<0)
					output=output+str[in-1]+","+str2[in2-1];
					else
						output=output+str2[in-1]+","+str[in-1];
					ck.add(output);
				}				
			}
		}
		return ck;
	}
	
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter no. of transactions");
		int n=Integer.parseInt(sc.nextLine());
		String trans[][]=new String[n][];
		List<String> inp=new ArrayList<String>();
		String input[]=new String[n];
		
		for(int i=0;i<n;i++)
		{
			System.out.println("Enter transaction "+(i+1)+" items(Separated with comma):");
			input[i]=sc.nextLine();
			inp.add(input[i]);
			String []parts=input[i].split(",");
			input[i]=" ";
			trans[i]=parts;
		}
//			System.out.println("Input Items:");
//			for(int j=0;j<inp.size();j++)
//			System.out.println(inp.get(j));
			
		System.out.println("The Items entered are:");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<trans[i].length;j++)
			System.out.print(trans[i][j]);
			
			System.out.println();
		}
	
		System.out.println("Enter support count value:");
		int supc=0;
		supc=Integer.parseInt(sc.nextLine());
	
		
		HashMap<String,Integer> tb=new HashMap<>();	
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<trans[i].length;j++)
			{
				if (tb.containsKey(trans[i][j]))
				{
					int v=tb.get(trans[i][j]);
					v++;
					tb.put(trans[i][j], v);
				}
				else
					tb.put(trans[i][j],1);
			}
		}
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<trans[i].length;j++)
			{
				try{
				int v=tb.get(trans[i][j]);
				if(v<supc){
					tb.remove(trans[i][j]);
				}
				}
				catch(Exception e){
					
				}
			}
		}
	
		List<String> lk=new ArrayList<String>();
		
		System.out.println(tb);
		
		List<Map<String, Integer>> L = new ArrayList<Map<String, Integer>>(); 
		
		for (String key: tb.keySet()) {
		    lk.add(key);
		}
		
		List<String> ck=new ArrayList<String>();
		List<String> print=new ArrayList<String>();
		
//		System.out.println("CK");
//		for(int k = 0; k < ck.size(); k++){
//            System.out.println(ck.get(k));
//			}
//		System.out.println("LK");
//		for(int k = 0; k < lk.size(); k++){
//            System.out.println(lk.get(k));
//			}
		
		  System.out.println("Frequent Items are:");
		while(!lk.isEmpty())
		{
			ck=selfJoin(lk);
			if(ck.size()==1)
				break;
		//	System.out.println("After Self joining");
			ck=removeDup(ck);
//			for(int k = 0; k < ck.size(); k++){
//	            System.out.println(ck.get(k));
//				}
//			
			
			ck=prune(ck,lk);
			//System.out.println("After Pruning");
			ck=removeDup(ck);
//			for(int k = 0; k < ck.size(); k++){
//	            System.out.println(ck.get(k));
//				}
//			
			lk=find(ck,supc,inp,n);
			lk=removeDup(lk);
			print=lk;
		//	System.out.println("LK:");
			for(int k = 0; k < print.size(); k++){
	            System.out.println(print.get(k));
				}
		}
        
            print=find(ck,supc,inp,n);
          
			for(int k = 0; k < print.size(); k++)
			{
	            System.out.println(print.get(k));
			}
			
			System.out.print("Bye");
	}



	private static List<String> removeDup(List<String> ck) {
		// TODO Auto-generated method stub
		
	List<String> tmp=new ArrayList<String>();
//		for(int i=0;i<ck.size();i++)
//		{
//			for(int j=i+1;j<ck.size();j++){
//				if(ck.get(i).equals(ck.get(j)))
//				{
//					//ck.remove(j);
//				}
//				
//			}
//		}
		Set<String> s = new LinkedHashSet<String>(ck);
		tmp.addAll(s);
		return tmp;
	}

	private static List<String> prune(List<String> ck,List<String> lk) {
		// TODO Auto-generated method stub
		List<String> tempy=new ArrayList();
		List<String> tempy1=new ArrayList();
	//	System.out.println("Entered prune method:");
		
		for(int h=0;h<ck.size();h++)
		{
			String temp=ck.get(h),sub;
			StringTokenizer st=new StringTokenizer(temp,",");
			String t="",k="";
			while(st.hasMoreElements())
			{
				t=st.nextToken();
				k=k.concat(t);
			}
			temp=k;
		int l=temp.length();
		
		for (int c = 0; c < l; c++)
	      {
	         for(int i = c+1; i <= l; i++)
	         {
	            sub = temp.substring(c,i);
				StringTokenizer stk=new StringTokenizer(sub,",");
				String tt="",kk="";
				while(stk.hasMoreElements())
				{
					tt=stk.nextToken();
					kk=kk.concat(tt);
				}
				sub=kk;
				//System.out.println(sub);
	            if(sub.length()==l-1)
	            {
	            	tempy.add(sub);
	            	// System.out.println("Sub "+sub);
	            }
	         }
	      }
		
		String lkt;
		List<String> temp1=new ArrayList();
		for(int i=0;i<lk.size();i++)
		{
			lkt=lk.get(i);
			StringTokenizer stk=new StringTokenizer(lkt,",");
			String tt="",kk="";
			while(stk.hasMoreElements())
			{
				tt=stk.nextToken();
				kk=kk.concat(tt);
			}
			//lk.remove(i);
			temp1.add(kk);
		}
		lk=temp1;
		
		
		
		
		for(int i=0;i<tempy.size();i++)
		{
			for(int j=0;j<lk.size();j++)
			{
			//	System.out.println("LK:"+lk.get(j));
				if(tempy.get(i).equals(lk.get(j)))
				{
				//	System.out.println("Tempy:"+tempy.get(i)+" "+lk.get(j));
					tempy1.add(ck.get(h));
			//		System.out.println("CK:"+ck.get(h));
				}
			}
		}
		
		}
		return tempy1;
	}
	
	static List<String> find(List<String> ck, int supc, List<String> inp,int n)
	{
		HashMap<String,Integer> tb=new HashMap<>();
		List<String> tmp=new ArrayList<String>();
		List<String> tmp1=new ArrayList<String>();
	String lkt="";
//		for(int i=0;i<ck.size();i++)
//		{
//			lkt=ck.get(i);
//			StringTokenizer stk=new StringTokenizer(lkt,",");
//			String tt="",kk="";
//			while(stk.hasMoreElements())
//			{
//				tt=stk.nextToken();
//				kk=kk.concat(tt);
//			}
//			//ck.remove(i);
//			tmp1.add(kk);
//		}
//		System.out.println("Ck inside find");
//		ck=tmp1;
//		for(int i=0;i<ck.size();i++)
//		{
//			System.out.println(ck.get(i));
//			
//		}
//		System.out.println("inp inside find");
//		tmp1.clear();
		for(int i=0;i<inp.size();i++)
		{
			lkt=inp.get(i);
			StringTokenizer stk=new StringTokenizer(lkt,",");
			String tt="",kk="";
			while(stk.hasMoreElements())
			{
				tt=stk.nextToken();
				kk=kk.concat(tt);
			}
		//	inp.remove(i);
			tmp1.add(kk);
		}
		inp=tmp1;
//		for(int i=0;i<inp.size();i++)
//		{
//			System.out.println(inp.get(i));
//			
//		}
		int cnt=0;
		String t;
		for(int i=0;i<ck.size();i++)
		{
			cnt=0;
			t=ck.get(i);
		//	System.out.println("T:"+t);
			StringTokenizer st=new StringTokenizer(t,",");
			String sarr[]=new String[1000];
			int in=0;
			while(st.hasMoreTokens())
			{
				sarr[in]=st.nextToken();
			//	System.out.println(sarr[in]);
				in++;
			}
			//Set<String> keys=hmap.keySet();
			int count=0;
			int h=in;
				List<String> l1=new ArrayList<String>();
				l1=inp;
				int x=0;
				for(int k=0;k<l1.size();k++)
				{
					
				for(int j=0;j<in;j++)
				{
					if((l1.get(k)).contains(sarr[j]))
						x=1;
					else { x=0; break; }
				}
				
				if(x==1)
					count++;
				
				}
			
			if(count>=supc)
			{
			//	System.out.println(t+" "+count);
				tmp.add(t);
				//System.out.println(ck.get(i)+"  "+cnt);
			}
		}
		
	return tmp;
	}
}