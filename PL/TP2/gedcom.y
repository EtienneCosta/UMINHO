%{
    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    #include "gedstruct.h"

    extern int yylex();
    extern int yylineno;
    extern char* yytext;
    extern FILE *yyin;
    int yyerror();

    int husb = 0;
    int wife = 0;
    int currentLevel = 0;
    char* currentID;

    S_Ind si = NULL;
    FamInfo fi = NULL;

    // Tables for inds and families
    GHashTable* inds;
    GHashTable* fams;
%}

%union{
    int vnumber;
    char valpha;
    char* vword;
}

%token zero ERRO numb pointer delim content
%token sex _date _time _name _div
%token USRTAG BIRT BURI CONC CONT CHR CHIL DIV DEAT DATE GIVN FAM FAMC FAMS
%token TIME CHAN NSFX ADDR HUSB MARR NAME NICK NOTE PLAC INDI SEX TITL TRLR REFN WIFE
%type <vnumber> numb Level
%type <vword> Pointer pointer content sex _date _time _name _div

%%

GedCom : Records zero Delim TRLR
       ;

Records
    : Records Record
    | Record
    ;

Record
    : FamRecord
    | IndiRecord
    ;

FamRecord
    : FamFstEntry FamInEntries
    ;

FamFstEntry
    : zero Delim Pointer Delim FAM  { fi = initFamInfo($3);
                                      g_hash_table_insert(fams, strdup(fi->idx), fi);
                                      currentID = strdup($3); createHtmlPage("Family", currentID);
                                      husb = 0; wife = 0; currentLevel = 0;
                                    }
    ;

FamInEntries
    : FamInEntries FamInEntry
    | FamInEntry
    ;

FamInEntry
    : Level { ( $1 > currentLevel && $1 - currentLevel > 1) ? yyerror() : (currentLevel = $1); } Delim FamTagTypes
    ;

FamTagTypes
    : FamTags
    | FamEvents
    | EventDetail
    ;

FamTags
    : HUSB Delim Pointer {  if(husb == 0) {
                                husb++;
                                addHusb(fi, $3);
                            }
                            else
                                yyerror();
                         }
    | WIFE Delim Pointer {  if(wife == 0) {
                                wife++;
                                addWife(fi, $3);
                            }
                            else
                                yyerror();
                         }
    | CHIL Delim Pointer {
        addSon(fi, $3);
    }
    | DIV _div           { addLevelInfo(currentLevel,currentID,"Divorced",$2); }
    ;

FamEvents
    : MARR  { addLevelInfo(currentLevel,currentID,"Marriage",NULL); }
    ;

IndiRecord
    : IndiFstEntry
    | IndiFstEntry IndiInEntries
    ;

IndiFstEntry
    : zero Delim Pointer Delim INDI {
        currentID = strdup($3); createHtmlPage("Individual",currentID);
        currentLevel = 0;
        si = initSInd(currentID);
        g_hash_table_insert(inds, strdup(si->idx), si);
    }
    ;

IndiInEntries
    : IndiInEntries IndiInEntry
    | IndiInEntry
    ;

IndiInEntry
    : Level { ( $1 > currentLevel && $1 - currentLevel > 1) ? yyerror() : (currentLevel = $1); } Delim IndiTagTypes
    ;

IndiTagTypes
    : IndiTags
    | IndiEvents
    | EventDetail
    ;

IndiTags
    : NAME _name           { addTagSInd(si, "name", "Name", $2, currentLevel);   }
    | USRTAG content       { addTagSInd(si, "utag", "User Tag", $2, currentLevel); }
    | GIVN _name           { addTagSInd(si, "id", "Oficial identification", $2, currentLevel);   }
    | NICK _name           { addTagSInd(si, "nick", "Nick name", $2, currentLevel);   }
    | SEX  sex             { addTagSInd(si, "sex", "Sex", $2, currentLevel);    }
    | NOTE content         { addTagSInd(si, "note", "Note", $2, currentLevel);   }
    | CONT content         { contTagSInd(si, $2); }
    | CONC content         { concTagSInd(si, $2); }
    | TITL content         { addTagSInd(si, "title", "Title", $2, currentLevel); }
    | FAMC Delim pointer   { addTagSInd(si, "famc", "Family where appears as a children", $3, currentLevel); }
    | FAMS Delim pointer   { addLevelInfo(currentLevel, currentID, "Family where appears as a husband/wife", genFamLink($3)); }
    ;

IndiEvents
    : BIRT { addLevelInfo(currentLevel,currentID,"Birth",NULL);  }
    | BURI { addLevelInfo(currentLevel,currentID,"Bury",NULL);   }
    | DEAT { addLevelInfo(currentLevel,currentID,"Death",NULL);  }
    | CHR  { addLevelInfo(currentLevel,currentID,"Baptism",NULL); }
    ;

EventDetail
    : DATE _date            { addLevelInfo(currentLevel,currentID,"Date",$2);         }
    | TIME _time            { addLevelInfo(currentLevel,currentID,"Time",$2);         }
    | PLAC content          { addLevelInfo(currentLevel,currentID,"Place",$2);        }
    | ADDR content          { addLevelInfo(currentLevel,currentID,"Adress",$2);       }
    | REFN Delim numb       { addLevelInfo(currentLevel,currentID,"Reference",NULL);  }
    |
    ;

Pointer
    : pointer { $$ = $1; }
    ;

Level
    : numb  { $$ = $1; }
    ;

Delim
    : delim
    ;

%%

int main(int argc, char** argv){
    yyin = fopen(argv[1],"r");
    fams = g_hash_table_new_full(g_str_hash, g_str_equal, free, destroyFamInfo);
    inds = g_hash_table_new_full(g_str_hash, g_str_equal, free, destroySInd);

    yyparse();

    createIndexHtml(fams,inds,argv[1]);
    postProcess(inds, fams);

    // Free tables
    g_hash_table_destroy(fams);
    g_hash_table_destroy(inds);

    return 0;
}

int yyerror(){
    printf("Erro na linha: %d, com o texto: %s\n", yylineno, yytext);
    return 0;
}
