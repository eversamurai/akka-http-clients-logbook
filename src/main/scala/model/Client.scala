package model

import spray.json.DefaultJsonProtocol

/**
  * Created by benzi on 8/20/2017.
  */
case class Client(name:String,phoneNumber:String,subscPayment:Double)

object ClientProtocol extends DefaultJsonProtocol{
  implicit val jsonProtocol = jsonFormat3(Client.apply)
}
