using MentorMe.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace MentorMe.Data
{
    public class DbInitializer
    {
        public static void Initialize(MentorMeContext context)
        {
            context.Database.EnsureDeleted();
            context.Database.EnsureCreated();

            //Look for any users.

            if (context.Users.Any())
            {
                return;
            }

            var users = new User[]
            {
            new User{Email= "admin@hotmail.com", Username = "admin", Password = "admin",Questions = new List<Question>(),Answers = new List<Answer>()},
            new User{Email= "etienne_costa@hotmail.com", Username = "EtienneCosta", Password = "costa",Questions = new List<Question>(),Answers = new List<Answer>()},
            new User{Email="costapedro.a1999@gmail.com",Username="pCosta99",Password="pCosta99",Questions = new List<Question>(),Answers = new List<Answer>()},
            new User{Email="alexandrecosta@hotmail.com",Username="AlexandreCosta",Password="costa",Questions = new List<Question>(),Answers = new List<Answer>()},
            new User{Email="mariosantos@hotmail.com",Username="MarioSantos",Password="santos",Questions = new List<Question>(),Answers = new List<Answer>()},
            new User{Email="marcossilva@hotmail.com",Username="MarcosSilva",Password="silva",Questions = new List<Question>(),Answers = new List<Answer>()}

            };
            foreach (User u in users)
            {
                context.Users.Add(u);
                context.SaveChanges();
            }
            /* ---------------------- User Type ---------------------- */
            if (context.UserTypes.Any())
            {
                return;
            }


            var userstypes = new UserType[]
            {
            new UserType{Description = "Admin"},
            new UserType{Description = "Mentor"},
            new UserType{Description = "Normal"}

            };
            foreach (UserType utype in userstypes)
            {
                context.UserTypes.Add(utype);
                context.SaveChanges();

            }

            /* ---------------------- Room ---------------------- */



            var rooms = new Room[]
            {
                new Room {Nome= "Algoritmos e Complexidade",Description="A complexidade de um algoritmo mede os recursos necessários para que este seja executado.", RoomKey = "ABC", Questions = new List<Question>(), Files = new List<File>()},
                new Room {Nome= "Álgebra Linear EI",Description="Ramo da matemática que surgiu do estudo detalhado de sistemas de equações lineares", RoomKey = "ABD", Questions = new List<Question>(), Files = new List<File>()},
                new Room {Nome = "Prolog", Description = "Ramo da matemática que se dedica ao estudo de taxas de variação de grandezas e a acumulação de quantidades.", RoomKey = "ABE", Questions = new List<Question>(), Files = new List<File>()},
                new Room {Nome= "Tópicos de Matemática Discreta", Description="Estudo das estruturas algébricas que são fundamentalmente discretas, em vez de contínuas.", RoomKey = "ABF", Questions = new List<Question>(), Files = new List<File>()},
                new Room {Nome= "Programação Funcional", Description="Paradigma de programação que trata a computação como uma avaliação de funções matemáticas e que evita estados ou dados mutáveis.", RoomKey = "ABG", Questions = new List<Question>(), Files = new List<File>()}
            };
            foreach (Room r in rooms)
            {
                context.Rooms.Add(r);
                context.SaveChanges();
            }

            /* ---------------------- Room ---------------------- */

            var userrooms = new UserRoom[]
            {
                new UserRoom {UserID=2 ,RoomID=1,UserTypeID=1},
                new UserRoom {UserID=2 ,RoomID=2,UserTypeID=1},
                new UserRoom {UserID=2 ,RoomID=3,UserTypeID=1},
                new UserRoom {UserID=3 ,RoomID=4,UserTypeID=1},
                new UserRoom {UserID=3 ,RoomID=5,UserTypeID=1},
                new UserRoom {UserID=4 ,RoomID=1,UserTypeID=3},
                new UserRoom {UserID=4 ,RoomID=3,UserTypeID=3},
                new UserRoom {UserID=2 ,RoomID=5,UserTypeID=3},
                new UserRoom {UserID=5 ,RoomID=5,UserTypeID=3},

            };

            foreach (UserRoom ur in userrooms)
            {
                context.UserRooms.Add(ur);
                context.SaveChanges();
            }



            var tags = new Tag[]
            {
                new Tag {Name="Matrizes"},//Id:1
                new Tag {Name="Determinantes"},//Id:2
                new Tag {Name="Sistemas de Equações"},//Id:3
                new Tag {Name="Funções"},//Id:4
                new Tag {Name="Derivadas"},//Id:5
                new Tag {Name="Integrais"},//Id:6
                new Tag {Name="Lógica"},//Id:7
                new Tag {Name="Teoria de Conjuntos "},//Id:8
                new Tag {Name="Indução"},//Id:9
                new Tag {Name="Grafos"},//Id:10
                new Tag {Name="Recursividade"},//Id:11
                new Tag {Name="Haskell"},//Id:12
                new Tag {Name="Complexidade"},//Id:13
                new Tag {Name="BFS"},//Id:14
                new Tag {Name="DFS"},//Id:15
                new Tag {Name="Estruturas"}//Id:16

            };
            foreach (Tag t in tags)
            {
                context.Tags.Add(t);
                context.SaveChanges();
            }



            var tagrooms = new TagRoom[]
            {

                new TagRoom {TagID=1,RoomID=1},
                new TagRoom {TagID=2,RoomID=1},
                new TagRoom {TagID=3,RoomID=1},
                new TagRoom {TagID=4,RoomID=1},

                new TagRoom {TagID=4,RoomID=2},
                new TagRoom {TagID=5,RoomID=2},
                new TagRoom {TagID=6,RoomID=2},

                new TagRoom {TagID=7,RoomID=3},
                new TagRoom {TagID=8,RoomID=3},
                new TagRoom {TagID=9,RoomID=3},
                new TagRoom {TagID=10,RoomID=3},

                new TagRoom {TagID=11,RoomID=4},
                new TagRoom {TagID=12,RoomID=4},


                new TagRoom {TagID=13,RoomID=5},
                new TagRoom {TagID=14,RoomID=5},
                new TagRoom {TagID=15,RoomID=5},
                new TagRoom {TagID=16,RoomID=5},


            };
            foreach (TagRoom tr in tagrooms)
            {
                context.TagRooms.Add(tr);
                context.SaveChanges();
            }

            /* ------------------------------------ TMD ------------------------------------------------------*/
            Question p = new Question { Title = "O que está mal neste código?", Content = "Estou a tentar fazer uma função que soma todos os elementos duma lista, porque não funciona?" + System.Environment.NewLine + "``soma = foldr (\\x -> x+) 0 ``", Date = DateTime.Now, Answers = new List<Answer>() };
            context.Rooms.Find(5).Questions.Add(p);
            context.Users.Find(5).Questions.Add(p);

            // Questions
            var questions_tmd_pcosta = new Question[]
            {
                new Question { Title = "O que se aprende nesta cadeira?", Content = "Estou bastante curioso sobre o conteúdo lecionado nesta cadeira, será que alguém me podia explicar muito brevemente?", Date = DateTime.Now, Answers = new List<Answer>()},
            };

            Room qr = context.Rooms.Find(4);
            User qu = context.Users.Find(3);

            foreach (Question q in questions_tmd_pcosta)
            {
                qr.Questions.Add(q);
                qu.Questions.Add(q);

                // Followers of the question
                FollowerQuestion fq = new FollowerQuestion { FollowerID = 3, Following = q };
                context.Add(fq);

                context.SaveChanges();
            }

            // Answers            
            Answer answer = new Answer { Content = "Tens de ter cuidado com os parênteses ao programar em Haskell!!" + System.Environment.NewLine + "Experimenta pôr ``\\x -> (+) x`` na função lambda!", Date = DateTime.Now, Valid = false, UserName = "EtienneCosta" };
            context.Questions.Find(2).Answers.Add(answer);
            context.Users.Find(3).Answers.Add(answer);
            var question = context.Questions.Find(1);
            var answers_tmd_pcosta = new Answer[]
            {
                new Answer { Content = "Tenho **exatamente** a mesma questão!", Date = DateTime.Now, Valid = false, UserName = "EtienneCosta"},
                new Answer { Content = "Nesta unidade curricular, estudaremos algumas noções básicas associadas ao Cálculo Proposicional Clássico e ao Cálculo de Predicados Clássico.", Date = DateTime.Now, Valid = true, UserName = "pCosta99"},
                new Answer { Content = "Obrigado pelo esclarecimento, caro mentor!", Date = DateTime.Now, Valid = false, UserName = "AlexandreCosta"},
            };

            foreach (Answer a in answers_tmd_pcosta)
            {
                qu.Answers.Add(a);
                question.Answers.Add(a);
                context.SaveChanges();
            }
            /* ---------------------------------------------------------------------------------------------*/




            /* ---------------------------------PROLOG------------------------------------------------------*/
            var questionsPrologAlex = new Question[]
           {
                new Question { Title = "Prolog Length",
                               Content = "> Estou a dar os primeiros passos na programação em lógica e gostaria de saber como implementar um predicado que calcule o número de elementos existentes numa lista.",
                               Date = DateTime.Now,
                               Answers = new List<Answer>(),
                               TagQuestions = new List<TagQuestion>()}
           };

            // Sala aonde é realizada a questão: Prolog.
            Room prologRoom = context.Rooms.Find(3);
            // Utilizador que realiza a questão.
            User alexUSer = context.Users.Find(4);


            foreach (Question q in questionsPrologAlex)
            {
                prologRoom.Questions.Add(q);
                alexUSer.Questions.Add(q);

                context.SaveChanges();
            }




            /* ---------------------------------------------------------------------------------------------*/

            // Notifications testing
            Notification req = new Notification
            {
                Type = "Request",
                Date = DateTime.Now,
                RoomID = 5,
                RoomName = "Programação Funcional",
                UserName = "EtienneCosta",
                UserID = 2
            };
            context.Notifications.Add(req);

            NotificationUser nu = new NotificationUser { UserID = 3, Notification = req, Visible = 1 };
            context.NotificationUsers.Add(nu);

            // Notifications testing
            req = new Notification
            {
                Type = "Request",
                Date = DateTime.Now,
                RoomID = 5,
                RoomName = "Programação Funcional",
                UserName = "MarcosSilva",
                UserID = 6
            };
            context.Notifications.Add(req);

            nu = new NotificationUser { UserID = 3, Notification = req, Visible = 1 };
            context.NotificationUsers.Add(nu);
            context.SaveChanges();
        }
    }
}