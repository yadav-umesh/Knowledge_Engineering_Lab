package FpTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Tree
{
	String item_name;
	int cnt=0;
	Tree parentNode;
	List<Tree> childs=new ArrayList<Tree>();

	int counts;
	 Tree nextNode;
	public String getItem() {
		return item_name;
	}
	//For Setting Item name
	public void setItem(String item) {
		this.item_name = item;
	}
	// To get parent node name
	public Tree getParentNode() {
		return parentNode;
	}
	//Setting parent name
	public void setParentNode(Tree parentNode) {
		this.parentNode = parentNode;
	}
	//Getting all childs associated with particular node
	public List<Tree> getChildNodes() {
		return childs;
	}
	//Setting all child for a node
	public void setChildNodes(List<Tree> childNodes) {
		this.childs = childs;
	}
	//count of item
	public int getCounts() {
		return counts;
	}
	// increment count
	public void increCounts() {
		this.counts = counts + 1;
	}
	//Getting next node
	public Tree getNextNode() {
		return nextNode;
	}
	//Setting next node
	public void setNextNode(Tree nextNode) {
		this.nextNode = nextNode;
	}
	//Setting count for a node
	public void setCounts(int counts) {
		this.counts = counts;
	}
	
}

public class Fp_tree 
{
	  static  int minsupc=0;
	  
	public static void itemSort(final Map<String, Integer> itemMap, ArrayList<ArrayList<String>> itset) {
			
			for(ArrayList<String> items : itset) {
				Collections.sort(items, new Comparator<String>() {
					@Override
					public int compare(String key1, String key2) {
						return itemMap.get(key2) - itemMap.get(key1);
					}
				});
			}
		}
	  
		public static void addAdjNode(Tree tn, ArrayList<Tree> head) 
		{
			Tree curNode = null;
			for(Tree node : head) {
				if(node.getItem().equals(tn.getItem())) {
					curNode = node;
					while(null != curNode.getNextNode()) {
						curNode = curNode.getNextNode();
					}
					curNode.setNextNode(tn);
				}
			}
		}
		
	 static public ArrayList<ArrayList<String>> read_csv(String path, String separator) throws IOException {
			File f = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String str;
			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
			while((str = reader.readLine()) != null) {
				if(!"".equals(str)) {
					ArrayList<String> tmpList = new ArrayList<String>();
					String[] s = str.split(separator);
					for(int i = 0; i <s.length; i++) {
						tmpList.add(s[i]);
					}
					list.add(tmpList);
				}
			}
			return list;
		}
	 
		public static Tree insert_tree(ArrayList<ArrayList<String>> itemSet, ArrayList<Tree> head) 
		{
			Tree root = new Tree();
			Tree curNode = root;
			for(ArrayList<String> items : itemSet) {
				for(String item : items) {
					Tree tmp = findChildNode(item, curNode);
					if(null == tmp) {
						tmp = new Tree();
						tmp.setItem(item);
						tmp.setParentNode(curNode);
						curNode.getChildNodes().add(tmp);
						addAdjNode(tmp, head);
					}
					curNode = tmp;
					tmp.increCounts();
				}
				curNode = root;
			}
			return root;
		}
	 
	 
	 public static ArrayList<Tree> buildHeadTable(ArrayList<ArrayList<String>> imtemSet) 
		{
			ArrayList<Tree> head = new ArrayList<Tree>();
			
			Map<String, Integer> itemMap = new HashMap<String, Integer>();
			
			for(ArrayList<String> items : imtemSet) {
				for(String item : items) {
					if(itemMap.get(item) == null) {
						itemMap.put(item, 1);
					} else {
						itemMap.put(item, itemMap.get(item) + 1);
					}
				}
			}
			
			Iterator<String> ite = itemMap.keySet().iterator();
			String key;
			List<String> abandonSet = new ArrayList<String>();
			while(ite.hasNext()) {
				key = (String)ite.next();
				
				if(itemMap.get(key) <minsupc) 
				{
					ite.remove();
					abandonSet.add(key);
				} 
				else
				{
					// Making a tree node such that we can traverse through the nodes.
					Tree tn = new Tree();
					tn.increCounts();
					tn.setItem(key);
					tn.setCounts(itemMap.get(key));
					//Head or header table consists of tree nodes..
					head.add(tn);
				}
			}
			
			for(ArrayList<String> items : imtemSet) {
				items.removeAll(abandonSet);
			}
			
			itemSort(itemMap, imtemSet);
			
			Collections.sort(head, new Comparator<Tree>() {
				public int compare(Tree key1, Tree key2) {
					return key2.getCounts() - key1.getCounts();
				}
			});
			return head;
		}
		
		public static Tree findChildNode(String item, Tree curNode) {
			List<Tree> childs = curNode.getChildNodes();
			if(null != childs) {
				for(Tree tn : curNode.getChildNodes()) {
					if(tn.getItem().equals(item)) {
						return tn;
					}
				}
			}
			return null;
		}
		
	 
	static void FP_growth(ArrayList<ArrayList<String>> ds,ArrayList<String> conditionalPattern)
	{
		//1.Building header table
		ArrayList<Tree> header=buildHeadTable(ds);
		//2.Build Fp tree
		Tree root =insert_tree(ds,header);
		//recursion exit
		if(root.getChildNodes().size()==0)
		{
			return;
		}
		//print pattern
		if (null != conditionalPattern)
		{
			for(Tree tn : header) {
				for(String s : conditionalPattern) {
					System.out.print(s + " ");
				}
				System.out.println(tn.getItem() + " :" + tn.getCounts());
			}
		}
		
		for (Tree hd:header)
		{
			ArrayList<String> Pattern=new ArrayList<String>();
			Pattern.add(hd.getItem());
			if(null!=conditionalPattern)
			{
				Pattern.addAll(conditionalPattern);
			}
			ArrayList<ArrayList<String>> newItemSet = new ArrayList<ArrayList<String>>();
			Tree curNode = hd.getNextNode();
			//finding conditional pattern bases
			while (curNode != null) 
			{
                int counter = curNode.getCounts();
                ArrayList<String> parentNodes = new ArrayList<String>();
                Tree parent = curNode;
                
                // traverse all parent nodes of curNode and put them into parentNodes
                while ((parent = parent.getParentNode()).getItem() != null) {
                    parentNodes.add(parent.getItem());
                }
                while (counter-- > 0) {
                	newItemSet.add(parentNodes);
                }
                curNode = curNode.getNextNode();
            }
			FP_growth(newItemSet,Pattern);
		}
		
	}
		public static void main(String args[]) throws IOException
		{
			ArrayList<ArrayList<String>> dataset=read_csv("C:\\Users\\Documents\\KE Lab\\data Set\\input.txt", ",");
			 System.out.println("Enter minimum support:");
			 Scanner sc=new Scanner(System.in);
			 int supc=sc.nextInt();
			 minsupc=supc;
			 System.out.println("Dataset is:"+dataset);
			 System.out.println("Frequent Items are:");
			 FP_growth(dataset,null);
		}
}
