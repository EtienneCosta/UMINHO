using MentorMe.Data;
using MentorMe.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Hosting;
using MimeKit;

namespace MentorMe.Controllers
{
    public class RoomController : Controller
    {

        IWebHostEnvironment _appEnvironment;
        private readonly MentorMeContext _context;

        public RoomController(MentorMeContext context, IWebHostEnvironment env)
        {
            _context = context;
            _appEnvironment = env;
        }

        public IActionResult Index()
        {
            return View();
        }

        /* Room Tags*/
        [HttpGet]
        public IActionResult AssociateTags()
        {

            return View();

        }

        [HttpPost]
        public IActionResult AssociateTags(string Name)
        {

            try
            {

                string username = HttpContext.Session.GetString("Username");
                int userid = (int)HttpContext.Session.GetInt32("Userid");

                int roomID = (int)HttpContext.Session.GetInt32("RoomID");

                // Create new Tag and save it on db
                Tag t = new Tag { Name = Name };
                _context.Tags.Add(t);
                _context.SaveChanges();

                // Create new TagRoom and save it on db
                TagRoom tr = new TagRoom { RoomID = roomID, TagID = t.TagID };
                _context.TagRooms.Add(tr);
                _context.SaveChanges();

                ViewBag.UserName = username;

                ViewBag.Name = Name;
                ViewBag.RoomName = HttpContext.Session.GetString("RoomName");
            }

            catch
            {
                ViewBag.Message = "Erro ao criar tag";
            }
            return View("Tag");

        }       

        [HttpGet]
        public IActionResult CreateRoom()
        {
            return View();
        }

        [HttpPost]
        public IActionResult CreateRoom(string Nome, string Description)
        {
            try
            {
                string username = HttpContext.Session.GetString("Username");
                int userid = (int) HttpContext.Session.GetInt32("Userid");
               
                // Generate the room key and grant its unique
                string room_key = Convert.ToBase64String(Guid.NewGuid().ToByteArray()).Substring(0, 8);
                while (_context.Rooms.Where(r => r.RoomKey == room_key).Count() > 0) room_key = Convert.ToBase64String(Guid.NewGuid().ToByteArray()).Substring(0, 8);

                if (_context.Rooms.Where(r => r.Nome.ToLower() == Nome.ToLower()).Count() > 0)
                {
                    ViewBag.AlreadyExists = true;
                    return View();
                }

                // Create new Room and save it on db
                Room r = new Room { Nome = Nome, Description = Description, RoomKey = room_key, Questions = new List<Question>(), Files = new List<Models.File>() };
                _context.Rooms.Add(r);
                _context.SaveChanges();

                HttpContext.Session.SetInt32("RoomID", r.RoomID);
                HttpContext.Session.SetString("RoomName", r.Nome);
                // Create new UserRoom and save it on db
                UserRoom ur = new UserRoom { UserID = userid, RoomID = r.RoomID, UserTypeID = 1 }; // admin               
                _context.UserRooms.Add(ur);
                _context.SaveChanges();


                ViewBag.RoomName = Nome;
                ViewBag.UserName = username;
                ViewBag.UserID = userid;
                ViewBag.AlreadyExists = true;

            }

            catch
            {
                ViewBag.Message = "Erro ao criar sala";
            }
            return View("Tag");
        }

        
        [HttpPost]
        public IActionResult JoinRoom(string RoomKey)
        {         
            // Get user ID
            int userid = (int)HttpContext.Session.GetInt32("Userid");
            string username = HttpContext.Session.GetString("Username");

            // Check if the code introduced actually matches with any room
            Room room = _context.Rooms.Where(r => r.RoomKey.Equals(RoomKey)).FirstOrDefault();

            if(room!= null)
            {
                List<UserRoom> userRoom = _context.UserRooms.Where(ur => ur.UserID == userid && ur.RoomID == room.RoomID).ToList();
                if (userRoom.Count() == 0)
                {
                    HttpContext.Session.SetInt32("RoomID", room.RoomID);
                    HttpContext.Session.SetString("RoomName", room.Nome);
                    // Create new UserRoom and save it on db
                    UserRoom ur = new UserRoom { UserID = userid, RoomID = room.RoomID, UserTypeID = 3 };
                    _context.UserRooms.Add(ur);
                    _context.SaveChanges();

                    // Update ViewBag and trigger the room view
                    ViewBag.RoomName = room.Nome;
                    ViewBag.UserName = username;
                    ViewBag.UserID = userid;
                    ViewBag.Room_key = room.RoomKey;

                    return RedirectToAction("EnterRoom", new { id = room.RoomID });
                }



            }

            // If it exists and the user doesnt belongs there add the user to the room


                TempData["Message"] = " Oops, código de acesso incorrecto !";


                return RedirectToAction("Dashboard", "Users", null);
        }

