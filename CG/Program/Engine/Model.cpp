#include <IL/il.h>
#include "Model.h"
using namespace std;

Model::Model() {}

Model::Model(string path)
{
    ifstream file;

    file.open(path, ifstream::in);
    while (file)
    {
        float x, y, z, n1, n2, n3, t1, t2, t3;
        file >> x;
        file >> y;
        file >> z;
        file >> n1;
        file >> n2;
        file >> n3;
        file >> t1;
        file >> t2;
        file >> t3;
        vertexes.push_back(Vertex(x, y, z));
        normals.push_back(Vertex(n1, n2, n3));
        textures.push_back(Vertex(t1, t2, t3));
    }
    file.close();    
    //fileTexture = texture;
};

Model::~Model()
{
}

void Model::fillBuffer(){
    float * pointsArray = (float*) malloc(sizeof(float) * vertexes.size() * 3);
    float * normalsArray = (float*) malloc(sizeof(float) * normals.size() * 3);
    float * texturesArray = (float*) malloc(sizeof(float) * textures.size() * 2);

    for (int j = 0; j < vertexes.size(); j++) {
        pointsArray[3 * j] = vertexes[j].x;
        pointsArray[3 * j + 1] = vertexes[j].y;
        pointsArray[3 * j + 2] = vertexes[j].z;
    }

    for (int j = 0; j < normals.size(); j++) {
        normalsArray[3 * j] = normals[j].x;
        normalsArray[3 * j + 1] = normals[j].y;
        normalsArray[3 * j + 2] = normals[j].z;
    }

    for (int j = 0; j < textures.size(); j++) {
        texturesArray[2 * j] = textures[j].x;
        texturesArray[2 * j + 1] = textures[j].y;
    }

    glGenBuffers(3, buffer);
    glBindBuffer(GL_ARRAY_BUFFER,buffer[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * vertexes.size() * 3, pointsArray, GL_STATIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER,buffer[1]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * normals.size() * 3, normalsArray, GL_STATIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, buffer[2]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * textures.size() * 2, texturesArray, GL_STATIC_DRAW);
		
    free(pointsArray); 
    free(normalsArray); 
    free(texturesArray);
}

void Model::draw(){
    glBindBuffer(GL_ARRAY_BUFFER,buffer[0]);
    glVertexPointer(3,GL_FLOAT,0,0);

    if(normals.size()!=0){
        glBindBuffer(GL_ARRAY_BUFFER, buffer[1]);
        glNormalPointer(GL_FLOAT, 0, 0);
    }

    if(textures.size()!=0 && fileTexture.compare("")){
        glBindBuffer(GL_ARRAY_BUFFER, buffer[2]);
        glTexCoordPointer(2, GL_FLOAT, 0, 0);
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    glDrawArrays(GL_TRIANGLES, 0, (vertexes.size()) * 3);
    glBindTexture(GL_TEXTURE_2D, 0);
}


void Model::prepareTexture(string s) {

    unsigned int t,tw,th;
    unsigned char *texData;

    ilInit();
    ilEnable(IL_ORIGIN_SET);
    ilOriginFunc(IL_ORIGIN_UPPER_LEFT);
    ilGenImages(1,&t);
    ilBindImage(t);
    ilLoadImage((ILstring)s.c_str());
    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);

    ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
    texData = ilGetData();

    glGenTextures(1,&texture);

    glBindTexture(GL_TEXTURE_2D,texture);
    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_WRAP_T, GL_REPEAT);

    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_MAG_FILTER,   	GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
    glGenerateMipmap(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, 0);
}