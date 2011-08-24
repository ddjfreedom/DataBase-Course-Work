package querymanager.lexical;
import ErrorMsg.ErrorMsg;
import querymanager.syntax.sym;

%%
%{
	private ErrorMsg errorMsg;
	private String tempString;
	Yylex(java.io.InputStream instream, ErrorMsg errorMsg){
		this();
		if(null == instream){
		throw (new Error("Error:Bad input stream initializer."));
	}
	yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	this.errorMsg = errorMsg;
}

private java_cup.runtime.Symbol tok(int k,Object value){
	return new java_cup.runtime.Symbol(k, yychar, yychar+yylength(), value);
}
%}

%function next_token
%type java_cup.runtime.Symbol
%implements java_cup.runtime.Scanner
%char
%eofval{
	{return tok(sym.EOF,null);}
%eofval}
%state STR
%public
digits=[0-9]+
doubleNum=[0-9]+[\.][0-9]+

%%
<YYINITIAL>create		{return tok(sym.CREATE,null);}
<YYINITIAL>update		{return tok(sym.UPDATE,null);}
<YYINITIAL>alter	{return tok(sym.ALTER,null);}
<YYINITIAL>insert		{return tok(sym.INSERT,null);}
<YYINITIAL>delete		{return tok(sym.DELETE,null);}
<YYINITIAL>assert		{return tok(sym.ASSERT,null);}
<YYINITIAL>database		{return tok(sym.DATABASE,null);}
<YYINITIAL>values		{return tok(sym.VALUES,null);}
<YYINITIAL>into		{return tok(sym.INTO,null);}
<YYINITIAL>distinct		{return tok(sym.DISTINCT,null);}
<YYINITIAL>drop		{return tok(sym.DROP,null);}
<YYINITIAL>table		{return tok(sym.TABLE,null);}
<YYINITIAL>select		{return tok(sym.SELECT,null);}
<YYINITIAL>from		{return tok(sym.FROM,null);}
<YYINITIAL>where		{return tok(sym.WHERE,null);}
<YYINITIAL>order		{return tok(sym.ORDER,null);}
<YYINITIAL>by		{return tok(sym.BY,null);}
<YYINITIAL>like		{return tok(sym.LIKE,null);}
<YYINITIAL>index		{return tok(sym.INDEX,null);}
<YYINITIAL>check		{return tok(sym.CHECK,null);}
<YYINITIAL>trigger		{return tok(sym.TRIGGER,null);}
<YYINITIAL>in		{return tok(sym.IN,null);}
<YYINITIAL>view		{return tok(sym.VIEW,null);}
<YYINITIAL>between		{return tok(sym.BETWEEN,null);}
<YYINITIAL>count		{return tok(sym.COUNT,null);}
<YYINITIAL>group		{return tok(sym.GROUP,null);}
<YYINITIAL>set		{return tok(sym.SET,null);}
<YYINITIAL>sum		{return tok(sym.SUM,null);}
<YYINITIAL>avg		{return tok(sym.AVG,null);}
<YYINITIAL>max		{return tok(sym.MAX,null);}
<YYINITIAL>min		{return tok(sym.MIN,null);}
<YYINITIAL>and		{return tok(sym.AND,null);}
<YYINITIAL>or		{return tok(sym.OR,null);}
<YYINITIAL>escape		{return tok(sym.ESCAPE,null);}
<YYINITIAL>primary		{return tok(sym.PRIMARY,null);}
<YYINITIAL>key		{return tok(sym.KEY,null);}
<YYINITIAL>references		{return tok(sym.REFERENCES,null);}
<YYINITIAL>join		{return tok(sym.JOIN,null);}
<YYINITIAL>not		{return tok(sym.NOT,null);}
<YYINITIAL>null		{return tok(sym.NULL,null);}
<YYINITIAL>as		{return tok(sym.AS,null);}
<YYINITIAL>on		{return tok(sym.ON,null);}
<YYINITIAL>all		{return tok(sym.ALL,null);}
<YYINITIAL>intersect		{return tok(sym.INTERSECT,null);}
<YYINITIAL>except		{return tok(sym.EXCEPT,null);}
<YYINITIAL>union		{return tok(sym.UNION,null);}
<YYINITIAL>natural		{return tok(sym.NATURAL,null);}
<YYINITIAL>full		{return tok(sym.FULL,null);}
<YYINITIAL>schema		{return tok(sym.SCHEMA,null);}
<YYINITIAL>outer		{return tok(sym.OUTER,null);}
<YYINITIAL>inner		{return tok(sym.INNER,null);}
<YYINITIAL>having		{return tok(sym.HAVING,null);}
<YYINITIAL>is		{return tok(sym.IS,null);}
<YYINITIAL>char		{return tok(sym.CHAR,null);}
<YYINITIAL>date		{return tok(sym.DATE,null);}
<YYINITIAL>int		{return tok(sym.INT,null);}
<YYINITIAL>varchar		{return tok(sym.VARCHAR,null);}
<YYINITIAL>cascade		{return tok(sym.CASCADE,null);}
<YYINITIAL>unknown		{return tok(sym.UNKNOWN,null);}
<YYINITIAL>true		{return tok(sym.TRUE,null);}
<YYINITIAL>false		{return tok(sym.FALSE,null);}
<YYINITIAL>cross		{return tok(sym.CROSS,null);}
<YYINITIAL>any		{return tok(sym.ANY,null);}
<YYINITIAL>time		{return tok(sym.TIME,null);}
<YYINITIAL>asc		{return tok(sym.ASC, null);}
<YYINITIAL>desc		{return tok(sym.DESC, null);}

