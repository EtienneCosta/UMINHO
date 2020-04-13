
#ifndef __POST__
#define __POST__

#include "date.h"
#include <glib.h>
#include "list.h"
typedef struct post* POST;

POST createPost(long id, long postTypeId, GDateTime *date, long userId, char* title, long answerCount, long commentCount, long score, long diffVotes, long parentId, char * tags);
long get_id(POST p);
long get_postTypeId(POST p);
GDateTime *get_creationDate(POST p);
void free_post(POST p);
long comparePost(POST p1, POST p2);
long get_userId(POST p);
char* get_title(POST p);
long get_answerCount(POST p);
long get_commentCount(POST p);
long get_score(POST p);
char** get_tagsOfPost(POST p);
long get_nTags(POST p);
long get_parentId(POST p);
long post_hash(POST _post);
//LONG_list get_idTags(POST p);
int post_equal(POST _p1, POST _p2);
#endif

