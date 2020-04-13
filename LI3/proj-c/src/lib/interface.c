#include <stdlib.h>
#include <stdio.h>
#include <glib.h>
#include <libxml/parser.h>
#include <string.h>
#include "interface.h"
#include "post.h"
#include "common.h"
#include "date.h"
#include "myuser.h"
#include "parser.h"
#include "treepair.h"
#include "doubleLongList.h"
#include "doublevalue.h"
#include "intString.h"
#include "finalStruct.h"


/***************************************************************************************************************************
	Funções auxiliares para percorrer as GTrees
****************************************************************************************************************************/

//Para a query 4
int containsTag (gpointer key, gpointer value, gpointer data){
	POST post = value;
	IntString withTag = data;
	int i=0, nTags = get_nTags(post);
	char ** tags = get_tagsOfPost(post);
	char * string = get_stringIntString(withTag);
	long id = get_id(post);
	LONG_list list = get_listIntString(withTag);	
	for(i = 0; i < nTags; i++){
		if(strcmp(tags[i], string) == 0){
			add_element_list(list, id);
		}
	}
	return 0;
}

//Para a query 5
int lastPosts (gpointer key, gpointer value, gpointer data){
	
	IntString aux = data;
	POST post = (POST) value;
	long id = get_id(post);
	LONG_list list = get_listIntString(aux);
	add_element_list(list, id);

	return 0;
}

//Para a query 6
gboolean addAnswer(gpointer key, gpointer value, gpointer data){
	DLongList aux = (DLongList)data;
	POST post = (POST) value;
	long postId = get_id(post) , postScore = get_score(post);
	int i=0, size = get_sizeList(aux);
	LONG_list listId = get_idList(aux), listScore = get_scoreList(aux);

	for(i=0; i<size && get_list(listId,i)!=-1 && get_list(listScore,i) >= postScore; i++);

	if(i != size){
		if(get_list(listId ,i) == -1){
			set_list(listId,i, postId);
			set_list(listScore, i, postScore); 
		}
		else if(get_list(listScore, i) < postScore){
			shift(listId, i);
			shift(listScore, i);
			set_list(listId, i, postId);
			set_list(listScore, i, postScore);
		}
	}

	return 0;
}

//Para a query 7
gboolean addQuestion(gpointer key, gpointer value, gpointer data){
	DLongList aux = (DLongList)data;
	POST post = (POST) value;
	long postId = get_id(post) , postAnswerC = get_answerCount(post);
	LONG_list listId = get_idList(aux), listAnswers = get_scoreList(aux);
	int i=0, size = get_sizeList(aux);

	for(i=0; i<size && get_list(listId,i)!=-1 && get_list(listAnswers,i) >= postAnswerC; i++);


	if(i != size){
		if(get_list(listId ,i) == -1){
			set_list(listId,i, postId);
			set_list(listAnswers, i, postAnswerC); 
		}
		else if(get_list(listAnswers, i) < postAnswerC){
			shift(listId, i);
			shift(listAnswers, i);
			set_list(listId, i, postId);
			set_list(listAnswers, i, postAnswerC);
		}
	}

	return 0;
}

//Para a query 8
int containsWord (gpointer key, gpointer value, gpointer data){
	POST post = value;
	IntString withWord = data;
	int count = get_countIntString(withWord);
	long id = get_id(post);
	char *title = get_title(post), *word = get_stringIntString(withWord);
	LONG_list list = get_listIntString(withWord);

	if(strstr(title, word)){
		set_list(list,count,id);
		set_countIntString(withWord, count + 1);
		if(count == get_size(list)){
			return 1;
		}
	}
	
	return 0;
}

//Para a query 9
struct longListQuestions{
	LONG_list questions;
	GHashTable * postsByParentId;
	int count;
	long id1;
	long id2;
	int foundBothUsers;
};

// Para a query 9
int usersParticipated (gpointer key, gpointer value, gpointer data){
	struct longListQuestions * aux = data;
	POST post = (POST) value;
	long idUser = get_userId(post);

	if(idUser == aux->id1 && aux->foundBothUsers!=1){
		aux->foundBothUsers ++;
	}

	if(idUser == aux->id2 && aux->foundBothUsers!=2){
		aux->foundBothUsers += 2;
	}

	if(aux->foundBothUsers == 3) return 1;
	return 0;

}

// Para a query 9
int both (gpointer key, gpointer value, gpointer data){
	struct longListQuestions * aux = data;
	POST question = (POST) value;
	long id = get_id(question);
	long idUser = get_userId(question);
	aux->foundBothUsers = 0;
	GTree * answers = g_hash_table_lookup(aux->postsByParentId, &id);
	if(answers){
		if(aux->id1 == idUser){
			aux->foundBothUsers ++;
		}

		if(aux->id2 == idUser){
			aux->foundBothUsers += 2;
		}

		g_tree_foreach(answers, usersParticipated, aux);

		if(aux->foundBothUsers == 3){
			set_list(aux->questions,aux->count,id);
			aux->count++;
			if(aux->count == get_size(aux->questions)){
				return 1;
			}
		}
	}
	return 0;
}

