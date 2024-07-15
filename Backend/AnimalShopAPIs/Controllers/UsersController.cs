using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using _2Sport_BE.Repository.Models;
using AnimalShopAPIs.DTOs;
using AnimalShopAPIs.Enums;
using Microsoft.AspNetCore.Authorization;
using Microsoft.IdentityModel.Tokens;
using Newtonsoft.Json;

namespace AnimalShopAPIs.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : Controller
    {
        private readonly PRM392DBContext _context;
        private readonly IConfiguration _configuration;

        public UsersController(PRM392DBContext context, IConfiguration configuration)
        {
            _context = context;
            _configuration = configuration;
        }
        // GET: Users/Details/5
        [HttpGet]
        [Route("user-details")]
        public async Task<ActionResult<UserInfoModel>> Details(int? id)
        {
            if (id == null || _context.Users == null)
            {
                return BadRequest("Invalid request");
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(m => m.UserId == id);
            if(user == null)
            {
                return NotFound();
            }
            UserInfoModel model = new UserInfoModel()
            {
                Usename = user.Username,
                FullName = user.FullName,
                Email = user.Email,
                Gender = user.Gender,
                Phone = user.Phone,
                CreateDate = user.CreateAt
            };

            return user != null ? Ok(model) : NotFound("User not found");
        }
        // GET: Users/Details/5
        [HttpPost]
        [Route("login")]
        public async Task<ActionResult<UserInfoModel>> Login([FromBody] LoginModel loginModel)
        {
            if (loginModel != null)
            {
                var user = await _context.Users
                    .Where(_ => _.Username.Equals(loginModel.Usename.Trim()) && _.Password.Equals(loginModel.Password.Trim()))
                    .FirstOrDefaultAsync();
                if (user != null)
                {
                    UserInfoModel model = new UserInfoModel()
                    {
                        Usename = user.Username,
                        FullName = user.FullName,
                        Email = user.Email,
                        Gender = user.Gender,
                        Phone = user.Phone,
                        CreateDate = user.CreateAt
                    };
                    return Ok(model);
                }
                else
                {
                    return NotFound("User not found");
                }
            }
            return BadRequest("Invalid request");
        }
        // POST: Users/Create
        [HttpPost]
        [Route("sign-up")]
        public async Task<ActionResult<UserInfoModel>> Create([FromBody] RegisterModel registerModel)
        {
            if (registerModel != null)
            {
                User user = new User()
                {
                    Username = registerModel.Usename,
                    Password = registerModel.Password,
                    Phone = registerModel.Phone,
                    FullName = registerModel.FullName,
                    Gender = registerModel.Gender,
                    Email = registerModel.Email,
                    CreateAt = DateTime.Now,
                    RoleId = (int)RolesEnum.Customer
                };
                var result =  _context.Add(user).Entity;
                await _context.SaveChangesAsync();


                UserInfoModel model = new UserInfoModel()
                {
                    Usename = user.Username,
                    FullName = user.FullName,
                    Email = user.Email,
                    Gender = user.Gender,
                    Phone = user.Phone,
                    CreateDate = user.CreateAt
                };

                return user != null ? Ok(model) : NotFound("User not found");
            }
            return BadRequest("Invalid request");
        }

        // GET: Users/Edit/5
        [HttpPost]
        [Route("update-user")]
        public async Task<ActionResult<UserInfoModel>> Edit(int? id, [FromBody] UserUpdateModel userUpdateModel)
        {
            if (id == null || _context.Users == null)
            {
                return NotFound();
            }

            var user = await _context.Users.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            user.FullName = userUpdateModel.FullName;
            user.Phone = userUpdateModel.Phone;
            user.Email = userUpdateModel.Email;
            user.Gender = userUpdateModel.Gender;
            user.CreateAt = DateTime.Now;

            _context.Update(user);
            await _context.SaveChangesAsync();


            UserInfoModel model = new UserInfoModel()
            {
                Usename = user.Username,
                FullName = user.FullName,
                Email = user.Email,
                Gender = user.Gender,
                Phone = user.Phone,
                CreateDate = user.CreateAt
            };

            return user != null ? Ok(model) : NotFound("User not found");
        }

        // POST: Users/Edit/5
        [HttpPost]
        [Route("change-password")]
        public async Task<ActionResult<UserInfoModel>> UpdatePassword(int id, [FromBody] ChangePasswordModel changePasswordModel)
        {
            if (id == null || changePasswordModel is null)
            {
                return BadRequest("Invalid request");
            }
            var user = await _context.Users
                    .Where(_ => _.Password.Equals(changePasswordModel.OldPassword))
                    .FirstOrDefaultAsync();
            try
            {
                if (user == null)
                {
                    return NotFound();
                }
                user.Password = changePasswordModel.NewPassword.Trim();
                _context.Update(user);
                await _context.SaveChangesAsync();

                UserInfoModel model = new UserInfoModel()
                {
                    Usename = user.Username,
                    FullName = user.FullName,
                    Email = user.Email,
                    Gender = user.Gender,
                    Phone = user.Phone,
                    CreateDate = user.CreateAt
                };

                return user != null ? Ok(model) : NotFound("User not found");
            }
            catch (DbUpdateConcurrencyException)
            {
                    if (!UserExists(user.UserId))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
            }
        }

        // GET: Users/Delete/5
        [HttpDelete]
        [Route("delete-user")]
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Users == null)
            {
                return NotFound();
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(m => m.UserId == id);
            if (user == null)
            {
                return NotFound();
            }
            _context.Users.Remove(user);
            await _context.SaveChangesAsync();

            return StatusCode(200, "Delete successfully");
        }
        [NonAction]
        private bool UserExists(int id)
        {
          return (_context.Users?.Any(e => e.UserId == id)).GetValueOrDefault();
        }
        [Authorize]
         // GET: api/Users/get-user-info
        [HttpGet]
        [Route("get-user-info")]
        [Authorize]
        public IActionResult GetUserInfo()
        {
            var userName = User.Identity.Name;
            var userId = User.Claims.FirstOrDefault(c => c.Type == ClaimTypes.NameIdentifier)?.Value;

            try
            {
                if (string.IsNullOrEmpty(userName) || string.IsNullOrEmpty(userId))
                {
                    return BadRequest(JsonConvert.SerializeObject("Token does not contain the required claims."));
                }

                var userInfo = _context.Users
                    .Select(x => new
                    {
                        x.UserId,
                        x.Username,
                        x.Email,
                        x.Gender,
                        x.RoleId
                    })
                    .FirstOrDefault(x => x.UserId == int.Parse(userId));
                return Ok(userInfo);
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }
        // API đăng nhập sử dụng JWT
        [HttpPost]
        [Route("login-jwt")]
        public async Task<IActionResult> LoginWithJwt([FromBody] LoginModel loginModel)
        {
            if (loginModel == null)
            {
                return BadRequest("Invalid client request");
            }

            var user = await _context.Users
                .FirstOrDefaultAsync(u => u.Username == loginModel.Usename && u.Password == loginModel.Password);

            if (user == null)
            {
                return Unauthorized();
            }

            var tokenString = GenerateJwtToken(user);
            return Ok(new { Token = tokenString });
        }

        private string GenerateJwtToken(User user)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(_configuration["Jwt:Key"]);
            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[]
                {
                    new Claim(ClaimTypes.Name, user.Username),
                    new Claim(ClaimTypes.NameIdentifier, user.UserId.ToString(),
                    new Claim(ClaimTypes.Role, user.RoleId?.ToString()).ToString())
                }),
                Expires = DateTime.UtcNow.AddHours(50),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);
        }
    }
}
