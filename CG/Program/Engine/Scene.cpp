#include "Scene.h"
#include "Group.h"
#include <vector>
using namespace std;

Scene::Scene(void) {
}

Scene::Scene(vector<Group> nGroups, vector<Light> nLights, string file)
{
    groups = nGroups;
    lights = nLights;
    fileScene = file;
};

Scene::~Scene()
{
}


