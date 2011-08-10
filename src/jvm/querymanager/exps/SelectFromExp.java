package jvm.querymanager.exps;

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

}
