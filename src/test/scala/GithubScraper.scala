package org.foundations.githubscraper

import org.scalatest.FunSpec

class GithubScraperTest extends FunSpec {

  val token = "a66fc509edfb11368da8c9f02d0df0cba4223857"

  describe("Get repositories from github") {

    it("should return repositories") {
      val scraper = new GithubScraper(token)
      var gh = scraper.getRepo()
      gh match {
        case Some(x) => assert(true)
        case None => assert(false)
      }

      println(gh.asInstanceOf[Some[GithubScraper.Repository]].toString())

    }


  }
  describe("should see if redis module works") {
    it(" should store elements on redis") {
      val storer = new GithubStorer()
      assert(storer.storeObject(1, "Dummy") == 1)
      storer.removeObject(1)

    }

    it("should remove elements from redis") {
      val storer = new GithubStorer()
      storer.storeObject(1, "Dummy")
      storer.removeObject(1)
      assert(storer.getObject(1).isEmpty)
      storer.flushKeys()

    }
    it("should get keys") {
      val storer = new GithubStorer()
      GithubTool.getValueFromGithubAndPutInRedis()
      assert(storer.getAllObjects().size > 0)
      storer.flushKeys()

    }
  }

  describe("See if persistence on redis work from github") {

    it("should download from github data and store on redis") {

    }
  }
}