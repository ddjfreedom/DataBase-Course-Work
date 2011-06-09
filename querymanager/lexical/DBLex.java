package querymanager.lexical;
import ErrorMsg.ErrorMsg;


class Yylex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int STR = 1;
	private final int yy_state_dtrans[] = {
		0,
		95
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NOT_ACCEPT,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NO_ANCHOR,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NO_ANCHOR,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NO_ANCHOR,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NO_ANCHOR,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NO_ANCHOR,
		/* 208 */ YY_NO_ANCHOR,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NO_ANCHOR,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NO_ANCHOR,
		/* 213 */ YY_NO_ANCHOR,
		/* 214 */ YY_NO_ANCHOR,
		/* 215 */ YY_NO_ANCHOR,
		/* 216 */ YY_NO_ANCHOR,
		/* 217 */ YY_NO_ANCHOR,
		/* 218 */ YY_NO_ANCHOR,
		/* 219 */ YY_NO_ANCHOR,
		/* 220 */ YY_NO_ANCHOR,
		/* 221 */ YY_NO_ANCHOR,
		/* 222 */ YY_NO_ANCHOR,
		/* 223 */ YY_NO_ANCHOR,
		/* 224 */ YY_NO_ANCHOR,
		/* 225 */ YY_NO_ANCHOR,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NO_ANCHOR,
		/* 228 */ YY_NO_ANCHOR,
		/* 229 */ YY_NO_ANCHOR,
		/* 230 */ YY_NO_ANCHOR,
		/* 231 */ YY_NO_ANCHOR,
		/* 232 */ YY_NO_ANCHOR,
		/* 233 */ YY_NO_ANCHOR,
		/* 234 */ YY_NO_ANCHOR,
		/* 235 */ YY_NO_ANCHOR,
		/* 236 */ YY_NO_ANCHOR,
		/* 237 */ YY_NO_ANCHOR,
		/* 238 */ YY_NO_ANCHOR,
		/* 239 */ YY_NO_ANCHOR,
		/* 240 */ YY_NO_ANCHOR,
		/* 241 */ YY_NO_ANCHOR,
		/* 242 */ YY_NO_ANCHOR,
		/* 243 */ YY_NO_ANCHOR,
		/* 244 */ YY_NO_ANCHOR,
		/* 245 */ YY_NO_ANCHOR,
		/* 246 */ YY_NO_ANCHOR,
		/* 247 */ YY_NO_ANCHOR,
		/* 248 */ YY_NO_ANCHOR,
		/* 249 */ YY_NO_ANCHOR,
		/* 250 */ YY_NO_ANCHOR,
		/* 251 */ YY_NO_ANCHOR,
		/* 252 */ YY_NO_ANCHOR,
		/* 253 */ YY_NO_ANCHOR,
		/* 254 */ YY_NO_ANCHOR,
		/* 255 */ YY_NO_ANCHOR,
		/* 256 */ YY_NO_ANCHOR,
		/* 257 */ YY_NO_ANCHOR,
		/* 258 */ YY_NO_ANCHOR,
		/* 259 */ YY_NO_ANCHOR,
		/* 260 */ YY_NO_ANCHOR,
		/* 261 */ YY_NO_ANCHOR,
		/* 262 */ YY_NO_ANCHOR,
		/* 263 */ YY_NO_ANCHOR,
		/* 264 */ YY_NO_ANCHOR,
		/* 265 */ YY_NO_ANCHOR,
		/* 266 */ YY_NO_ANCHOR,
		/* 267 */ YY_NO_ANCHOR,
		/* 268 */ YY_NO_ANCHOR,
		/* 269 */ YY_NO_ANCHOR,
		/* 270 */ YY_NO_ANCHOR,
		/* 271 */ YY_NO_ANCHOR,
		/* 272 */ YY_NO_ANCHOR,
		/* 273 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"45:9,41,43,45:2,42,45:18,41,45:2,39,45:3,44,29,30,25,32,26,33,31,45,40:10,2" +
"7,28,35,34,36,45:2,38:26,45:4,39,45,4,13,1,8,3,16,23,19,10,24,21,9,17,11,15" +
",7,38,2,12,5,6,14,18,22,20,38,45,37,45:3,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,274,
"0,1,2,1:10,3,4,5,6,7,1,8,9,10:2,11,10,1:4,10:4,12,10:6,13,10:47,14,1,15,16," +
"1,13,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,4" +
"0,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,6" +
"5,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,9" +
"0,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,11" +
"1,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,1" +
"30,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148," +
"149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167" +
",168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,18" +
"6,187,188,189,190,10,191,192,193,194,195")[0];

	private int yy_nxt[][] = unpackFromString(196,46,
"1,2,234,247,91,253,256,258,260,262,94,153,154,96,264,97,265,155,266,267,268" +
",156,268,269,270,3,4,5,6,7,8,9,10,11,12,13,14,15,268,92,16,17,90,17,18,92,-" +
"1:47,268,271,268,272,268:10,273,268:3,157,268:5,-1:13,268:3,-1:39,25,-1,26," +
"-1:43,27,-1:48,28,-1:39,93,-1:8,16,-1:46,17,90,17,-1:3,268:11,255,268:12,-1" +
":13,268:3,-1:6,268:4,33,268:2,192,268:2,193,259,268:12,-1:13,268:3,-1:6,268" +
":24,-1:13,268:3,-1:6,268:7,197,268:16,-1:13,268:3,-1:6,268:2,244,268:11,47," +
"268:9,-1:13,268:3,-1:45,40,-1:6,88:42,-1:2,88,-1:43,17,-1:3,268:8,98,268,99" +
",19,268,100,268:10,-1:13,268:3,-1:6,268:10,20,21,268:12,-1:13,268:3,-1:5,1," +
"88:42,-1,89,88,-1,268:2,237,268:16,22,268:4,-1:13,268:3,-1:6,268,23,268:3,2" +
"49,268:4,24,268:13,-1:13,268:3,-1:6,268:4,186,268:3,29,268:15,-1:13,268:3,-" +
"1:6,268:7,30,268:11,31,268:4,-1:13,268:3,-1:6,268:22,32,268,-1:13,268:3,-1:" +
"6,268:4,34,268:19,-1:13,268:3,-1:6,268:4,35,268:3,263,268:15,-1:13,268:3,-1" +
":6,268:16,36,268:7,-1:13,268:3,-1:6,268:21,37,268:2,-1:13,268:3,-1:6,268:10" +
",38,268:13,-1:13,268:3,-1:6,268:19,39,268:4,-1:13,268:3,-1:6,268,41,268:22," +
"-1:13,268:3,-1:6,268:2,42,268:21,-1:13,268:3,-1:6,268:2,43,268:21,-1:13,268" +
":3,-1:6,268:6,44,268:17,-1:13,268:3,-1:6,268:2,45,212,268:20,-1:13,268:3,-1" +
":6,268:2,46,268:21,-1:13,268:3,-1:6,268:8,48,268:15,-1:13,268:3,-1:6,268:17" +
",49,268:6,-1:13,268:3,-1:6,268:16,50,268:7,-1:13,268:3,-1:6,268:8,51,268:15" +
",-1:13,268:3,-1:6,268:10,52,268:13,-1:13,268:3,-1:6,268:11,53,268:12,-1:13," +
"268:3,-1:6,268:4,54,268:19,-1:13,268:3,-1:6,268:20,55,268:3,-1:13,268:3,-1:" +
"6,268,56,268:22,-1:13,268:3,-1:6,268:2,57,268:21,-1:13,268:3,-1:6,268:10,58" +
",268:13,-1:13,268:3,-1:6,268:21,59,268:2,-1:13,268:3,-1:6,268,60,268:22,-1:" +
"13,268:3,-1:6,268,61,268:22,-1:13,268:3,-1:6,268,62,268:22,-1:13,268:3,-1:6" +
",268:2,63,268:21,-1:13,268:3,-1:6,268:2,64,268:21,-1:13,268:3,-1:6,268:6,65" +
",268:17,-1:13,268:3,-1:6,268:2,66,268:21,-1:13,268:3,-1:6,268:2,67,268:21,-" +
"1:13,268:3,-1:6,268:4,68,268:19,-1:13,268:3,-1:6,268:4,69,268:19,-1:13,268:" +
"3,-1:6,268:2,70,268:21,-1:13,268:3,-1:6,268:17,71,268:6,-1:13,268:3,-1:6,26" +
"8:2,72,268:21,-1:13,268:3,-1:6,268:4,73,268:19,-1:13,268:3,-1:6,268:3,74,26" +
"8:20,-1:13,268:3,-1:6,268:4,75,268:19,-1:13,268:3,-1:6,268:11,76,268:12,-1:" +
"13,268:3,-1:6,268:22,77,268,-1:13,268:3,-1:6,268:2,78,268:21,-1:13,268:3,-1" +
":6,268,79,268:22,-1:13,268:3,-1:6,268:19,80,268:4,-1:13,268:3,-1:6,268:8,81" +
",268:15,-1:13,268:3,-1:6,268:10,82,268:13,-1:13,268:3,-1:6,268,83,268:22,-1" +
":13,268:3,-1:6,268:2,84,268:21,-1:13,268:3,-1:6,268:4,85,268:19,-1:13,268:3" +
",-1:6,268:2,86,268:21,-1:13,268:3,-1:6,268:4,87,268:19,-1:13,268:3,-1:6,268" +
":3,171,268,172,268:8,101,268:9,-1:13,268:3,-1:6,173,268,102,268:2,103,268:1" +
"8,-1:13,268:3,-1:6,268:3,104,268:5,105,268:14,-1:13,268:3,-1:6,268:2,106,26" +
"8:21,-1:13,268:3,-1:6,268:2,185,107,268:20,-1:13,268:3,-1:6,268:15,238,268:" +
"8,-1:13,268:3,-1:6,239,268:23,-1:13,268:3,-1:6,268:5,108,268:3,187,268:14,-" +
"1:13,268:3,-1:6,268:12,188,268:11,-1:13,268:3,-1:6,268:16,109,268:7,-1:13,2" +
"68:3,-1:6,268:7,251,268:16,-1:13,268:3,-1:6,268:9,189,268:10,190,268:3,-1:1" +
"3,268:3,-1:6,268:9,191,268:14,-1:13,268:3,-1:6,268:14,110,268:9,-1:13,268:3" +
",-1:6,268:8,257,268:15,-1:13,268:3,-1:6,268:4,111,268:19,-1:13,268:3,-1:6,2" +
"68:11,254,268:12,-1:13,268:3,-1:6,268:20,112,268:3,-1:13,268:3,-1:6,268:4,1" +
"94,268:19,-1:13,268:3,-1:6,268:8,113,268:15,-1:13,268:3,-1:6,268:18,261,268" +
":5,-1:13,268:3,-1:6,268,196,268:6,241,268:15,-1:13,268:3,-1:6,268:2,114,268" +
":21,-1:13,268:3,-1:6,268:14,115,268:9,-1:13,268:3,-1:6,268:8,116,268:15,-1:" +
"13,268:3,-1:6,268:2,200,268:21,-1:13,268:3,-1:6,268:13,201,268:10,-1:13,268" +
":3,-1:6,268:14,202,268:9,-1:13,268:3,-1:6,268:9,117,268:14,-1:13,268:3,-1:6" +
",268:3,203,268:20,-1:13,268:3,-1:6,268:11,118,268:12,-1:13,268:3,-1:6,268:1" +
"0,119,268:13,-1:13,268:3,-1:6,120,268:23,-1:13,268:3,-1:6,268:2,121,268:21," +
"-1:13,268:3,-1:6,268:22,240,268,-1:13,268:3,-1:6,268:8,122,268:15,-1:13,268" +
":3,-1:6,268:14,123,268:9,-1:13,268:3,-1:6,268:10,210,268:13,-1:13,268:3,-1:" +
"6,268:16,243,268:7,-1:13,268:3,-1:6,268:2,124,268:21,-1:13,268:3,-1:6,268:2" +
",125,268:21,-1:13,268:3,-1:6,268:5,252,268:18,-1:13,268:3,-1:6,268:17,216,2" +
"68:6,-1:13,268:3,-1:6,217,268:23,-1:13,268:3,-1:6,268:2,126,268:21,-1:13,26" +
"8:3,-1:6,268:2,127,268:21,-1:13,268:3,-1:6,268:11,128,268:12,-1:13,268:3,-1" +
":6,268,129,268:22,-1:13,268:3,-1:6,268:9,219,268:14,-1:13,268:3,-1:6,268:5," +
"130,268:18,-1:13,268:3,-1:6,268:4,131,268:19,-1:13,268:3,-1:6,268:3,220,268" +
":20,-1:13,268:3,-1:6,268,245,268:22,-1:13,268:3,-1:6,268:6,132,268:17,-1:13" +
",268:3,-1:6,268:6,133,268:17,-1:13,268:3,-1:6,268,134,268:22,-1:13,268:3,-1" +
":6,268:4,135,268:19,-1:13,268:3,-1:6,268:14,136,268:9,-1:13,268:3,-1:6,268:" +
"4,137,268:19,-1:13,268:3,-1:6,268:12,223,268:11,-1:13,268:3,-1:6,268,138,26" +
"8:22,-1:13,268:3,-1:6,268:16,139,268:7,-1:13,268:3,-1:6,140,268:23,-1:13,26" +
"8:3,-1:6,268:2,227,268:21,-1:13,268:3,-1:6,268:18,228,268:5,-1:13,268:3,-1:" +
"6,268:2,141,268:21,-1:13,268:3,-1:6,268:10,142,268:13,-1:13,268:3,-1:6,268:" +
"7,143,268:16,-1:13,268:3,-1:6,268:2,144,268:21,-1:13,268:3,-1:6,268,145,268" +
":22,-1:13,268:3,-1:6,268:3,229,268:20,-1:13,268:3,-1:6,268:10,230,268:13,-1" +
":13,268:3,-1:6,268:11,231,268:12,-1:13,268:3,-1:6,268:3,146,268:20,-1:13,26" +
"8:3,-1:6,268:2,147,268:21,-1:13,268:3,-1:6,268:3,148,268:20,-1:13,268:3,-1:" +
"6,268:11,149,268:12,-1:13,268:3,-1:6,150,268:23,-1:13,268:3,-1:6,268:2,233," +
"268:21,-1:13,268:3,-1:6,151,268:23,-1:13,268:3,-1:6,152,268:23,-1:13,268:3," +
"-1:6,268:2,158,268:21,-1:13,268:3,-1:6,250,268:23,-1:13,268:3,-1:6,268:8,19" +
"9,268:15,-1:13,268:3,-1:6,268:4,195,268:19,-1:13,268:3,-1:6,268:2,205,268:2" +
"1,-1:13,268:3,-1:6,268:3,206,268:20,-1:13,268:3,-1:6,268:22,221,268,-1:13,2" +
"68:3,-1:6,268:5,218,268:18,-1:13,268:3,-1:6,268:9,224,268:14,-1:13,268:3,-1" +
":6,268:3,222,268:20,-1:13,268:3,-1:6,268,225,268:22,-1:13,268:3,-1:6,268:2," +
"246,268:21,-1:13,268:3,-1:6,268:10,232,268:13,-1:13,268:3,-1:6,268:11,159,2" +
"68:9,235,268:2,-1:13,268:3,-1:6,204,268:23,-1:13,268:3,-1:6,268:4,198,268:1" +
"9,-1:13,268:3,-1:6,268:2,207,268:21,-1:13,268:3,-1:6,268:3,209,268:20,-1:13" +
",268:3,-1:6,268,226,268:22,-1:13,268:3,-1:6,268,160,268,161,268:5,162,268:1" +
"4,-1:13,268:3,-1:6,268:4,242,268:19,-1:13,268:3,-1:6,268:2,208,268:21,-1:13" +
",268:3,-1:6,268:6,163,268:3,164,268:13,-1:13,268:3,-1:6,268:2,211,268:21,-1" +
":13,268:3,-1:6,268,165,268:22,-1:13,268:3,-1:6,268:2,213,268:21,-1:13,268:3" +
",-1:6,268,166,167,168,268:5,169,268:14,-1:13,268:3,-1:6,268:2,214,268:21,-1" +
":13,268:3,-1:6,268:9,170,268:14,-1:13,268:3,-1:6,268:2,215,268:21,-1:13,268" +
":3,-1:6,268:3,174,268:5,175,268:14,-1:13,268:3,-1:6,268,176,268,236,268,177" +
",268:18,-1:13,268:3,-1:6,268:18,178,268:5,-1:13,268:3,-1:6,268:3,179,268:20" +
",-1:13,268:3,-1:6,268,180,268:22,-1:13,268:3,-1:6,268:14,181,268:9,-1:13,26" +
"8:3,-1:6,268:2,182,268:11,183,268:9,-1:13,268:3,-1:6,268:11,248,268:12,-1:1" +
"3,268:3,-1:6,268:5,184,268:18,-1:13,268:3,-1:5");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	{return tok(sym.EOF,null);}
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return  tok(sym.ID,yytext());}
					case -3:
						break;
					case 3:
						{return tok(sym.STAR,null);}
					case -4:
						break;
					case 4:
						{return tok(sym.COMMA,null);}
					case -5:
						break;
					case 5:
						{return tok(sym.COLON,null);}
					case -6:
						break;
					case 6:
						{return tok(sym.SEMICOLON,null);}
					case -7:
						break;
					case 7:
						{return tok(sym.LPAREN,null);}
					case -8:
						break;
					case 8:
						{return tok(sym.RPAREN,null);}
					case -9:
						break;
					case 9:
						{return tok(sym.DOT,null);}
					case -10:
						break;
					case 10:
						{return tok(sym.PLUS,null);}
					case -11:
						break;
					case 11:
						{return tok(sym.MINUS,null);}
					case -12:
						break;
					case 12:
						{return tok(sym.EQ,null);}
					case -13:
						break;
					case 13:
						{return tok(sym.LT,null);}
					case -14:
						break;
					case 14:
						{return tok(sym.GT,null);}
					case -15:
						break;
					case 15:
						{errorMsg.error(yychar,"illegal character");}
					case -16:
						break;
					case 16:
						{return tok(sym.NUMBER, new Integer(yytext()));}
					case -17:
						break;
					case 17:
						{}
					case -18:
						break;
					case 18:
						{tempString = new String();yybegin(STR);}
					case -19:
						break;
					case 19:
						{return tok(sym.AS,null);}
					case -20:
						break;
					case 20:
						{return tok(sym.IN,null);}
					case -21:
						break;
					case 21:
						{return tok(sym.IS,null);}
					case -22:
						break;
					case 22:
						{return tok(sym.BY,null);}
					case -23:
						break;
					case 23:
						{return tok(sym.OR,null);}
					case -24:
						break;
					case 24:
						{return tok(sym.ON,null);}
					case -25:
						break;
					case 25:
						{return tok(sym.LE,null);}
					case -26:
						break;
					case 26:
						{return tok(sym.NEQ,null);}
					case -27:
						break;
					case 27:
						{return tok(sym.GE,null);}
					case -28:
						break;
					case 28:
						{return tok(sym.CONNECT,null);}
					case -29:
						break;
					case 29:
						{return tok(sym.ALL,null);}
					case -30:
						break;
					case 30:
						{return tok(sym.AND,null);}
					case -31:
						break;
					case 31:
						{return tok(sym.ANY,null);}
					case -32:
						break;
					case 32:
						{return tok(sym.AVG,null);}
					case -33:
						break;
					case 33:
						{return tok(sym.INT,null);}
					case -34:
						break;
					case 34:
						{return tok(sym.NOT,null);}
					case -35:
						break;
					case 35:
						{return tok(sym.SET,null);}
					case -36:
						break;
					case 36:
						{return tok(sym.SUM,null);}
					case -37:
						break;
					case 37:
						{return tok(sym.MAX,null);}
					case -38:
						break;
					case 38:
						{return tok(sym.MIN,null);}
					case -39:
						break;
					case 39:
						{return tok(sym.KEY,null);}
					case -40:
						break;
					case 40:
						{return tok(sym.DOUBLE, new Double(yytext()));}
					case -41:
						break;
					case 41:
						{return tok(sym.CHAR,null);}
					case -42:
						break;
					case 42:
						{return tok(sym.TRUE,null);}
					case -43:
						break;
					case 43:
						{return tok(sym.TIME,null);}
					case -44:
						break;
					case 44:
						{return tok(sym.DROP,null);}
					case -45:
						break;
					case 45:
						{return tok(sym.DATE,null);}
					case -46:
						break;
					case 46:
						{return tok(sym.LIKE,null);}
					case -47:
						break;
					case 47:
						{return tok(sym.INTO,null);}
					case -48:
						break;
					case 48:
						{return tok(sym.NULL,null);}
					case -49:
						break;
					case 49:
						{return tok(sym.VIEW,null);}
					case -50:
						break;
					case 50:
						{return tok(sym.FROM,null);}
					case -51:
						break;
					case 51:
						{return tok(sym.FULL,null);}
					case -52:
						break;
					case 52:
						{return tok(sym.JOIN,null);}
					case -53:
						break;
					case 53:
						{return tok(sym.CROSS,null);}
					case -54:
						break;
					case 54:
						{return tok(sym.COUNT,null);}
					case -55:
						break;
					case 55:
						{return tok(sym.CHECK,null);}
					case -56:
						break;
					case 56:
						{return tok(sym.ALTER,null);}
					case -57:
						break;
					case 57:
						{return tok(sym.TABLE,null);}
					case -58:
						break;
					case 58:
						{return tok(sym.UNION,null);}
					case -59:
						break;
					case 59:
						{return tok(sym.INDEX,null);}
					case -60:
						break;
					case 60:
						{return tok(sym.INNER,null);}
					case -61:
						break;
					case 61:
						{return tok(sym.ORDER,null);}
					case -62:
						break;
					case 62:
						{return tok(sym.OUTER,null);}
					case -63:
						break;
					case 63:
						{return tok(sym.FALSE,null);}
					case -64:
						break;
					case 64:
						{return tok(sym.WHERE,null);}
					case -65:
						break;
					case 65:
						{return tok(sym.GROUP,null);}
					case -66:
						break;
					case 66:
						{return tok(sym.CREATE,null);}
					case -67:
						break;
					case 67:
						{return tok(sym.ESCAPE,null);}
					case -68:
						break;
					case 68:
						{return tok(sym.EXCEPT,null);}
					case -69:
						break;
					case 69:
						{return tok(sym.ASSERT,null);}
					case -70:
						break;
					case 70:
						{return tok(sym.UPDATE,null);}
					case -71:
						break;
					case 71:
						{return tok(sym.UNKNOWN,null);}
					case -72:
						break;
					case 72:
						{return tok(sym.DELETE,null);}
					case -73:
						break;
					case 73:
						{return tok(sym.INSERT,null);}
					case -74:
						break;
					case 74:
						{return tok(sym.SCHEMA,null);}
					case -75:
						break;
					case 75:
						{return tok(sym.SELECT,null);}
					case -76:
						break;
					case 76:
						{return tok(sym.VALUES,null);}
					case -77:
						break;
					case 77:
						{return tok(sym.HAVING,null);}
					case -78:
						break;
					case 78:
						{return tok(sym.CASCADE,null);}
					case -79:
						break;
					case 79:
						{return tok(sym.TRIGGER,null);}
					case -80:
						break;
					case 80:
						{return tok(sym.PRIMARY,null);}
					case -81:
						break;
					case 81:
						{return tok(sym.NATURAL,null);}
					case -82:
						break;
					case 82:
						{return tok(sym.BETWEEN,null);}
					case -83:
						break;
					case 83:
						{return tok(sym.VARCHAR,null);}
					case -84:
						break;
					case 84:
						{return tok(sym.DATABASE,null);}
					case -85:
						break;
					case 85:
						{return tok(sym.DISTINCT,null);}
					case -86:
						break;
					case 86:
						{return tok(sym.REFERENCE,null);}
					case -87:
						break;
					case 87:
						{return tok(sym.INTERSECT,null);}
					case -88:
						break;
					case 88:
						{tempString = tempString.concat(yytext());}
					case -89:
						break;
					case 89:
						{yybegin(YYINITIAL);return tok(sym.STRING, tempString);}
					case -90:
						break;
					case 91:
						{return  tok(sym.ID,yytext());}
					case -91:
						break;
					case 92:
						{errorMsg.error(yychar,"illegal character");}
					case -92:
						break;
					case 94:
						{return  tok(sym.ID,yytext());}
					case -93:
						break;
					case 96:
						{return  tok(sym.ID,yytext());}
					case -94:
						break;
					case 97:
						{return  tok(sym.ID,yytext());}
					case -95:
						break;
					case 98:
						{return  tok(sym.ID,yytext());}
					case -96:
						break;
					case 99:
						{return  tok(sym.ID,yytext());}
					case -97:
						break;
					case 100:
						{return  tok(sym.ID,yytext());}
					case -98:
						break;
					case 101:
						{return  tok(sym.ID,yytext());}
					case -99:
						break;
					case 102:
						{return  tok(sym.ID,yytext());}
					case -100:
						break;
					case 103:
						{return  tok(sym.ID,yytext());}
					case -101:
						break;
					case 104:
						{return  tok(sym.ID,yytext());}
					case -102:
						break;
					case 105:
						{return  tok(sym.ID,yytext());}
					case -103:
						break;
					case 106:
						{return  tok(sym.ID,yytext());}
					case -104:
						break;
					case 107:
						{return  tok(sym.ID,yytext());}
					case -105:
						break;
					case 108:
						{return  tok(sym.ID,yytext());}
					case -106:
						break;
					case 109:
						{return  tok(sym.ID,yytext());}
					case -107:
						break;
					case 110:
						{return  tok(sym.ID,yytext());}
					case -108:
						break;
					case 111:
						{return  tok(sym.ID,yytext());}
					case -109:
						break;
					case 112:
						{return  tok(sym.ID,yytext());}
					case -110:
						break;
					case 113:
						{return  tok(sym.ID,yytext());}
					case -111:
						break;
					case 114:
						{return  tok(sym.ID,yytext());}
					case -112:
						break;
					case 115:
						{return  tok(sym.ID,yytext());}
					case -113:
						break;
					case 116:
						{return  tok(sym.ID,yytext());}
					case -114:
						break;
					case 117:
						{return  tok(sym.ID,yytext());}
					case -115:
						break;
					case 118:
						{return  tok(sym.ID,yytext());}
					case -116:
						break;
					case 119:
						{return  tok(sym.ID,yytext());}
					case -117:
						break;
					case 120:
						{return  tok(sym.ID,yytext());}
					case -118:
						break;
					case 121:
						{return  tok(sym.ID,yytext());}
					case -119:
						break;
					case 122:
						{return  tok(sym.ID,yytext());}
					case -120:
						break;
					case 123:
						{return  tok(sym.ID,yytext());}
					case -121:
						break;
					case 124:
						{return  tok(sym.ID,yytext());}
					case -122:
						break;
					case 125:
						{return  tok(sym.ID,yytext());}
					case -123:
						break;
					case 126:
						{return  tok(sym.ID,yytext());}
					case -124:
						break;
					case 127:
						{return  tok(sym.ID,yytext());}
					case -125:
						break;
					case 128:
						{return  tok(sym.ID,yytext());}
					case -126:
						break;
					case 129:
						{return  tok(sym.ID,yytext());}
					case -127:
						break;
					case 130:
						{return  tok(sym.ID,yytext());}
					case -128:
						break;
					case 131:
						{return  tok(sym.ID,yytext());}
					case -129:
						break;
					case 132:
						{return  tok(sym.ID,yytext());}
					case -130:
						break;
					case 133:
						{return  tok(sym.ID,yytext());}
					case -131:
						break;
					case 134:
						{return  tok(sym.ID,yytext());}
					case -132:
						break;
					case 135:
						{return  tok(sym.ID,yytext());}
					case -133:
						break;
					case 136:
						{return  tok(sym.ID,yytext());}
					case -134:
						break;
					case 137:
						{return  tok(sym.ID,yytext());}
					case -135:
						break;
					case 138:
						{return  tok(sym.ID,yytext());}
					case -136:
						break;
					case 139:
						{return  tok(sym.ID,yytext());}
					case -137:
						break;
					case 140:
						{return  tok(sym.ID,yytext());}
					case -138:
						break;
					case 141:
						{return  tok(sym.ID,yytext());}
					case -139:
						break;
					case 142:
						{return  tok(sym.ID,yytext());}
					case -140:
						break;
					case 143:
						{return  tok(sym.ID,yytext());}
					case -141:
						break;
					case 144:
						{return  tok(sym.ID,yytext());}
					case -142:
						break;
					case 145:
						{return  tok(sym.ID,yytext());}
					case -143:
						break;
					case 146:
						{return  tok(sym.ID,yytext());}
					case -144:
						break;
					case 147:
						{return  tok(sym.ID,yytext());}
					case -145:
						break;
					case 148:
						{return  tok(sym.ID,yytext());}
					case -146:
						break;
					case 149:
						{return  tok(sym.ID,yytext());}
					case -147:
						break;
					case 150:
						{return  tok(sym.ID,yytext());}
					case -148:
						break;
					case 151:
						{return  tok(sym.ID,yytext());}
					case -149:
						break;
					case 152:
						{return  tok(sym.ID,yytext());}
					case -150:
						break;
					case 153:
						{return  tok(sym.ID,yytext());}
					case -151:
						break;
					case 154:
						{return  tok(sym.ID,yytext());}
					case -152:
						break;
					case 155:
						{return  tok(sym.ID,yytext());}
					case -153:
						break;
					case 156:
						{return  tok(sym.ID,yytext());}
					case -154:
						break;
					case 157:
						{return  tok(sym.ID,yytext());}
					case -155:
						break;
					case 158:
						{return  tok(sym.ID,yytext());}
					case -156:
						break;
					case 159:
						{return  tok(sym.ID,yytext());}
					case -157:
						break;
					case 160:
						{return  tok(sym.ID,yytext());}
					case -158:
						break;
					case 161:
						{return  tok(sym.ID,yytext());}
					case -159:
						break;
					case 162:
						{return  tok(sym.ID,yytext());}
					case -160:
						break;
					case 163:
						{return  tok(sym.ID,yytext());}
					case -161:
						break;
					case 164:
						{return  tok(sym.ID,yytext());}
					case -162:
						break;
					case 165:
						{return  tok(sym.ID,yytext());}
					case -163:
						break;
					case 166:
						{return  tok(sym.ID,yytext());}
					case -164:
						break;
					case 167:
						{return  tok(sym.ID,yytext());}
					case -165:
						break;
					case 168:
						{return  tok(sym.ID,yytext());}
					case -166:
						break;
					case 169:
						{return  tok(sym.ID,yytext());}
					case -167:
						break;
					case 170:
						{return  tok(sym.ID,yytext());}
					case -168:
						break;
					case 171:
						{return  tok(sym.ID,yytext());}
					case -169:
						break;
					case 172:
						{return  tok(sym.ID,yytext());}
					case -170:
						break;
					case 173:
						{return  tok(sym.ID,yytext());}
					case -171:
						break;
					case 174:
						{return  tok(sym.ID,yytext());}
					case -172:
						break;
					case 175:
						{return  tok(sym.ID,yytext());}
					case -173:
						break;
					case 176:
						{return  tok(sym.ID,yytext());}
					case -174:
						break;
					case 177:
						{return  tok(sym.ID,yytext());}
					case -175:
						break;
					case 178:
						{return  tok(sym.ID,yytext());}
					case -176:
						break;
					case 179:
						{return  tok(sym.ID,yytext());}
					case -177:
						break;
					case 180:
						{return  tok(sym.ID,yytext());}
					case -178:
						break;
					case 181:
						{return  tok(sym.ID,yytext());}
					case -179:
						break;
					case 182:
						{return  tok(sym.ID,yytext());}
					case -180:
						break;
					case 183:
						{return  tok(sym.ID,yytext());}
					case -181:
						break;
					case 184:
						{return  tok(sym.ID,yytext());}
					case -182:
						break;
					case 185:
						{return  tok(sym.ID,yytext());}
					case -183:
						break;
					case 186:
						{return  tok(sym.ID,yytext());}
					case -184:
						break;
					case 187:
						{return  tok(sym.ID,yytext());}
					case -185:
						break;
					case 188:
						{return  tok(sym.ID,yytext());}
					case -186:
						break;
					case 189:
						{return  tok(sym.ID,yytext());}
					case -187:
						break;
					case 190:
						{return  tok(sym.ID,yytext());}
					case -188:
						break;
					case 191:
						{return  tok(sym.ID,yytext());}
					case -189:
						break;
					case 192:
						{return  tok(sym.ID,yytext());}
					case -190:
						break;
					case 193:
						{return  tok(sym.ID,yytext());}
					case -191:
						break;
					case 194:
						{return  tok(sym.ID,yytext());}
					case -192:
						break;
					case 195:
						{return  tok(sym.ID,yytext());}
					case -193:
						break;
					case 196:
						{return  tok(sym.ID,yytext());}
					case -194:
						break;
					case 197:
						{return  tok(sym.ID,yytext());}
					case -195:
						break;
					case 198:
						{return  tok(sym.ID,yytext());}
					case -196:
						break;
					case 199:
						{return  tok(sym.ID,yytext());}
					case -197:
						break;
					case 200:
						{return  tok(sym.ID,yytext());}
					case -198:
						break;
					case 201:
						{return  tok(sym.ID,yytext());}
					case -199:
						break;
					case 202:
						{return  tok(sym.ID,yytext());}
					case -200:
						break;
					case 203:
						{return  tok(sym.ID,yytext());}
					case -201:
						break;
					case 204:
						{return  tok(sym.ID,yytext());}
					case -202:
						break;
					case 205:
						{return  tok(sym.ID,yytext());}
					case -203:
						break;
					case 206:
						{return  tok(sym.ID,yytext());}
					case -204:
						break;
					case 207:
						{return  tok(sym.ID,yytext());}
					case -205:
						break;
					case 208:
						{return  tok(sym.ID,yytext());}
					case -206:
						break;
					case 209:
						{return  tok(sym.ID,yytext());}
					case -207:
						break;
					case 210:
						{return  tok(sym.ID,yytext());}
					case -208:
						break;
					case 211:
						{return  tok(sym.ID,yytext());}
					case -209:
						break;
					case 212:
						{return  tok(sym.ID,yytext());}
					case -210:
						break;
					case 213:
						{return  tok(sym.ID,yytext());}
					case -211:
						break;
					case 214:
						{return  tok(sym.ID,yytext());}
					case -212:
						break;
					case 215:
						{return  tok(sym.ID,yytext());}
					case -213:
						break;
					case 216:
						{return  tok(sym.ID,yytext());}
					case -214:
						break;
					case 217:
						{return  tok(sym.ID,yytext());}
					case -215:
						break;
					case 218:
						{return  tok(sym.ID,yytext());}
					case -216:
						break;
					case 219:
						{return  tok(sym.ID,yytext());}
					case -217:
						break;
					case 220:
						{return  tok(sym.ID,yytext());}
					case -218:
						break;
					case 221:
						{return  tok(sym.ID,yytext());}
					case -219:
						break;
					case 222:
						{return  tok(sym.ID,yytext());}
					case -220:
						break;
					case 223:
						{return  tok(sym.ID,yytext());}
					case -221:
						break;
					case 224:
						{return  tok(sym.ID,yytext());}
					case -222:
						break;
					case 225:
						{return  tok(sym.ID,yytext());}
					case -223:
						break;
					case 226:
						{return  tok(sym.ID,yytext());}
					case -224:
						break;
					case 227:
						{return  tok(sym.ID,yytext());}
					case -225:
						break;
					case 228:
						{return  tok(sym.ID,yytext());}
					case -226:
						break;
					case 229:
						{return  tok(sym.ID,yytext());}
					case -227:
						break;
					case 230:
						{return  tok(sym.ID,yytext());}
					case -228:
						break;
					case 231:
						{return  tok(sym.ID,yytext());}
					case -229:
						break;
					case 232:
						{return  tok(sym.ID,yytext());}
					case -230:
						break;
					case 233:
						{return  tok(sym.ID,yytext());}
					case -231:
						break;
					case 234:
						{return  tok(sym.ID,yytext());}
					case -232:
						break;
					case 235:
						{return  tok(sym.ID,yytext());}
					case -233:
						break;
					case 236:
						{return  tok(sym.ID,yytext());}
					case -234:
						break;
					case 237:
						{return  tok(sym.ID,yytext());}
					case -235:
						break;
					case 238:
						{return  tok(sym.ID,yytext());}
					case -236:
						break;
					case 239:
						{return  tok(sym.ID,yytext());}
					case -237:
						break;
					case 240:
						{return  tok(sym.ID,yytext());}
					case -238:
						break;
					case 241:
						{return  tok(sym.ID,yytext());}
					case -239:
						break;
					case 242:
						{return  tok(sym.ID,yytext());}
					case -240:
						break;
					case 243:
						{return  tok(sym.ID,yytext());}
					case -241:
						break;
					case 244:
						{return  tok(sym.ID,yytext());}
					case -242:
						break;
					case 245:
						{return  tok(sym.ID,yytext());}
					case -243:
						break;
					case 246:
						{return  tok(sym.ID,yytext());}
					case -244:
						break;
					case 247:
						{return  tok(sym.ID,yytext());}
					case -245:
						break;
					case 248:
						{return  tok(sym.ID,yytext());}
					case -246:
						break;
					case 249:
						{return  tok(sym.ID,yytext());}
					case -247:
						break;
					case 250:
						{return  tok(sym.ID,yytext());}
					case -248:
						break;
					case 251:
						{return  tok(sym.ID,yytext());}
					case -249:
						break;
					case 252:
						{return  tok(sym.ID,yytext());}
					case -250:
						break;
					case 253:
						{return  tok(sym.ID,yytext());}
					case -251:
						break;
					case 254:
						{return  tok(sym.ID,yytext());}
					case -252:
						break;
					case 255:
						{return  tok(sym.ID,yytext());}
					case -253:
						break;
					case 256:
						{return  tok(sym.ID,yytext());}
					case -254:
						break;
					case 257:
						{return  tok(sym.ID,yytext());}
					case -255:
						break;
					case 258:
						{return  tok(sym.ID,yytext());}
					case -256:
						break;
					case 259:
						{return  tok(sym.ID,yytext());}
					case -257:
						break;
					case 260:
						{return  tok(sym.ID,yytext());}
					case -258:
						break;
					case 261:
						{return  tok(sym.ID,yytext());}
					case -259:
						break;
					case 262:
						{return  tok(sym.ID,yytext());}
					case -260:
						break;
					case 263:
						{return  tok(sym.ID,yytext());}
					case -261:
						break;
					case 264:
						{return  tok(sym.ID,yytext());}
					case -262:
						break;
					case 265:
						{return  tok(sym.ID,yytext());}
					case -263:
						break;
					case 266:
						{return  tok(sym.ID,yytext());}
					case -264:
						break;
					case 267:
						{return  tok(sym.ID,yytext());}
					case -265:
						break;
					case 268:
						{return  tok(sym.ID,yytext());}
					case -266:
						break;
					case 269:
						{return  tok(sym.ID,yytext());}
					case -267:
						break;
					case 270:
						{return  tok(sym.ID,yytext());}
					case -268:
						break;
					case 271:
						{return  tok(sym.ID,yytext());}
					case -269:
						break;
					case 272:
						{return  tok(sym.ID,yytext());}
					case -270:
						break;
					case 273:
						{return  tok(sym.ID,yytext());}
					case -271:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
