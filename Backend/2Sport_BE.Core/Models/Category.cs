using System;
using System.Collections.Generic;

namespace _2Sport_BE.Repository.Models
{
    public partial class Category
    {
        public Category()
        {
            Products = new HashSet<Product>();
        }

        public int CategoryId { get; set; }
        public string Cname { get; set; }
        public string Description { get; set; }
        public DateTime? CreateAt { get; set; }

        public virtual ICollection<Product> Products { get; set; }
    }
}
