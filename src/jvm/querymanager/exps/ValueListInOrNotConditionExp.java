package querymanager.exps;

public class ValueListInOrNotConditionExp extends InOrNotConditionExp {
	ConstantList values;
	
	public ValueListInOrNotConditionExp(ParameterExp parameter, ConstantList values){
		super(parameter);
		this.values = values;
	}
}
