﻿namespace AnimalShopAPIs.DTOs
{
    public class OrderDetailDTO
    {
        public int? ProductId { get; set; }
        public int? Quantity { get; set; }
        public decimal? UnitPrice { get; set; }
    }
}
