/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flink.table.planner.plan.stream.sql.agg

import org.apache.flink.table.planner.plan.rules.physical.stream.IncrementalAggregateRule
import org.apache.flink.table.planner.utils.AggregatePhaseStrategy
import org.apache.flink.testutils.junit.extensions.parameterized.{ParameterizedTestExtension, Parameters}

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

import java.util

@ExtendWith(Array(classOf[ParameterizedTestExtension]))
class IncrementalAggregateTest(
    splitDistinctAggEnabled: Boolean,
    aggPhaseEnforcer: AggregatePhaseStrategy)
  extends DistinctAggregateTest(splitDistinctAggEnabled, aggPhaseEnforcer) {

  @BeforeEach
  override def before(): Unit = {
    super.before()
    // enable incremental agg
    util.tableEnv.getConfig
      .set(IncrementalAggregateRule.TABLE_OPTIMIZER_INCREMENTAL_AGG_ENABLED, Boolean.box(true))
  }
}

object IncrementalAggregateTest {
  @Parameters(name = "splitDistinctAggEnabled={0}, aggPhaseEnforcer={1}")
  def parameters(): util.Collection[Array[Any]] = {
    util.Arrays.asList(
      Array(true, AggregatePhaseStrategy.TWO_PHASE)
    )
  }
}
