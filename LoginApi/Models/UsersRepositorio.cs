using MongoDB.Driver;
using WebApi.Entities;
using Repositorio;
using WebApi.Helpers;
using WebApi.Mongo.Repositorio;

namespace Users.Repositorio;
public class RepositorioUsersMongoDB : MongoRepository<User>
{
    public override string Add(User entity){
        var users = GetCollection();
        if (users.Find(x => x.Username == entity.Username).Any()){
                throw new AppException("Username '" + entity.Username + "' is already taken");
            }

        return base.Add(entity);

    }

    public User FindByUsername(string username){
        var database = base.GetDatabase();
        var collection = database.GetCollection<User>("users.net");

        var filter = Builders<User>.Filter.And(
            Builders<User>.Filter.Eq(user => user.Username, username)
        );

        return collection.Find(filter).FirstOrDefault();
    }
    
    public override IMongoCollection<User> GetCollection()
    {
        var database = base.GetDatabase();
        var collection = database.GetCollection<User>("users.net");
        return collection;
    }

    public override string GetCollectionName()
    {
        return "users";
    }
}
