using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace MentorMe.Models
{
    public class UserType
    {
        public int UserTypeID { get; set; }

        [Required][StringLength(10)]
        public string Description { get; set; }

        /* --- Relationship --- */

        public List<UserRoom> UserRooms { get; set; }

    }
}