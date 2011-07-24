package querymanager.exps;

public class ConditionExpLists extends Exp {
	ConditionExpList head;
	ConditionExpLists tail;
	
	public ConditionExpLists(ConditionExpList head, ConditionExpLists tail){
		this.head = head;
		this.tail = tail;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
