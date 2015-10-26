/**
 * Copyright (c) 2015 Lattice Markets, All rights reserved.
 *
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only with
 * Lattice Markets, except with written permission of Lattice Markets.
 */
package com.lattice.lib.integration.lc.model

/**
 * @author ze97286
 */
case class ExecutionReport(
  orderInstructId: Option[Int], // A unique LC assigned id identifying the OrderInstruct.
  orderConfirmations: Seq[OrderConfirmation]) // order status for each incoming order
  
  