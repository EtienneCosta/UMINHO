#ifndef __TREEPAIR_H__
#define __TREEPAIR_H__

#include <glib.h>
typedef struct tree_pair * TREE_pair;

TREE_pair create_tree_pair(GTree* fst, GTree* snd);
GTree * get_fst_tree(TREE_pair pair);
GTree * get_snd_tree(TREE_pair pair);
void set_fst_tree(TREE_pair pair, GTree * tree);
void set_snd_tree(TREE_pair pair, GTree * tree);
void free_tree_pair(TREE_pair pair);
#endif
