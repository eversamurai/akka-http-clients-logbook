package api


import spray.json._
import DefaultJsonProtocol._
import akka.actor.ActorSystem

import scala.concurrent.Future
import akka.stream.{ActorMaterializer, Materializer}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import model.{Client, ClientEntity}
import repository.ClientManager

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext

/**
  * Created by benzi on 8/21/2017.
  */
trait RestApi{
  import model.ClientProtocol._
  import model.ClientEntity._
  import model.ClientEntityProtocol.EntityFormat

  implicit val system : ActorSystem

  implicit val materializer : Materializer

  implicit val ec: ExecutionContext

  val route =
  pathPrefix("clients") {
    (post & entity(as[Client])){ client =>
      complete {
        ClientManager.save(client) map { r =>
          Created -> Map("id" -> r.id).toJson
        }
      }
    }~
      (get & path(Segment)){ id =>
        complete{
          ClientManager.findByID(id) map { t =>
            OK -> t
          }
        }
      }~
      (delete & path(Segment)){ id =>
        complete{
          ClientManager.deleteByID(id) map { _ =>
            NoContent
          }
        }
      }~
      (get){
        complete{
          ClientManager.find map { ts =>
            OK -> ts.map(_.as[ClientEntity])
          }
        }
      }
    }
}

object ApiApp extends App with RestApi {

  override implicit val system = ActorSystem("rest-api")

  override implicit val materializer = ActorMaterializer()

  override implicit val ec = system.dispatcher

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  Console.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.shutdown())
}
