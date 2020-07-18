%option noyywrap yylineno

%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <gmodule.h>

/* Useful for handling metadata */
GHashTable* dictionary;

/* Needed to know the paths of the files */
GHashTable* pathTable;

/* Tree */
char *command = NULL, *fileName = NULL, *currentPath = "";
char** pathToDir = NULL;
int depth = 0, prefixWhitespace = 0, pathToDirIndex = 0;

int count_depth(char* string){
    int count;
    for(count = 0; string[count] == '-'; count++);
    return count;
}

char* arrayToString(){
    char* ret = calloc(sizeof(char), 200);
    for(int i = 0; pathToDir[i]; i++) {
        strcat(ret, pathToDir[i]);
        strcat(ret, "/");
    }
    return ret;
}

/* Possible cases:
--> Newline terminated and noExtension = Makefile\n
--> !Newline terminated and noExtension = {%name%}
--> !Newline terminated and !noExtension = something.something
*/
void createFile(int newlineTerminated, int noExtension){
    char* fullFile = malloc(sizeof(char) * 100);
    if(!newlineTerminated && !noExtension) sprintf(fullFile, "%s%s", fileName, yytext);
    else if(newlineTerminated && noExtension){
        int size = strlen(fileName);
        memcpy(fullFile, fileName, size);
        fullFile[size-1] = 0;
    } else fullFile = strdup(fileName);
    char* touchPath = malloc(sizeof(char) * 100);
    sprintf(touchPath, "%s%s", currentPath, fullFile);
    g_hash_table_insert(pathTable, fullFile, touchPath);
    sprintf(command, "touch %s", touchPath);
    system(command);
}

/* Filestate (anything after tree) */
FILE* fileDescriptor = NULL;
char* key;
int whitespace;

/* Same logic as createFile */
void openFileDescriptor(int newlineTerminated, int noExtension){
    char* fullPath = malloc(sizeof(char) * 100);
    if(!newlineTerminated && !noExtension) sprintf(fullPath, "%s%s", key, yytext);
    else if(newlineTerminated && noExtension){
        int size = strlen(key);
        memcpy(fullPath, key, size);
        fullPath[size-1] = 0;
    } else fullPath = strdup(key);
    char* path = g_hash_table_lookup(pathTable, fullPath);
    fileDescriptor = fopen(path, "w");
}

%}

%x META TREE FILESTATE INFILE

STRING [a-zA-Z]+

%%
<INITIAL>\=\=\=\ meta                    { BEGIN(META); }
    /* Generic metadata matcher */
<META>^{STRING}:\ [a-z@A-Zã\.]+/\n     {
    char* key = calloc(sizeof(char), 100);
    /* Find where the whitespace is (that allows to split the string) */
    int metadata_separator = strcspn(yytext, " ");
    memcpy(key, yytext, metadata_separator-1);
    metadata_separator += 1;
    char* value = strdup(yytext+metadata_separator);
    /* Insert key and value in the dictionary */
    g_hash_table_insert(dictionary, key, value);
}

<INITIAL,META>\=\=\=\ tree      { BEGIN(TREE); }

    /* Calculates the depth we are at */
<TREE>^[-]*\ ? {
    int newdepth = count_depth(yytext);
    if(newdepth < depth) {
        pathToDirIndex--;
        pathToDir[pathToDirIndex] = 0;
        currentPath = strdup(arrayToString());
    } else if (newdepth > depth){
        pathToDir[pathToDirIndex++] = strdup(fileName);
        currentPath = strdup(arrayToString());
    }
    depth = newdepth;
    /* Check if there is a whitespace before the first ascii char or not */
    prefixWhitespace = yytext[depth] == ' ';
}
    /* Everything to be created matches here except if it uses a tag */
<TREE>{STRING}\n? {
    fileName = strdup(yytext);
    int last = strlen(fileName) - 1;
    if(fileName[last] == '\n') createFile(1, 1);
}

    /* Everything that matches a tag will be matched here */
<TREE>\{\%{STRING}\%\}\n? {
    /* Easily allows us to take advantage of a dictionary and use it around */
    char* tag = malloc(sizeof(char) * 100);
    int size = strlen(yytext);
    int newlineTerminated = yytext[size-1] == '\n';
    size -= (4 + newlineTerminated);
    memcpy(tag, yytext + 2, size);
    tag[size] = 0;
    fileName = strdup(g_hash_table_lookup(dictionary, tag));
    if(newlineTerminated) createFile(1, 0);
}
    /* The extension of the file matches here */
<TREE>\.{STRING}/\n {
    createFile(0, 0);
}

    /* Creates folders */
<TREE>\/\n {
    sprintf(command, "mkdir -p %s%s", currentPath, fileName);
    system(command);
}

    /* Catch start of a new section that doesn't has a tag */
<TREE,FILESTATE,INFILE>\=\=\=\ {STRING}\n? {
    BEGIN(FILESTATE);
    if(fileDescriptor) fclose(fileDescriptor);
    int whitespace = strcspn(yytext, " ") + 1;
    key = strdup(yytext + whitespace);
    int last = strlen(key) - 1;
    if(key[last] == '\n') {
        BEGIN(INFILE);
        openFileDescriptor(1,1);
    }
}

    /* Catch start of a new section that has a tag */
<TREE,FILESTATE,INFILE>\=\=\=\ \{\%{STRING}\%\}\n? {
    BEGIN(FILESTATE);
    if(fileDescriptor) fclose(fileDescriptor);
    char* tag = malloc(sizeof(char) * 100);
    int whitespace = strcspn(yytext, " ") + 1;
    int size = strlen(yytext);
    int newlineTerminated = yytext[size-1] == '\n';
    size -= (4 + newlineTerminated + whitespace);
    memcpy(tag, yytext + whitespace + 2, size);
    tag[size] = 0;
    key = strdup(g_hash_table_lookup(dictionary, tag));
    if(newlineTerminated) {
        BEGIN(INFILE);
        openFileDescriptor(1, 0);
    }
}

    /* Catch extension of a file and open it for writing */
<FILESTATE>\.{STRING}/\n {
    BEGIN(INFILE);
    openFileDescriptor(0,0);
}

    /* Catches tags inside of files */
<INFILE>\{\%{STRING}\%\} {
    char* tag = malloc(sizeof(char) * 100);
    int size = strlen(yytext) - 4;
    memcpy(tag, yytext + 2, size);
    tag[size] = 0;
    fprintf(fileDescriptor, "%s", (char*) g_hash_table_lookup(dictionary, tag));
}

    /* Anything that is read here gets written to the correct file */
<INFILE>.|\n {
    fprintf(fileDescriptor, "%s", yytext);
}

<*>.|\n                            {  }
%%
int main(int argc, char** argv){
    if(argc != 2){
        printf("Call this program with the name of the project you wish to create.\n");
        return 0;
    }
    dictionary = pathTable = g_hash_table_new(g_str_hash, g_str_equal);
    char* name = strdup("name");
    command = fileName = calloc(sizeof(char), 100);
    pathToDir = calloc(sizeof(char*), 10);
    g_hash_table_insert(dictionary, name, strdup(argv[1]));
    yylex();
    return 0;
}

