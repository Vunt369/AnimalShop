using API_PRM.RequestVM;
using Microsoft.AspNetCore.SignalR;
using System.Threading.Tasks;

namespace API_PRM.Hub
{
    public class ChatHub : Microsoft.AspNetCore.SignalR.Hub
    {
        private readonly string SERVER_METHOD_RECEIVED = "ReceiveMessage";

        public async Task JoinSpecificChatRoom(UserConnection conn)
        {
            await Groups.AddToGroupAsync(Context.ConnectionId, conn.ChatRoom);
        }
        public async Task SendMessageWithFilePathToRoom(long userId, string Username, string Mess, string ChatRoom, string FilePath)
        {
            await Clients.All.SendAsync(SERVER_METHOD_RECEIVED, userId, Username, Mess, ChatRoom, FilePath);
        }
    }
}