
#include "list.h"
#include "doubleLongList.h"
#include <stdlib.h>

/***************************************************************************************************************************
  Struct DLongList                                       
****************************************************************************************************************************/
struct doubleLongList{
  LONG_list ids;
  LONG_list scores;
  int size;
};

DLongList createDoubleLongList(int size){
  DLongList doubleList = malloc(sizeof(struct doubleLongList));

  doubleList->ids = create_list(size);
  doubleList->scores = create_list(size);
  doubleList->size = size;

  return doubleList;
}

/***************************************************************************************************************************
  Getters                                       
****************************************************************************************************************************/
LONG_list get_idList(DLongList doubleList){
  //LONG_list l = NULL;
  //l = clone(doubleList->ids);
  return doubleList->ids;
}

LONG_list get_scoreList(DLongList doubleList){
  //LONG_list l = NULL;
  //l = clone(doubleList->scores);
  return doubleList->scores;
}

int get_sizeList(DLongList doubleList){
  return doubleList->size;
}

/***************************************************************************************************************************
  Setters e Free                                       
****************************************************************************************************************************/
void set_idList(DLongList doubleList, LONG_list l){
  doubleList->ids = l;
}


void set_scoreList(DLongList doubleList, LONG_list l){
  doubleList->scores = l;
}

void set_sizeList(DLongList doubleList, int n){
  doubleList->size = n;
}

void free_doubleLongList(DLongList doubleList){
  if(doubleList){
    free_list(doubleList->ids);
    free_list(doubleList->scores);
    free(doubleList);
  }  
}