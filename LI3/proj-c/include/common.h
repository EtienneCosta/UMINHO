#include <glib.h>
#ifndef __MY_COMMON__
#define __MY_COMMON__
char * mystrdup (const char *s);
int compareInt(int * a, int * b);
int hashFunction(int *a);
int compareLong(long *a, long *b);
int equalsLong(long *a, long *b);
#endif
