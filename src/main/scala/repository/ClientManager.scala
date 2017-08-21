package repository

import model.ClientEntity
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.ExecutionContext


object ClientManager {
  import repository.MongoDB._
    val collection = db[BSONCollection]("clients")
  def save(clientEntity: ClientEntity)(implicit ec : ExecutionContext) =
    collection.insert (clientEntity).map(_ => Created(clientEntity.id.stringify))

  def findByID(id : String)(implicit ec: ExecutionContext) =
    collection.find(queryByID(id)).one[ClientEntity]


  def deleteByID(id: String)(implicit ec: ExecutionContext) =
    collection.remove(queryByID(id)).map(_=>Deleted)

  def find()(implicit ec: ExecutionContext) =
    collection.find(emptyQuery).cursor[BSONDocument].collect[List]()



  private def queryByID(id: String) = BSONDocument ("_id" -> BSONObjectID(id))
  private def emptyQuery = BSONDocument()

}
