using System.Linq;
using MentorMe.Models;
using Microsoft.EntityFrameworkCore;

namespace MentorMe.Data
{
    public class MentorMeContext : DbContext
    {
        public MentorMeContext(DbContextOptions<MentorMeContext> options)
                : base(options)
        {
        }

        public DbSet<Answer> Answers { get; set; }
        public DbSet<File> Files { get; set; }
        public DbSet<Notification> Notifications { get; set; }
        public DbSet<NotificationUser> NotificationUsers { get; set; }
        public DbSet<Question> Questions { get; set; }
        public DbSet<Room> Rooms { get; set; }
        public DbSet<Tag> Tags { get; set; }
        public DbSet<TagQuestion> TagQuestions { get; set; }
        public DbSet<TagRoom> TagRooms { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<UserRoom> UserRooms { get; set; }
        public DbSet<UserType> UserTypes { get; set; }
        public DbSet<FollowerQuestion> FollowerQuestions { get; set; }

        protected override void OnModelCreating(ModelBuilder modelbuilder)
        {
            foreach (var relationship in modelbuilder.Model.GetEntityTypes().SelectMany(e => e.GetForeignKeys()))
            {
                relationship.DeleteBehavior = DeleteBehavior.Restrict;
            }

            base.OnModelCreating(modelbuilder);

            /* ------------------- Users ------------------- */

            // Many-to-Many : User-Room

            modelbuilder.Entity<UserRoom>().HasKey(u => new { u.UserRoomID });           

            modelbuilder.Entity<UserRoom>().HasOne(ur => ur.User)
                                           .WithMany(u => u.UserRooms)
                                           .HasForeignKey(ur => ur.UserID);

            modelbuilder.Entity<UserRoom>().HasOne(ur => ur.Room)
                                           .WithMany(r => r.UserRooms)
                                           .HasForeignKey(ur => ur.RoomID);

            // Many-to-Many : User-Notification

            modelbuilder.Entity<NotificationUser>().HasKey(u => new { u.NotificationUserID });

            modelbuilder.Entity<NotificationUser>().HasOne(nu => nu.Notification)
                                                   .WithMany(n => n.NotificationUsers)
                                                   .HasForeignKey(nu => nu.NotificationID);

            modelbuilder.Entity<NotificationUser>().HasOne(nu => nu.User)
                                                   .WithMany(u => u.NotificationUsers)
                                                   .HasForeignKey(nu => nu.UserID);

            // One-to-Many : User - Question

            modelbuilder.Entity<User>().HasMany(u => u.Questions)
                                       .WithOne(q => q.User);

            // One-to-Many : User - Answers
            modelbuilder.Entity<User>().HasMany(u => u.Answers)
                                       .WithOne(a => a.User);

            /* ------------------- Room ------------------- */

            // One-to-Many : Room - File

            modelbuilder.Entity<Room>().HasMany(r => r.Files)
                                       .WithOne(f => f.Room);


            // One-to-Many : Room - Notification

            modelbuilder.Entity<Room>().HasMany(r => r.Notifications)
                                       .WithOne(n => n.Room);

            // One-to-Many : Room - Question

            modelbuilder.Entity<Room>().HasMany(r => r.Questions)
                                       .WithOne(n => n.Room);

            // Many-to-Many : Room - Tag


            modelbuilder.Entity<TagRoom>().HasKey(tr => new { tr.TagRoomID });


            modelbuilder.Entity<TagRoom>().HasOne(tr => tr.Tag)
                                          .WithMany(t => t.TagRooms)
                                          .HasForeignKey(tr => tr.TagID);

            modelbuilder.Entity<TagRoom>().HasOne(tr => tr.Room)
                                          .WithMany(r => r.TagRooms)
                                          .HasForeignKey(tr => tr.RoomID);


            /* ------------------- TAG ------------------- */

            // Many-to-Many : Tag - Question

            modelbuilder.Entity<TagQuestion>().HasKey(tq => new {tq.TagQuestionID});


            modelbuilder.Entity<TagQuestion>().HasOne(tq => tq.Tag)
                                              .WithMany(t => t.TagQuestions)
                                              .HasForeignKey(tq => tq.TagID);

            modelbuilder.Entity<TagQuestion>().HasOne(tq => tq.Question)
                                              .WithMany(q => q.TagQuestions)
                                              .HasForeignKey(tq => tq.QuestionID);


            /* ------------------- Question ------------------- */

            // One-to-Many : Question - Answer

            modelbuilder.Entity<Question>().HasMany(q => q.Answers)
                                    .WithOne(a => a.Question);

            // Many-to-Many : Question - Users (followers)
            modelbuilder.Entity<FollowerQuestion>().HasKey(fq => new { fq.FollowerQuestionID });


            modelbuilder.Entity<FollowerQuestion>().HasOne(fq => fq.Follower)
                                              .WithMany(u => u.FollowerQuestions)
                                              .HasForeignKey(fq => fq.FollowerID);

            modelbuilder.Entity<FollowerQuestion>().HasOne(fq => fq.Following)
                                              .WithMany(q => q.FollowerQuestions)
                                              .HasForeignKey(fq => fq.FollowingID);

            /* ------------------- UserType ------------------- */


            // One-to-Many : UserType - UserRoom

            modelbuilder.Entity<UserType>().HasMany(ut => ut.UserRooms)
                                           .WithOne(ur => ur.UserType);




            /*

            modelbuilder.Entity<Answer>().ToTable("Answer");
            modelbuilder.Entity<File>().ToTable("File");
            modelbuilder.Entity<Notification>().ToTable("Notification");
            modelbuilder.Entity<NotificationUser>().ToTable("NotificationUser");
            modelbuilder.Entity<Question>().ToTable("Question");
            modelbuilder.Entity<Room>().ToTable("Room");
            modelbuilder.Entity<Tag>().ToTable("Tag");
            modelbuilder.Entity<TagQuestion>().ToTable("TagRoom");
            modelbuilder.Entity<User>().ToTable("User");
            modelbuilder.Entity<UserRoom>().ToTable("UserRoom");
            modelbuilder.Entity<UserType>().ToTable("UserType");
            modelbuilder.Entity<Answer>().ToTable("Answer");
            */
        }



    }
}
 