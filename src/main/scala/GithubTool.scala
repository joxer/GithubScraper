package org.foundations.githubscraper

import scala.pickling.Defaults._
import scala.pickling.json._

/**
 * Created by joxer on 22/10/15.
 */
object GithubTool {

  val scraper = new GithubScraper(Const.token);
  val storer = new GithubStorer()

  def getValueFromGithubAndPutInRedis(offset: Integer): Unit = {
    var objects = scraper.getReposAndConstructObject(offset)

    for (obj <- objects) {
      storer.storeObject(obj.name, obj.pickle.value)
    }
  }
}
