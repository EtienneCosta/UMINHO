#include "Group.h"
#include "Model.h"
#include "Vertex.h"
#include <vector>
using namespace std;

Group::Group(void) {
}

Group::Group(Vertex rot, float rotAng, Vertex trans, Vertex scle, vector<Model> modls, vector<Group> subs, vector<Vertex> orbitPoint, float timeTranslation, float timeRotation)
{
    rotation = rot;
    rotationAngle = rotAng;
    translation = trans;
    scale = scle;
    models = modls;
    subGroups = subs;
    orbitPoints = orbitPoint;
    translationTime = timeTranslation;
    rotationTime = timeRotation;
};

Group::~Group()
{
}


