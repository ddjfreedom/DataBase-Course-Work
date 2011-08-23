package querymanager.exps;

public class ConstantInsertExp extends InsertExp {
	ConstantList values;
	public ConstantInsertExp(String listName, ParameterExpList parameterList, ConstantList values) {
		super(listName, parameterList);
		this.values = values;
	}
}
