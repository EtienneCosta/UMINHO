#include "Vertex.h"
#ifndef MATERIAL_H
#define MATERIAL_H
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

class Material{
public:
	float diffuse[4], ambient[4], diffuseANDambient[4], specular[4], emission[4], shininess;
	Material(void);
	Material(Vertex diff, Vertex amb, Vertex diffAm, Vertex spec, Vertex emiss, float shi, bool texture);
	~Material();
	void draw();
};

#endif
