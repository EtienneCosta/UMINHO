using System;
using System.ComponentModel.DataAnnotations;
using System.Collections.Generic;


namespace MentorMe.Models
{
    public class Answer
    {
        public int AnswerID { get; set; }

        [Required][StringLength(500)]
        public string Content { get; set; }

        [Required]
        public Boolean Valid { get; set; }

        [Required]
        public string UserName { get; set; }

        [Required]
        public DateTime Date { get; set; }

        /* --- Relationship --- */
        public int QuestionID { get; set; }
        public Question Question { get; set; }

        /* --- Relationship --- */
        public int UserID { get; set; }

        public User User { get; set; }
    }
}
