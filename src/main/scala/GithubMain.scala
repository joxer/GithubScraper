package org.foundations.githubscraper

import org.clapper.argot.ArgotConverters._
import org.clapper.argot._


/**
 * Created by joxer on 27/10/15.
 */
object GithubMain {

  val parser = new ArgotParser(
    "GithubMain",
    preUsage = Some("GithubMainTool: Version 0.1. Copyright (c) " +
      "2012, Brian M. Clapper. Not really reactive.")
  )
  val iterations = parser.option[Int](List("i", "iterations"), "n",
                                      "Total iterations")


  def main(args: Array[String]) = {

    try {
      parser.parse(args)
      var client = new GithubScraper(Const.token)
      for (i <- Range.apply(0, iterations.value.get)) {
        Console.println(client.getRepo())
      }
    }
    catch {
      case e: ArgotUsageException => println(e.message)
    }

  }

}
