#ifndef __IntString__
#define __IntString__


typedef struct intString * IntString;

IntString createIntString(char * string, int size);
LONG_list get_listIntString(IntString s);
char * get_stringIntString(IntString s);
int get_countIntString(IntString s);
void set_countIntString(IntString s, int n);
void free_intString(IntString s);
#endif