<YYINITIAL>CREATE		{return tok(sym.CREATE,null);}
<YYINITIAL>UPDATE		{return tok(sym.UPDATE,null);}
<YYINITIAL>ALTER	{return tok(sym.ALTER,null);}
<YYINITIAL>INSERT		{return tok(sym.INSERT,null);}
<YYINITIAL>DELETE		{return tok(sym.DELETE,null);}
<YYINITIAL>ASSERT		{return tok(sym.ASSERT,null);}
<YYINITIAL>DATABASE		{return tok(sym.DATABASE,null);}
<YYINITIAL>VALUES		{return tok(sym.VALUES,null);}
<YYINITIAL>INTO		{return tok(sym.INTO,null);}
<YYINITIAL>DISTINCT		{return tok(sym.DISTINCT,null);}
<YYINITIAL>DROP		{return tok(sym.DROP,null);}
<YYINITIAL>TABLE		{return tok(sym.TABLE,null);}
<YYINITIAL>SELECT		{return tok(sym.SELECT,null);}
<YYINITIAL>FROM		{return tok(sym.FROM,null);}
<YYINITIAL>WHERE		{return tok(sym.WHERE,null);}
<YYINITIAL>ORDER		{return tok(sym.ORDER,null);}
<YYINITIAL>BY		{return tok(sym.BY,null);}
<YYINITIAL>LIKE		{return tok(sym.LIKE,null);}
<YYINITIAL>INDEX		{return tok(sym.INDEX,null);}
<YYINITIAL>CHECK		{return tok(sym.CHECK,null);}
<YYINITIAL>TRIGGER		{return tok(sym.TRIGGER,null);}
<YYINITIAL>IN		{return tok(sym.IN,null);}
<YYINITIAL>VIEW		{return tok(sym.VIEW,null);}
<YYINITIAL>BETWEEN		{return tok(sym.BETWEEN,null);}
<YYINITIAL>COUNT		{return tok(sym.COUNT,null);}
<YYINITIAL>GROUP		{return tok(sym.GROUP,null);}
<YYINITIAL>SET		{return tok(sym.SET,null);}
<YYINITIAL>SUM		{return tok(sym.SUM,null);}
<YYINITIAL>AVG		{return tok(sym.AVG,null);}
<YYINITIAL>MAX		{return tok(sym.MAX,null);}
<YYINITIAL>MIN		{return tok(sym.MIN,null);}
<YYINITIAL>AND		{return tok(sym.AND,null);}
<YYINITIAL>OR		{return tok(sym.OR,null);}
<YYINITIAL>ESCAPE		{return tok(sym.ESCAPE,null);}
<YYINITIAL>PRIMARY		{return tok(sym.PRIMARY,null);}
<YYINITIAL>KEY		{return tok(sym.KEY,null);}
<YYINITIAL>REFERENCES		{return tok(sym.REFERENCES,null);}
<YYINITIAL>JOIN		{return tok(sym.JOIN,null);}
<YYINITIAL>NOT		{return tok(sym.NOT,null);}
<YYINITIAL>NULL		{return tok(sym.NULL,null);}
<YYINITIAL>AS		{return tok(sym.AS,null);}
<YYINITIAL>ON		{return tok(sym.ON,null);}
<YYINITIAL>ALL		{return tok(sym.ALL,null);}
<YYINITIAL>INTERSECT		{return tok(sym.INTERSECT,null);}
<YYINITIAL>EXCEPT		{return tok(sym.EXCEPT,null);}
<YYINITIAL>UNION		{return tok(sym.UNION,null);}
<YYINITIAL>NATURAL		{return tok(sym.NATURAL,null);}
<YYINITIAL>FULL		{return tok(sym.FULL,null);}
<YYINITIAL>SCHEMA		{return tok(sym.SCHEMA,null);}
<YYINITIAL>OUTER		{return tok(sym.OUTER,null);}
<YYINITIAL>INNER		{return tok(sym.INNER,null);}
<YYINITIAL>HAVING		{return tok(sym.HAVING,null);}
<YYINITIAL>IS		{return tok(sym.IS,null);}
<YYINITIAL>CHAR		{return tok(sym.CHAR,null);}
<YYINITIAL>DATE		{return tok(sym.DATE,null);}
<YYINITIAL>INT		{return tok(sym.INT,null);}
<YYINITIAL>VARCHAR		{return tok(sym.VARCHAR,null);}
<YYINITIAL>CASCADE		{return tok(sym.CASCADE,null);}
<YYINITIAL>UNKNOWN		{return tok(sym.UNKNOWN,null);}
<YYINITIAL>TRUE		{return tok(sym.TRUE,null);}
<YYINITIAL>FALSE		{return tok(sym.FALSE,null);}
<YYINITIAL>CROSS		{return tok(sym.CROSS,null);}
<YYINITIAL>ANY		{return tok(sym.ANY,null);}
<YYINITIAL>TIME		{return tok(sym.TIME,null);}
<YYINITIAL>ASC	{return tok(sym.ASC, null);}
<YYINITIAL>DESC		{return tok(sym.DESC, null);}
<YYINITIAL>smallint		{return tok(sym.SMALLINT, null);}
<YYINITIAL>SMALLINT		{return tok(sym.SMALLINT, null);}
<YYINITIAL>REAL		{return tok(sym.REAL, null);}
<YYINITIAL>real		{return tok(sym.REAL, null);}
<YYINITIAL>FLOAT		{return tok(sym.FLOAT, null);}
<YYINITIAL>float		{return tok(sym.FLOAT, null);}
<YYINITIAL>UNIGUE		{return tok(sym.UNIQUE, null);}
<YYINITIAL>unique		{return tok(sym.UNIQUE, null);}


