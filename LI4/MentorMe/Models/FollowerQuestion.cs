using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MentorMe.Models
{
    public class FollowerQuestion
    {
        public int FollowerQuestionID { get; set; }

        /* --- Relationship --- */

        public int FollowerID { get; set; }
        public User Follower { get; set; }

        /* --- Relationship --- */

        public int FollowingID { get; set; }
        public Question Following { get; set; }
    }
}
