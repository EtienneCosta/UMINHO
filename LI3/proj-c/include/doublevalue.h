#ifndef __DVALUE__
#define __DVALUE__
#include <glib.h>

typedef struct doubleValue * DVALUE;
DVALUE create_doubleValue(GHashTable * users, double maior, long id);
GHashTable * get_hashUsers(DVALUE doubleV);
double get_maior(DVALUE doubleV);
long get_idDoubleValue(DVALUE doubleV);
void set_maior(DVALUE doubleV, double n);
void set_idDoubleValue(DVALUE doubleV, long id);
void free_doubleValue(DVALUE doubleV);

#endif