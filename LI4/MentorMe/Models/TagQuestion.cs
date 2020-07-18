using System;
namespace MentorMe.Models
{
    public class TagQuestion
    {
        public int TagQuestionID { get; set; }

        /* --- Relationship --- */

        public int TagID { get; set; }
        public Tag Tag { get;  set; }

        /* --- Relationship --- */

        public int QuestionID { get; set; }
        public Question Question { get; set; }



    }
}
