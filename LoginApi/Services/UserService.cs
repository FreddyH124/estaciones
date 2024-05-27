using WebApi.Authorization;
using WebApi.Entities;
using WebApi.Models.Responses;
using Repositorio;
using System.Security.Claims;
using WebApi.Helpers;
using Users.Repositorio;
using BCrypt.Net;

namespace WebApi.Services;

public interface IUserService{
    public User GetById(string id);
    public List<User> GetAll();
    public void UserSignUp(SignUpRequest request);
    //public SignInResponse UserSignIn(SignInRequest request);
    public void Unsubscribe(string id);
    public IDictionary<string,object> VerifyUser(SignInRequest credentials);
}

public class UserService : IUserService
{
    private IJwtUtils _jwtUtils;
    private ISignUpCodeService _signUpCodeService;
    private Repositorio<User, string> _userRepository;

    public UserService(IJwtUtils jwtUtils, Repositorio<User, string> userRepository, ISignUpCodeService signUpCodeService){
        _jwtUtils = jwtUtils;
        _userRepository = userRepository;
        _signUpCodeService = signUpCodeService;
        
    }

    public User GetById(string id)
    {
        var user = _userRepository.GetById(id);
        if(user == null) throw new KeyNotFoundException("User Not Found");
        return user;
    }

    public List<User> GetAll() 
    {
        return _userRepository.GetAll();
    }

    public void UserSignUp(SignUpRequest model){
        var user = new User();
        user.FirstName = model.FirstName;
        user.LastName = model.LastName;
        user.Username = model.Username;
        user.Role = model.Role;
        user.PasswordHash = BCrypt.Net.BCrypt.HashPassword(model.Password);

        if(_signUpCodeService.ValidateSignUpCode(model.UserIdForToken, model.Token)){
            _userRepository.Add(user);
        }else{
            throw new AppException($"No se ha podido validar el token para el usuario {model.UserIdForToken}");
        }

        
    }

    public void Unsubscribe(string id) 
    {
        var user = GetById(id);
        _userRepository.Delete(user);
    }

    public IDictionary<string,object> VerifyUser(SignInRequest credentials)
    {
        string username = credentials.Username;
        string pwd = credentials.Password;

        User aux =  ((RepositorioUsersMongoDB)_userRepository).FindByUsername(username);
        if(aux == null){
            throw new AppException($"User: {username} Not Found");
        }

        if (aux == null || !BCrypt.Net.BCrypt.Verify(pwd, aux.PasswordHash))
            throw new AppException("Password is incorrect");
            
        IDictionary<string, object> claimsUser = new Dictionary<string, object>();
        
        claimsUser.Add("id", aux.Id); 
        claimsUser.Add("name", $"{aux.FirstName} {aux.LastName}");
        claimsUser.Add("rol", aux.Role);
        
        return claimsUser;
    }

}