<YYINITIAL>"*"		{return tok(sym.STAR,null);}
<YYINITIAL>","		{return tok(sym.COMMA,null);}
<YYINITIAL>":"		{return tok(sym.COLON,null);}
<YYINITIAL>";"		{return tok(sym.SEMICOLON,null);}
<YYINITIAL>"("		{return tok(sym.LPAREN,null);}
<YYINITIAL>")"		{return tok(sym.RPAREN,null);}
<YYINITIAL>"."		{return tok(sym.DOT,null);}
<YYINITIAL>"+"		{return tok(sym.PLUS,null);}
<YYINITIAL>"-"		{return tok(sym.MINUS,null);}
<YYINITIAL>"/"		{return tok(sym.DIVIDE,null);}

<YYINITIAL>"="		{return tok(sym.EQ,null);}
<YYINITIAL>"<>"		{return tok(sym.NEQ,null);}
<YYINITIAL>"<"		{return tok(sym.LT,null);}
<YYINITIAL>"<="		{return tok(sym.LE,null);}
<YYINITIAL>">"		{return tok(sym.GT,null);}
<YYINITIAL>">="		{return tok(sym.GE,null);}
<YYINITIAL>"||"		{return tok(sym.CONNECT,null);}


<YYINITIAL>[a-zA-Z][a-zA-Z0-9_#]*	{return  tok(sym.ID,yytext());}
<YYINITIAL>{doubleNum} {return tok(sym.DOUBLENUMBER, new Double(yytext()));}
<YYINITIAL>{digits}	{return tok(sym.NUMBER, new Integer(yytext()));}
<YYINITIAL>(" "|\r\n|\t|\n)+ {}


<YYINITIAL>"'"		{tempString = new String();yybegin(STR);}
<STR>"'"			{yybegin(YYINITIAL);return tok(sym.STRING, tempString);}
<STR>[^\'\n]+		{tempString = tempString.concat(yytext());}


<YYINITIAL>.			{errorMsg.error(yychar,"illegal character");}
