import java.io.*;
import java.util.*;
public class Apriori_algorithm

{
	int min_sup;
	//Total input transactions are stored into this hmap object
	Map<String,List<String>> hmap=new HashMap<>();
	
	//Final output will be stored into this ans object
	Map<String,String> ans=new HashMap<>();
	public void singleItemGeneration( )
	{
		Map<String,Integer> str=new HashMap<>();		//to store candidate set ck
		Set<String> keys=hmap.keySet();       			//To iterate over hashmap
		for(String i:keys)								//For all key values pair traverse
		{
			List<String> s1=new ArrayList<String>();
			s1=hmap.get(i);								// Taking each List<String> from hashmap to check all the elements
			for(int j=0;j<s1.size();j++)				//Traversing inside the list
			{
				if(str.containsKey(s1.get(j)))			// Checking if already it is present in the candidate set  
					{
						int r=str.get(s1.get(j));		//taking the count of the item
						r++;
						str.put(s1.get(j),r);
					}							
				else
					str.put(s1.get(j),1);				//The item is seen first time so making count as 1 and inserting in the list
			}
		}		
		Set<String> kkr=str.keySet();
		for(String cout:kkr)					
		{
			int c=str.get(cout);
			if(c>=min_sup)								// Checking if the count is greater than minimum support 
				ans.put(cout,Integer.toString(c));		// if condition satisfied then put in the ans as L1
		}
	}
	
	public void frequentItems()
	{
		// For the second item onwards
		int count;
		List<String> lk=new ArrayList<String>();   // TO store lk candidate sets
		List<String> ck=new ArrayList<String>();	// To store ck candidate sets
		Set<String> key=ans.keySet();
		for(String i:key)
			lk.add(i);					// Retrieving the l1 items
		while(!lk.isEmpty())			// Repeats loop until lk is not empty
		{
			ck.clear();					// Clearing ck to delete older ck-1 items
			ck=selfJoin(lk);// Performing self join operation to generate ck from lk-1
			lk.clear();					// Here lk is clearing to delete lk-1 items and to freshly insert lk items
			for(int i=0;i<ck.size();i++)
			{
				count=Scan(ck.get(i));
				if(count>=min_sup)
				{
				lk.add(ck.get(i));
				ans.put(ck.get(i),Integer.toString(count));
				}
			}
		}
		Set<String> keys=ans.keySet();
		for(String i:keys )
		{
			System.out.println(i+"\t \t \t count="+ans.get(i));
		}
		
	}
	
	public int Scan(String s)
	{
		String sarr[]=s.split(",");			// Splitting the string as individual items
		Set<String> keys=hmap.keySet();		// To iterate over original database
		int count=0;					// TO check how many times its repeated
		for(String i:keys)
		{
			List<String> l1=new ArrayList<String>();		 
			l1=hmap.get(i);								// Retrieving each itemset and placing in l1 list
			if(l1.containsAll(Arrays.asList(sarr)))		// Checking whether all the items of the string are present or not
				count++;
		}
		return count;
	}
	
	public List<String> selfJoin(List<String> lk)
	{
		List<String> ck=new ArrayList<String>();
		if(lk.size()==1)
			return lk;
		for(int i=0;i<lk.size()-1;i++)
		{
			int in=0;
			String s=lk.get(i);
			String str[]=s.split(","); // As we are getting the items in string format with , separated we are splitting them into an array
			in=str.length;				// Specifying the length of that array
			for(int j=i+1;j<lk.size();j++)
			{
				int in2=0;
				String s1=lk.get(j);		// We are taking the next string 
				String str2[]=s1.split(",");			// and converting into array
				in2=str2.length;					// specifying the length
				int x=0;
				String output="";				// The output string that has to be generated
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
						x=0;
						break;
					}
				}
				if(x==1)
				{
					// We are doing in two steps to preserve the lexicographic order
					if(str[in-1].compareTo(str2[in2-1])<0)			// Comparing lexicographically it is small or big
					output=output+str[in-1]+","+str2[in2-1];		// In the output string we are combining the items with , separation
					else
						output=output+str2[in-1]+","+str[in-1]; 		// Here also we are combining the items with , separation
					ck.add(output);											
				}				
			}
		}
		ck=prune(ck,lk);
		return ck;
	}
	
	public List<String> subset(String s)
	{
		//Generating subsets
		List<String> ans=new ArrayList<String>();
		String str[]=s.split(",");				// Splitting the string to get individual items 
		for(int i=0;i<str.length;i++)
		{
			String sub1[]=Arrays.copyOfRange(str,0,i);		// the elements before ith index
				String sub2[]=Arrays.copyOfRange(str,i+1,str.length);		//the elements after ith index
				String s3=String.join(",",sub1)+","+String.join(",",sub2);	// Generating total substring joining with , as seperator
				s3=s3.replaceAll("^,+","");						// Removing , if the substring is started with ,
				s3=s3.replaceAll(",+$","");						// Removing , if the substring is ended with ,
			ans.add(s3);								// adding the substring to the list
		}	
		return ans;
	}
	
	public List<String> prune(List<String> ck,List<String> lk)
	{
		// Here Lk denotes Lk-1  
		List<String> ans=new ArrayList<String>();
		for(int i=0;i<ck.size();i++)
		{
			//For every string in the ck we are generating another list
		String s=ck.get(i);
		if(s.length()<=2)
			return ck;
		List<String> sub=subset(s);		// Each string is in the form of I1,I2,I3 i.e.combination of items
		if(lk.containsAll(sub))			// checking whether Lk-1 contains all subsets
			ans.add(s);					// If all subsets are present then it is sent to scan
		}
		return ans;
	}
	
	
	public static void main(String args[])
	{
		//Creating object of class apriori_algorithm
		Apriori_algorithm ap=new Apriori_algorithm();
		Scanner sc=new Scanner(System.in);
		
		//the first of dataset contains attribute names to store those names
		List<String> columnNames=new ArrayList<String>();	
		int count=0;
		
		//Taking input dataset from user
		System.out.println("Give  the file name of transactional datset along with the  path of directory");
		String path=sc.nextLine();
		
		//taking minimum support as input
		System.out.println("Enter minimum support count");
		ap.min_sup=sc.nextInt();
		
		//reading all transactions placing in the hmap object
		try
		{
			Scanner fileReader=new Scanner(new FileReader(path));
			while(fileReader.hasNext())
			{
				List<String> itemset=new ArrayList<String>();
				Set<String> items=new HashSet<String>();
				String line = fileReader.nextLine();
				String st[]=line.split(",");
				if(count==0)
				{
					columnNames.addAll(Arrays.asList(st));
				}
				else
				{
				items.addAll(Arrays.asList(st));
				itemset.addAll(items);
				ap.hmap.put(Integer.toString(count),itemset);				
				}
				count++;
			}
			fileReader.close();
		}
		catch(Exception ex){ System.out.println("Something wrong with the file:Maybe file not found"); }
		ap.singleItemGeneration();
		ap.frequentItems();
	}		
}
