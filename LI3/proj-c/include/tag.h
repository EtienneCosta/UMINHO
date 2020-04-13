#ifndef __TAG__
#define __TAG__

#include <glib.h>
typedef struct tag * TAG;

TAG createTag(long idTag, char * tagName);
long get_idTag(TAG t);
char * get_tagName(TAG t);
#endif