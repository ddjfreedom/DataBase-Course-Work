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
	public ConditionExp getHead() {
		return head;
	}
	public void setHead(ConditionExp head) {
		this.head = head;
	}
	public ConditionExpList getTail() {
		return tail;
	}
	public void setTail(ConditionExpList tail) {
		this.tail = tail;
	}

}
