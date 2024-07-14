﻿using System;
using System.Collections.Generic;

namespace _2Sport_BE.Repository.Models
{
    public partial class Role
    {
        public Role()
        {
            Users = new HashSet<User>();
        }

        public int RoleId { get; set; }
        public string Rname { get; set; }
        public string Description { get; set; }
        public DateTime? CreateAt { get; set; }

        public virtual ICollection<User> Users { get; set; }
    }
}
