#include <stdlib.h>
#include <stdio.h>
#include <glib.h>
#include <libxml/parser.h>
#include <string.h>
#include "post.h"
#include "common.h"
#include "date.h"
#include "myuser.h"
#include "treeHash.h"
#include "treepair.h"
#include "tag.h"
#include "list.h"
#include "finalStruct.h"

//Carregamento dos posts
ThreeHash load_posts(TAD_community com, char *dump_path, char *postsFilename){
	GHashTable * postsById = get_postsById(com), * postsByDate = get_postsByDate(com), * postsByParentId = get_postsByParentId(com), * users = get_users(com);
	GDateTime * myDate = NULL, *data = NULL;
	TREE_pair postsOfDay = NULL;
	GTree * answersByParentId = NULL, * postsUser = NULL, * questionsOfDay, *answersOfDay;
	MYUSER u = NULL;
	//TAG t = NULL;
	POST post = NULL;
	TREE_pair tp = NULL;
	//int i = 0;
	long *postId=NULL, postTypeId=0, userId=0, answerCount=0, commentCount = 0, score = 0, * parentId = NULL;
	char * creationDate = NULL, *title = NULL, *uid = NULL, *tagsPost = NULL, *ansC = NULL, *comC=NULL, *parId = NULL;
	char *file_path = (char*) malloc((strlen(dump_path) + strlen(postsFilename)) * sizeof(char));
	strcpy(file_path, dump_path);
	strcat(file_path, postsFilename);

	xmlDocPtr doc = xmlParseFile(file_path);
	if(!doc) {
		return NULL;
	}

	xmlNodePtr cur = xmlDocGetRootElement(doc);
	if(!cur) {
		xmlFreeDoc(doc);
		return NULL;
	} else {
		cur = cur -> xmlChildrenNode;
		while(cur) {
			if(!xmlStrcmp(cur->name, (const xmlChar *) "row")){			
				postId = (long*) malloc(sizeof(long));
				parentId = (long*) malloc(sizeof(long));
				// Ler cada um dos atributos do elemento XML para uma variável
				*postId = atoi((char *)xmlGetProp(cur,(const xmlChar *)"Id"));
				postTypeId = atoi((char *)xmlGetProp(cur,(const xmlChar *)"PostTypeId"));
				creationDate = (char*)xmlGetProp(cur,(const xmlChar *)"CreationDate");
				uid = (char *)xmlGetProp(cur,(const xmlChar *)"OwnerUserId");
				userId = (uid) ? atoi(uid) : -1 ;
				title = (char*)xmlGetProp(cur,(const xmlChar *) "Title");
				tagsPost = (char*)xmlGetProp(cur, (const xmlChar *) "Tags");
				ansC = (char*)xmlGetProp(cur, (const xmlChar *) "AnswerCount");
				answerCount = (ansC) ? atoi(ansC) : -1;
				comC = (char*)xmlGetProp(cur, (const xmlChar *) "CommentCount");
				commentCount = (comC) ? atoi(comC) : -1;
				score = atoi((char*)xmlGetProp(cur, (const xmlChar *) "Score"));
				parId = (char*)xmlGetProp(cur, (const xmlChar *) "ParentId");
				*parentId = (parId) ? atoi(parId) : -1;
				data = g_date_time_new_from_iso8601(creationDate,g_time_zone_new_utc());
				
				// Instanciar um objeto do tipo POST com os atributos lidos
				post = createPost(*postId, postTypeId, data, userId, title, answerCount, commentCount, score, 0, *parentId, tagsPost);

				//Fazer pesquisa na hash table de users, e caso corresponda, incrementar número de posts e inserir o post na arvore	
				u = g_hash_table_lookup(users,&userId);				
				if(u) {
					set_numberPosts(u, get_numberPosts(u)+1);
					postsUser = get_postsUser(u);
					if(g_tree_lookup(postsUser, data)){
						data =  g_date_time_add_seconds(data, 0.00001);
						g_tree_insert(postsUser, data, post);
					}
					else {
						g_tree_insert(postsUser, data, post);
					}
				}

				/*
				LONG_list idTags = create_list(get_nTags(post));
				for(i=0; i < get_nTags(post) ; i++){
					char * tag = get_tagsOfPost(post)[i];
					t = g_hash_table_lookup(tags, tag);
					if(t){
						long idTag = get_idTag(t);
						set_list(idTags, i, idTag);
					}			
				}
				*/

				// Inserir o POST na hash table emq ue a chave é o seu ID
				g_hash_table_insert(postsById,postId,post);
				myDate = g_date_time_new_local(g_date_time_get_year(data),g_date_time_get_month(data),g_date_time_get_day_of_month(data),0,0,0);

				//Inserir o POST na arvore de perguntas ou resposta conforme o seu tipo
				postsOfDay = g_hash_table_lookup(postsByDate, myDate);
				if(postsOfDay){
					if(postTypeId == 1){
						if(g_tree_lookup(questionsOfDay, data)){
							data = g_date_time_add_seconds(data, 0.00001);
							g_tree_insert(get_fst_tree(postsOfDay), data, post);
						}
						else g_tree_insert(get_fst_tree(postsOfDay), data, post);
					}
					else if(postTypeId == 2){
						if(g_tree_lookup(answersOfDay, data)){
							data = g_date_time_add_seconds(data, 0.00001);
							g_tree_insert(get_snd_tree(postsOfDay), data, post);
						}
						else
						g_tree_insert(get_snd_tree(postsOfDay), data, post);
					}
				}
				else{
					questionsOfDay = g_tree_new(g_date_time_compare);
					answersOfDay = g_tree_new(g_date_time_compare);
					tp = create_tree_pair(questionsOfDay, answersOfDay);
					if(postTypeId == 1){
						g_tree_insert(questionsOfDay, data, post);
					}
					else if(postTypeId == 2){
						g_tree_insert(answersOfDay, data, post);
					}
					g_hash_table_insert(postsByDate, myDate, tp);
				}

				//Inserir o post do tipo resposta 
				answersByParentId = g_hash_table_lookup(postsByParentId, parentId);
				if(answersByParentId){
					g_tree_insert(answersByParentId, postId, post);
				}
				else {
					if(postTypeId == 2){
						answersByParentId = g_tree_new((GCompareFunc)compareLong);
						g_tree_insert(answersByParentId, postId, post);
						g_hash_table_insert(postsByParentId, parentId, answersByParentId);
					}
				}
			}
			cur = cur -> next;
		}
	}
	xmlFree(cur);
	xmlFreeDoc(doc);


	ThreeHash th =createThreeHash(postsById, postsByDate, postsByParentId);
	return th;
}

