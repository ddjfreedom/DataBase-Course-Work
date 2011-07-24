package querymanager.exps;

public class AnyAllCompareCompareConditionExp extends CompareConditionExp {
	ParameterExp operand1;
	AnyOrAll anyOrall;
	QueryExp query;
	
	public AnyAllCompareCompareConditionExp(ParameterExp operand1, AnyOrAll anyOrAll, QueryExp query){
		this.operand1 = operand1;
		this.anyOrall = anyOrAll;
		this.query = query;
	}
}
