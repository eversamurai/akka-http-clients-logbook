package model

import spray.json._
import scala.util._
//import akka.http.scaladsl.model.StatusCodes.Success
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

/**
  * Created by benzi on 8/20/2017.
  */
case class ClientEntity(id: BSONObjectID = BSONObjectID.generate,
                        name:String,
                        phoneNumber: String,
                        subscPayment: Double )

object ClientEntity {
  implicit def toClientEntity(client : Client) : ClientEntity = {
      ClientEntity(name = client.name,phoneNumber = client.phoneNumber,
        subscPayment =  client.subscPayment)
  }
  implicit object ClientEntityBSONReader extends BSONDocumentReader[ClientEntity]{
    override def read(doc: BSONDocument): ClientEntity =
      ClientEntity(
        id = doc.getAs[BSONObjectID]("_id").get,
        name = doc.getAs[String]("name").get,
        phoneNumber = doc.getAs[String]("phoneNumber").get,
        subscPayment = doc.getAs[Double]("subscPayment").get
      )
  }
  implicit object ClientEntityBSONWriter extends BSONDocumentWriter[ClientEntity]{
     def write(t: ClientEntity): BSONDocument =
      BSONDocument(
        "_id" -> t.id,
        "name" -> t.name,
        "phoneNumber" -> t.phoneNumber,
        "subscPayment" -> t.subscPayment
      )

  }
}

object ClientEntityProtocol extends DefaultJsonProtocol{
  implicit  object BSONObjectIdProtocol extends RootJsonFormat[BSONObjectID]{
    override def write(obj: BSONObjectID): JsValue = JsString(obj.stringify)

    override def read(json: JsValue): BSONObjectID = json match{
      case JsString(id) => BSONObjectID.parse(id) match {
      case Success(validId) => validId
      case _ => deserializationError("Invalid BSON Object Id")
      }
      case _ => deserializationError("BSON Object Id expected")
    }
  }

  implicit val EntityFormat = jsonFormat4(ClientEntity.apply)
}
