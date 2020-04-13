#include "Material.h"
#include <vector>
using namespace std;

Material::Material(void) {
}

Material::Material(Vertex d, Vertex a, Vertex dAa, Vertex s, Vertex e, float shi, bool texture)
{
    diffuse[0] = d.x;
    diffuse[1] = d.y;
    diffuse[2] = d.z;
    diffuse[3] = 1;

    ambient[0] = a.x;
    ambient[1] = a.y;
    ambient[2] = a.z;
    ambient[3] = 1;

    diffuseANDambient[0] = dAa.x;
    diffuseANDambient[1] = dAa.y;
    diffuseANDambient[2] = dAa.z;
    diffuseANDambient[3] = 1;

    specular[0] = s.x;
    specular[1] = s.y;
    specular[2] = s.z;
    specular[3] = 1;

    emission[0] = e.x;
    emission[1] = e.y;
    emission[2] = e.z;
    emission[3] = 1;

    shininess = shi;

    float red, green, blue;
    bool notDiffuse = diffuse[0]==0 && diffuse[1]==0 && diffuse[2]==0;
    bool notAmbient = ambient[0]==0 && ambient[1]==0 && ambient[2]==0;
    bool notDiffuseANDambient = diffuseANDambient[0]==0 && diffuseANDambient[1]==0 && diffuseANDambient[2]==0;
    bool notEmission = emission[0]==0 && emission[1]==0 && emission[2]==0;
    bool notSpecular = specular[0]==0 && specular[1]==0 && specular[2]==0;

    if(notDiffuse && notAmbient && notDiffuseANDambient && notEmission && notSpecular && !texture){
        srand(time(NULL));

        red = (float) rand() / (float) RAND_MAX;
        green = (float) rand() / (float) RAND_MAX;
        blue = (float) rand() / (float) RAND_MAX;

        if (red <= 0.1 && green <= 0.1 && blue <= 0.1) red = 1;

        diffuseANDambient[0]=red;
        diffuseANDambient[1]=green;
        diffuseANDambient[2]=blue;
    }
    if(texture) {
        diffuseANDambient[0]=1;
        diffuseANDambient[1]=1;
        diffuseANDambient[2]=1;
    }

};

Material::~Material()
{
}

void Material::draw() {
    glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, ambient);
    glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, diffuse);

    if(diffuseANDambient[0]!=0 || diffuseANDambient[1]!=0 || diffuseANDambient[2]!=0)
        glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, diffuseANDambient);

    glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, shininess);
    glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, specular);
    glMaterialfv(GL_FRONT_AND_BACK, GL_EMISSION, emission);
}
