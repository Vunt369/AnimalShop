using _2Sport_BE.Repository.Models;
using Net.payOS;
using Net.payOS.Types;

namespace AnimalShopAPIs.Services
{
    public class PayOSSettings
    {
        public string ClientId { get; set; }
        public string ApiKey { get; set; }
        public string ChecksumKey { get; set; }
    }
    public interface IPaymentService
    {
        //PAYOS
        Task<string> PaymentWithPayOs(Order order);
        Task<PaymentLinkInformation> CancelPaymentLink(int orderId, string reason);
        Task<PaymentLinkInformation> GetPaymentLinkInformationAsync(int orderId);
    }
    public class PaymentService : IPaymentService
    {
        private PayOS _payOs;
        private readonly IConfiguration _configuration;
        private PayOSSettings payOSSettings;
        private PRM392DBContext _context;
        public PaymentService(IConfiguration configuration, PRM392DBContext context)
        {
            _configuration = configuration;
            payOSSettings = new PayOSSettings()
            {
                ClientId = _configuration["PayOSSettings:ClientId"],
                ApiKey = _configuration["PayOSSettings:ApiKey"],
                ChecksumKey = _configuration["PayOSSettings:ChecksumKey"]
            };
            _payOs = new PayOS(payOSSettings.ClientId, payOSSettings.ApiKey, payOSSettings.ChecksumKey);
            _context = context;
        }

        public async Task<string> PaymentWithPayOs(Order order)
        {
            if (order != null)
            {
                Order check = order;
                List<ItemData> orders = new List<ItemData>();
                var listOrderDetail = order.OrderDetails.ToList();
                var user = _context.Users.Where(u => u.UserId == (int)order.UserId).FirstOrDefault();
                var shipmentDetail = order.ShipmentDetails.FirstOrDefault();
                for (int i = 0; i < listOrderDetail.Count; i++)
                {
                    var product =  _context.Products.Where(p => p.ProductId == (int)listOrderDetail[i].ProductId).FirstOrDefault();
                    var name = product.Pname;
                    var soluong = listOrderDetail[i].Quantity ?? 0;
                    var thanhtien = Convert.ToInt32(listOrderDetail[i].UnitPrice.ToString());
                    ItemData item = new ItemData(name, soluong, thanhtien);
                    orders.Add(item);
                }
                if (order.TranSportFee > 0)
                {
                    ItemData item = new ItemData("Chi phi van chuyen", 1, (int)order.TranSportFee);
                    orders.Add(item);
                }
                string content = $"Thanh toan hoa don {order.OrderCode}";
                int expiredAt = (int)(DateTimeOffset.UtcNow.ToUnixTimeSeconds() + (60 * 5));
                PaymentData data = new PaymentData(Int32.Parse(order.OrderCode), Int32.Parse(order.TotalPrice.ToString()), content, orders, "https://localhost:7085/api/Orders/cancel", "https://localhost:7085/api/Orders/return", null, user.FullName, user.Email, user.Phone, shipmentDetail.Address, expiredAt);
                var createPayment = await _payOs.createPaymentLink(data);
                return createPayment.checkoutUrl;
            }
            return String.Empty;
        }
        public async Task<PaymentLinkInformation> CancelPaymentLink(int orderId, string reason)
        {
            PaymentLinkInformation cancelledPaymentLinkInfo = await _payOs.cancelPaymentLink(orderId, reason);
            return cancelledPaymentLinkInfo;
        }
        public async Task<PaymentLinkInformation> GetPaymentLinkInformationAsync(int orderId)
        {

            PaymentLinkInformation paymentLinkInformation = await _payOs.getPaymentLinkInformation(orderId);

            return paymentLinkInformation;
        }
    }
}
