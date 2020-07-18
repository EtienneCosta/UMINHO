using System;
namespace MentorMe.Models
{
    public class UserRoom
    {

        public int UserRoomID { get; set; }

        /* --- Relationship --- */

        public int UserID { get; set; }
        public User User { get; set; }

        /* --- Relationship --- */

        public int RoomID { get; set; }
        public Room Room { get; set; }

        /* --- Relationship --- */

        public int UserTypeID { get; set; }
        public UserType UserType { get; set; }




    }
}
