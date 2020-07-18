using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MentorMe.Data;
using MentorMe.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace MentorMe.Controllers
{
    public class QuestionController : Controller
    {

        private readonly MentorMeContext _context;

        public QuestionController(MentorMeContext context)
        {
            _context = context;
        }

        public IActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public IActionResult CreateAnswer()
        {
            return View();
        }

        /* Answer Question */
        [HttpPost]
        public IActionResult CreateAnswer(String Content)
        {

            // get room
            int room_id = (int)HttpContext.Session.GetInt32("RoomID");
            // get logged user
            int loggedUser = (int)HttpContext.Session.GetInt32("Userid");
            //get QuestionID
            int questionID = (int)HttpContext.Session.GetInt32("QuestionID");

            // Create new answer and save it on db
            Answer a;

            // If it's a mentor answering automatically mark as valid
            int usertype = _context.UserRooms.Where(ur => ur.RoomID == room_id && ur.UserID == loggedUser).FirstOrDefault().UserTypeID;
            if (_context.UserTypes.Find(usertype).Description == "Mentor" || _context.UserTypes.Find(usertype).Description == "Admin")
            {
                a = new Answer { Content = Content, Date = DateTime.Now, QuestionID = questionID, UserID = loggedUser, Valid = true, UserName = _context.Users.Find(loggedUser).Username };
            }
            else a = new Answer { Content = Content, Date = DateTime.Now, QuestionID = questionID, UserID = loggedUser, Valid = false, UserName = _context.Users.Find(loggedUser).Username };           
            _context.Answers.Add(a);

            // Notify owner of the question and followers
            Question q = _context.Questions.Find(questionID);
            Notification n = new Notification { Type = "New answer on question " + q.Title, 
                                                Date = DateTime.Now, 
                                                RoomID = room_id, 
                                                RoomName = _context.Rooms.Find(room_id).Nome,
                                                UserName = _context.Users.Find(loggedUser).Username,
                                                UserID = loggedUser
                                                };
            _context.Add(n);

            // Owner
            NotificationUser owner = new NotificationUser { UserID = q.UserID, Notification = n, Visible = 1 };
            if(owner.UserID != loggedUser) _context.Add(owner);

            // Followers
            foreach (FollowerQuestion fq in _context.FollowerQuestions.Where(fq => fq.FollowingID == q.QuestionID))
            {
                NotificationUser nu = new NotificationUser { UserID = fq.FollowerID, Notification = n };
                if (nu.UserID != loggedUser && nu.UserID != owner.UserID) _context.Add(nu);
            }
            _context.SaveChanges();


            return RedirectToAction("EnterQuestion", new { id = questionID });
        }

        /* Enter Question */
        public IActionResult EnterQuestion(int id)
        {
            // get room
            int room_id = (int)HttpContext.Session.GetInt32("RoomID");
            HttpContext.Session.SetInt32("QuestionID", id);
            Room room = _context.Rooms.Find(room_id);
            ViewBag.Message = room.Nome;
            ViewBag.RoomID = room_id;

            // get logged user
            int loggedUser = (int)HttpContext.Session.GetInt32("Userid");

            if (_context.UserRooms.Any(ur => ur.RoomID == room_id && ur.UserID == loggedUser))
            {
                ViewBag.valid = true;

                // set the current room
                ViewBag.CurrentRoom = room;


                // get the room admin
                int admin_id = _context.UserRooms.Where(ur => ur.RoomID == room.RoomID && ur.UserTypeID == 1).First().UserID;
                ViewBag.MentorName = _context.Users.Find(admin_id).Username;

                // get the question
                Question q = _context.Questions.Find(id);
                ViewBag.Question = q;

                // set the answers for the view
                List<Answer> answers = _context.Answers.Where(a => a.QuestionID == id).ToList();
                answers.Sort((y,x) => x.Valid.CompareTo(y.Valid));
                ViewBag.AnswersLength = answers.Count();
                ViewBag.Answers = answers;
            }
            else
            {
                // user doesnt belong to room
                ViewBag.valid = false;
            }

            return View("Question");
        }

        [HttpGet]
        public IActionResult CreateQuestion()
        {
            return View();
        }

        [HttpPost]
        public IActionResult CreateQuestion(string Content, string Title)
        {
            int roomid = (int)HttpContext.Session.GetInt32("RoomID");
            int userid = (int)HttpContext.Session.GetInt32("Userid");

            try
            {
                Room room = _context.Rooms.Find(roomid);


                // Create new Question  and save it on db
                Question q = new Question { Title = Title, Content = Content, Date = DateTime.Now, UserID = userid, RoomID = roomid, Answers = new List<Answer>() };
                _context.Questions.Add(q);

                // Notify all users of the room about a new question
                Notification newQuestion = new Notification { Type = "New question", 
                                                              Date = DateTime.Now, 
                                                              RoomID = room.RoomID, 
                                                              RoomName = room.Nome,
                                                              UserName = _context.Users.Find(userid).Username,
                                                              UserID = userid
                                                              };
                _context.Add(newQuestion);
                foreach (UserRoom ur in _context.UserRooms.Where(ur => ur.RoomID == room.RoomID))
                {
                    NotificationUser nu = new NotificationUser { UserID = ur.UserID, Notification = newQuestion, Visible = 1 };
                    _context.Add(nu);
                }

                _context.SaveChanges();
            }

            catch
            {
                ViewBag.Message = "Erro ao criar question";
            }

            return RedirectToAction("EnterRoom", "Room", new { id = roomid });
        }
       
        public IActionResult MarkAsValidAnswer(int id)
        {
            //get QuestionID
            int questionID = (int)HttpContext.Session.GetInt32("QuestionID");

            // get other stuff
            int roomid = (int)HttpContext.Session.GetInt32("RoomID");
            int userid = (int)HttpContext.Session.GetInt32("Userid");
            int usertype = _context.UserRooms.Where(ur => ur.RoomID == roomid && ur.UserID == userid).FirstOrDefault().UserTypeID;

            // if the user is a mentor allow him to flip the state of the answer
            if(_context.UserTypes.Find(usertype).Description != "Normal")
            {
                Answer a = _context.Answers.Find(id);
                a.Valid = !a.Valid;
                _context.Update(a);
                _context.SaveChanges();
            }

            return RedirectToAction("EnterQuestion", new { id = questionID });
        }
    }
}