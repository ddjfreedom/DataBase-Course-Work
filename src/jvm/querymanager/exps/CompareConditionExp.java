package querymanager.exps;

public class CompareConditionExp extends ConditionExp {
	CompareOp compareOp;
	
	public CompareConditionExp(CompareOp compareOp){
		this.compareOp = compareOp;
	}

	public CompareOp getCompareOp() {
		return compareOp;
	}

	public void setCompareOp(CompareOp compareOp) {
		this.compareOp = compareOp;
	}
}
