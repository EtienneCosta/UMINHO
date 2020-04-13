#include "Vertex.h"
#include "Material.h"
#include <string>
#include <vector>
#include <IL/il.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif
#include <fstream>
#include <iostream>
#ifndef MODEL_H
#define MODEL_H
using namespace std;

class Model{
public:
	string fileTexture;
	vector<Vertex> vertexes;
	vector<Vertex> normals;
	vector<Vertex> textures;
	GLuint buffer[3];
	GLuint texture;
	Vertex color;
	Material material;
	Model();
	Model(string path);	
	~Model();
	void fillBuffer();
	void draw();
	void prepareTexture(string s);
};

#endif
