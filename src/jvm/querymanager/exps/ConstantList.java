package querymanager.exps;

public class ConstantList {
	Constant head;
	ConstantList tail;
	
	public ConstantList(Constant head, ConstantList tail){
		this.head = head;
		this.tail = tail;
	}

	public Constant getHead() {
		return head;
	}

	public void setHead(Constant head) {
		this.head = head;
	}

	public ConstantList getTail() {
		return tail;
	}

	public void setTail(ConstantList tail) {
		this.tail = tail;
	}
}