        public IActionResult RefineSearchAction(int RoomID) {

            int loggedUser = -1;
            if (HttpContext.Session.GetInt32("Userid") != null) loggedUser = (int)HttpContext.Session.GetInt32("Userid");
            else
            {
                return RedirectToAction("Login", "Users", null);
            }

            if (_context.UserRooms.Where(usr => usr.UserID == loggedUser && usr.RoomID == RoomID).Count()>0) {

                return RedirectToAction("EnterRoom",new { id = RoomID});
            }

            Notification newRequest = new Notification
            {
                Type = "Request",
                Date = DateTime.Now,
                RoomID = RoomID,
                RoomName = _context.Rooms.Find(RoomID).Nome,
                UserName = _context.Users.Find(loggedUser).Username,
                UserID = loggedUser
            };
            _context.Add(newRequest);


            int mentor = _context.UserRooms.Where(usr => usr.RoomID == RoomID && usr.UserTypeID == 1).FirstOrDefault().UserID;

            NotificationUser nu = new NotificationUser { UserID = mentor, Notification = newRequest, Visible = 1 };
            _context.Add(nu);
            _context.SaveChanges();
            
            return RedirectToAction("DashBoard", "Users");

        }


        /* Enter Room */
        public IActionResult EnterRoom(int id)
        {
            // get room
            Room room = _context.Rooms.Find(id);
            ViewBag.Name = room.Nome;

            HttpContext.Session.SetInt32("RoomID", room.RoomID);

            // get logged user
            int loggedUser = -1;
            if (HttpContext.Session.GetInt32("Userid") != null) loggedUser = (int)HttpContext.Session.GetInt32("Userid");           
            else
            {
                 return RedirectToAction("Login", "Users", null);
            }

            if (_context.UserRooms.Any(ur => ur.RoomID == id && ur.UserID == loggedUser))
            {
                ViewBag.valid = true;

                // set the current room
                ViewBag.CurrentRoom = room;

                // get the room admin
                int admin_id = _context.UserRooms.Where(ur => ur.RoomID == room.RoomID && ur.UserTypeID == 1).First().UserID;
                ViewBag.MentorName = _context.Users.Find(admin_id).Username;

                // set the questions for the view
                var db_questions = _context.Questions.Where(q => q.RoomID == id).ToList();
                var questions = new List<Question>();
                if (db_questions != null) db_questions.ForEach(q => questions.Add(q));
                ViewBag.Questions = questions;
            }
            else
            {
                // user doesnt belong to room
                ViewBag.valid = false;
            }


            return View("Room");
        }      

      

        public IActionResult Members()
        {

           int roomid =(int) HttpContext.Session.GetInt32("RoomID");
           int userid = (int) HttpContext.Session.GetInt32("Userid");

            ViewBag.isAdmin = _context.UserRooms.Where(ur => ur.RoomID == roomid && ur.UserTypeID == 1).First().UserID == userid ;

            var members = _context.UserRooms.Where(ur => ur.RoomID == roomid).ToList();
                var subscribers = members.Where(m => _context.UserTypes.Find(m.UserTypeID).Description == "Normal").ToList();
                var mentors = members.Where(m => _context.UserTypes.Find(m.UserTypeID).Description != "Normal").ToList();

                ViewBag.NoSubscribers = (subscribers.Count() == 0);
                ViewBag.NoMentors = (mentors.Count() == 0);

                // Transform the SubscriberList into User
                var SubscribersList = new List<User>();
                subscribers.ForEach(s => SubscribersList.Add(_context.Users.Find(s.UserID)));
                ViewBag.Subscribers = SubscribersList;

                var MentorsList = new List<User>();
                mentors.ForEach(m => MentorsList.Add(_context.Users.Find(m.UserID)));
                ViewBag.Mentors = MentorsList;

            
            return View();
        }


        public IActionResult Promote(int id)
        {
            int roomID = (int)HttpContext.Session.GetInt32("RoomID");
            var UserRoom = _context.UserRooms.Where(ur => ur.RoomID == roomID && ur.UserID == id).First();
            UserRoom.UserTypeID = 2;
            _context.Update(UserRoom);
            _context.SaveChanges();
            return RedirectToAction("Members");
        }

