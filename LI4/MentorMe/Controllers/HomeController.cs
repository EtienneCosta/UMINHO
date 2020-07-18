using MentorMe.Models;
using MentorMe.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics;
using Westwind.AspNetCore.Markdown;

namespace MentorMe.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly MentorMeContext _context;

        public HomeController(ILogger<HomeController> logger, MentorMeContext context)
        {
            _logger = logger;
            _context = context;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

        /* Handles the data gotten through the search bar */
        public IActionResult Search(String Content)
        {
            List<Room> match = new List<Room>();
            List<Room> rooms = _context.Rooms.ToList();
            /* Go through every single room */
            foreach (var room in rooms)
            {
                /* If the name contains the keyword, add it to the list and move to the next */
                if (room.Nome.ToLower().Contains(Content.ToLower()))
                {
                    match.Add(room);
                    break;
                }
                /* If any tag contains the keyword, add it to the list and move to the next */
                List<TagRoom> tagrooms = _context.TagRooms.Where(t => t.RoomID == room.RoomID).ToList();
                List<Tag> tags = new List<Tag>();
                foreach (var tr in tagrooms) tags.Add(_context.Tags.Find(tr.TagID));
                foreach (var tag in tags)
                {
                    if (tag.Name.ToLower().Contains(Content.ToLower()))
                    {
                        match.Add(room);
                        break;
                    }
                }
            }
            ViewBag.Rooms = match;
            ViewBag.Keyword = Content;
            return View("SearchRooms");
        }




        /* Advanced Search */
        public IActionResult AdvancedSearch(String Name, String Tags, String Mentor)
        {

            List<Room> match = new List<Room>();
            List<Room> rooms = _context.Rooms.ToList();
            /* Go through every single room */
            foreach (var room in rooms)
            {
                /* If the name contains the keyword, add it to the list and move to the next */
                if (Name != null && room.Nome.ToLower().Contains(Name.ToLower()))
                {
                    match.Add(room);
                    break;
                }
                /* If any tag contains the keyword, add it to the list and move to the next */
                List<TagRoom> tagrooms = _context.TagRooms.Where(t => t.RoomID == room.RoomID).ToList();
                List<Tag> tags = new List<Tag>();
                foreach (var tr in tagrooms) tags.Add(_context.Tags.Find(tr.TagID));
                foreach (var tag in tags)
                {
                    if (Tags != null && tag.Name.ToLower().Contains(Tags.ToLower()))
                    {
                        match.Add(room);
                        break;
                    }
                }
                List<UserRoom> roomusers = _context.UserRooms.Where(u => u.RoomID == room.RoomID && u.UserTypeID == 2).ToList();
                List<User> users = new List<User>();
                foreach (var usr in roomusers) users.Add(_context.Users.Find(usr.UserID));
                foreach (var us in users)
                {
                    if (Mentor != null && us.Username.ToLower().Contains(Mentor.ToLower()))
                    {
                        match.Add(room);
                        break;
                    }
                }
            }
            ViewBag.Rooms = match;
            ViewBag.RoomCount = match.Count();
            return View("RefinedSearch");
        }
    }
}
