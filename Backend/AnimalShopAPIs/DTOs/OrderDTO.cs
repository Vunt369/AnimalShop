namespace AnimalShopAPIs.DTOs
{
    public class OrderDTO
    {
        public int? UserId { get; set; }
        public decimal? IntoMoney { get; set; }
        public decimal? TotalPrice { get; set; }
        public decimal? TranSportFee { get; set; }

        public ShipmentDetailDTO shipmentDetailDTO { get; set; }

        public List<OrderDetailDTO> OrderDetailList { get; set; }

    }

    public class OrderCM : OrderDTO
    {

    }
    public class OrderUM : OrderDTO
    {

    }
    public class OrderVM : OrderDTO
    {
        public int? id { get; set; }
        public int? Status { get; set; }
        public decimal? IntoMoney { get; set; }
        public string? PaymentMethod { get; set; }
        public int? ShipmentDetailId { get; set; }
        public string? PaymentLink { get; set; }
    }
}
