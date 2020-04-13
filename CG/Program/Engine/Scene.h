#include "Light.h"
#include "Group.h"
#ifndef SCENE_H
#define SCENE_H
using namespace std;

class Scene{
public:
	vector<Group> groups;
	vector<Light> lights;
	string fileScene;
	Scene(void);
	Scene(vector<Group> nGroups, vector<Light> nLights, string file);
	~Scene();
};

#endif
