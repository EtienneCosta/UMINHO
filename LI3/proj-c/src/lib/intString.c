#include <stdlib.h>
#include "list.h"
#include "intString.h"
#include "common.h"

/***************************************************************************************************************************
  Struct IntString                                     
****************************************************************************************************************************/
struct intString{
	LONG_list lista;
	char * string;
	int count;
	int size;
};

IntString createIntString(char * string, int size){
	IntString s = malloc(sizeof(struct intString));

	s->lista = create_list(size);
	s->string = string ? mystrdup(string) : NULL;
	s->count = 0;
	s->size = size;

	return s;
}

/***************************************************************************************************************************
  Getters                                     
****************************************************************************************************************************/
LONG_list get_listIntString(IntString s){
	return s->lista;
}

char * get_stringIntString(IntString s){
	if(s)
		return mystrdup(s->string);
	return NULL;
}

int get_countIntString(IntString s){
	return s->count;
}

/***************************************************************************************************************************
  Setters e Free                                      
****************************************************************************************************************************/
void set_countIntString(IntString s, int n){
	s->count = n;
}

void free_intString(IntString s){
	if(s){
		free_list(s->lista);
		free(s->string);
		free(s);
	}
}