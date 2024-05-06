using MongoDB.Driver;
using Repositorio;
using WebApi.Entities;

namespace WebApi.Mongo.Repositorio;

public abstract class MongoRepository<T> : Repositorio<T, string> where T : IIdentificable 
{
    private string _connectionString { get; set; }
    private IMongoCollection<T> _collection;
    private MongoClient _client;

    public MongoRepository(){
        _connectionString = "mongodb+srv://freddy:Abril1204@cluster.bfkjqaz.mongodb.net/dbUsersNET?retryWrites=true&w=majority";
        _client = new MongoClient(_connectionString);
        _collection = GetCollection();

    }

    public abstract IMongoCollection<T> GetCollection();
    public abstract string GetCollectionName();

    protected IMongoDatabase GetDatabase(){
        return _client.GetDatabase(GetCollectionName());
    }
    
    public virtual string Add(T entity)
    {
        if(entity == null) {
            throw new ArgumentNullException("User can not be null");
        }

        _collection.InsertOne(entity);
        return entity.Id;
    }

    public void Delete(T entity)
    {
        if(entity == null) {
            throw new ArgumentNullException("User can not be null");
        }

        _collection.DeleteOne(user => user.Id == entity.Id);
    }

    public List<T> GetAll()
    {
        return _collection.Find(_ => true).ToList();
    }

    public T GetById(string id)
    {
        if(id == null) {
            throw new ArgumentNullException("Id can not be null");
        }
        return _collection.Find(user => user.Id == id)
        .FirstOrDefault();
    }

    public List<string> GetIds()
    {
        var all = _collection.Find(_ => true).ToList();
        return all.Select(p => p.Id).ToList();
    }

    public void Update(T entity)
    {
        if(entity == null) {
            throw new ArgumentNullException("User can not be null");
        }
        _collection.ReplaceOne(user => user.Id == entity.Id, entity);
    }
}