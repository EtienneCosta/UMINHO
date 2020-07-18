using System;
using System.ComponentModel.DataAnnotations;

namespace MentorMe.Models
{
    public class File
    {

        public int FileID { get; set; }

        [Required][StringLength(300)]
        public string Title { get; set; }

        [Required]
        public DateTime Date { get; set; }

        [Required]
        public string Path { get; set; }


        /* --- Relationship --- */
        public int RoomID { get; set; }
        public Room Room { get; set; }
    }
}