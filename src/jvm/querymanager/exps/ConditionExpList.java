package querymanager.exps;

public class ConditionExpList extends Exp {
	ConditionExp head;
	ConditionExpList tail;
	
	public ConditionExpList(ConditionExp head, ConditionExpList tail){
		this.head = head;
		this.tail = tail;
	}
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
