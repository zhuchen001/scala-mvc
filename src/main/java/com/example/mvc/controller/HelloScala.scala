package com.example.mvc.controller

import com.example.mvc.dao.{ProductMapper, ProductMapper2, SubMapper}
import com.example.mvc.demo.UserDomain
import com.example.mvc.domain.{ProductBean, ProductBean2, SubBean}
import com.example.mvc.scala.RichPO._
import com.example.mvc.service.ProductService
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import javax.annotation.Resource
import scala.collection.JavaConverters._

@RestController
class HelloScala {

  @Resource
  implicit val productMapper: ProductMapper = null

  @Resource
  implicit val productService: ProductService = null

  @Resource
  implicit val subMapper: SubMapper = null


  @Resource
  implicit val productMapper2: ProductMapper2 = null

  @RequestMapping(Array("/index2"))
  def getUser: UserDomain = {
    new UserDomain().setId("123").setName("zhuzhu").setAge(20)
  }

  @RequestMapping(Array("/index3"))
  def insert: Any = {

    val domain: ProductBean = createDomain

    //productService.saveProduct(null)

    productService.saveProduct(domain)

    productService.deleteProduct(domain.getId)


    val domain2 = new ProductBean2().setId("123").setName("zhuzhu2").setType(2).setStafferId("45435")

    domain2 +>

    domain2 --

    val ret = new ProductBean().setId("A220110406000200001")

    productService.findById(ret.getId)
  }

  private def createDomain = {
    val domain = ProductBean.builder().id("123").name("zhuzhu").ptype(2).stafferId("45435").build()

    val subBean1 = SubBean.builder().name("sub1").build()
    val subBean2 = SubBean.builder().name("sub2").build()

    domain.setSubBeanList(List(subBean1, subBean2).asJava)

    domain
  }
}
