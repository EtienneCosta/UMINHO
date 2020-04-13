#include "date.h"
#include "pair.h"
#include "list.h"
#include "user.h"
#include "myuser.h"
#include "treeHash.h"
#include "finalStruct.h"
#include <glib.h>

ThreeHash load_posts(TAD_community com, char *dump_path, char *postsFilename);
GHashTable * load_users(TAD_community com, char *dump_path, char *usersFilename);
//GHashTable * load_tags(char * dump_path, char * tagsFilename);
