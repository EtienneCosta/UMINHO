#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <glib.h>
#include "post.h"
#include "date.h"
#include "common.h"
#include "list.h"


/***************************************************************************************************************************
  Struct post                                        
****************************************************************************************************************************/

struct post {
  long id;
  long postTypeId;
  GDateTime *creationDate;
  long userId;
  char * title;
  long answerCount;
  long commentCount;
  long score;
  long nTags;
  char ** tags;
  long parentId;
  //LONG_list idTags;
};


POST createPost(long id, long postTypeId, GDateTime *date, long userId, char* title, long answerCount, long commentCount, long score, long diffVotes, long parentId, char * tags) {
  POST p = malloc(sizeof(struct post)); 
  p->id = id;
  p->postTypeId = postTypeId;

  p->creationDate =g_date_time_new_local(g_date_time_get_year(date),g_date_time_get_month(date),g_date_time_get_day_of_month(date),g_date_time_get_hour(date), g_date_time_get_minute(date), g_date_time_get_second(date));
  p->userId = userId;
  p->title = title ? mystrdup(title) : NULL;
  p->answerCount = answerCount;
  p->commentCount = commentCount;
  p->score = score;
  

  char* token = strtok(tags, "><");
  char ** aux = NULL;
  long i=0, j=0;
  while (token){
      aux = p->tags;
      p->tags = (char**) malloc((i+1) * sizeof(char*));
      for(j=0;j<i;j++){
        p->tags[j] = mystrdup(aux[j]);
        free(aux[j]);
      }

      p->tags[i] = mystrdup(token);
  
      token = strtok(NULL, "><");
      i++;

  }

  p->nTags = i;
  p->parentId = parentId;
  //p->idTags = idTags;

  return p;
}

/***************************************************************************************************************************
  Getters                                        
****************************************************************************************************************************/

long get_id(POST p) {
  return p->id;
}

long get_postTypeId(POST p) {
  return p->postTypeId;
}

GDateTime *get_creationDate(POST p) {
  return  p->creationDate;
}

long get_userId(POST p){
  return p->userId;
}

char* get_title(POST p){
  if(p)
    return mystrdup(p->title);
  return NULL;
}

long get_answerCount(POST p){
  return p->answerCount; 
}

long get_commentCount(POST p){
  return p->commentCount;
}

long get_score(POST p){
  return p->score;
}


char** get_tagsOfPost(POST p){
  if(p)
    return p->tags;
  return NULL;
}

long get_nTags(POST p) {
  return p->nTags;
}

long get_parentId(POST p){
  return p->parentId;
}

/*
LONG_list get_idTags(POST p){
  return p->idTags;
}
*/

/***************************************************************************************************************************
  Funções auxiliares 
****************************************************************************************************************************/
glong comparePost(POST p1, POST p2){
  if(p1->id < p2->id){
    return -1;
  }
  if(p1->id == p2->id){
    return 0;
  }
  return 1;
}


void free_post(POST p) {
  if(p) {
    g_date_time_unref(p->creationDate);
    free(p->title);
    free(p->tags);
    free(p);
  }
}


