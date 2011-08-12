package querymanager.exps;

public class CompareConditionExp extends ConditionExp {
	CompareOp compareOp;
	
	public CompareConditionExp(CompareOp compareOp){
		this.compareOp = compareOp;
	}
}
