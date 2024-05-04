using System.Net;
using System.Text.Json;

namespace WebApi.Helpers;

public class ErrorHandlerMiddleware{
    private readonly RequestDelegate _next;

    public ErrorHandlerMiddleware(RequestDelegate next){
        _next = next;
    }

    public async Task Invoke(HttpContext context){
        try{
            await _next(context);
        }
        catch(Exception error){
            var response = context.Response;
            response.ContentType = "application/json";
            
            switch(error){
                case AppException e :
                    //Excepcion creada para manejar los errores de la aplicacion
                    response.StatusCode = (int)HttpStatusCode.BadRequest;
                    break;
                case KeyNotFoundException e:
                    //Cuando no se encuentra lo requerido en el API
                    response.StatusCode = (int)HttpStatusCode.NotFound;
                    break;
                default:
                    //Errores sin manejar
                    response.StatusCode = (int)HttpStatusCode.InternalServerError;
                    break;
            }

            var result = JsonSerializer.Serialize(new { message = error?.Message });
            await response.WriteAsync(result);
        }
    }
}