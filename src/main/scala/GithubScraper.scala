package org.foundations.githubscraper

import java.util.{Calendar, Date}

import org.kohsuke.github._

class GithubScraper(token: String) {

  val client = GitHub.connectUsingOAuth(Const.token)
  var iterator: Option[PagedIterator[GHRepository]] = None

  private def getIterator(): PagedIterator[GHRepository] = {
    iterator match {
      case None => iterator = Some(client.searchRepositories().pushed("<" + (Calendar.getInstance().get(Calendar.YEAR) + 1).toString + "-01-01").list().iterator)
        return iterator.get
      case Some(x) => return x
    }
  }

  def getRepo(): Option[GithubScraper.Repository] = {

    if (client.getRateLimit.limit > 0) {
      if (getIterator().hasNext()) {
        return Some(GithubScraper.Repository.apply(getIterator().next()))
      }
    }

    return None
  }

}

object GithubScraper {

  case class Repository(id: Int, name: String, updatedAt: Date, url: String, owner: String) {
    override def toString() = {
      "[id: %s, name: %s, updated_at: %s, url: %s, owner: %s]".format(id, name, updatedAt, url, owner)
    }
  }

  object Repository {
    def apply(repo: GHRepository): Repository = {
      this.apply(repo.getId, repo.getName, repo.getUpdatedAt, repo.getUrl.toString, repo.getOwnerName)
    }
  }

}
