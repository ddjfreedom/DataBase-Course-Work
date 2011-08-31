package querymanager.exps;

public class TableElementTypeExp extends Exp {
	String parameterName;
	ElementDataType elementType;
	Restrict restrict;
	
	public TableElementTypeExp(String name, ElementDataType elementType, Restrict restrict){
		this.parameterName = name;
		this.elementType = elementType;
		this.restrict = restrict;
	}
	
	@Override
	Result execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getParameterName() {
		return parameterName;
	}

	public ElementDataType getElementType() {
		return elementType;
	}

	public Restrict getRestrict() {
		return restrict;
	}

}
