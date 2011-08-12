package querymanager.exps;

public class ExistConditionExp extends ConditionExp {
	boolean isNot;
	QueryExp query;
	
	public ExistConditionExp(boolean isNot, QueryExp query){
		this.isNot = isNot;
		this.query = query;
	}
}
