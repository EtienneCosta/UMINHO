#include "parser.h"
#include <string>
#include <vector>
#include "Group.h"
#include "Model.h"
#include "Vertex.h"
#include "Scene.h"
#include "Light.h"
#include "Material.h"
#include <tinyxml.h>
#include "tinystr.h"
using namespace std;

Vertex ParseAttributes(TiXmlElement *element)
{
    float x = 0, y = 0, z = 0;
    if (element->Attribute("X"))
        x = atof(element->Attribute("X"));
    if (element->Attribute("axisX"))
        x = atof(element->Attribute("axisX"));
    if (element->Attribute("Y"))
        y = atof(element->Attribute("Y"));
    if (element->Attribute("axisY"))
        y = atof(element->Attribute("axisY"));
    if (element->Attribute("Z"))
        z = atof(element->Attribute("Z"));
    if (element->Attribute("axisZ"))
        z = atof(element->Attribute("axisZ"));
    
    return Vertex(x, y, z);
}

Vertex ParseColor(TiXmlElement *element)
{
    float r = 0, g = 0, b = 0;
   
    if (element->Attribute("diffR"))
        r = atof(element->Attribute("diffR"));
    if (element->Attribute("diffG"))
        g = atof(element->Attribute("diffG"));
    if (element->Attribute("diffB"))
        b = atof(element->Attribute("diffB"));

    return Vertex(r, g, b);
}

Material parseMaterial(TiXmlElement *element, bool texture){
    Vertex diffuse = Vertex(0.8f, 0.8f, 0.8f);
    Vertex ambient = Vertex(0.0f, 0.0f, 0.0f);
    Vertex diffuseAndAmbient = Vertex(0.0f, 0.0f, 0.0f);
    Vertex specular = Vertex(0.0f, 0.0f, 0.0f);
    Vertex emission = Vertex(0.2f, 0.2f, 0.2f);
    float shininess = 128.0;

    //Diffuse
    if(element->Attribute("diffuseX"))
        diffuse.x = (atof(element->Attribute("diffuseX")));
    if(element->Attribute("diffuseY"))
        diffuse.y = (atof(element->Attribute("diffuseY")));
    if(element->Attribute("diffuseZ"))
        diffuse.z = (atof(element->Attribute("diffuseZ")));

    //Ambient
    if(element->Attribute("ambientX"))
        ambient.x = (atof(element->Attribute("ambientX")));
    if(element->Attribute("ambientY"))
        ambient.y = (atof(element->Attribute("ambientY")));
    if(element->Attribute("ambientZ"))
        ambient.z = (atof(element->Attribute("ambientZ")));

    //Diffuse and Ambient
    if(element->Attribute("diffuseANDambientX"))
        diffuseAndAmbient.x = (atof(element->Attribute("diffuseANDambientX")));
    if(element->Attribute("diffuseANDambientY"))
        diffuseAndAmbient.y = (atof(element->Attribute("diffuseANDambientY")));
    if(element->Attribute("diffuseANDambientZ"))
        diffuseAndAmbient.z = (atof(element->Attribute("diffuseANDambientZ")));

    //Specular
    if(element->Attribute("specularX"))
        specular.x = (atof(element->Attribute("specularX")));
    if(element->Attribute("specularY"))
        specular.y = (atof(element->Attribute("specularY")));
    if(element->Attribute("specularZ"))
        specular.z = (atof(element->Attribute("specularZ")));

    //Emission
    if(element->Attribute("emissionX"))
        emission.x = (atof(element->Attribute("emissionX")));
    if(element->Attribute("emissionY"))
        emission.y = (atof(element->Attribute("emissionY")));
    if(element->Attribute("emissionZ"))
        emission.z = (atof(element->Attribute("emissionZ")));

    // Shininess
    if(element->Attribute("shininess"))
        shininess = atof(element->Attribute("shininess"));

    Material material = Material(diffuse, ambient, diffuseAndAmbient, specular, emission, shininess, texture);
    
    return material;
}

