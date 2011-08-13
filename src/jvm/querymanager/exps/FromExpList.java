package querymanager.exps;

public class FromExpList extends Exp {
	FromExp head;
	FromExpList tail;
	
	public FromExpList(FromExp head, FromExpList tail){
		this.head = head;
		this.tail = tail;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public FromExp getHead() {
		return head;
	}

	public void setHead(FromExp head) {
		this.head = head;
	}

	public FromExpList getTail() {
		return tail;
	}

	public void setTail(FromExpList tail) {
		this.tail = tail;
	}

}