//Carregamento dos users
GHashTable * load_users(TAD_community com, char *dump_path, char *usersFilename){
	GHashTable * users = get_users(com);
	GTree * postsUser = NULL;
	MYUSER user = NULL;
	char *file_path = (char*) malloc((strlen(dump_path) + strlen(usersFilename)) * sizeof(char));
	strcpy(file_path, dump_path);
	strcat(file_path, usersFilename);

	xmlDocPtr doc = xmlParseFile(file_path);
	if(!doc) {
		return NULL;
	}

	xmlNodePtr cur = xmlDocGetRootElement(doc);
	if(!cur) {
		xmlFreeDoc(doc);
		return NULL;
	} else {
		cur = cur -> xmlChildrenNode;
		long *idUser=NULL, reputation = 0;
		char * name = NULL, * bio = NULL;
		while(cur) {
			if(!xmlStrcmp(cur->name, (const xmlChar *) "row")){
				postsUser = g_tree_new(g_date_time_compare);
				idUser= malloc(sizeof(long));
				// Ler cada um dos atributos do elemento XML para uma variável
				*idUser = atoi((char *)xmlGetProp(cur,(const xmlChar *)"Id"));
				if(idUser>0){
					name = (char*)xmlGetProp(cur,(const xmlChar *) "DisplayName");
					reputation = atoi((char*)xmlGetProp(cur, (const xmlChar *) "Reputation"));
					bio = (char*)xmlGetProp(cur, (const xmlChar *) "AboutMe");

					// Instanciar um objeto do tipo MYUSER com os atributos lidos
					user = createMyUser(*idUser, name, reputation, 0, bio, postsUser);
					// Inserir o MYUSER na hash table
					g_hash_table_insert(users,idUser,user);
				}
			}
			cur = cur -> next;
		}
	}
	xmlFree(cur);
	xmlFreeDoc(doc);

	return users;
}

/*
GHashTable * load_tags(char * dump_path, char * tagsFilename){
	printf("Inicio do load tags\n");

	GHashTable * tags = g_hash_table_new(g_str_hash, g_str_equal);
	TAG tag = NULL;
	char *file_path = (char*) malloc((strlen(dump_path) + strlen(tagsFilename)) * sizeof(char));
	strcpy(file_path, dump_path);
	strcat(file_path, tagsFilename);

	xmlDocPtr doc = xmlParseFile(file_path);
	if(!doc) {
		return NULL;
	}

	xmlNodePtr cur = xmlDocGetRootElement(doc);
	if(!cur) {
		xmlFreeDoc(doc);
		return NULL;
	} else {
		cur = cur -> xmlChildrenNode;
		long tagId = 0;
		char * tagName = NULL;
		while(cur) {
			if(!xmlStrcmp(cur->name, (const xmlChar *) "row")){
				tagName = (char*) malloc(sizeof(char));
				tagId = atoi((char *)xmlGetProp(cur,(const xmlChar *)"Id"));
				tagName = (char*)xmlGetProp(cur,(const xmlChar *)"TagName");
				printf("Meio do load tags\n");

				tag = createTag(tagId, tagName);
				g_hash_table_insert(tags, tagName, tag);
			}
			cur = cur -> next;
		}
	}

	printf("Fim do load tags\n");

	xmlFree(cur);
	xmlFreeDoc(doc);

	return tags;
}
*/