package org.foundations.githubscraper

import scala.pickling.Defaults._
import scala.pickling.json._

/**
 * Created by joxer on 22/10/15.
 */
object GithubTool {

  val scraper = new GithubScraper(Const.token);
  val storer = new GithubStorer()

  def getValueFromGithubAndPutInRedis(): Integer = {
    var obj = scraper.getRepo() match {
      case Some(x) => storer.storeObject(x.id, x.pickle.value)
      case _ => throw new NoSuchElementException
    }
    return obj
  }
}
