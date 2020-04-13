#ifndef __TREEHASH_H__
#define __TREEHASH_H__

#include <glib.h>
typedef struct ThreeHash * ThreeHash;

GHashTable * get_hashTable(ThreeHash th);
GHashTable * get_sndhashTable(ThreeHash th);
GHashTable * get_thrhashTable(ThreeHash th);
ThreeHash createThreeHash(GHashTable * hashTable, GHashTable * hashTable1, GHashTable * hashTable2);
void freeThreeHash(ThreeHash th);
#endif