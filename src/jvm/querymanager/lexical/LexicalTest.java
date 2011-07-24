package querymanager.lexical;

import java.io.FileNotFoundException;
import java.io.IOException;


public class LexicalTest {
	public static void main(String [] args){
		String fileName = args[0];
		ErrorMsg.ErrorMsg errorMsg = new ErrorMsg.ErrorMsg(fileName);
	      java.io.InputStream inp = null;
		try {
			inp = new java.io.FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      Yylex lexer = new Yylex(inp,errorMsg);
	      java_cup.runtime.Symbol tok = null;

	      do { 
	         try {
				tok=lexer.next_token();
				System.out.printf("%s\t\t%s\n",symnames[tok.sym],tok.value);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	      } while (tok.sym != sym.EOF);

	    try {
			inp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static String symnames[] = new String[100];
	  static {
			symnames[sym.CREATE] = "create";
			symnames[sym.UPDATE] = "UPDATE";
			symnames[sym.ALTER] = "ALTER";
			symnames[sym.INSERT] = "INSERT";
			symnames[sym.UNIQUE] = "UNIQUE";
			symnames[sym.DELETE] = "DELETE";
			symnames[sym.SELECT] = "SELECT";
			symnames[sym.DROP] = "DROP";
			symnames[sym.INDEX] = "INDEX";
			symnames[sym.CHECK] = "CHECK";
			symnames[sym.TRIGGER] = "TRIGGER";
			symnames[sym.ASSERT] = "ASSERT";
			symnames[sym.TABLE] = "TABLE"; 
			symnames[sym.DATABASE] = "DATABASE";
			symnames[sym.VALUES] = "VALUES";
			symnames[sym.INTO] = "INTO";
			symnames[sym.FROM] = "FROM";
			symnames[sym.DISTINCT] = "DISTINCT";
			symnames[sym.LIKE] = "LIKE";
			symnames[sym.IN] = "IN";
			symnames[sym.VIEW] = "VIEW";
			symnames[sym.BETWEEN] = "BETWEEN";
			symnames[sym.COUNT] = "COUNT";
			symnames[sym.GROUP] = "GROUP"; 
			symnames[sym.HAVING] = "HAVING";
			symnames[sym.BY] = "BY";
			symnames[sym.ORDER] = "ORFER";
			symnames[sym.SET] = "SET";
			symnames[sym.SUM] = "SUM";
			symnames[sym.AVG] = "AVG";
			symnames[sym.WHERE] = "WHERE";
			symnames[sym.MAX] = "MAX"; 
			symnames[sym.MIN] = "MIN";
			symnames[sym.AND] = "AND";
			symnames[sym.OR] = "OR";
			symnames[sym.ESCAPE] = "ESCAPE";
			symnames[sym.PRIMARY] = "PRIMARY"; 
			symnames[sym.KEY] = "KEY";
			symnames[sym.REFERENCE] = "REFERENCE";
			symnames[sym.JOIN] = "JOIN";
			symnames[sym.NOT] = "NOT";
			symnames[sym.NULL] = "NULL";
			symnames[sym.AS] = "AS";
			symnames[sym.ON] = "ON";
			symnames[sym.ALL] = "ALL"; 
			symnames[sym.INTERSECT] = "INTERSECT";
			symnames[sym.EXCEPT] = "EXCEPT";
			symnames[sym.UNION] = "UNION";
			symnames[sym.NATURAL] = "NATURAL"; 
			symnames[sym.FULL] = "FULL";
			symnames[sym.SCHEMA] = "SCHEMA";
			symnames[sym.OUTER] = "OUTER"; 
			symnames[sym.INNER] = "INNER";
			symnames[sym.IS] = "IS"; 
			symnames[sym.CHAR] = "CHAR";
			symnames[sym.DATE] = "DATE";
			symnames[sym.INT] = "INT";
			symnames[sym.VARCHAR] = "VARCHAR";
			symnames[sym.CASCADE] = "CASCADE"; 
			symnames[sym.UNKNOWN] = "UNKNOWN";
			symnames[sym.TRUE] = "TRUE";
			symnames[sym.FALSE] = "FALSE";
			symnames[sym.CROSS] = "CROSS";
			symnames[sym.TIME] = "TIME";
			symnames[sym.NUMBER] = "NUMBER"; 
			symnames[sym.STRING] = "STRING";
			symnames[sym.ANY] = "ANY";
			symnames[sym.STAR] = "STAR";
			symnames[sym.COMMA] = "COMMA";
			symnames[sym.COLON] = "COLON";
			symnames[sym.SEMICOLON] = "SEMICOLON";
			symnames[sym.LPAREN] = "LPAREN";
			symnames[sym.RPAREN] = "RPAREN";
			symnames[sym.DOT] = "DOT";
			symnames[sym.PLUS] = "PLUS";
			symnames[sym.MINUS] = "MINUS";
			symnames[sym.EQ] = "EQ";
			symnames[sym.NEQ] = "NEQ";
			symnames[sym.LT] = "LT"; 
			symnames[sym.LE] = "LE";
			symnames[sym.GT] = "GT";
			symnames[sym.GE] = "GE";
			symnames[sym.CONNECT] = "CONNECT";
			symnames[sym.EOF] = "EOF";
			symnames[sym.ID] = "ID";
	     	   }
}
