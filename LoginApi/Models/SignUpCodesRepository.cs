using MongoDB.Driver;
using Repositorio;
using WebApi.Entities;
using WebApi.Mongo.Repositorio;
using Microsoft.Extensions.Configuration;

namespace SignUpCodes.Repositorio;

public class SignUpCodesRepository : MongoRepository<SignUpCode>
{
    public SignUpCodesRepository(IConfiguration configuration) : base(configuration){

    }

    public override IMongoCollection<SignUpCode> GetCollection()
    {
        var database = base.GetDatabase();
        var collection = database.GetCollection<SignUpCode>("codes");
        return collection;
    }

    public override string GetCollectionName()
    {
        return "dbUsuarios";
    }

    public SignUpCode FindByUserIdAndToken(string userId, string token){
        var database = base.GetDatabase();
        var collection = database.GetCollection<SignUpCode>("codes");

        var filter = Builders<SignUpCode>.Filter.And(
            Builders<SignUpCode>.Filter.Eq(code => code.UserId, userId),
            Builders<SignUpCode>.Filter.Eq(code => code.Code, token)
        );

        return collection.Find(filter).FirstOrDefault();
    }
}