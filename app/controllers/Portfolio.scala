/*
 * Copyright (c) 2015 Lattice Markets, All rights reserved.
 *
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only with
 * Lattice Markets, except with written permission of Lattice Markets.
 *
 */

package controllers

import java.time.LocalDate

import com.lattice.lib.portfolio.MarketPlaceFactory
import models.Originator
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.libs.json.Json

import com.lattice.lib.integration.lc.model.Formatters.marketplacePortfolioAnalyticsFormat
import com.lattice.lib.integration.lc.model.Formatters.mapGradeIntFormat
import com.lattice.lib.integration.lc.model.Formatters.mapDoubleDoubleInt
import com.lattice.lib.integration.lc.model.Formatters.mapIntMapGradeValueIntFormat
import com.lattice.lib.integration.lc.model.Formatters.mapIntMapDoubleDoubleIntFormat
import com.lattice.lib.integration.lc.model.Formatters.mapIntMapStringIntFormat

import utils.Constants

/**
 * @author : julienderay
 * Created on 02/11/2015
 */
class Portfolio extends Controller {

  private val portfolio = MarketPlaceFactory.portfolio(Originator.LendingClub)

  def LCportfolioAnalytics = Action.async {
    portfolio.portfolioAnalytics(Constants.portfolioName).map(portfolioAnalytics => Ok( Json.toJson(portfolioAnalytics) ) )
  }

  def notesAcquiredTodayByGrade = Action.async {
    portfolio.portfolioAnalytics(Constants.portfolioName).map(portfolioAnalytics => Ok( Json.toJson(portfolioAnalytics.notesAcquiredTodayByGrade) ) )
  }

  def notesAcquiredTodayByYield = Action.async {
    portfolio.portfolioAnalytics(Constants.portfolioName).map(portfolioAnalytics => Ok( Json.toJson(portfolioAnalytics.notesAcquiredTodayByYield) ) )
  }

  def notesAcquiredTodayByPurpose = Action.async {
    portfolio.portfolioAnalytics(Constants.portfolioName).map(portfolioAnalytics => Ok( Json.toJson(portfolioAnalytics.notesAcquiredTodayByPurpose) ) )
  }

  def notesAcquiredThisYearByMonthByGrade = Action.async {
    portfolio.portfolioAnalytics(Constants.portfolioName).map(portfolioAnalytics =>{
      Ok( Json.toJson(
        portfolioAnalytics.notesAcquiredByGrade(LocalDate.now().minusYears(1), LocalDate.now())
          .groupBy(_._1.getMonthValue)
          .mapValues(_.values)
          .map { case(i, m) => (i, m reduce (_ ++ _)) }
      ))
    })
  }

  def notesAcquiredThisYearByMonthByYield = Action.async {
    portfolio.portfolioAnalytics(Constants.portfolioName).map(portfolioAnalytics =>{
      Ok( Json.toJson(
        portfolioAnalytics.notesAcquiredByYield(LocalDate.now().minusYears(1), LocalDate.now())
          .groupBy(_._1.getMonthValue)
          .mapValues(_.values)
          .map { case(i, m) => (i, m reduce (_ ++ _)) }
      ))
    })
  }

  def notesAcquiredThisYearByMonthByPurpose = Action.async {
    portfolio.portfolioAnalytics(Constants.portfolioName).map(portfolioAnalytics =>{
      Ok( Json.toJson(
        portfolioAnalytics.notesAcquiredByPurpose(LocalDate.now().minusYears(1), LocalDate.now())
          .groupBy(_._1.getMonthValue)
          .mapValues(_.values)
          .map { case(i, m) => (i, m reduce (_ ++ _)) }
      ))
    })
  }
}