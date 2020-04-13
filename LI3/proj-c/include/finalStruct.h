#ifndef __FinalStruct__
#define __FinalStruct__
#include <glib.h>

typedef struct TCD_community * TAD_community;
TAD_community create_finalStruct(GHashTable * users, GHashTable * postsById, GHashTable * postsByDate, GHashTable * postsByParentId);
GHashTable * get_users(TAD_community com);
GHashTable * get_postsById(TAD_community com);
GHashTable * get_postsByDate(TAD_community com);
GHashTable * get_postsByParentId(TAD_community com);
void set_users(TAD_community com, GHashTable * users);
void set_postsById(TAD_community com, GHashTable * postsById);
void set_postsByDate(TAD_community com, GHashTable * postsByDate);
void set_postsByParentId(TAD_community com, GHashTable * postsByParentId);
void free_finalStruct(TAD_community com);
#endif