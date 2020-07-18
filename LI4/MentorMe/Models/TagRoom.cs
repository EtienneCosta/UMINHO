using System;
namespace MentorMe.Models
{
    public class TagRoom
    {
        public int TagRoomID { get; set; }

        /* --- Relationship --- */

        public int RoomID { get; set; }
        public Room Room { get; set; }

        /* --- Relationship --- */

        public int TagID { get; set; }
        public Tag Tag { get; set; }

    }
}