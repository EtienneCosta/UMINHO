using namespace std;
#ifndef VERTEX_H
#define VERTEX_H

class Vertex{
public:
	float x;
	float y;
	float z;
	Vertex();
	Vertex(float xx, float yy, float zz);
	~Vertex();
};

#endif