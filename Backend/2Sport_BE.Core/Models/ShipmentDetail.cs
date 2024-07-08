using System;
using System.Collections.Generic;

namespace _2Sport_BE.Repository.Models
{
    public partial class ShipmentDetail
    {
        public int Id { get; set; }
        public string FullName { get; set; }
        public string Address { get; set; }
        public string PhoneNumber { get; set; }
        public int UserId { get; set; }
        public int? OrderId { get; set; }

        public virtual Order Order { get; set; }
        public virtual User User { get; set; }
    }
}
