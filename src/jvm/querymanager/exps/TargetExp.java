package querymanager.exps;

public class TargetExp extends Exp {
	String alias;
	
	public TargetExp() {
		alias = null;
	}
	
	public TargetExp(String alias){
		this.alias = alias;
	}
	
	@Override
	Result execute() {
		return null;
	}

}
