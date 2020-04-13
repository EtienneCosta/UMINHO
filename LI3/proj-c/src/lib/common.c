#include <common.h>
#include <string.h>
#include <stdlib.h>
#define N 997


// definido par nao dar warnings ao usar a funcao da GNU
char * mystrdup (const char *s) {
    if(s == NULL) return NULL;          
    char *d = malloc (strlen (s) + 1); 
    if (d == NULL) return NULL;       
    strcpy (d,s);                    
    return d;                       
}

int compareInt(int *a, int *b){
	return (*a < *b) ? -1 : (*a > *b);
}

int hashFunction(int *a){
	return (*a) % N;
}

int compareLong(long *a, long *b){
	if(*a < *b) return -1;
	else if(*a > *b) return 1;
	else return 0;
}

int equalsLong(long *a, long *b){
	return (*a == *b) ? 1 : 0;
}


