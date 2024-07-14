using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using _2Sport_BE.Repository.Models;
using AnimalShopAPIs.DTOs;
using static Microsoft.Extensions.Logging.EventSource.LoggingEventSource;

namespace AnimalShopAPIs.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductsController : ControllerBase
    {
        private readonly PRM392DBContext _context;

        public ProductsController(PRM392DBContext context)
        {
            _context = context;
        }

        // GET: api/Products
        [HttpGet]
        [Route("get-all-product")]
        public async Task<ActionResult<List<ProductVM>>> GetProducts(int? categoryId)
        {
            if (_context.Products == null)
            {
                return NotFound();
            }
            var query = _context.Products.Include("Category").AsQueryable();
            if(categoryId > 0 && categoryId is not null)
            {
                query = query.Where(x => x.CategoryId == (int)categoryId);
            }
            List<ProductVM> result = query.Select(p => new ProductVM()
            {
                Pname = p.Pname,
                Description = p.Description,
                ImageUrl = p.ImageUrl,
                Price = p.Price,
                Quantity = p.Quantity,
                CategoryId = p.CategoryId,
                ProductId = p.ProductId,
                CategoryName = p.Category.Cname,
            }).ToList();
            return Ok(result);
        }
        [HttpGet]
        [Route("search-products")]
        public async Task<ActionResult<List<ProductVM>>> SearchProducts(string keywords)
        {
            if (_context.Products == null)
            {
                return NotFound();
            }
            var query = _context.Products.Include("Category").AsQueryable();
            if (keywords is not null)
            {
                query = query.Where(x => x.Pname.ToLower().Contains(keywords.ToLower()));
            }
            List<ProductVM> result = query.Select(p => new ProductVM()
            {
                Pname = p.Pname,
                Description = p.Description,
                ImageUrl = p.ImageUrl,
                Price = p.Price,
                Quantity = p.Quantity,
                CategoryId = p.CategoryId,
                ProductId = p.ProductId,
                CategoryName = p.Category.Cname,
            }).ToList();
            return Ok(result);
        }

        // GET: api/Products/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ProductVM>> GetProductById(int id)
        {
          if (_context.Products == null)
          {
              return NotFound();
          }
            var p = await _context.Products.Include("Category").FirstOrDefaultAsync(_ => _.ProductId == id);

            if (p == null)
            {
                return NotFound();
            }
            var result = new ProductVM()
            {
                Pname = p.Pname,
                Description = p.Description,
                ImageUrl = p.ImageUrl,
                Price = p.Price,
                Quantity = p.Quantity,
                CategoryId = p.CategoryId,
                ProductId = p.ProductId,
                CategoryName = p.Category.Cname,
            };
            return Ok(result);
        }

        // PUT: api/Products/5
        [HttpPut("{id}")]
        public async Task<ActionResult<ProductVM>> PutProduct(int id, ProductUM productUM)
        {
            
            var product = await _context.Products.Include("Category").FirstOrDefaultAsync(_ => _.ProductId == id);
            if (product == null)
            {
                return NotFound();
            }
            product.Pname = productUM.Pname;
            product.Description = productUM.Description;
            product.ImageUrl = productUM.ImageUrl;
            product.Price = productUM.Price;
            product.Quantity = productUM.Quantity;
            product.CategoryId = productUM.CategoryId;
            _context.Entry(product).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
                var result = new ProductVM()
                {
                    Pname = product.Pname,
                    Description = product.Description,
                    ImageUrl = product.ImageUrl,
                    Price = product.Price,
                    Quantity = product.Quantity,
                    CategoryId = product.CategoryId,
                    ProductId = product.ProductId,
                    CategoryName = product.Category.Cname,
                };

                return Ok(result);
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ProductExists(id))
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

        // POST: api/Products
        [HttpPost]
        public async Task<ActionResult<Product>> PostProduct(ProductCM productCM)
        {
          if (_context.Products == null)
          {
              return Problem("Entity set 'PRM392DBContext.Products'  is null.");
          }
          if(productCM != null)
            {
                Product product = new Product()
                {
                    Pname = productCM.Pname,
                    Description = productCM.Description,
                    ImageUrl = productCM.ImageUrl,
                    Price = productCM.Price,
                    CreateAt = DateTime.Now,
                    Quantity = productCM.Quantity,
                    CategoryId = productCM.CategoryId
                };
                _context.Products.Add(product);
                await _context.SaveChangesAsync();
                return Ok(product);
            }
            else
            {
                return NoContent();
            }    
        }

        // DELETE: api/Products/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteProduct(int id)
        {
            if (_context.Products == null)
            {
                return NotFound();
            }
            var product = await _context.Products.FindAsync(id);
            if (product == null)
            {
                return NotFound();
            }

            _context.Products.Remove(product);
            await _context.SaveChangesAsync();

            return Ok("Delete successfully");
        }
        [NonAction]
        private bool ProductExists(int id)
        {
            return (_context.Products?.Any(e => e.ProductId == id)).GetValueOrDefault();
        }
    }
}
