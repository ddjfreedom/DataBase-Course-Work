package querymanager.exps;

public class SelectFromExp extends Exp {

	TargetExpList targetList;
	FromExpList fromList;
	DisOrAll disOrAll;
	public SelectFromExp(DisOrAll disOrAll, TargetExpList targetList, FromExpList fromList){
		this.disOrAll = disOrAll;
		this.targetList = targetList;
		this.fromList = fromList;
	}
	@Override
	Result execute() {
		return null;
	}
	public TargetExpList getTargetList() {
		return targetList;
	}
	public void setTargetList(TargetExpList targetList) {
		this.targetList = targetList;
	}
	public FromExpList getFromList() {
		return fromList;
	}
	public void setFromList(FromExpList fromList) {
		this.fromList = fromList;
	}
	public DisOrAll getDisOrAll() {
		return disOrAll;
	}
	public void setDisOrAll(DisOrAll disOrAll) {
		this.disOrAll = disOrAll;
	}

}
