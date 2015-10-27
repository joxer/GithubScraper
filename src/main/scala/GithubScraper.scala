package org.foundations.githubscraper

import net.caoticode.buhtig.Converters._
import net.caoticode.buhtig._
import org.json4s.JsonAST.{JField, JInt, JString}
import org.json4s.{JArray, JObject}

class GithubScraper(token: String) {

  val buhtig = new Buhtig(token)
  val client = buhtig.syncClient

  def getReposAndConstructObject(offset: Int = 0): List[GithubScraper.Repository] = {

    val repoJSON = getRepos(offset);

    val repos = for {
      JObject(child) <- repoJSON
      JField("name", JString(name)) <- child
      JField("id", JInt(id)) <- child
      JField("url", JString(url)) <- child
      JField("owner", JObject(owner)) <- child
      JField("commits_url", JString(commit_url)) <- child
      JField("login", JString(login_name)) <- owner
    } yield new GithubScraper.Repository(id.toInt, name, normalizeCommitUrl(commit_url), url, login_name)

    return repos
  }

  def getRepos(offset: Int = 0): JArray = {
    val values: JArray = ((client.repositories ? ("since" -> offset.toString)).get[JSON]).asInstanceOf[JArray]
    values
  }

  def normalizeCommitUrl(url: String): String = {
    return url.replace("{/sha}", "")
  }
}

object GithubScraper {

  case class Repository(id: Int, name: String, commit_url: String, url: String, owner: String) {
    override def toString() = {
      "[id: %s, name: %s, commit_url: %s, url: %s, owner: %s]".format(id, name, commit_url, url, owner)
    }
  }

}
