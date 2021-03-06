/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.catalyst.analysis

import org.apache.spark.sql.catalyst.catalog.ExternalCatalog.TablePartitionSpec


/**
 * Thrown by a catalog when an item cannot be found. The analyzer will rethrow the exception
 * as an [[org.apache.spark.sql.AnalysisException]] with the correct position information.
 */
abstract class NoSuchItemException extends Exception {
  override def getMessage: String
}

class NoSuchDatabaseException(db: String) extends NoSuchItemException {
  override def getMessage: String = s"Database $db not found"
}

class NoSuchTableException(db: String, table: String) extends NoSuchItemException {
  override def getMessage: String = s"Table $table not found in database $db"
}

class NoSuchPartitionException(
    db: String,
    table: String,
    spec: TablePartitionSpec)
  extends NoSuchItemException {

  override def getMessage: String = {
    s"Partition not found in table $table database $db:\n" + spec.mkString("\n")
  }
}

class NoSuchFunctionException(db: String, func: String) extends NoSuchItemException {
  override def getMessage: String = s"Function $func not found in database $db"
}
