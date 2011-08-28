package querymanager.exps;

public class SetQueryExp extends QueryExp {
	QueryExp query1;
	QueryExp query2;
	int option;
	public static int EXCEPT = 0;
	public static int UNION = 1;
	public static int INTERSECT = 2;
	public SetQueryExp(QueryExp query1, QueryExp query2, int option){
		this.query1 = query1;
		this.query2 = query2;
		this.option = option;
	}
	public QueryExp getQuery1() {
		return query1;
	}
	public void setQuery1(QueryExp query1) {
		this.query1 = query1;
	}
	public QueryExp getQuery2() {
		return query2;
	}
	public void setQuery2(QueryExp query2) {
		this.query2 = query2;
	}
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
	}
}
