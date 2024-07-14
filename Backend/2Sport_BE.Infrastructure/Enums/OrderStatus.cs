using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace _2Sport_BE.Service.Enums
{
    public enum OrderStatus : int
    {
        Canceled = 0,
        Order_Confirmation = 1,
        Order_Placed = 2,
        In_Transit = 3,
        Delivered = 4,
        Delayed = 5
    }
}
