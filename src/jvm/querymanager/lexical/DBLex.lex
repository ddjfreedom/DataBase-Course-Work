package querymanager.lexical;
import ErrorMsg.ErrorMsg;

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
<YYINITIAL>reference		{return tok(sym.REFERENCE,null);}
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


<YYINITIAL>"*"		{return tok(sym.STAR,null);}
<YYINITIAL>","		{return tok(sym.COMMA,null);}
<YYINITIAL>":"		{return tok(sym.COLON,null);}
<YYINITIAL>";"		{return tok(sym.SEMICOLON,null);}
<YYINITIAL>"("		{return tok(sym.LPAREN,null);}
<YYINITIAL>")"		{return tok(sym.RPAREN,null);}
<YYINITIAL>"."		{return tok(sym.DOT,null);}
<YYINITIAL>"+"		{return tok(sym.PLUS,null);}
<YYINITIAL>"-"		{return tok(sym.MINUS,null);}
<YYINITIAL>"/"		{return tok(sym.DEVIDE,null);}

<YYINITIAL>"="		{return tok(sym.EQ,null);}
<YYINITIAL>"<>"		{return tok(sym.NEQ,null);}
<YYINITIAL>"<"		{return tok(sym.LT,null);}
<YYINITIAL>"<="		{return tok(sym.LE,null);}
<YYINITIAL>">"		{return tok(sym.GT,null);}
<YYINITIAL>">="		{return tok(sym.GE,null);}
<YYINITIAL>"||"		{return tok(sym.CONNECT,null);}


<YYINITIAL>[a-zA-Z][a-zA-Z0-9_#]*	{return  tok(sym.ID,yytext());}
<YYINITIAL>{doubleNum} {return tok(sym.DOUBLE, new Double(yytext()));}
<YYINITIAL>{digits}	{return tok(sym.NUMBER, new Integer(yytext()));}
<YYINITIAL>(" "|\r\n|\t|\n)+ {}


<YYINITIAL>"'"		{tempString = new String();yybegin(STR);}
<STR>"'"			{yybegin(YYINITIAL);return tok(sym.STRING, tempString);}
<STR>[^\'\n]+		{tempString = tempString.concat(yytext());}


<YYINITIAL>.			{errorMsg.error(yychar,"illegal character");}
