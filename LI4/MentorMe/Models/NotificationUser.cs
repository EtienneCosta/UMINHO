using System;
namespace MentorMe.Models
{
    public class NotificationUser
    {

        public int NotificationUserID { get; set; }

        public int Visible { get; set; }

        /* --- Relationship --- */

        public int NotificationID { get; set; }
        public Notification Notification { get; set; }

        /* --- Relationship --- */

        public int UserID { get; set; }
        public User User { get; set; }

    }
}