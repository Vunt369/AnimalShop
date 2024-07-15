namespace API_PRM.RequestVM;

public class UserConnection
{
    public int UserId { get; set; }
    public string Username { get; set; } = string.Empty;
    public string ChatRoom { get; set; } = string.Empty;
    public string mess { get; set; } = string.Empty;
    public string? FilePath { get; set; } = string.Empty;
}