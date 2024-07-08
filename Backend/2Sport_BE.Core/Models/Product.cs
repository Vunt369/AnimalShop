using System;
using System.Collections.Generic;

namespace _2Sport_BE.Repository.Models
{
    public partial class Product
    {
        public Product()
        {
            CartItems = new HashSet<CartItem>();
            OrderDetails = new HashSet<OrderDetail>();
        }

        public int ProductId { get; set; }
        public string Pname { get; set; }
        public string Description { get; set; }
        public decimal Price { get; set; }
        public long? Quantity { get; set; }
        public int? CategoryId { get; set; }
        public DateTime? CreateAt { get; set; }
        public string ImageUrl { get; set; }

        public virtual Category Category { get; set; }
        public virtual ICollection<CartItem> CartItems { get; set; }
        public virtual ICollection<OrderDetail> OrderDetails { get; set; }
    }
}
