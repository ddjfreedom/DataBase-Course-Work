package querymanager.exps;

public class ParameterExp extends Exp {
	String listName;
	String parameter;
	public static final String STAR = "1111";
	public ParameterExp(String listName, String parameter){
		this.listName = listName;
		this.parameter = parameter;
	}
	@Override
	Result execute() {
		return null;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}
