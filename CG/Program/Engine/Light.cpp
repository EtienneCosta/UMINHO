#include "Light.h"
#include <vector>
using namespace std;

Light::Light(void) {
}

Light::Light(Vertex ligthPos,string light)
{
    lightP = ligthPos;
    lightType = light;
};

Light::~Light()
{
}

void Light::draw() {
    float isPoint = 1.0;
    if(!lightType.compare("directional")) isPoint = 0.0;

    GLfloat pos[4] = {lightP.x, lightP.y, lightP.z, isPoint};

    // light position
    glLightfv(GL_LIGHT0, GL_POINT, pos);

    GLfloat amb[4] = {0.1, 0.1, 0.1, 1.0};
    GLfloat diff[4] = {1.0, 1.0, 1.0, 1.0};

    // light colors
    glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, diff);
}


