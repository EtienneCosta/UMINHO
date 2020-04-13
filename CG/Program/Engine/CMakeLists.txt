cmake_minimum_required(VERSION 2.8)

# Project Name
PROJECT(engine)

list(APPEND CMAKE_MODULE_PATH "${CMAKE_CURRENT_LIST_DIR}/Build")

if (NOT APPLE)
	if (WIN32)
		set(GLEW_ROOT_FOLDER "" CACHE PATH "Path to GLEW folder")
		include_directories(${GLEW_ROOT_FOLDER})
		link_directories(${GLEW_ROOT_FOLDER})
	else(WIN32)
		find_package(GLEW REQUIRED)
		include_directories(${GLEW_INCLUDE_DIRS})
		link_libraries(${GLEW_LIBRARIES})
	endif(WIN32)
endif(NOT APPLE)

find_package(GLUT REQUIRED)
include_directories(${GLUT_INCLUDE_DIR})
link_directories(${GLUT_LIBRARY_DIRS})
add_definitions(${GLUT_DEFINITIONS})

find_package(OpenGL REQUIRED)
include_directories(${OpenGL_INCLUDE_DIRS})
link_directories(${OpenGL_LIBRARY_DIRS})
add_definitions(${OpenGL_DEFINITIONS})

find_package(TinyXML REQUIRED)
include_directories(${TinyXML_INCLUDE_DIRS})
link_directories(${TinyXML_LIBRARY_DIRS})
add_definitions(${TinyXML_DEFINITIONS})

find_package(DevIL REQUIRED)
link_libraries(${IL_LIBRARIES} )
include_directories(ยง{IL_INCLUDE_DIR})

add_executable(${PROJECT_NAME} engine.cpp Group.h Group.cpp Vertex.h Vertex.cpp Model.h Model.cpp parser.h parser.cpp Light.cpp Light.h Scene.cpp Scene.h Material.cpp Material.h)


if (WIN32)
	target_link_libraries(${PROJECT_NAME} ${OPENGL_LIBRARIES} ${GLUT_LIBRARY} glew32 DevIL)
	file(COPY ${TOOLKITS_FOLDER}/devil/DevIL.dll DESTINATION ${CMAKE_BINARY_DIR})
else(WIN32)
	target_link_libraries(${PROJECT_NAME} ${OPENGL_LIBRARIES} ${GLUT_LIBRARY} ${TinyXML_LIBRARIES})
endif (WIN32)