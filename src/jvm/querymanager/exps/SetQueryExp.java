package querymanager.exps;

public class SetQueryExp extends QueryExp {
	QueryExp query1;
	QueryExp query2;
	int option;
	public static int EXCEPT = 0;
	public static int UNION = 0;
	public static int INTERSECT = 0;
	public SetQueryExp(QueryExp query1, QueryExp query2, int option){
		this.query1 = query1;
		this.query2 = query2;
		this.option = option;
	}
}
