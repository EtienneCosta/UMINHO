using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace MentorMe.Models
{
    public class Room
    {
        public int RoomID { get; set; }

        public string RoomKey { get; set; }

        [Required][StringLength(60)]
        public string Nome { get; set; }

        [Required]
        public string Description { get; set; }

        public List<Question> Questions { get; set; }

        /* --- Relationship --- */

        public List<UserRoom> UserRooms { get; set; }

        /* --- Relationship --- */

        public List<File> Files { get; set; }

        /* --- Relationship --- */

        public List<TagRoom> TagRooms { get; set; }

        /* --- Relationship --- */

        public List<Notification> Notifications { get; set; }

    }
}