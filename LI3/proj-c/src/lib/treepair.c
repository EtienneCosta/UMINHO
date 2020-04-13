#include <glib.h>
#include <stdlib.h>
#include "treepair.h"
#include "myuser.h"


/***************************************************************************************************************************
  Struct TREE_pair                                      
****************************************************************************************************************************/
struct tree_pair {
  GTree* fst;
  GTree* snd;
};

TREE_pair create_tree_pair(GTree* fst, GTree* snd) {
  TREE_pair t = malloc(sizeof(struct tree_pair));
  t->fst = fst;
  t->snd = snd;
  return t;
}


/***************************************************************************************************************************
  Getters                                     
****************************************************************************************************************************/
GTree * get_fst_tree(TREE_pair pair){
	return pair->fst;
}

GTree * get_snd_tree(TREE_pair pair){
	return pair->snd;
}

/***************************************************************************************************************************
  Setters e Free                                      
****************************************************************************************************************************/
void set_fst_tree(TREE_pair pair, GTree * tree) {
  pair->fst = tree;
}

void set_snd_tree(TREE_pair pair, GTree * tree) {
  pair->snd = tree;
}

void free_tree_pair(TREE_pair pair){
  if(pair){
    g_tree_foreach(pair->fst, freeNode, NULL);
    g_tree_foreach(pair->snd, freeNode, NULL);
    g_tree_destroy(pair->fst);
    g_tree_destroy(pair->snd);
    free(pair);
  }
}