//Para a query 10
int bestAverage (gpointer key, gpointer value, gpointer data){
	DVALUE aux = data;
	POST post = (POST) value;
	long score = get_score(post), commentC = get_commentCount(post), idUser = get_userId(post), idPost = get_id(post);
	double media = 0, maior = get_maior(aux);
	MYUSER u = g_hash_table_lookup(get_hashUsers(aux), &idUser);
	long reputation = get_reputation(u);
			
	media = score * 0.45 + reputation * 0.25 + score * 0.2 + commentC * 0.1;

	if (media > maior) {
		set_maior(aux, media);
		set_idDoubleValue(aux, idPost);
	}

	return 0;

}


void free_hash_postsParentId(GTree * postsByParentId){
	if(postsByParentId){
		g_tree_foreach(postsByParentId, freeNode, NULL);
	  	g_tree_destroy(postsByParentId);
	}
}


/***************************************************************************************************************************
			Inicialização da TAD_community e carregamento de dados para a estrutura
****************************************************************************************************************************/
TAD_community init(){
	GHashTable * postsById = g_hash_table_new_full(g_int64_hash, g_int64_equal, NULL, (GDestroyNotify)free_post);
	GHashTable * postsByDate = g_hash_table_new_full(g_date_time_hash, g_date_time_equal, (GDestroyNotify)g_date_time_unref, (GDestroyNotify)free_tree_pair);
	GHashTable * postsByParentId = g_hash_table_new_full(g_int64_hash, g_int64_equal, NULL, (GDestroyNotify)free_hash_postsParentId);
	GHashTable * users = g_hash_table_new_full(g_int64_hash, g_int64_equal, NULL, (GDestroyNotify)free_myUser);

	TAD_community estrutura = create_finalStruct(users, postsById, postsByDate, postsByParentId);
	
	return estrutura;
}

TAD_community load(TAD_community com, char* dump_path){  //diretoria onde estarão os ficheiros do dump
	GHashTable * users = load_users(com, dump_path,"Users.xml");
	set_users(com, users);
	ThreeHash posts = load_posts(com, dump_path,"Posts.xml");

	set_postsById(com, get_hashTable(posts));
	set_postsByDate(com, get_sndhashTable(posts));
	set_postsByParentId(com, get_thrhashTable(posts));

	freeThreeHash(posts);
	
	return com;
}

/***************************************************************************************************************************
	Queries
****************************************************************************************************************************/

//Query 1
STR_pair info_from_post(TAD_community com, long id){
	STR_pair result = create_str_pair(NULL,NULL);

	if(com && get_postsById(com) && get_users(com)){
		POST p=g_hash_table_lookup(get_postsById(com),&id);
		if(p){
			long idUser = get_userId(p);
			MYUSER u=g_hash_table_lookup(get_users(com),&idUser); 
			   
			if(get_postTypeId(p)==2){
				long parentI = get_parentId(p);
				POST paux =g_hash_table_lookup(get_postsById(com),&parentI);
				long userI = get_userId(paux);	
				MYUSER uaux =g_hash_table_lookup(get_users(com),&userI);

				set_fst_str(result, get_title(paux));
				set_snd_str(result,get_name(uaux));
			} 
			else {
				set_fst_str(result,get_title(p));
				set_snd_str(result, get_name(u));
			}
		}
	}
			
	return result;
}

//Query 2
LONG_list top_most_active(TAD_community com, int N){
	LONG_list list = create_list(N);
	int i = 0;
	long aux ;
	GHashTableIter it;
	MYUSER u = NULL;
	GHashTable * users = get_users(com);
	g_hash_table_iter_init (&it, users);
	while(g_hash_table_iter_next(&it, NULL, (gpointer*)&u)) {
		long nPosts = get_numberPosts(u);
		long id = get_idUser(u);
		if(id>0){
			for(i=0; i < get_size(list) ; i++){
				aux = get_list(list, i);
				if(aux == -1){
					set_list(list, i, id);
					break;
				}
				else{
					MYUSER u2 = g_hash_table_lookup(users, &aux);
					if(nPosts > get_numberPosts(u2)){
						shift(list,i);
						set_list(list, i, id);
						break;
					}
				}
			}
		}
		
	}


	return list;
 }

	
