using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace MentorMe.Models
{
    public class Tag
    {
        public int TagID { get; set; }

        [Required][StringLength(45)]
        public string Name { get; set; }

        /* --- Relationship --- */

        public List<TagRoom> TagRooms { get; set; }

        /* --- Relationship --- */

        public List<TagQuestion> TagQuestions { get; set; }

    }
}
