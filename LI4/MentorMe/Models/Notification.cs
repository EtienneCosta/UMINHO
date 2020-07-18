using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace MentorMe.Models
{
    public class Notification
    {
        public int NotificationID { get; set; }

        [Required][StringLength(130)]
        public string Type { get; set; }
        
        [Required][StringLength(130)]
        public string RoomName { get; set; }

        public string UserName { get; set; }
        public int UserID { get; set; }

        [Required]
        public DateTime Date { get; set; }

        /* --- Relationship --- */

        public int RoomID { get; set; }
        public Room Room { get; set; }

        /* --- Relationship --- */

        public List<NotificationUser> NotificationUsers { get; set; }
    }
}