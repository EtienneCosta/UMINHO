#include <glib.h>
#include "finalStruct.h"

/***************************************************************************************************************************
	Struct TCD_community													 					   	
****************************************************************************************************************************/

struct TCD_community{
	GHashTable * users;
	GHashTable * postsById;
	GHashTable * postsByDate;
	GHashTable * postsByParentId;
};

TAD_community create_finalStruct(GHashTable * users, GHashTable * postsById, GHashTable * postsByDate, GHashTable * postsByParentId){
	TAD_community com = (TAD_community) malloc(sizeof(struct TCD_community));
	com->users = users;
	com->postsById = postsById;
	com->postsByDate = postsByDate;
	com->postsByParentId = postsByParentId;
	return com;
}

/***************************************************************************************************************************
	Getters
****************************************************************************************************************************/

//Hash Table de users, em que a key é o identificador do user
GHashTable* get_users(TAD_community com){
	return com->users;
}

//Hash Table de posts, em que a key é o identificador do post
GHashTable* get_postsById(TAD_community com){
	return com->postsById;
}


//Hash Table de posts, em que a key é a data do post
GHashTable* get_postsByDate(TAD_community com){
	return com->postsByDate;
}

//Hash Table de posts, em que a key é o parentId
GHashTable * get_postsByParentId(TAD_community com){
	return com->postsByParentId;
}

/***************************************************************************************************************************
  Setters e Free                                  
****************************************************************************************************************************/

void set_users(TAD_community com, GHashTable * users){
	com->users = users;
}

void set_postsById(TAD_community com, GHashTable * postsById){
	com->postsById = postsById;
}


void set_postsByDate(TAD_community com, GHashTable * postsByDate){
	com->postsByDate = postsByDate;
}

void set_postsByParentId(TAD_community com, GHashTable * postsByParentId){
	com->postsByParentId = postsByParentId;
}

void free_finalStruct(TAD_community com){
	if(com){
		g_hash_table_destroy(com->users);
		g_hash_table_destroy(com->postsById);
		g_hash_table_destroy(com->postsByDate);
		g_hash_table_destroy(com->postsByParentId);
		free(com);
	}
}

