/* 
 * Copyright 2012-2015 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aerospike.client.policy;

/**
 * Defines type of node partition targeted by read commands.
 */
public enum Replica {
	/**
	 * Read from node containing key's master partition.  This is the default behavior.
	 */
	MASTER,

	/**
	 * Distribute reads across nodes containing key's master and replicated partitions
	 * in round-robin fashion.  This option requires {@link com.aerospike.client.policy.ClientPolicy#requestProleReplicas}
	 * to be enabled in order to function properly.
	 */
	MASTER_PROLES,
	
	/**
	 * Distribute reads across all nodes in cluster in round-robin fashion.
	 * This option is useful when the replication factor equals the number
	 * of nodes in the cluster and the overhead of requesting proles is not desired.
	 */
	RANDOM;
}
