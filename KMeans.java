
import java.util.*;
class Point{
	double x;
	double y;
	public double euclideanDistance(Point p1,Point p2){
		return Math.sqrt( Math.pow(p1.x-p2.x, 2)+Math.pow(p1.y-p2.y,2));
	}
	public String toString(){
		return "("+this.x+" , "+this.y+")";
	}
	public Point centroid(List<Point> l){
		Point p=new Point();
		double x=0,y=0;
		for(Point pt:l){
			x+=pt.x;
			y+=pt.y;		
		}
		p.x=(double)(x/l.size());
		p.y=(double)(y/l.size());
		return p;
	}
	public boolean samePoints(Point p1,Point p2){
		if(p1.x==p2.x&&p1.y==p2.y)
			return true;
		return false;
	}
}
public class KMeans {
	public static void main(String args[]){
		System.out.println("enter the number of points ");
		int n;
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		n=sc.nextInt();
		List<Point> points=new ArrayList<Point>();
		while(n!=0){
			n--;
			System.out.println("Enter x and y values");
			Point p=new Point();
			p.x=sc.nextInt();
			p.y=sc.nextInt();
			points.add(p);
		}
		int k;
		System.out.println("enter the k value");
		k=sc.nextInt();
		List<Integer> rand=new ArrayList<Integer>();
		int i=1;
		while(i<points.size()){
			rand.add(i);
			i++;
		}
		Collections.shuffle(rand);
		List<Point> centroids=new ArrayList<Point>();
		int j=0;
		while(j<k){
			j++;
			centroids.add(points.get(rand.get(j)));
		}
		List<List<Point>> clusters=new ArrayList<List<Point>>();
		clusters.add(new ArrayList<>());
		clusters.add(new ArrayList<>());
		clusters.add(new ArrayList<>());
		List<Point> cluster = null;
		int re=0;
		while(true){	
			clusters=new ArrayList<List<Point>>();
			clusters.add(new ArrayList<>());
			clusters.add(new ArrayList<>());
			clusters.add(new ArrayList<>());
			for(Point x:points){
				double min=9999,dist;
				cluster=new ArrayList<>();
				for(int it=0;it<centroids.size();it++){
					Point c=centroids.get(it);
					dist=c.euclideanDistance(c, x);
					if(dist<min){
						cluster=clusters.get(it);
						re=it;
						min=dist;
					}
				}
				cluster.add(x);
				clusters.remove(re);
				clusters.add(re, cluster);
			}
			List<Point> nc=new ArrayList<>();
			for(int it=0;it<centroids.size();it++){
				Point p=new Point();
				p=p.centroid(clusters.get(it));
				nc.add(p);
			}
			boolean b=true;
			for(int it=0;it<centroids.size();it++){
				if(!centroids.get(it).samePoints(centroids.get(it),nc.get(it))){
					b=false;
					break;
				}
			}
			if(b)
				break;
			else
				centroids=nc;
		}
		for(List<Point> p:clusters) {
			if(p.size()!=0)
				System.out.println(p);
		}
	}
}