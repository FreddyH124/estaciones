using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using WebApi.Authorization;
using WebApi.Entities;
using WebApi.Helpers;
using WebApi.Models.Responses;
using WebApi.Services;

namespace WebApi.Controllers;

[ApiController]
[Route("[controller]")]
public class UsersController : ControllerBase{
    private IUserService _userService;
    private ISignUpCodeService _signUpCodeService;
    private readonly AppSettings _appSettings;

    public UsersController(
        IUserService userService,
        ISignUpCodeService signUpCodeService,
        IOptions<AppSettings> appSettings)
    {
        _userService = userService;
        _signUpCodeService = signUpCodeService;
        _appSettings = appSettings.Value;
    }

    [HttpGet("signup-token/{id}")]
    public IActionResult GetSignUpCode(string id){
        return Ok( _signUpCodeService.GenerateSignUpCode(id));
    }

    [HttpGet]
    public IActionResult GetAllUsers(){
        return Ok(_userService.GetAll());
    }

    [HttpDelete("{id}")]
    public IActionResult DeleteUser(string id){
        _userService.Unsubscribe(id);
        return Ok(new { message = $"User {id} deleted" });
    }

    [HttpPost("signup")]
    public IActionResult SignUpUser(SignUpRequest model){
        _userService.UserSignUp(model);
        return Ok(new { message = "Registration successful" });
    }


    [HttpPost("verify")]
    public IActionResult VerifyUser(SignInRequest model){
        IDictionary<string,object> response = _userService.VerifyUser(model);
        return Ok(response);
    }
}