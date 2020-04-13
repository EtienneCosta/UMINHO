#include <stdlib.h>
#include <stdio.h>
#include "date.h"
#include <glib.h>

/***************************************************************************************************************************
  Struct Date                                       
****************************************************************************************************************************/
struct date {
  int day;
  int month;
  int year;
};

Date createDate(int day, int month, int year) {
    Date d = (Date) malloc(sizeof(struct date));
    d->day = day;
    d->month = month;
    d->year = year;
    return d;
}

int get_day(Date d) {
    return d->day; 
}

int get_month(Date d) {
    return d->month; 
}

int get_year(Date d) {
    return d->year;
}

/***************************************************************************************************************************
   Funções auxiliares e free                                      
****************************************************************************************************************************/
Date stringToDate(char * date){
  int day = 0, month=0, year=0;
  sscanf(date,"%d-%d-%d",&year,&month,&day);
  return createDate(day,month,year);
}

gint compareDate(Date d1, Date d2){
  if(d1->year < d2->year)
    return -1;
  if(d1->year == d2->year && d1->month < d2->month)
    return -1;
  if(d1->year == d2->year && d1->month == d2->month && d1->day < d2->day)
    return -1;
  if(d1->year == d2->year && d1->month == d2->month && d1->day == d2->day)
    return 0;
  return 1;
}

unsigned int dateHash(GDateTime * date){
  return (unsigned int) (g_date_time_get_year(date)*10000 + g_date_time_get_month(date)*100 + g_date_time_get_day_of_month(date)); 
}

void free_date(Date d) {
    free(d);
}