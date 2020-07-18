CC = gcc
CFLAGS = -Wall -g
LIBS = `pkg-config --libs --cflags gmodule-2.0`

all:
	flex -o lexmkfromtemplate.c mkfromtemplate.f
	$(CC) $(CFLAGS)  $(LIBS) lexmkfromtemplate.c -o mkfromtemplate
	./mkfromtemplate teste < template-flex

clean:
	rm -rf mkfromtemplate a.out lexmkfromtemplate.c teste
