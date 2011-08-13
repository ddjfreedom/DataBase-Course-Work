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

	public ConditionExpList getHead() {
		return head;
	}

	public void setHead(ConditionExpList head) {
		this.head = head;
	}

	public ConditionExpLists getTail() {
		return tail;
	}

	public void setTail(ConditionExpLists tail) {
		this.tail = tail;
	}

}
