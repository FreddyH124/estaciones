using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using WebApi.Authorization;
using WebApi.Entities;
using WebApi.Helpers;
using WebApi.Models.Responses;
using WebApi.Services;

namespace WebApi.Controllers;

[Authorize]
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

    [Authorize(Role.Gestor)]
    [HttpGet("signup-token/{id}")]
    public IActionResult GetSignUpCode(string id){
        return Ok( _signUpCodeService.GenerateSignUpCode(id));
    }

    [Authorize(Role.Gestor)]
    [HttpGet]
    public IActionResult GetAllUsers(){
        return Ok(_userService.GetAll());
    }

    [Authorize(Role.Gestor)]
    [HttpDelete("{id}")]
    public IActionResult DeleteUser(string id){
        _userService.Unsubscribe(id);
        return Ok(new { message = $"User {id} deleted" });
    }

    [AllowAnonymous]
    [HttpPost("signup")]
    public IActionResult SignUpUser(SignUpRequest model){
        _userService.UserSignUp(model);
        return Ok(new { message = "Registration successful" });
    }

    [AllowAnonymous]
    [HttpPost("signin")]
    public IActionResult SignInUser(SignInRequest model){
        SignInResponse response = _userService.UserSignIn(model);
        return Ok(response);
    }
}