Model ParseModel(TiXmlElement *modelElement)
{
    string filePath = "", textureFile = "";
    bool texture = false;
    Model m = Model(); 
    if (modelElement->Attribute("file"))
    {
        string filePath = modelElement->Attribute("file");
        m = Model(filePath);
        m.fillBuffer();
    }
    if(modelElement->Attribute("texture")){
        m.fileTexture = modelElement->Attribute("texture");
        m.prepareTexture(modelElement->Attribute("texture"));
        texture = true;
    }
    /*else{
        m.color = ParseColor(modelElement);
    }*/

    m.material =  parseMaterial(modelElement, texture);

    return m;
}

Light ParseLight(TiXmlElement *lightElement)
{
    Light l = Light();
    if (lightElement->Attribute("type"))
    {
        l.lightType = lightElement->Attribute("type");
    }
    l.lightP = ParseAttributes(lightElement);

    return l;
}

Group ParseGroup(TiXmlElement *groupElement)
{
    Group group = Group();
    group.subGroups = vector<Group>();
    group.orbitPoints = vector<Vertex>();
    TiXmlElement *grandChildElement, *childElement, *childTranslationElement;
    group.rotation.x = 0;
    group.rotation.y = 0;
    group.rotation.z = 0;
    group.rotationAngle = 0;
    group.translation.x = 0;
    group.translation.y = 0;
    group.translation.z = 0;
    group.scale.x = 0;
    group.scale.y = 0;
    group.scale.z = 0;
    group.translationTime = 0;
    group.rotationTime = 0;

    bool translate = false, rotate = false, scale = false;

    for (childElement = groupElement->FirstChildElement(); childElement; childElement = childElement->NextSiblingElement())
    {
        if (!strcmp(childElement->Value(), "models"))
        {
            for (grandChildElement = childElement->FirstChildElement("model"); grandChildElement; grandChildElement = grandChildElement->NextSiblingElement())
            {
                if (!strcmp(grandChildElement->Value(), "model"))
                {
                    group.models.push_back(ParseModel(grandChildElement));
                }
            }
        }
        else if (!strcmp(childElement->Value(), "rotate") && !rotate)
        {
            group.rotation = ParseAttributes(childElement);
            if(childElement->Attribute("angle")){
                group.rotationAngle = atof(childElement->Attribute("angle"));
            }
            else{
                group.rotationTime = atof(childElement->Attribute("time"));
            }
            rotate = true;
        }
        else if (!strcmp(childElement->Value(), "translate") && !translate)
        {
            group.translation = ParseAttributes(childElement);
            if (childElement->Attribute("time")){
                group.translationTime = atof(childElement->Attribute("time"));
                for (grandChildElement = childElement->FirstChildElement("point"); grandChildElement; grandChildElement = grandChildElement->NextSiblingElement())
                {
                    if (!strcmp(grandChildElement->Value(), "point"))
                    {
                        Vertex point = ParseAttributes(grandChildElement);
                        group.orbitPoints.push_back(point);
                    }
                }
            }
            translate = true;
        }
        else if (!strcmp(childElement->Value(), "scale") && !scale)
        {
            group.scale = ParseAttributes(childElement);
            scale = true;
        }
        else if (!strcmp(childElement->Value(), "group"))
        {
            group.subGroups.push_back(ParseGroup(childElement));
        }
    }
    return group;
}

Scene ParseXMLFile(char *fileName)
{
    Scene scene = Scene();
    TiXmlDocument doc(fileName);
    TiXmlElement *groupElement, *grandChildElement;

    if (!doc.LoadFile())
    {
        cout << "Could not load XML file: " << fileName << "." << endl;
        return scene;
    }

    for (groupElement = doc.FirstChildElement("scene")->FirstChildElement(); groupElement; groupElement = groupElement->NextSiblingElement())
    {
        if(!strcmp(groupElement->Value(), "lights"))
        {
            for (grandChildElement = groupElement->FirstChildElement("light"); grandChildElement; grandChildElement = grandChildElement->NextSiblingElement())
            {
                if (!strcmp(grandChildElement->Value(), "light"))
                {
                    scene.lights.push_back(ParseLight(grandChildElement));
                }
            }

        }
        if(!strcmp(groupElement->Value(), "group"))
            scene.groups.push_back(ParseGroup(groupElement));
    }

    scene.fileScene = fileName;

    return scene;
}