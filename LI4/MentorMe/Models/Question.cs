using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
namespace MentorMe.Models
{
    public class Question
    {
        
        public int QuestionID { get; set; }

        [Required][StringLength(100)]
        public string Title { get; set; }

        [Required][StringLength(500)]
        public string Content { get; set; }

        [Required]
        public DateTime Date { get; set; }

        /* --- Relationship --- */

        public int RoomID { get; set; }
        public Room Room { get; set; }

        /* --- Relationship --- */
        public int UserID { get; set; }
        public User User { get; set; }

        /* --- Relationship --- */
        public List<FollowerQuestion> FollowerQuestions { get; set; }

        /* --- Relationship --- */
        public List<TagQuestion> TagQuestions { get; set; }


        /* --- Relationship --- */
        public List<Answer> Answers { get; set; }

    }
}
