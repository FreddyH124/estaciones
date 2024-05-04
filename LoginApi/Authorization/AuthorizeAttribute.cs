using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using WebApi.Entities;

namespace WebApi.Authorization;

[AttributeUsage(AttributeTargets.Class | AttributeTargets.Method)]
public class AuthorizeAttribute : Attribute, IAuthorizationFilter{

    private readonly IList<Role> _roles;

    public AuthorizeAttribute(params Role[] roles){
        _roles = roles ?? new Role[] {};
    }
    public void OnAuthorization(AuthorizationFilterContext context){

        var allowAnonymus = context.ActionDescriptor.EndpointMetadata.OfType<AllowAnonymousAttribute>().Any();
        if(allowAnonymus)
            return;

        var user = (User)context.HttpContext.Items["User"];
        if(user == null || (_roles.Any() && !_roles.Contains(user.Role)))
            context.Result = new JsonResult(new { message = "Unauthorized" }) { StatusCode = StatusCodes.Status401Unauthorized };
        
    }
}