#ifndef __LONG_LIST__
#define __LONG_LIST__

typedef struct llist * LONG_list;

LONG_list create_list(int size);
long get_list(LONG_list l, int index);
int get_size(LONG_list l);
void add_element_list(LONG_list l, long element);
void set_list(LONG_list l, int index, long value);
void free_list(LONG_list l);
int is_full(LONG_list l);
void shift(LONG_list l, int index);
void reverseL(LONG_list l);
LONG_list clone(LONG_list l);
#endif
