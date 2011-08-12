package querymanager.exps;

public class OrderExp extends Exp {
	ParameterExp parameter;
	AscOrDesc ascOrDesc;
	public OrderExp(ParameterExp parameter, AscOrDesc ascOrDesc){
		this.parameter = parameter;
		this.ascOrDesc = ascOrDesc;
	}
	
	@Override
	Result execute() {
		return null;
	}

}
