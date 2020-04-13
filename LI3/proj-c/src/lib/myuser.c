#include <string.h>
#include <stdlib.h>
#include "myuser.h"
#include "date.h"
#include "common.h"
#include "post.h"
#include <glib.h>

/***************************************************************************************************************************
  Struct myuser                                        
****************************************************************************************************************************/

struct myuser {
  long idUser;
  char * name;
  long reputation;
  long numberPosts;
  char * bio;
  GTree * postsUser;
};


MYUSER createMyUser(long idUser, char* name, long reputation, long numberPosts, char * bio, GTree * postsUser){
  MYUSER u = malloc(sizeof(struct myuser));

  u->idUser = idUser;
  u->name = mystrdup(name);
  u->reputation = reputation;
  u->numberPosts = numberPosts;
  u->bio = mystrdup(bio);
  u->postsUser = postsUser;

  return u;
}

/***************************************************************************************************************************
  Getters
****************************************************************************************************************************/

long get_idUser(MYUSER u){
  return u->idUser;
}

char* get_name(MYUSER u){
  if(u)
    return u->name;
  return NULL;
}

long get_reputation(MYUSER u){
  return u->reputation;
}

long get_numberPosts(MYUSER u){
  return u->numberPosts;
}

char * get_bioUser(MYUSER u){
  if (u) 
    return u->bio;
  return NULL; 
}

GTree * get_postsUser(MYUSER u){
  return u->postsUser;
}

/***************************************************************************************************************************
Setters e Free
****************************************************************************************************************************/

void set_numberPosts(MYUSER u, long n){
  u->numberPosts = n;
}

/***************************************************************************************************************************
Funções Auxiliares
****************************************************************************************************************************/

int freeNode(gpointer key, gpointer value, gpointer data){
  POST post = (POST) value;

  free(key);
  free_post(post);

  return 0;
}


void free_myUser(MYUSER u){
  if(u){
    free(u->name);
    free(u->bio);
    g_tree_foreach(u->postsUser, freeNode, NULL);
    g_tree_destroy(u->postsUser);
    free(u);
  }
}

glong compareUser(MYUSER u1, MYUSER u2){
  if(u1->idUser < u2->idUser)
    return -1;
  else if(u1->idUser == u2->idUser) return 0;
  else return 1;
}