        public IActionResult Demote(int id)
        {
            int roomID = (int)HttpContext.Session.GetInt32("RoomID");
            int userid = (int)HttpContext.Session.GetInt32("Userid");
            var UserRoom = _context.UserRooms.Where(ur => ur.RoomID == roomID && ur.UserID == id).First();
            // Can't demote himself
            if (id != userid)
            {
                UserRoom.UserTypeID = 3;
                _context.Update(UserRoom);
                _context.SaveChanges();
            }
            return RedirectToAction("Members");
        }

        public IActionResult KickMember(int id)
        {
            int roomID = (int)HttpContext.Session.GetInt32("RoomID");
            var UserRoom = _context.UserRooms.Where(ur => ur.RoomID == roomID && ur.UserID == id).First();
            _context.UserRooms.Remove(UserRoom);
            _context.SaveChanges();
            return RedirectToAction("Members");
        }

        public IActionResult Files()
        {
            int roomID = (int)HttpContext.Session.GetInt32("RoomID");
            List<Models.File> files = _context.Files.Where(f => f.RoomID == roomID).ToList();
            ViewBag.Files = files;
            ViewBag.FilesLength = files.Count(); 
            return View("RoomFiles");
        }

        public async Task<IActionResult> SendFiles(List<IFormFile> files)
        {
            // Size in bytes
            long filesSizeInBytes = files.Sum(f => f.Length);
            int roomid = (int)HttpContext.Session.GetInt32("RoomID");

            // Goes through the files we got and process them
            foreach (var file in files)
            {
                // Error situation
                if (file == null || file.Length == 0)
                {
                    // Put info in the view bag
                    List<Models.File> filesForView2 = _context.Files.Where(f => f.RoomID == roomid).ToList();
                    ViewBag.Files = filesForView2;
                    ViewBag.FilesLength = filesForView2.Count();
                    // Returns the view data with an error
                    ViewData["Erro"] = "Couldn't find the files you were asking for.";
                    return View("RoomFiles");
                }
                // Creates a unique name for the file              
                string fileName = "r" + roomid + "_" + file.FileName;
                //Path to db
                string pathToDb = _appEnvironment.ContentRootPath;
                // Assemble the full file path
                // ~\UploadedFiles\FileName
                string saveFileIn = pathToDb + "\\UploadedFiles\\" + fileName;
                //copia o arquivo para o local de destino original
                using (var stream = new FileStream(saveFileIn, FileMode.Create))
                {
                    await file.CopyToAsync(stream);
                }
                // Store file register in database
                Models.File f = new Models.File { Title = file.FileName, Date = DateTime.Now, Path = saveFileIn, RoomID = roomid };
                _context.Files.Add(f);
                _context.SaveChanges();
            }
            // Setup ViewData with results of the file uploading
            ViewData["Resultado"] = $"{files.Count} arquivos foram enviados ao servidor, " +
             $"com tamanho total de : {filesSizeInBytes} bytes";
            // Put info in the view bag
            List<Models.File> filesForView = _context.Files.Where(f => f.RoomID == roomid).ToList();
            ViewBag.Files = filesForView;
            ViewBag.FilesLength = filesForView.Count();
            // Return the same view
            return View("RoomFiles");
        }

        [HttpGet]
        public IActionResult DownloadFile(int id)
        {                     
            Models.File file = _context.Files.Find(id);
            if (file == null) return NotFound();          

            //return File(file.Path, "application/pdf", file.Title); gives file not found
            //return PhysicalFile(file.Path, MimeTypes.GetMimeType(file.Path), file.Title);
            Response.Headers.Add("Content-Disposition", "inline; filename=" + file.Title);
            return new PhysicalFileResult(file.Path, MimeTypes.GetMimeType(file.Path)); //works on mac
        }

        public IActionResult RemoveFiles(int id)
        {
            Models.File file = _context.Files.Find(id);
            if (file != null)
            {
                _context.Files.Remove(file);
                _context.SaveChanges();
            }
            return RedirectToAction("Files");
        }

        public IActionResult LeaveRoom()
        {
            int roomid = (int)HttpContext.Session.GetInt32("RoomID");
            int userid = (int)HttpContext.Session.GetInt32("Userid");

            UserRoom delete = _context.UserRooms.Where(ur => ur.RoomID == roomid && ur.UserID == userid).FirstOrDefault();
            _context.UserRooms.Remove(delete);
            _context.SaveChanges();

            return RedirectToAction("Dashboard", "Users");
        }

    }
}