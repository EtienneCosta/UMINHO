#ifndef __MYUSER__
#define __MYUSER__
typedef struct myuser* MYUSER;
#include <glib.h>



long get_idUser(MYUSER u);
char* get_name(MYUSER u);
long get_reputation(MYUSER u);
void free_myUser(MYUSER u);
long compareUser(MYUSER u1, MYUSER u2);
long get_numberPosts(MYUSER u);
char * get_bioUser(MYUSER u);
void set_numberPosts(MYUSER u, long n);
int freeNode(gpointer key, gpointer value, gpointer data);
GTree * get_postsUser(MYUSER u);
MYUSER createMyUser(long idUser, char* name, long reputation, long numberPosts, char * bio, GTree * postsUser);
#endif

