package querymanager.exps;

public class AssignmentExpList extends Exp {
	AssignmentExp head;
	AssignmentExpList tail;
	
	public AssignmentExpList(AssignmentExp head, AssignmentExpList tail){
		this.head = head;
		this.tail = tail;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
