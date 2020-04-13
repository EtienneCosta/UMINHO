#include <date.h>
#include <stdio.h>
#include "interface.h"
#include <glib.h>
#include <time.h>



int main(){
  TAD_community estrutura = init();


  estrutura = load(estrutura , "/Users/etiennecosta/Desktop/LI3/Grupo50/dump_files/" );

  if(estrutura == NULL){
  printf("Failed to load/parse data to memory\n");
  return 1;
  }
  printf("Data successfully loaded to memory\n");

 
  printf("Interrogação 1:\nDado o identificador de um post, retornar o título do post e o nome de utilizador do autor.\nInterrogação 2:\nTop N utilizadores com maior número de posts de sempre.\nInterrogação 3:\nDado um intervalo de tempo arbitrário, obter o número total de posts (identificando perguntas e respostas separadamente) neste período.\nInterrogação 4:\nDado um intervalo de tempo arbitrário, retornar todas as perguntas contendo uma determinada tag.\nInterrogação 5:\nDado um ID de utilizador, devolver a informação do seu perfil (short bio) e os IDs dos seus 10 últimos posts (perguntas ou respostas), ordenados por cronologia inversa.\nInterrogação 6:\nDado um intervalo de tempo arbitrário, devolver os IDs das N respostas com mais votos, em ordem decrescente do número de votos.\nInterrogação 7:\nDado um intervalo de tempo arbitrário, devolver as IDs das N perguntas com mais respostas, em ordem decrescente do número.\nInterrogação 8:\nDado uma palavra, devolver uma lista com os IDs de N perguntas cujos títulos a contenham, ordenados por cronologia inversa.\nInterrogação 9:\nDados os IDs de dois utilizadores, devolver as últimas N perguntas (cronologia inversa) em que participaram dois utilizadores específicos.\nInterrogação 10:\nDado o ID de uma pergunta, obter a melhor resposta.\nInterrogação 11:\nDado um intervalo arbitrário de tempo, devolver os identificadores das N tags mais usadas pelos N utilizadores com melhor reputação. Em ordem decrescente do número de vezes em que a tag foi usada.\n\n");
  char cmd[32];
  int interrog = 1, k = 0;
  long id = 1;
  clock_t tic,tac;
  double time;
  while(interrog!=-1){
    printf("Insira o número da interrogação que pretende fazer: ");
    scanf("%d", &interrog);
    switch(interrog){
      case 1:
        printf("Insira o ID do post: ");
        scanf("%ld", &id);
        tic = clock();
        STR_pair res1= info_from_post(estrutura,id);
        tac = clock();
        if(get_fst_str(res1) == NULL) printf("Não existe nenhum post com o id fornecido\n");
        else printf("Título do post: %s Nome do utilizador: %s\n", get_fst_str(res1), get_snd_str(res1));
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        printf("Query 1 : %f ms\n", time);
        free_str_pair(res1);
        break;
      case 2:
        printf("Insira o número de utilizadores: ");
        scanf("%d", &interrog);
        tic = clock();
        LONG_list res2 = top_most_active(estrutura, interrog);
        tac = clock();
        for(k= 0; k< get_size(res2) && get_list(res2,k) != -1; k++){ 
          printf("Id do post : %ld\n", get_list(res2, k));
        }
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        printf("Query 2 : %f ms\n", time);
        free_list(res2);
        break;
      case 3:
        printf("Insira a data inicial (yy-mm-dd): ");
        scanf("%s", cmd);
        Date begin = stringToDate(cmd);
        printf("Insira a data final (yy-mm-dd): ");
        scanf("%s", cmd);
        Date end = stringToDate(cmd);
        tic = clock();
        LONG_pair res3 = total_posts(estrutura,begin,end);
        tac = clock();
        printf("Number of questions: %ld ---- Number of answers: %ld \n", get_fst_long(res3),get_snd_long(res3));
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        printf("Query 3 : %f ms\n", time); 
        free_long_pair(res3);
        break;
      case 4:
        printf("Insira a data inicial (yy-mm-dd): ");
        scanf("%s", cmd);
        Date begin1 = stringToDate(cmd);
        printf("Insira a data final (yy-mm-dd): ");
        scanf("%s", cmd);
        Date end1 = stringToDate(cmd);
        printf("Insira a tag a procurar: ");
        scanf("%s", cmd);
        char * tag = cmd;
        tic = clock();
        LONG_list res4 = questions_with_tag(estrutura,tag,begin1,end1);
        tac = clock();
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        for(k= 0; k < get_size(res4) && get_list(res4,k) != -1; k++){ 
          printf("Id do post = %ld\n", get_list(res4, k));
        }
        printf("Query 4 : %f ms\n", time); 
        free_list(res4);
        // Chamar a função e passar parâmetro(s) lido(s)
        break;
      case 5:
        printf("Insira o ID do utilizador: ");
        scanf("%ld", &id);
        tic = clock();
        USER res5 = get_user_info(estrutura, id);
        tac = clock();
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        long * list5 = get_10_latest_posts(res5);
        for(k = 0; k < 10 && list5[k]!=-1; k++){ 
            printf("%ld\n", list5[k]);
        }
        printf("%s\n", get_bio(res5));
        printf("Query 5 : %f ms\n", time); 
        free_user(res5);
        // Chamar a função e passar parâmetro(s) lido(s)
        break;
      case 6:
        printf("Insira o número de utilizadores: ");
        scanf("%d", &interrog);
        printf("Insira a data inicial (yy-mm-dd): ");
        scanf("%s", cmd);
        Date begin2 = stringToDate(cmd);
        printf("Insira a data final (yy-mm-dd): ");
        scanf("%s", cmd);
        Date end2 = stringToDate(cmd);
        tic = clock();
        LONG_list res6 = most_voted_answers(estrutura, interrog, begin2, end2);
        tac = clock();
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        for(k= 0; k< get_size(res6) && get_list(res6,k) != -1; k++){
         printf("Id da pergunta = %ld\n", get_list(res6, k));
        }
        printf("Query 6 : %f ms\n", time); 
        free_list(res6);
        // Chamar a função e passar parâmetro(s) lido(s)
        break;
      case 7:
        printf("Insira o número de utilizadores: ");
        scanf("%d", &interrog);
        printf("Insira a data inicial (yy-mm-dd): ");
        scanf("%s", cmd);
        Date begin3 = stringToDate(cmd);
        printf("Insira a data final (yy-mm-dd): ");
        scanf("%s", cmd);
        Date end3 = stringToDate(cmd);
        tic = clock();
        LONG_list res7 = most_answered_questions(estrutura, interrog, begin3, end3);
        tac = clock();
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        for(k= 0; k< get_size(res7) && get_list(res7,k) != -1; k++){
         printf("Id da resposta = %ld\n", get_list(res7, k));
        }
        printf("Query 7 : %f ms\n", time); 
        free_list(res7);
        // Chamar a função e passar parâmetro(s) lido(s)
        break;
      case 8:
        printf("Insira a palavra a procurar nos títulos: ");
        scanf("%s", cmd);
        char * title = cmd;
        printf("Insira o número de utilizadores: ");
        scanf("%d", &interrog);
        tic = clock();
        LONG_list res8 = contains_word(estrutura, title, interrog);
        tac = clock();
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        for(k= 0; k < get_size(res8) && get_list(res8,k) != -1; k++){
          printf("Id do post = %ld\n", get_list(res8, k));
        }
        printf("Query 8 : %f ms\n", time); 
        free_list(res8);
        // Chamar a função e passar parâmetro(s) lido(s)
        break;
      case 9:
        printf("Insira o id do utilizador 1: ");
        scanf("%ld", &id);
        int id1 = id;
        printf("Insira o id do utilizador 2: ");
        scanf("%ld", &id);
        int id2 = id;
        printf("Insira o número de perguntas: ");
        scanf("%d", &interrog);
        tic = clock();
        LONG_list res9 = both_participated(estrutura, id1, id2, interrog);
        tac = clock();
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        for(k= 0; k< get_size(res9) && get_list(res9,k) != -1; k++){ 
            printf("Id do post = %ld\n", get_list(res9, k));
        }
        printf("Query 9 : %f ms\n", time); 
        free_list(res9);
        // Chamar a função e passar parâmetro(s) lido(s)
        break;
      case 10:
        printf("Insira o ID da pergunta: ");
        scanf("%ld", &id);
        tic = clock();
        long res10 = better_answer(estrutura, id);
        tac = clock();
        time = (double) (tac-tic) * 1000.0 / CLOCKS_PER_SEC;
        if(res10 == 0) printf("Não existe nenhuma resposta a essa pergunta!\n");
        printf("A melhor resposta é %ld\n", res10);
        printf("Query 10 : %f ms\n", time); 
        break;
      case 11:
        printf("Insira o número de utilizadores: ");
        scanf("%d", &interrog);
        printf("Insira a data inicial (yy-mm-dd): ");
        scanf("%s", cmd);
        Date begin4 = stringToDate(cmd);
        printf("Insira a data final (yy-mm-dd): ");
        scanf("%s", cmd);
        Date end4 = stringToDate(cmd);
        most_used_best_rep(estrutura, interrog, begin4, end4);
        break;
      default:
        printf("Nao existe nenhuma interrogação com esse número.\n");
    }
  }

  estrutura = clean(estrutura);
  return 0;
} 



