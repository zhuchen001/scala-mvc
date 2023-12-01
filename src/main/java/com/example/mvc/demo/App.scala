package com.example.mvc.demo

import com.example.mvc.scala.RichObject._

object App {
  def main(args: Array[String]): Unit = {
    println("Hello")

    val str = "zhuzhu"

    str.foreach(c => println(c))

    val ret = (1 to 9).reduce(_ + _)

    println(ret)

    // 先根据key进行分组（返回一个map，key是分组id，value是分组后的字符串列表）
    // 然后通过mapValues，对value进行处理，每个value作为输入得到新的value
    val dd = str.map(x => (x, x + "a")).groupBy(_._1).mapValues(_.map(_._2).reduce(_ + _))

    println(dd)

    val list = List(("key1", 1), ("key2", 2), ("key1", 3), ("key2", 4))

    val mergedMap = list.groupBy(_._1).mapValues(_.map(_._2).sum)

    println(mergedMap)

    val domain =  new UserDomain().setId("123").setName("zhuzhu").setAge(20)

    println(domain.toJson)

    val dd1 = null.asInstanceOf[String]

    println(dd1)

    f7(println(_))


  }

  def f7(f: (Int) => Unit) = {
    f(1)
  }

}

