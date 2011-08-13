package querymanager.exps;

public class ValueListInOrNotConditionExp extends InOrNotConditionExp {
	ConstantList values;
	
	public ValueListInOrNotConditionExp(ParameterExp parameter, ConstantList values, boolean isNot){
		super(parameter, isNot);
		this.values = values;
	}

	public ConstantList getValues() {
		return values;
	}

	public void setValues(ConstantList values) {
		this.values = values;
	}
}
