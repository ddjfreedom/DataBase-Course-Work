package querymanager.exps;

/*
 * TargetExpList represents the part that follows the 'select'
 * and stands before 'where' in the select sql expression
 */
public class TargetExpList extends Exp {
	TargetExp head;
	TargetExpList tail;
	
	public TargetExpList(TargetExp target, TargetExpList tail){
		this.head = target;
		this.tail = tail;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
