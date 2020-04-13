#include <stdlib.h>
#include <glib.h>
#include "doublevalue.h"
#include "myuser.h"

/***************************************************************************************************************************
  Struct DVALUE                                       
****************************************************************************************************************************/
struct doubleValue{
	GHashTable * users;	
	double maior;
	long id;
};

DVALUE create_doubleValue(GHashTable * users, double maior, long id){
	DVALUE doubleV = malloc(sizeof(struct doubleValue));

	doubleV->users = users;
	doubleV->maior = maior;
	doubleV->id = id;

	return doubleV;
}

/***************************************************************************************************************************
  Getters                                      
****************************************************************************************************************************/
GHashTable * get_hashUsers(DVALUE doubleV){
	return doubleV->users;
}

double get_maior(DVALUE doubleV){
	return doubleV->maior;
}

long get_idDoubleValue(DVALUE doubleV){
	return doubleV->id;
}

/***************************************************************************************************************************
  Setters e Free                                       
****************************************************************************************************************************/
void set_maior(DVALUE doubleV, double n){
	doubleV->maior = n;
}

void set_idDoubleValue(DVALUE doubleV, long id){
	doubleV->id = id;
}

void free_doubleValue(DVALUE doubleV){
	// GHashTableIter it;
	// MYUSER u = NULL;
	if(doubleV){

		// while(g_hash_table_iter_next(&it, NULL, (gpointer*)&u)){
		// 	free_myUser(u);
		// }
		// g_hash_table_remove_all(doubleV->users);
		// g_hash_table_destroy(doubleV->users);
		free(doubleV);
	}
}