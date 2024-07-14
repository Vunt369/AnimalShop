namespace AnimalShopAPIs.DTOs
{
    public class ProductDTO
    {
        public string? Pname { get; set; }
        public string? Description { get; set; }
        public decimal Price { get; set; }
        public long? Quantity { get; set; }
        public string? ImageUrl { get; set; }
    }
    public class ProductCM : ProductDTO
    {
        public int? CategoryId { get; set; }
    }
    public class ProductUM : ProductDTO
    {
        public int? CategoryId { get; set; }
    }
    public class ProductVM : ProductDTO
    {
        public int ProductId { get; set; }
        public int? CategoryId { get; set; }
        public string? CategoryName { get; set; }
    }
}
