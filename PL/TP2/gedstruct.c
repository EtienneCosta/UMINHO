#include "gedstruct.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>

#define STD_STR_SIZE 255

// Single ind section -----------------------------------------------------------------------
S_Ind initSInd(char* idx){
    S_Ind si = malloc(sizeof(struct s_ind));

    si->idx = strdup(idx);
    si->fields = g_hash_table_new_full(g_str_hash, g_str_equal, free, free);
    si->display = g_hash_table_new_full(g_str_hash, g_str_equal, free, free);
    si->last_updated_key = calloc(STD_STR_SIZE,sizeof(char));

    return si;
}

void addTagSInd(S_Ind si, char* utag, char* displayAs, char* content, int level){
    g_hash_table_insert(si->fields, strdup(utag), strdup(content));
    g_hash_table_insert(si->display, strdup(utag), strdup(displayAs));
    // If there was a previous utag, write it to the html
    if(*si->last_updated_key) {
        char* identifier = g_hash_table_lookup(si->display, si->last_updated_key);
        char* write = g_hash_table_lookup(si->fields, si->last_updated_key);
        if(!strcmp(si->last_updated_key,"famc") || !strcmp(si->last_updated_key,"fams")) addLevelInfo(level, si->idx, identifier, genFamLink(write));
        else addLevelInfo(level, si->idx, identifier, write);
    }
    si->last_updated_key = strdup(utag);
}

void writeLastTag(S_Ind si, int level){
    if(*si->last_updated_key) {
        char* identifier = g_hash_table_lookup(si->display, si->last_updated_key);
        char* write = g_hash_table_lookup(si->fields, si->last_updated_key);
        if(!strcmp(si->last_updated_key,"famc") || !strcmp(si->last_updated_key,"fams")) addLevelInfo(level, si->idx, identifier, genFamLink(write));
        else addLevelInfo(level, si->idx, identifier, write);
    }
}

void concTagSInd(S_Ind si, char* content){
    char* value = g_hash_table_lookup(si->fields, si->last_updated_key);
    strcat(value, content);
    g_hash_table_insert(si->fields, strdup(si->last_updated_key), strdup(value));
    free(value);
}

void contTagSInd(S_Ind si, char* content){
    char* value = g_hash_table_lookup(si->fields, si->last_updated_key);
    strcat(value, "\n");
    strcat(value, content);
    g_hash_table_insert(si->fields, strdup(si->last_updated_key), strdup(value));
    free(value);
}

void destroySInd(void* siPtr){
    S_Ind si = (S_Ind) siPtr;
    free(si->idx);
    free(si->last_updated_key);
    g_hash_table_destroy(si->fields);
    free(si);
}
// Single ind section -----------------------------------------------------------------------

// Single family section --------------------------------------------------------------------
FamInfo initFamInfo(char* idx){
    FamInfo fi = malloc(sizeof(struct fam_info));

    fi->idx = strdup(idx);
    fi->husb = NULL;
    fi->wife = NULL;
    fi->children = g_ptr_array_new();

    return fi;
}

void addHusb(FamInfo fi, char* husb){
    fi->husb = strdup(husb);
}

void addWife(FamInfo fi, char* wife){
    fi->wife = strdup(wife);
}

void addSon(FamInfo fi, char* son){
    g_ptr_array_add(fi->children, strdup(son));
}

void destroyFamInfo(void* fi_ptr){
    FamInfo fi = (FamInfo) fi_ptr;
    free(fi->idx);
    free(fi->husb);
    free(fi->wife);
    g_ptr_array_free(fi->children, TRUE);
    free(fi);
}
// Single family section --------------------------------------------------------------------
//
// HTML section -----------------------------------------------------------------------------
//
/* qsort C-string comparison function */
int cstring_cmp(const void *a, const void *b)
{
    const char **ia = (const char **)a;
    const char **ib = (const char **)b;
    int int_a = atoi(*ia+1);
    int int_b = atoi(*ib+1);

    return int_a - int_b;
}

void createIndexHtml(GHashTable* fams, GHashTable* inds, char* file){
    char* current_directory = getcwd(NULL, 0);
    int fams_size = g_hash_table_size(fams);
    int inds_size = g_hash_table_size(inds);
    int max = fams_size > inds_size ? fams_size : inds_size;
    char** fams_keys = (char**) g_hash_table_get_keys_as_array(fams,NULL);
    char** inds_keys = (char**) g_hash_table_get_keys_as_array(inds,NULL);

    // Sort the arrays alfabetically
    qsort(fams_keys, fams_size, sizeof(char*), cstring_cmp);
    qsort(inds_keys, inds_size, sizeof(char*), cstring_cmp);

    // Write down the "static" part first
    FILE *fp;
    fp = fopen("html/index.html", "w");
    fprintf(fp, "<head><meta charset=\"UTF-8\">\n");
    fprintf(fp, "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n");

    fprintf(fp, "</head>\n");
    fprintf(fp, "<html><body id=\"fundo\"><div id=\"title\"><h1>Index</h1></div>\n");

    fprintf(fp,"<h2>Loaded file - %s</h2>\n",file);
    fprintf(fp,"<div id=\"fundo\">\n");
    fprintf(fp,"<table id=\"customers\">\n");
    fprintf(fp, "<tr id=\"header\"><th>Families</th><th>Individuals</th></tr>\n");
    for(int i = 0; i < max; i++){
        fprintf(fp,"<tr>\n");
        if (i < fams_size )
            fprintf(fp, "<td><a href=file://%s/html/%s.html>%s</a></td>\n", current_directory, fams_keys[i],fams_keys[i]);
        else
            fprintf(fp, "<td></td>\n");

        if ( i < inds_size )
            fprintf(fp, "<td><a href=file://%s/html/%s.html>%s</a></td>\n", current_directory, inds_keys[i],inds_keys[i]);
        else
            fprintf(fp, "<td></td>\n");

        fprintf(fp,"</tr>\n");
    }
    fprintf(fp, "</table>\n");
    fprintf(fp,"</div>\n");

    fclose(fp);
}

