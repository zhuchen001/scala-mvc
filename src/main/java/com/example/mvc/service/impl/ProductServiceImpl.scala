package com.example.mvc.service.impl

import com.example.mvc.dao.{ProductMapper, SubMapper}
import com.example.mvc.domain.{ProductBean, SubBean}
import com.example.mvc.scala.RichObject._
import com.example.mvc.scala.RichPO._
import com.example.mvc.service.ProductService
import com.example.mvc.utils.QueryWrapperBuild
import org.slf4j.LoggerFactory
import org.springframework.aop.framework.AopContext
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.annotation.Resource
import scala.collection.JavaConverters._

@Service
class ProductServiceImpl extends ProductService {

  private val logger = LoggerFactory.getLogger(classOf[ProductServiceImpl])

  @Resource
  implicit val productMapper: ProductMapper = null

  @Resource
  implicit val subMapper: SubMapper = null

  @Resource
  implicit val kafkaTemplate: KafkaTemplate[String, String] = null

  /**
   * 保存 Product
   *
   * @param bean
   * @return
   */
  override def saveProduct(domain: ProductBean): Boolean = {
    require(domain != null, "domain must not null")

    logger.info("save product:{}", domain)

    (domain?).isEmpty

    // 走内部切面 test d sd hello
    AopContext.currentProxy().asInstanceOf[ProductServiceImpl].saveProductInner(domain)

    // 如果执行异常
    if (>>>(() => excep(domain))) {
      // TODO ·比如短路返回一些信息
    }

    domain.sendMQ("testtopic")

    true
  }

  private def excep(domain: ProductBean): ProductBean = {
    //throw new RuntimeException("xxxxxx")
    domain
  }

  @Transactional
  override def deleteProduct(id: String): Boolean = {
    require(id != null, "id must not null")

    val domain: ProductBean = productMapper.selectById(id)

    if (domain == null) return true

    // 删除主表
    domain --

    //删除子表
    subMapper.delete(QueryWrapperBuild.create(classOf[SubBean]).eq(SubBean.Fields.parentId, id).build())

    true
  }

  override def findById(id: String): ProductBean = {
    val domain: ProductBean = productMapper.selectById(id)

    if (domain == null) return null

    val qw = QueryWrapperBuild.create(classOf[SubBean]).eq(SubBean.Fields.parentId, id).build()

    domain.setSubBeanList(qw.findByConditon.asJava)

    domain
  }

  /**
   * 切面必须是public方法
   */
  @Transactional
  def saveProductInner(domain: ProductBean): Unit = {
    // 保存主表
    domain +>

    if (domain.getSubBeanList == null) return

    // sub保存
    domain.getSubBeanList.asScala.foreach(_.setParentId(domain.getId) ++)

  }
}
