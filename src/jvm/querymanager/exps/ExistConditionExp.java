package querymanager.exps;

public class ExistConditionExp extends ConditionExp {
	boolean not;
	QueryExp query;
	
	public ExistConditionExp(boolean not, QueryExp query){
		this.not = not;
		this.query = query;
	}
}
