package com.advancedspark.serving.prediction

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey

import org.jblas.DoubleMatrix

import com.netflix.dyno.jedis._

import collection.JavaConverters._

class UserItemRecommendationsCommand(
    dynoClient: DynoJedisClient, namespace: String, version: String, userId: String, startIdx: Int, endIdx: Int) 
  extends HystrixCommand[Seq[String]](HystrixCommandGroupKey.Factory.asKey("UserItemRecommendations")) {

  def run(): Seq[String] = {
    try{
      dynoClient.zrevrange(s"${namespace}:${version}:recommendations:${userId}", startIdx, endIdx).asScala.toSeq
    } catch { 
       case e: Throwable => {
         System.out.println(e) 
         throw e
       }
    }
  }

  override def getFallback(): Seq[String] = {
    System.out.println("UserRecommendations Source is Down!  Fallback!!")

    List("10001", "10002", "10003", "10004", "10005")
  }
}
