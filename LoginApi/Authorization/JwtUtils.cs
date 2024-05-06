using WebApi.Entities;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Text;
using WebApi.Helpers;
using Microsoft.Extensions.Options;
using System.Security.Claims;

namespace WebApi.Authorization;

public interface IJwtUtils{
    public string GenerateToken(IDictionary<string,object> claims);
    public string GenerateSignUpCodeToken(string user);
    public string? ValidateToken(string token);
}

public class JwtUtils : IJwtUtils{

    private readonly AppSettings _appSettings;

    public JwtUtils(IOptions<AppSettings> appSettings){
        _appSettings = appSettings.Value;
    }

    public string GenerateToken(IDictionary<string,object> claims){
        var tokenHandler = new JwtSecurityTokenHandler();
        var key = Encoding.ASCII.GetBytes(_appSettings.Secret);

        var claimList = new List<Claim>();
        foreach(var claim in claims){
            claimList.Add(new Claim(claim.Key, claim.Value.ToString()));
        }

        var tokenDescriptor = new SecurityTokenDescriptor {
            Subject = new ClaimsIdentity(claimList),
            Expires = DateTime.UtcNow.AddDays(2),
            SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
        };

        var token = tokenHandler.CreateToken(tokenDescriptor);
        return tokenHandler.WriteToken(token);
    }

    public string GenerateSignUpCodeToken(string userId){
        var tokenHandler = new JwtSecurityTokenHandler();
        var key = Encoding.ASCII.GetBytes(_appSettings.Secret);
        var tokenDescriptor = new SecurityTokenDescriptor {
            Subject = new ClaimsIdentity(new [] { new Claim("id", userId) }),
            Expires = DateTime.UtcNow.AddMinutes(10),
            SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
        };

        var token = tokenHandler.CreateToken(tokenDescriptor);
        return tokenHandler.WriteToken(token);
    }

    public string? ValidateToken(String token){
        if(token == null)
            return null;

        var tokenHandler = new JwtSecurityTokenHandler();
        var key = Encoding.ASCII.GetBytes(_appSettings.Secret);

        try{
            tokenHandler.ValidateToken(token, new TokenValidationParameters{
                ValidateIssuerSigningKey = true,
                IssuerSigningKey = new SymmetricSecurityKey(key),
                ValidateIssuer = false,
                ValidateAudience = false,
                //Dado que la ventana de tolerancia a la hora de validar el token es de 5 minutos, ponemos el 
                //clockskew a 0 para que no existan esos 5 min de margen. Asi expirara exactamente a la fecha y hora de expiracion
                ClockSkew = TimeSpan.Zero
            }, out SecurityToken validatedToken);

            var jwtToken = (JwtSecurityToken)validatedToken;
            var userId = /* int.Parse( */jwtToken.Claims.First(x => x.Type == "id").Value/* ) */;

            //retornamos el id de ususario del token en caso de validacion exitosa
            return userId;
        }catch{
            // aqui entraria si la verificacion falla por tanto devolvemos null
            return null;
        }
    }
}