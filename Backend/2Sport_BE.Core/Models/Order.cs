using System;
using System.Collections.Generic;

namespace _2Sport_BE.Repository.Models
{
    public partial class Order
    {
        public Order()
        {
            OrderDetails = new HashSet<OrderDetail>();
            ShipmentDetails = new HashSet<ShipmentDetail>();
        }

        public int OrderId { get; set; }
        public int? UserId { get; set; }
        public decimal? IntoMoney { get; set; }
        public decimal? TotalPrice { get; set; }
        public string OrderCode { get; set; }
        public int? ShipmentDetailId { get; set; }
        public int? Status { get; set; }
        public decimal? TranSportFee { get; set; }
        public DateTime? CreateAt { get; set; }

        public virtual User User { get; set; }
        public virtual ICollection<OrderDetail> OrderDetails { get; set; }
        public virtual ICollection<ShipmentDetail> ShipmentDetails { get; set; }
    }
}
