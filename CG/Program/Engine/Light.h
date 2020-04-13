#include "Vertex.h"
#ifndef LIGHT_H
#define LIGHT_H
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif
using namespace std;
#include <string>
class Light{
public:
	Vertex lightP;
	string lightType;
	Light(void);
	Light(Vertex ligthPos, string light);
	~Light();
    void draw();
};

#endif