void createHtmlPage(char* title, char* file){
    FILE* fp;
    char filename[512];
    sprintf(filename,"html/%s.html",file);
    fp = fopen(filename, "w");
    fprintf(fp, "<head><meta charset=\"UTF-8\">\n");
    fprintf(fp, "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n");
    fprintf(fp, "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n");
    fprintf(fp, "</head>\n");

    fprintf(fp, "<html><body id=\"fundo\"><div id=\"title\"><h1>%s %s</h1><a id=\"home\" href=\"index.html\"><h2 id=\"home\">Home</h2></a></div>\n",title,file);

    fclose(fp);
}

void addLevelInfo(int level, char* file, char* header, char* content){
    int padding = 50 * level;
    char filename[512];
    sprintf(filename,"html/%s.html",file);
    FILE* fp = fopen(filename,"a");
    fprintf(fp,"<ul id=\"fundo\" style=\"padding-left: %dpx\">\n",padding);
    if( content )
        fprintf(fp,"<li>%s: %s</li>\n", header,content);
    else
        fprintf(fp,"<li >%s</li>\n", header);
    fprintf(fp,"</ul>\n");

    fclose(fp);
}

char* genFamLink(char* fam){
    char* current_directory = getcwd(NULL, 0);
    char* link = malloc(sizeof(char) * STD_STR_SIZE);

    sprintf(link, "<a href=file://%s/html/%s.html>%s</a>", current_directory, fam, fam);

    return link;
}

char* genIndLink(char* ind){
    char* current_directory = getcwd(NULL, 0);
    char* link = malloc(sizeof(char) * STD_STR_SIZE);

    sprintf(link, "<a href=file://%s/html/%s.html>%s</a>", current_directory, ind, ind);

    return link;
}

char* genNameLink(char* name, char* ind){
    char* current_directory = getcwd(NULL, 0);
    char* link = malloc(sizeof(char) * STD_STR_SIZE);

    sprintf(link, "<a href=file://%s/html/%s.html>%s</a>", current_directory, ind, name);

    return link;
}
// POST PROCESSING -------------------------------------------------------------------------
char* assembleNameAndID(GHashTable* inds, char* link_id){
    char* str = malloc(sizeof(char) * STD_STR_SIZE);
    S_Ind si = g_hash_table_lookup(inds, link_id);
    if(si) sprintf(str, "%s", genNameLink(g_hash_table_lookup(si->fields, "name"), link_id));
    return str;
}

void addHusbAndWife(gpointer key, gpointer value, gpointer user_data){
    char* idx = (char*) key;
    void** ptrs = (void**) user_data;
    GHashTable* fams = (GHashTable*) ptrs[0];
    GHashTable* inds = (GHashTable*) ptrs[1];
    S_Ind si = (S_Ind) value;

    // Get husb and wife from famc of SInd and add them as level 2
    writeLastTag(si, 1);
    char* famc = g_hash_table_lookup(si->fields, "famc");
    if(famc){
        FamInfo fi = g_hash_table_lookup(fams, famc);
        if(fi && fi->husb) {
            char* str = assembleNameAndID(inds, fi->husb);
            addLevelInfo(1, si->idx, "Father", str);
            free(str);
        }
        if(fi && fi->wife) {
            char* str = assembleNameAndID(inds, fi->wife);
            addLevelInfo(1, si->idx, "Mother", str);
            free(str);
        }
    }
}

void postProcessInds(GHashTable* inds, GHashTable* fams){
    void** tables = malloc(sizeof(void*) * 2);
    tables[0] = fams; tables[1] = inds;

    g_hash_table_foreach(inds, addHusbAndWife, tables);
}

void foreachSon(gpointer data, gpointer user_data){
    char* son = (char*) data;
    void** ptrs = (void**) user_data;
    GHashTable* inds = (GHashTable*) ptrs[0];
    char* idx = (char*) ptrs[1];

    char* temp = assembleNameAndID(inds, son);
    addLevelInfo(1, idx, "Son", temp);
    free(temp);
}

void addFamMembers(gpointer key, gpointer value, gpointer user_data){
    char* idx = (char*) key;
    void** ptrs = (void**) user_data;
    GHashTable* fams = (GHashTable*) ptrs[0];
    GHashTable* inds = (GHashTable*) ptrs[1];
    FamInfo fi = (FamInfo) value;

    // Write husb and wife
    if(fi->husb) addLevelInfo(1, fi->idx, "Husband", assembleNameAndID(inds, fi->husb));
    if(fi->wife) addLevelInfo(1, fi->idx, "Wife", assembleNameAndID(inds, fi->wife));

    // Write all sons in the same way
    void** data = malloc(sizeof(void*) * 2);
    data[0] = inds; data[1] = fi->idx;
    g_ptr_array_foreach(fi->children, foreachSon, data);
}

void postProcessFams(GHashTable* fams, GHashTable* inds){
    void** tables = malloc(sizeof(void*) * 2);
    tables[0] = fams; tables[1] = inds;

    g_hash_table_foreach(fams, addFamMembers, tables);
}

void postProcess(GHashTable* inds, GHashTable* fams){
    postProcessFams(fams, inds);
    postProcessInds(inds, fams);
}
// POST PROCESSING -------------------------------------------------------------------------
