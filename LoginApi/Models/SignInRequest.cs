using System.ComponentModel.DataAnnotations;

namespace WebApi.Models.Responses;

public class SignInRequest{
    [Required]
    public string Username { get; set; }

    [Required]
    public string Password { get; set; }
}