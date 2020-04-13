#include <string.h>
#include <stdlib.h>
#include "tag.h"
#include "common.h"

/***************************************************************************************************************************
  Struct tag e respetivo create                                       
****************************************************************************************************************************/
struct tag{
	long idTag;
	char * tagName;
};

TAG createTag(long idTag, char * tagName){
	TAG t = malloc(sizeof(struct tag));

	t->idTag = idTag;
	t->tagName = tagName ? mystrdup(tagName) : NULL;

	return t;
}

/***************************************************************************************************************************
  Getters                                        
****************************************************************************************************************************/
long get_idTag(TAG t){
	return t->idTag;
}

char * get_tagName(TAG t){
	if(t)
		return mystrdup(t->tagName);
	return NULL;
}

