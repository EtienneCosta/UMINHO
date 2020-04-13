#include <glib.h>
#include <stdlib.h>
#include "treeHash.h"

/***************************************************************************************************************************
  Struct ThreeHash e respetivo create                                    
****************************************************************************************************************************/
struct ThreeHash{
	GHashTable * hashTable;
	GHashTable * hashTable1;
	GHashTable * hashTable2;
};

ThreeHash createThreeHash(GHashTable * hashTable, GHashTable * hashTable1, GHashTable * hashTable2){
	ThreeHash ret = (ThreeHash) malloc(sizeof(struct ThreeHash));
	ret->hashTable = hashTable;
	ret->hashTable1 = hashTable1;
	ret->hashTable2 = hashTable2;
	return ret;
}

/***************************************************************************************************************************
 	Getters                                      
****************************************************************************************************************************/
GHashTable * get_hashTable(ThreeHash th){
	return th->hashTable;
}

GHashTable * get_sndhashTable(ThreeHash th){
	return th->hashTable1;
}

GHashTable * get_thrhashTable(ThreeHash th){
	return th->hashTable2;
}

/***************************************************************************************************************************
   Free
****************************************************************************************************************************/

void freeThreeHash(ThreeHash th){
	if(th){
		free(th);
	}
}