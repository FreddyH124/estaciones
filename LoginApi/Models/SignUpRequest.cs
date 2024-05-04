using System.ComponentModel.DataAnnotations;
using WebApi.Entities;

namespace WebApi.Models.Responses;

public class SignUpRequest{
    [Required]
    public string FirstName { get; set; }

    [Required]
    public string LastName { get; set; }

    [Required]
    public string Username { get; set; }

    [Required]
    public string Password { get; set; }

    [Required]
    public Role Role { get; set; }
    [Required]
    public string UserIdForToken { get; set; }

    [Required]
    public string Token { get; set; }

}