//Query 3
 LONG_pair total_posts(TAD_community com, Date begin, Date end){


	GDateTime *b=g_date_time_new_local(get_year(begin),get_month(begin),get_day(begin),0,0,0);
	GDateTime *e=g_date_time_new_local(get_year(end),get_month(end),get_day(end),0,0,0);
	GHashTable * postsByDate = get_postsByDate(com);
	long Question=0,Answer=0;
	LONG_pair result=create_long_pair(Question,Answer);

	
	if(com && postsByDate){
	
		while(g_date_time_compare(b,e)<=0){
				
			TREE_pair p = g_hash_table_lookup(get_postsByDate(com),b);
			if(p){	
			 
			GTree * q = get_fst_tree(p);
			GTree * a = get_snd_tree(p);
			Question += g_tree_nnodes(q);
			Answer += g_tree_nnodes(a);
			b = g_date_time_add_days(b,1);
			}

		}
 
	}

	set_fst_long(result, Question);
	set_snd_long(result, Answer);
	g_date_time_unref(b);
	g_date_time_unref(e);
	
	return result;
 }


//Query 4
LONG_list questions_with_tag(TAD_community com, char* tag, Date begin, Date end){
	GDateTime * b =g_date_time_new_local(get_year(begin),get_month(begin),get_day(begin),0,0,0);
	GDateTime * e =g_date_time_new_local(get_year(end),get_month(end),get_day(end),0,0,0);
	IntString withTag = createIntString(tag, 0);
	LONG_list l = get_listIntString(withTag);
	LONG_list result = NULL;

	
	if(com && get_postsByDate(com)){
	
		while(g_date_time_compare(b,e) <= 0){
				
			TREE_pair p = g_hash_table_lookup(get_postsByDate(com),b);
			if(p){	
			 
				GTree * questions = get_fst_tree(p);
				g_tree_foreach(questions, containsTag, withTag);				
				b = g_date_time_add_days(b,1);
			}
		}
	}

	reverseL(l);
	result = clone(l);
	free_intString(withTag);
	g_date_time_unref(b);
	g_date_time_unref(e);


	return result;
 }


//Query 5
USER get_user_info(TAD_community com, long id){
	MYUSER u = g_hash_table_lookup(get_users(com), &id);
	char * bio = get_bioUser(u);
	long * posts = malloc(sizeof(long) * 10);
	int k=0, i=0;
	USER user = NULL;
	for(k= 0; k < 10; k++){ 
		posts[k] = -1;
	}
	if(u){
		GTree * postsUser = get_postsUser(u);
		IntString tenPosts = createIntString(NULL, 0);
		LONG_list l = get_listIntString(tenPosts);

		g_tree_foreach(postsUser, lastPosts, tenPosts);

		int size = get_size(l);
		i=0;
		for(k = size-1; k >= 0 && i<10; k--){ 
			posts[i] = get_list(l, k);
			i++;
		}

		user = create_user(bio, posts);
		
		return user;
	}
	else{
		printf("Não existe nenhum utilizador com o ID fornecido\n");
		return NULL;

	}	
}


//Query 6
LONG_list most_voted_answers(TAD_community com, int N, Date begin, Date end){
	GDateTime * b = g_date_time_new_local(get_year(begin),get_month(begin),get_day(begin),0,0,0);
	GDateTime * e = g_date_time_new_local(get_year(end),get_month(end),get_day(end),0,0,0);
	DLongList aux = createDoubleLongList(N);
	LONG_list ids = get_idList(aux);
	LONG_list result = NULL;

	if(com && get_postsByDate(com)){
		while(g_date_time_compare(b,e)<=0){
			TREE_pair p = g_hash_table_lookup(get_postsByDate(com),b);
			if(p){
				GTree * respostas = get_snd_tree(p);
				g_tree_foreach(respostas, addAnswer, aux);
			}
			
			b = g_date_time_add_days(b,1);
		}
	}

	result = clone(ids);
	
	free_doubleLongList(aux);
	g_date_time_unref(b);
	g_date_time_unref(e);

  	return result;
}


//Query 7
LONG_list most_answered_questions(TAD_community com, int N, Date begin, Date end){
	GDateTime * b = g_date_time_new_local(get_year(begin),get_month(begin),get_day(begin),0,0,0);
	GDateTime * e = g_date_time_new_local(get_year(end),get_month(end),get_day(end),0,0,0);
	DLongList aux = createDoubleLongList(N);
	LONG_list ids = get_idList(aux);
	LONG_list result = NULL;

	if(com && get_postsByDate(com))
		while(g_date_time_compare(b,e)<=0){

			TREE_pair p=g_hash_table_lookup(get_postsByDate(com), b);
				if(p){
					GTree * questions = get_fst_tree(p);
					g_tree_foreach(questions, addQuestion, aux);
				}
			b = g_date_time_add_days(b,1);
		}
	
	result = clone(ids);


	free_doubleLongList(aux);
	g_date_time_unref(b);
	g_date_time_unref(e);

  	return result;

}

