using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace MentorMe.Models
{
    public class User
    {
        public int UserID { get; set; }

        [Required][StringLength(30)]
        public string Email { get; set; }

        [Required][StringLength(30)]
        public string Username { get; set; }

        [Required][StringLength(10)]
        public string Password { get; set; }

       public List<Question> Questions { get; set; }

       public List<Answer> Answers { get; set; }

        /* --- Relationship --- */

        public List<UserRoom> UserRooms { get; set; }

        /* --- Relationship --- */
        public List<FollowerQuestion> FollowerQuestions { get; set; }

        /* --- Relationship --- */

        public List<NotificationUser> NotificationUsers { get; set; }
    }
}