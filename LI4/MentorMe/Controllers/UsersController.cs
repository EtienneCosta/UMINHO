using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using MentorMe.Data;
using MentorMe.Models;
using Microsoft.AspNetCore.Http;

namespace MentorMe.Controllers
{
    public class UsersController : Controller
    {
        private readonly MentorMeContext _context;

        public UsersController(MentorMeContext context)
        {
            _context = context;
        }



        /* ---LOGIN --- */


        [HttpGet]
        public IActionResult Login()
        {
            if (Request.Cookies["LastLoggedTime"] != null)
                ViewBag.LastLogin = Request.Cookies["LastLoggedTime"].ToString();
            return View();
        }

        [HttpPost]
        public IActionResult Login(User us)
        {

            if (us.Username != null && us.Password != null && us.Password.Length > 3)
            {
                User loggedUser = _context.Users
                                      .Where(u => u.Username == us.Username && u.Password == us.Password)
                                      .FirstOrDefault();
                if (loggedUser == null)
                {
                    ViewBag.Message = "Dados Inválidos !";
                    return View();
                }

                // Guarda informação do utilizador na sessão
                HttpContext.Session.SetString("Username", loggedUser.Username);
                HttpContext.Session.SetInt32("Userid", loggedUser.UserID);



                // guardar o ultimo loggin feito pelo utilizador
                Response.Cookies.Append("LastLoggedTime", DateTime.Now.ToString());

                return RedirectToAction("DashBoard");
            }

            return View();
        }


        /* ---  REGISTER --- */


        [HttpGet]
        public IActionResult Register()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Register(string email, string username, string password)
        {
            if (email.Length > 2 && username.Length > 2 && password.Length > 2)
            {
                //Check if email or username already exist
                int existsUser = _context.Users.Where(u => u.Username == username).Count();
                int existsEmail = _context.Users.Where(u => u.Email == email).Count();

                if (existsUser > 0 || existsEmail > 0)
                {
                    if (existsUser > 0) ViewBag.Message = "Nome de utilizador já existente.";
                    if (existsEmail > 0) ViewBag.Message = "Email já existente.";

                    return View();
                }

                // Create new user and save it on db
                User u = new User { Email = email, Username = username, Password = password, Answers = new List<Answer>() };
                _context.Users.Add(u);
                _context.SaveChanges();

                // Guarda informacao do utilizador na sessao
                HttpContext.Session.SetString("Username", u.Username);
                HttpContext.Session.SetInt32("Userid", u.UserID);

                // guardar o ultimo loggin feito pelo utilizador
                Response.Cookies.Append("LastLoggedTime", DateTime.Now.ToString());

                return RedirectToAction("DashBoard");
            }

            else
            {
                ViewBag.Message = "Dados inválidos!";
            }

            return View();
        }

        /* Room Tags*/
        public IActionResult Dashboard()
        {
            // check if any user is logged in
            string username = HttpContext.Session.GetString("Username");
            if (username == null) return RedirectToAction("Login");

            int id = (int) HttpContext.Session.GetInt32("Userid");            
            ViewBag.UserName = username;
            ViewBag.UserId = id; 

            var rooms = _context.UserRooms.Where(ur => ur.User.Username == username).ToList();
            var reg_rooms = rooms.Where(r => _context.UserTypes.Find(r.UserTypeID).Description == "Normal").ToList();
            var mentor_rooms = rooms.Where(r => _context.UserTypes.Find(r.UserTypeID).Description != "Normal").ToList();
           
            ViewBag.NoMenRooms = (mentor_rooms.Count() == 0);
            ViewBag.NoRegRooms = (reg_rooms.Count() == 0);

            // Transform the UserRooms into Rooms
            var reg_rooms_list = new List<Room>();
            reg_rooms.ForEach(r => reg_rooms_list.Add(_context.Rooms.Find(r.RoomID)));
            ViewBag.Rooms = reg_rooms_list;

            // Transform the MentorRooms into Rooms
            var mentor_rooms_list = new List<Room>();
            mentor_rooms.ForEach(r => mentor_rooms_list.Add(_context.Rooms.Find(r.RoomID)));
            ViewBag.MentorRooms = mentor_rooms_list;

            // Get notifications for current user
            var notificationUsers_list = _context.NotificationUsers.Where(nu => nu.UserID == id && nu.Visible == 1).ToList();
            /*List<String> notifications_list = new List<String>();
            foreach(NotificationUser nu in notificationUsers_list){
                Notification n = _context.Notifications.Find(nu.NotificationID);
                String s = n.Date + ": " + n.Type + " in  room " + _context.Rooms.Find(n.RoomID).Nome;
                notifications_list.Add(s); 
            }
            ViewBag.Notifications = notifications_list;*/
            ViewBag.NotificationsCount = notificationUsers_list.Count();

            return View();
        }

