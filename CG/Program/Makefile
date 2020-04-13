all: ./Generator/generator.o
	g++ -o ./Generator/generator ./Generator/generator.o
	#remove a versão anterior do gerador
	-rm ./Generator/generator.o
	#---------------------box (x,y,z,numDeDivisões)
	./Generator/generator box 4 4 4 2 ./Engine/Build/box.3d
	#---------------------sphere (raio, linhasVerticais, linhasHorizontais)
	./Generator/generator sphere 4 100 100 ./Engine/Build/sphere.3d
	#---------------------cone (raioDaBase, altura, LinhasVerticais, linhasHorizontais)
	./Generator/generator cone 1 2 50 50 ./Engine/Build/cone.3d
	#---------------------plane (comprimentoDosLados)
	./Generator/generator plane 5 ./Engine/Build/plane.3d

generator.o: ./Generator/generator.cpp
	g++ -c ./Generator/generator.cpp
	
#Arranca com o "executavel"
#./Engine/Build/engine config.xml



# Remove os modelos 3D anteriores
clean3Ds: 
	-rm ./Generator/*.3d
	-rm ./Engine/Build/*.3d




# Made by:
#		Maurício Salgado(A71***)
#		Rafael Alves(A72629)
#		Etienne Costa(A76089)
#		Joana Cruz(A76270)


