#include <stdlib.h>
#include <stdio.h>
#include "list.h"

struct llist {
  int size;
  long * list;
};

LONG_list create_list(int size) {
    if(size < 0) return NULL;
    LONG_list l = malloc(sizeof(struct llist));
    l->size = size;
    l->list = malloc(sizeof(long) * size);
    int i=0;
    for(i=0;i<size;i++){
        l->list[i] = (long)-1 ;
    }
    return l;
}

long get_list(LONG_list l, int index) {
    return l->list[index]; 
}

int get_size(LONG_list l){
    return l -> size;
}

void add_element_list(LONG_list l, long element){
    int i;
    long * aux = malloc(sizeof(long) * ((l->size)+1));
    for(i=0;i < l->size; i++){
        aux[i] = l-> list[i];
    }
    aux[i] = element;
    free(l->list);
    l->list = aux;
    l->size++;
}

int is_full(LONG_list l){
    int i = 0;
    for(i=0 ; i < l->size ; i++){
        if(l->list[i] == -1) return 0;
    }
    return 1;
}

void set_list(LONG_list l, int index, long value) {
    l->list[index] = value;
}
void free_list(LONG_list l) {
    if(l) {
        free(l->list);
        free(l);
    }
}

void shift(LONG_list l, int index){
    int i;
    for( i=l->size-1 ; i > index ; i--){
        l->list[i] = l->list[i-1];
    }
}


void reverseL(LONG_list l){
    int i = 0, j = l->size - 1, temp = 0;
    for(i=0; i < j; i++){
        temp = l->list[i];
        l->list[i] = l->list[j];
        l->list[j] = temp;
        j--;       
    }

}

LONG_list clone(LONG_list l){
    int i, size = get_size(l);
    LONG_list list = create_list(size);

    for(i=0; i < size; i++){
        set_list(list, i, get_list(l, i));
    }

    return list;
}