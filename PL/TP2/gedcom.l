%option noyywrap yylineno

%{
	#include "y.tab.h"
    char* c;

    int trailing(char* level){
        int i;
        for(i = 0; (level[i] == ' ' || level[i] == '\t') && i < strlen(level); i++);
        return i;
    }
%}

alphanum     [a-zA-Z0-9]

alpha        [a-zA-Z]

digit        [0-9]

otherchar    ([!"]|[$&'\(\)\*\+,-\.\/]|[:;\<\>\=\?]|[\[\]\^]|`|[{\|}\~])

pointer      @{alphanum}({alpha}|{digit}|{otherchar}|\ |#)*@

month        ((?i:jan)|(?i:feb)|(?i:mar)|(?i:apr)|(?i:may)|(?i:jun)|(?i:jul)|(?i:aug)|(?i:sep)|(?i:oct)|(?i:nov)|(?i:dec))

year         [0-9]{1,4}

name         ([a-zA-Z0-9 ]|[áàâãéèêíóôõúçñÁÀÂÃÉÈÍÓÔÕÚÇ,-.])+

%x INPOINTER INCONTENT INDATE INNAME INTIME INSEX INDIV

%%

^\t*                    { ; }

0              { return zero; }

([1-9][0-9]?)  { yylval.vnumber = atoi(yytext) ; return numb;  }

{pointer}               { if( yyleng < 22 ) {yytext[yyleng-1] = 0; yylval.vword = strdup(yytext + 1) ; return pointer; } }

\                       { return delim; }

ADDR                    { BEGIN INCONTENT ; return ADDR; }

BIRT                    { return BIRT; }

BURI                    { return BURI; }

CONC                    { BEGIN INCONTENT ; return CONC; }

CONT                    { BEGIN INCONTENT ; return CONT; }

CHR                     { return CHR;  }

CHIL                    { return CHIL; }

CHAN                    { return CHAN; }

DIV                     { BEGIN INDIV ; return DIV; }

DEAT                    { return DEAT; }

DATE                    { BEGIN INDATE ; return DATE; }

GIVN                    { BEGIN INNAME ; return GIVN; }

FAM                     { return FAM; }

FAMC                    { return FAMC; }

FAMS                    { return FAMS; }

HUSB                    { return HUSB; }

MARR                    { return MARR; }

NAME                    { BEGIN INNAME ; return NAME; }

NICK                    { BEGIN INNAME ; return NICK; }

NOTE                    { BEGIN INCONTENT ; return NOTE; }

PLAC                    { BEGIN INCONTENT ; return PLAC; }

INDI                    { return INDI; }

SEX                     { BEGIN INSEX ; return SEX; }

TITL                    { BEGIN INCONTENT ; return TITL; }

TIME                    { BEGIN INTIME ; return TIME; }

TRLR                    { return TRLR; }

REFN                    { return REFN; }

WIFE                    { return WIFE; }

NSFX                    { BEGIN INCONTENT ; return NSFX;   }

_{alphanum}+            { BEGIN INCONTENT ; return USRTAG; }


<INCONTENT>.*                                                     { yylval.vword = strdup(yytext) ; return content; }

<INDATE>\ *([0-3]?[0-9])?\ *{month}?\ *({year}|{year}\/{year})?	  { yylval.vword = strdup(yytext) ; return _date;   }

<INTIME>\ *[0-2][0-9]:[0-5][0-9]:[0-5][0-9]       	              { yylval.vword = strdup(yytext) ; return _time;   }

<INSEX>\ *((?i:m)|(?i:male))   	                                  { yylval.vword = strdup("Male")   ; return sex;   } 
<INSEX>\ *((?i:f)|(?i:female))                                    { yylval.vword = strdup("Female") ; return sex;   }

<INDIV>\ *((?i:y)|(?i:yes))                     		          { yylval.vword = strdup("Yes") ; return _div;     }
<INDIV>\ *((?i:n)|(?i:no))                                        { yylval.vword = strdup("No")  ; return _div;     }

<INNAME>.*                                       		          { yylval.vword = strdup(yytext) ; return _name;   }

<*>.				 { ; }
<*>\n        { BEGIN 0; }


%%
