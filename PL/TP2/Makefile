FLAGS = `pkg-config --cflags gmodule-2.0` -g
LIBS = `pkg-config --libs gmodule-2.0`


gedcom : y.tab.o lex.yy.o gedstruct.o
	gcc $(FLAGS) $(LIBS) -o gedcom gedstruct.o y.tab.o lex.yy.o

y.tab.o : y.tab.c
	gcc -c $(FLAGS) y.tab.c

lex.yy.o : lex.yy.c
	gcc -c lex.yy.c

y.tab.c y.tab.h : gedcom.y
	yacc -v -d gedcom.y

lex.yy.c : gedcom.l y.tab.h
	flex gedcom.l

gedstruct.o : gedstruct.c gedstruct.h
	gcc -c $(FLAGS) $< -o $@

clean :
	rm -f html/*.html y.output lex.yy.c y.tab.c y.tab.o lex.yy.o y.tab.h gedcom gedstruct.o
