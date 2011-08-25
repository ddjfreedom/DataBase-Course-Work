package querymanager.exps;

public class DropExp extends Exp {
	String listName;
	RestrictOrCascade restrictOrCascade;
	
	public DropExp(String listName, RestrictOrCascade restrictOrCascade){
		this.listName = listName;
		this.restrictOrCascade = restrictOrCascade;
	}
	@Override
	Result execute() {
		return null;
	}

}
