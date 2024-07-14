using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using _2Sport_BE.Repository.Models;
using AnimalShopAPIs.DTOs;
using AnimalShopAPIs.Enums;
using AnimalShopAPIs.Services;
using _2Sport_BE.DataContent;
using AnimalShopAPIs.ViewModels;

namespace AnimalShopAPIs.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class OrdersController : ControllerBase
    {
        private readonly PRM392DBContext _context;
        private readonly IPaymentService _paymentService;

        public OrdersController(PRM392DBContext context, IPaymentService paymentService)
        {
            _context = context;
            _paymentService = paymentService;
        }

        // GET: api/Orders
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Order>>> GetOrders()
        {
          if (_context.Orders == null)
          {
              return NotFound();
          }
            return await _context.Orders.ToListAsync();
        }

        // GET: api/Orders/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Order>> GetOrder(int id)
        {
          if (_context.Orders == null)
          {
              return NotFound();
          }
            var order = await _context.Orders.FindAsync(id);

            if (order == null)
            {
                return NotFound();
            }

            return order;
        }
        [HttpGet("history-orders")]
        public async Task<ActionResult<List<Order>>> GetHistoryOrders(int userId)
        {
            if (_context.Orders == null)
            {
                return NotFound();
            }
            var order = await _context.Orders.Where(_ => _.UserId == userId).ToListAsync();

            if (order == null)
            {
                return NotFound();
            }

            return order;
        }
        // PUT: api/Orders/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutOrder(int id, Order order)
        {
            if (id != order.OrderId)
            {
                return BadRequest();
            }

            _context.Entry(order).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!OrderExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Orders
        [HttpPost]
        public async Task<ActionResult<Order>> PostOrder(OrderCM orderCM)
        {
          if (_context.Orders == null)
          {
              return Problem("Entity set 'PRM392DBContext.Orders'  is null.");
          }
          if(orderCM == null && orderCM.OrderDetailList is null && orderCM.shipmentDetailDTO is null)
          {
                return BadRequest("Request invalid");
          }

            var result = await CreateOrderAsync(orderCM);
            ResponseModel<string> responseModel = new ResponseModel<string>();
            if (result.Length > 0)
            {
                
                responseModel.Data = result;
                responseModel.IsSuccess = true;
                responseModel.Message = "Đơn hàng đã được tạo thành công.";

                return Ok(responseModel);
            }
            else
            {
                responseModel.Data = string.Empty;
                responseModel.IsSuccess = false;
                responseModel.Message = "Tạo đơn hàng thất bại.";
                return BadRequest(responseModel);
            }
        }
        [NonAction]
        private bool OrderExists(int id)
        {
            return (_context.Orders?.Any(e => e.OrderId == id)).GetValueOrDefault();
        }
        [NonAction]
        public async Task<string> CreateOrderAsync(OrderCM orderDTO)
        {
            // Step 1: Create Order without ShipmentDetailId
            var order = new Order
            {
                UserId = orderDTO.UserId,
                IntoMoney = orderDTO.IntoMoney,
                TotalPrice = orderDTO.TotalPrice,
                OrderCode = GenerateOrderCode(),
                TranSportFee = orderDTO.TranSportFee,
                CreateAt = DateTime.UtcNow,
                Status = (int) OrderStatusEnum.PENDING
            };

            _context.Orders.Add(order);
            await _context.SaveChangesAsync(); 

            var shipmentDetail = new ShipmentDetail
            {
                FullName = orderDTO.shipmentDetailDTO.FullName,
                Address = orderDTO.shipmentDetailDTO.Address,
                PhoneNumber = orderDTO.shipmentDetailDTO.PhoneNumber,
                UserId = (int)orderDTO.UserId,
                OrderId = order.OrderId 
            };

            _context.ShipmentDetails.Add(shipmentDetail);
            await _context.SaveChangesAsync(); 

            
            order.ShipmentDetailId = shipmentDetail.Id;
            _context.Orders.Update(order);
            await _context.SaveChangesAsync();

            // Step 4: Create OrderDetails
            if (orderDTO.OrderDetailList != null && orderDTO.OrderDetailList.Any())
            {
                foreach (var detailDTO in orderDTO.OrderDetailList)
                {
                    var orderDetail = new OrderDetail
                    {
                        OrderId = order.OrderId,
                        ProductId = detailDTO.ProductId,
                        Quantity = detailDTO.Quantity,
                        UnitPrice = detailDTO.UnitPrice
                    };
                    _context.OrderDetails.Add(orderDetail);
                }
            }

            int result = await _context.SaveChangesAsync();
            if(result > 0)
            {
                Order data = await GetOrderByIdAsync(order.OrderId);
                string paymentLink = await _paymentService.PaymentWithPayOs(data);
                return paymentLink;
            }
            return string.Empty;
        }
        [HttpGet("cancel")]
        public async Task<IActionResult> HandleCancel([FromQuery] PaymentResponse paymentResponse)
        {
            if (!ModelState.IsValid || AreAnyStringsNullOrEmpty(paymentResponse))
            {
                return BadRequest(new ResponseModel<object>
                {
                    IsSuccess = false,
                    Message = "Invalid request data.",
                    Data = null
                });
            }
            Order order = await GetOrderByCodeAsync(paymentResponse.orderCode);
            if (order == null)
            {
                return NotFound(new ResponseModel<object>
                {
                    IsSuccess = false,
                    Message = "Order not found.",
                    Data = null
                });
            }
            // Cập nhật trạng thái Order thành "Cancelled"
            order.Status = (int)OrderStatusEnum.CANCELLED;
            int result = await _context.SaveChangesAsync();
            if (result <= 0)
            {
                return StatusCode(500, new ResponseModel<object>
                {
                    IsSuccess = false,
                    Message = "Update status thất bại.",
                    Data = null
                });
            }
            // Tạo và trả về Response
            OrderVM orderVM = new OrderVM
            {
                id = order.OrderId,
                IntoMoney = order.IntoMoney,
                Status = order.Status,
                ShipmentDetailId = order.ShipmentDetailId,
                TranSportFee = order.TranSportFee,
                PaymentMethod = "PayOs",
                OrderDetailList = order.OrderDetails.Select(item => new OrderDetailDTO
                {
                    ProductId = item.ProductId,
                    UnitPrice = (decimal)item.UnitPrice,
                    Quantity = item.Quantity
                }).ToList()
            };

            return Ok(new ResponseModel<OrderVM>
            {
                IsSuccess = true,
                Message = "Hóa đơn được hủy thành công.",
                Data = orderVM
            });
        }
        [HttpGet("return")]
        public async Task<IActionResult> HandleReturn([FromQuery] PaymentResponse paymentResponse)
        {
            if (!ModelState.IsValid || AreAnyStringsNullOrEmpty(paymentResponse))
            {
                return BadRequest(new ResponseModel<object>
                {
                    IsSuccess = false,
                    Message = "Invalid request data.",
                    Data = null
                });
            }
            Order order = await GetOrderByCodeAsync(paymentResponse.orderCode);
            if (order == null)
            {
                return NotFound(new ResponseModel<object>
                {
                    IsSuccess = false,
                    Message = "Order not found.",
                    Data = null
                });
            }
            order.Status = (int)OrderStatusEnum.PAID;

            foreach (var orderDetail in order.OrderDetails)
            {
                var updatedProduct = _context.Products.FirstOrDefault(p => p.ProductId == orderDetail.ProductId);
                if (updatedProduct != null && updatedProduct.Quantity > 0)
                {
                    updatedProduct.Quantity = updatedProduct.Quantity - orderDetail.Quantity;
                }
                else
                {
                    return StatusCode(500, new ResponseModel<object>
                    {
                        IsSuccess = false,
                        Message = "Số lượng không đủ.",
                        Data = null
                    });
                }
            }
            int result = await _context.SaveChangesAsync();
            if (result <= 0)
            {
                return StatusCode(500, new ResponseModel<object>
                {
                    IsSuccess = false,
                    Message = "Update status thất bại.",
                    Data = null
                });
            }
            // Tạo và trả về Response
            OrderVM orderVM = new OrderVM
            {
                id = order.OrderId,
                IntoMoney = order.IntoMoney,
                Status = order.Status,
                ShipmentDetailId = order.ShipmentDetailId,
                TranSportFee = order.TranSportFee,
                PaymentMethod = "PayOs",
                OrderDetailList = order.OrderDetails.Select(item => new OrderDetailDTO
                {
                    ProductId = item.ProductId,
                    UnitPrice = (decimal)item.UnitPrice,
                    Quantity = item.Quantity
                }).ToList()
            };

            return Ok(new ResponseModel<OrderVM>
            {
                IsSuccess = true,
                Message = "Hóa đơn được thanh toán thành công.",
                Data = orderVM
            });
        }
        [NonAction]
        public string GenerateOrderCode()
        {
            Random random = new Random();
            return random.Next(100000, 1000000).ToString();
        }
        [NonAction]
        public async Task<Order> GetOrderByIdAsync(int orderId)
        {
            return await _context.Orders
                .Include(o => o.User)
                .Include(o => o.OrderDetails)
                .ThenInclude(od => od.Product)
                .Include(o => o.ShipmentDetails)
                .FirstOrDefaultAsync(o => o.OrderId == orderId);
        }
        [NonAction]
        public async Task<Order> GetOrderByCodeAsync(string code)
        {
            return await _context.Orders
                .Include(o => o.User)
                .Include(o => o.OrderDetails)
                .ThenInclude(od => od.Product)
                .Include(o => o.ShipmentDetails)
                .FirstOrDefaultAsync(o => o.OrderCode == code);
        }
        [NonAction]
        public bool AreAnyStringsNullOrEmpty(PaymentResponse response)
        {
            return string.IsNullOrEmpty(response.status) ||
                   string.IsNullOrEmpty(response.code) ||
                   string.IsNullOrEmpty(response.id) ||
                   string.IsNullOrEmpty(response.orderCode);
        }
    }
}
