#ifndef __DLongList__
#define __DLongList__

#include "list.h"
#include <glib.h>

typedef struct doubleLongList * DLongList;
DLongList createDoubleLongList(int size);
LONG_list get_idList(DLongList doubleList);
LONG_list get_scoreList(DLongList doubleList);
int get_sizeList(DLongList doubleList);
void set_idList(DLongList doubleList, LONG_list l);
void set_scoreList(DLongList doubleList, LONG_list l);
void set_sizeList(DLongList doubleList, int n);
void free_doubleLongList(DLongList doubleList);
#endif