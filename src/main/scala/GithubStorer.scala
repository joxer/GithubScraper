package org.foundations.githubscraper

import com.redis._

import scala.pickling.Defaults._
import scala.pickling.json._

/**
 * Created by joxer on 22/10/15.
 */
class GithubStorer(address: String = "localhost", port: Integer = 6379) {

  val client = new RedisClient(address, port)

  def storeObject(key: String, obj: String): Boolean = {
    client.set(key, obj);
  }

  def removeObject(key: String): Unit = {
    client.del(key)
  }

  def getAllObjects(): List[GithubScraper.Repository] = {
    return for {obj <- client.keys("*").get} yield JSONPickle.apply(getObject(obj.get).get).unpickle[GithubScraper.Repository]
  }

  def getObject(key: String): Option[String] = {
    client.get(key)
  }

  def flushKeys(): Unit = {
    client.flushall
  }
}
