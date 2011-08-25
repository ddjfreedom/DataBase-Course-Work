package querymanager.exps;

public class IndexElementExp extends Exp {
	String parameterName;
	AscOrDesc ascOrDesc;
	
	public IndexElementExp(String parameterName, AscOrDesc ascOrDesc){
		this.parameterName = parameterName;
		this.ascOrDesc = ascOrDesc;
	}
	
	@Override
	Result execute() {
		return null;
	}

}
