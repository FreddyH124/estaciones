using MongoDB.Driver;
using Repositorio;
using SignUpCodes.Repositorio;
using WebApi.Authorization;
using WebApi.Entities;
using WebApi.Models.Responses;

namespace WebApi.Services;

public interface ISignUpCodeService{
     public CodeGenerationResponse GenerateSignUpCode(string id);
     public bool ValidateSignUpCode(string userId, string code);
}


public class SignUpCodeService : ISignUpCodeService{
    private Repositorio<SignUpCode, string> _codeRepository;
    private IJwtUtils _jwtUtils;

    public SignUpCodeService(Repositorio<SignUpCode, string> codeRepository, IJwtUtils jwtUtils){
        _codeRepository = codeRepository;
        _jwtUtils = jwtUtils;
    }

     public CodeGenerationResponse GenerateSignUpCode(string id)
    {
        var response = new CodeGenerationResponse();
        response.token = _jwtUtils.GenerateSignUpCodeToken(id);
        var signUpCode = new SignUpCode();
        signUpCode.UserId = id;
        signUpCode.Code = response.token;
        _codeRepository.Add(signUpCode);
        return response;
    }

    public bool ValidateSignUpCode(string userId, string code)
    {
        try
    {
        var signUpCode = ((SignUpCodesRepository)_codeRepository).FindByUserIdAndToken(userId, code);
        if (signUpCode == null)
        {
            Console.WriteLine($"El código de activación '{code}' no coincide con el usuario con ID '{userId}'.");
            return false;
        }

        string validatedUserId = _jwtUtils.ValidateToken(signUpCode.Code);

        if (validatedUserId == userId)
        {
            Console.WriteLine($"El código de activación '{code}' para el usuario con ID '{userId}' es válido.");
            return true;
        }
        else
        {
            Console.WriteLine($"El código de activación '{code}' no es válido o no coincide con el usuario con ID '{userId}'.");
            return false;
        }
        }
        catch (Exception)
        {
            Console.WriteLine($"El código de activación '{code}' para el usuario con ID '{userId}' no es válido.");
            return false;
        }   
    }

}