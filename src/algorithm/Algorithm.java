package algorithm;

import java.util.ArrayList;

import tools.Position;

public class Algorithm {
	public static ArrayList<Position> AlkToussaint( ArrayList<Position> points) {
		if (points.size()<4) return points;
		Position ouest = points.get(0);
		Position sud = points.get(0);
		Position est = points.get(0);
		Position nord = points.get(0);
        for (Position p: points){
            if (p.x<ouest.x) ouest=p;
            if (p.y>sud.y) sud=p;
            if (p.x>est.x) est=p;
            if (p.y<nord.y) nord=p;
        }
        ArrayList<Position> result = new ArrayList<Position>();
        result.add(ouest);
        result.add(sud);
        result.add(est);
        result.add(nord);

        ArrayList<Position> rest = (ArrayList<Position>)points.clone();
        for (int i=0;i<rest.size();i++) {
            if (triangleContientPoint(ouest,sud,est,rest.get(i)) ||
                    triangleContientPoint(ouest,est,nord,rest.get(i))) {
                rest.remove(i);
                i--;
                    }
        }

        for (int i=0;i<result.size();i++) {
        	Position a = result.get(i);
        	Position b = result.get((i+1)%result.size());
        	Position ref = result.get((i+2)%result.size());

            double signeRef = crossProduct(a,b,a,ref);
            double maxValue = 0;
            Position maxPoint = a;

            for (Position p: points) {
                double piki = crossProduct(a,b,a,p);
                if (signeRef*piki<0 && Math.abs(piki)>maxValue) {
                    maxValue = Math.abs(piki);
                    maxPoint = p;
                }
            }
            if (maxValue!=0){
                for (int j=0;j<rest.size();j++) {
                    if (triangleContientPoint(a,b,maxPoint,rest.get(j))){
                        rest.remove(j);
                        j--;
                    }
                }
                result.add(i+1,maxPoint);
                i--;
            }
        }
        return result;
	}
	private static boolean triangleContientPoint(Position a, Position b, Position c, Position x) {
        double l1 = ((b.y-c.y)*(x.x-c.x)+(c.x-b.x)*(x.y-c.y))/(double)((b.y-c.y)*(a.x-c.x)+(c.x-b.x)*(a.y-c.y));
        double l2 = ((c.y-a.y)*(x.x-c.x)+(a.x-c.x)*(x.y-c.y))/(double)((b.y-c.y)*(a.x-c.x)+(c.x-b.x)*(a.y-c.y));
        double l3 = 1-l1-l2;
        return (0<l1 && l1<1 && 0<l2 && l2<1 && 0<l3 && l3<1);
    }
	private static double crossProduct(Position p, Position q, Position s, Position t){
        return ((q.x-p.x)*(t.y-s.y)-(q.y-p.y)*(t.x-s.x));
    }
}
