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

	public ParameterExp getParameter() {
		return parameter;
	}

	public void setParameter(ParameterExp parameter) {
		this.parameter = parameter;
	}

	public AscOrDesc getAscOrDesc() {
		return ascOrDesc;
	}

	public void setAscOrDesc(AscOrDesc ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}

}
