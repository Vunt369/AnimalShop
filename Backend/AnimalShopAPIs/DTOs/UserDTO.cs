namespace AnimalShopAPIs.DTOs
{
    public class UserDTO
    {
        public string? Usename { get; set; }
        public string? Password { get; set; }

    }
    public class ChangePasswordModel
    {
        public string? OldPassword { get; set; }
        public string? NewPassword {  get; set; }
    }
    public class LoginModel : UserDTO
    {
        
    }
    public class RegisterModel : UserDTO 
    {
        public string? FullName { get; set; }
        public string? Email { get; set; }
        public string? Phone { get; set; }
        public string? Gender { get; set; }
    }
    public class UserUpdateModel
    {
        public string? FullName { get; set; }
        public string? Email { get; set; }
        public string? Phone { get; set; }
        public string? Gender { get; set; }
    }
    public class UserInfoModel
    {
        public string? Usename { get; set; }
        public string? FullName { get; set; }
        public string? Email { get; set; }
        public string? Phone { get; set; }
        public string? Gender { get; set; }
        public DateTime? CreateDate { get; set; }
    }

}
