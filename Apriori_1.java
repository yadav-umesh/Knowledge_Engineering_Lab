private static scanner reader=new scanner(System.in);
private static int mincount;
private static ArrayList<TreeSet<String>> transactions=new ArrayList<>();
private static ArrayList<TreeMap<String,Integer>> L=new ArrayList<>();
private static ArrayList<TreeMap<String,Integer>> C=new ArrayList<>();
public static void main(String args[]) throws FileNotFoundException
{
	println("enter path of the transaction file");
	String filepath=reader.nextLine();
	Scanner sc=new Scanner(new File(filepath));
	while(scan.hasNextLine())
	{
		String line=sc.nextLine();
		String[] lineArray=line.split(regex = ",");
		TreeSet<String> set=new TreeSet<>(Arrays.asList(lineArray));
		transactions.add(set);
		
		
		
		private static TreeMap<String,Integer> findL1()
		{
			TreeMap<String,Integer> C1=new TreeMap<>();
			for(TreeSet<String> transaction:transactions)
			{
				for(String item:transaction)
				{
					if(!C1.containsKey(item))
					{
						C1.put(item,1);
						
					}
					else
					{
						C1.put(item,C1.get(item)+1);
						
					}
				}
			}
			C.add(C1);
			
	        TreeMap<String,Integer> L1=new TreeMap<>();
	        for(Map.Entry<String,Integer> entry: C1.entrySet())
	        {
	        	if(entry.getValue()>=mincount)
	        	{
	        		L1.put(entry.getKey(),entry.getValue());
	        		
	        	}
	        }
	        return L1;
		}
		
		//find L1=frequent 1 item-data-sets
		L.add(findL1());
		printMap(L.get(L.size()-1));
		for(int k=2;!L.get(k-1).isEmpty();++k)
		{
			C.add(AprioriGen(L.get(k-1)));
			printMap(C.get(C.size()-1));
			TreeMap<String,Integer> l=new TreeMap<>();
			for(Map.Entry<String,Integer) c: C.get(C.size()-1).entrySet())
{
	if(c.getValue()>=mincount)
	{
		l.put(c.getKey(),c.getValue());
		
	}
}
L.add(l);
printMap(L.get(L.size()-1));

}
		System.out.println("Frequents items sets are as below--:");
		printMap(L.get(L.size()-2));
		
		
		private static TreeMap<String,Integer> AprioriGen(TreeMap<String,Integer> l){
			TreeMap<String,Integer> c=new TreeMap<>();
			//since l1 andd l2 will be of form 'ABC' 'DCE'
			for(Map.Entry<String,Integer>)
		}
		
		
	}
}