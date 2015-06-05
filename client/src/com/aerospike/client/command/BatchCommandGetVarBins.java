/*
 * Copyright 2012-2015 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements WHICH ARE COMPATIBLE WITH THE APACHE LICENSE, VERSION 2.0.
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
package com.aerospike.client.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.BatchRecord;
import com.aerospike.client.Key;
import com.aerospike.client.policy.Policy;

public final class BatchCommandGetVarBins extends MultiCommand {
	private final BatchNode batch;
	private final Policy policy;
	private final List<BatchRecord> records;

	public BatchCommandGetVarBins(BatchNode batch, Policy policy, List<BatchRecord> records) {
		super(batch.node, false);
		this.batch = batch;
		this.policy = policy;
		this.records = records;
	}

	@Override
	protected Policy getPolicy() {
		return policy;
	}

	@Override
	protected void writeBuffer() {
		setBatchRead(policy, records, batch);
	}

	@Override
	protected void parseRow(Key key) throws IOException {
		BatchRecord record = records.get(batchIndex);
		
		if (Arrays.equals(key.digest, record.key.digest)) {
			if (resultCode == 0) {
				record.record = parseRecord();
			}
		}
		else {
			throw new AerospikeException.Parse("Unexpected batch key returned: " + key.namespace + ',' + Buffer.bytesToHexString(key.digest) + ',' + batchIndex);
		}
	}
}