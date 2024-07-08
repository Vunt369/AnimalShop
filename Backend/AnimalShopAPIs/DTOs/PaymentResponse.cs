namespace AnimalShopAPIs.ViewModels
{
    public class PaymentResponse
    {
        public string? code { get; set; }
        public string? id { get; set; }
        public bool cancel { get; set; }
        public string? status { get; set; }
        public string? orderCode { get; set; }
    }
}
