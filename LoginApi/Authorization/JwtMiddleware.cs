using Microsoft.Extensions.Options;
using WebApi.Helpers;
using WebApi.Services;

namespace WebApi.Authorization;

public class JwtMiddleware { 
    private readonly RequestDelegate _next;
    //private readonly AppSettings _appSettings;

    public JwtMiddleware(RequestDelegate next/* , IOptions<AppSettings> appSettings */){
        _next = next;
        //_appSettings = appSettings.Value;
    }

    public async Task Invoke(HttpContext context, IUserService userService, IJwtUtils jwtUtils){
        var token = context.Request.Headers["Authorization"].FirstOrDefault()?.Split(" ").Last();
        var userId = jwtUtils.ValidateToken(token);
        if(userId != null){
            //Asociamos el usuario al contexto en caso de que la validacion de JWT sea exitosa
            context.Items["User"] = userService.GetById(userId);
        }

        await _next(context);
    }
}