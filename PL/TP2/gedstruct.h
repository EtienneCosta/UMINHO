#ifndef _GEDSTRUCT_H_
#define _GEDSTRUCT_H_

#include <gmodule.h>

// Structure that holds necessary values of a family
typedef struct fam_info{
    char* idx;
    char* husb;
    char* wife;
    GPtrArray* children;
}*FamInfo;

// Structure that holds information about a user in an hashtable.
// There is a 1-1 mapping between a ind and it's ID.
typedef struct s_ind{
    char* idx;

    GHashTable* fields;
    // The hashtable holds values like:
    //NAME USRTAG GIVN NICK SEX  NOTE TITL
    //FAMC Delim pointer
    //FAMS Delim pointer;

    // Display maps each type of tag into it's displayable format
    GHashTable* display;

    // Last updated key holds the last key uploaded. We need this to provide CONC and CONT functionality.
    char* last_updated_key;
} *S_Ind;

// Single ind section
S_Ind initSInd(char* idx);

void addTagSInd(S_Ind si, char* utag, char* displayAs, char* ucontent, int level);

void concTagSInd(S_Ind si, char* ucontent);

void contTagSInd(S_Ind si, char* ucontent);

void destroySInd(void* si);
// Single ind section

// Single family section
FamInfo initFamInfo(char* idx);

void addHusb(FamInfo fi, char* husb);

void addWife(FamInfo fi, char* wife);

void addSon(FamInfo fi, char* son);

void destroyFamInfo(void* fi);
// Single family section

void createIndexHtml(GHashTable* fams, GHashTable* inds, char* file);

void createHtmlPage(char* title, char* file);

void addLevelInfo(int,char*,char*,char*);

void addInfo(char* file, char*, char* id);

char* genFamLink(char* fam);

void postProcess(GHashTable* inds, GHashTable* fams);

#endif
