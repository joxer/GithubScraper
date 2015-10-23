package org.foundations.githubscraper

import java.util

import net.caoticode.buhtig.Converters.JSON
import net.caoticode.buhtig._
import org.json4s.{JArray, JObject}
import org.scalatest.FunSpec
import org.scalatest._

class GithubScraperTest extends FunSpec {

  val token = "a66fc509edfb11368da8c9f02d0df0cba4223857"

  describe("Get repositories from github") {

    it("should be a json array") {
      val scraper = new GithubScraper(token)
      assert(scraper.getRepos().isInstanceOf[JArray])
    }

    it("should download repositories from github after 20") {
      val scraper = new GithubScraper(token)
      assert(scraper.getRepos(20).arr.size > 0)
    }


    it("should return repositories") {
      val scraper = new GithubScraper(token)
      assert(scraper.getRepos(20).arr != null)
    }

    it("should take repo with some values") {
      val scraper = new GithubScraper(token)
      assert(scraper.getReposAndConstructObject(0).size > 0)
    }
  }
  describe("should see if redis module works") {
    it(" should store elements on redis") {
      val storer = new GithubStorer()
      assert(storer.storeObject("Dummy", "Dummy") == true)
      storer.removeObject("Dummy")
    }

    it("should remove elements from redis") {
      val storer = new GithubStorer()
      storer.storeObject("Dummy", "Dummy")
      storer.removeObject("Dummy")
      assert(storer.getObject("Dummy").isEmpty)
      storer.flushKeys()

    }
    it("should get keys") {
      val storer = new GithubStorer()
      GithubTool.getValueFromGithubAndPutInRedis(0)
      assert(storer.getAllObjects().size() > 0)
      storer.flushKeys()
    }
  }

  describe("See if persistence on redis work from github") {

    it("should download from github data and store on redis") {
      GithubTool.getValueFromGithubAndPutInRedis(0)
      val storer = new GithubStorer()
      val rubinius = storer.getObject("rubinius")
      storer.flushKeys()
      rubinius match {
        case Some(value) => assert(true)
        case _ => assert(false)
      }
    }
  }
}