//Query 8
LONG_list contains_word(TAD_community com, char* word, int N){
	GHashTableIter it;
	TREE_pair pair = NULL;
	GTree * questions = NULL;
	GHashTable * posts = get_postsByDate(com);
	GDateTime * lastDate = g_date_time_new_local(1970,1,1,0,0,0), *firstDate = g_date_time_new_now_local(), *aux = NULL;
	IntString containWord = createIntString(word, N);
	LONG_list l = get_listIntString(containWord);
	LONG_list result = NULL;
	g_hash_table_iter_init (&it, posts);


	while(g_hash_table_iter_next(&it, (gpointer*)&aux,NULL)){
		if(aux){
			if(g_date_time_compare(lastDate, aux) == -1){
				lastDate = aux;
			}
			if(g_date_time_compare(firstDate, aux) == 1){
				firstDate = aux;
			}
		}
	}

	while(g_date_time_compare(firstDate,lastDate)<=0 && get_countIntString(containWord) < N){
		pair = g_hash_table_lookup(posts, lastDate);
			if(pair){
				questions = get_fst_tree(pair);
				g_tree_foreach(questions, containsWord, containWord);
			}
		lastDate = g_date_time_add_days(lastDate, -1);
	}


	result = clone(l);


	free_intString(containWord);
	g_date_time_unref(lastDate);
	g_date_time_unref(firstDate);

  	return result;
}


//Query 9
LONG_list both_participated(TAD_community com, long id1, long id2, int N){
	LONG_list l = create_list(N);
	LONG_list result = NULL;
	struct longListQuestions * nQuestions = malloc(sizeof(struct longListQuestions));
	nQuestions->questions = l;
	nQuestions->id1 = id1;
	nQuestions->id2 = id2; 
	nQuestions->count = 0;
	nQuestions->postsByParentId = get_postsByParentId(com);
	GHashTableIter it;
	GHashTable * postsByDate = get_postsByDate(com);
	GDateTime * lastDate = g_date_time_new_local(1970,1,1,0,0,0), *firstDate = g_date_time_new_now_local(), *aux = NULL;
	GTree * questions=NULL;
	TREE_pair p;

	g_hash_table_iter_init(&it, postsByDate);

	while(g_hash_table_iter_next(&it, (gpointer*)&aux,NULL)){
		if(aux){
			if(g_date_time_compare(lastDate, aux) == -1){
				lastDate = aux;
			}
			if(g_date_time_compare(firstDate, aux) == 1){
				firstDate = aux;
			}
		}
	}

	while(g_date_time_compare(firstDate,lastDate)<=0 && nQuestions->count < N){
      	p = g_hash_table_lookup(postsByDate, lastDate);
	      	if(p){
		        questions = get_fst_tree(p);
		        g_tree_foreach(questions, both, nQuestions);
	      	}
      	lastDate = g_date_time_add_days(lastDate, -1);
    }
   	
	result = clone(l);
	if(nQuestions){
		free_list(nQuestions->questions);
		free(nQuestions);
	}

	return result;	
}


// Query 10
long better_answer(TAD_community com, long id){
	long result=0;
	GHashTable * posts = get_postsByParentId(com);
	DVALUE aux = create_doubleValue(get_users(com), 0, result);

	if(com && posts){
		GTree * answers = g_hash_table_lookup(posts, &id);
		if(answers){
			g_tree_foreach(answers, bestAverage, aux);
		}
	}
	
	result = get_idDoubleValue(aux);
	free_doubleValue(aux);

	return result;
}


//Query 11 - Apenas retorna os N utilizadores com melhor reputação
LONG_list most_used_best_rep(TAD_community com, int N, Date begin, Date end){

	LONG_list list = create_list(N);
	int i = 0, k;
	long aux ;
	GHashTableIter it;
	MYUSER u = NULL;
	GHashTable * users = get_users(com);
	g_hash_table_iter_init (&it, users);

	while(g_hash_table_iter_next(&it, NULL, (gpointer*)&u)) {
		long reputation = get_reputation(u);
		long id = get_idUser(u);
		if(id > 0)
			for(i=0; i < get_size(list) ; i++){
				aux = get_list(list, i);
				if(aux == -1){
					set_list(list, i, id);
					break;
				}
				else{
					MYUSER u2 = g_hash_table_lookup(users, &aux);
					if(reputation > get_reputation(u2)){
						shift(list,i);
						set_list(list, i, id);
						break;
					}
				}
			}
		
	}
	for(k= 0; k< get_size(list); k++){ 
		long x = get_list(list, k);
		printf("Valor da lista[%d] = %ld -> %ld\n", k, x, get_reputation(g_hash_table_lookup(users, &x)));
	}
	printf("\n");
	
	return list;
	
}


TAD_community clean(TAD_community com){

	free_finalStruct(com);

	return com;
}