        public IActionResult Notifications()
        {
            int id = (int)HttpContext.Session.GetInt32("Userid");

            var notificationUsers_list = _context.NotificationUsers.Where(nu => nu.UserID == id).ToList();
            List<Notification> notifications_list = new List<Notification>();
            foreach(NotificationUser nu in notificationUsers_list){
                Notification n = _context.Notifications.Find(nu.NotificationID);                
                // If it hasn't been deleted, send it to the view
                if(nu.Visible == 1) notifications_list.Add(n); 
            }
            ViewBag.Notifications = notifications_list;

            return View("Notifications");
        }

        public IActionResult SetInvisibleNotification(int notID)
        {
            int id = (int)HttpContext.Session.GetInt32("Userid");

            NotificationUser nu = _context.NotificationUsers.Where(n => n.UserID == id && n.NotificationID == notID).FirstOrDefault();
            nu.Visible = 0;
            _context.Update(nu);
            _context.SaveChanges();

            return RedirectToAction("Notifications");
        }

        // Adds a user to the room
        public IActionResult AddUserToRoom(int userid, int roomid, int notID)
        {
            // If the user is not in the room already (can happen), add him.
            if(_context.UserRooms.Where(ur => ur.RoomID == roomid && ur.UserID == userid).Count() == 0)
            {
                UserRoom ur = new UserRoom { UserID = userid, RoomID = roomid, UserTypeID = 3 };
                _context.UserRooms.Add(ur);
                _context.SaveChanges();
            }            

            int id = (int)HttpContext.Session.GetInt32("Userid");

            // "Deletes" the notification because it was accepted
            NotificationUser nu = _context.NotificationUsers.Where(n => n.UserID == id && n.NotificationID == notID).FirstOrDefault();
            nu.Visible = 0;
            _context.Update(nu);
            _context.SaveChanges();

            return RedirectToAction("SetInvisibleNotification", new { notID });
        }

        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            ViewData.Add(new KeyValuePair<string, object>("isLog", false));
            return RedirectToAction("Index","Home");
        }

        // GET: Users
        public async Task<IActionResult> Index()
        {

            int id = (int)HttpContext.Session.GetInt32("Userid");
            return View(await _context.Users.Where(u => u.UserID == id).ToListAsync());


        }

        // GET: Users/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(m => m.UserID == id);
            if (user == null)
            {
                return NotFound();
            }

            return View(user);
        }

        // GET: Users/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Users/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("UserID,Email,Username,Password")] User user)
        {
            if (ModelState.IsValid)
            {
                _context.Add(user);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(user);
        }

        // GET: Users/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            ViewBag.UserId = id;

            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }
            return View(user);
        }

        // POST: Users/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("UserID,Email,Username,Password")] User user)
        {
            if (id != user.UserID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(user);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!UserExists(user.UserID))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index),new { id });
            }
            return View(user);
        }

        // GET: Users/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(m => m.UserID == id);
            if (user == null)
            {
                return NotFound();
            }

            return View(user);
        }

        // POST: Users/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var user = await _context.Users.FindAsync(id);
            _context.Users.Remove(user);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool UserExists(int id)
        {
            return _context.Users.Any(e => e.UserID == id);
        }
    }
}
