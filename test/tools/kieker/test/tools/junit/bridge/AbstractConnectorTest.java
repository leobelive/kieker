/***************************************************************************
 * Copyright 2013 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package kieker.test.tools.junit.bridge;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;

import kieker.common.record.IMonitoringRecord;
import kieker.common.record.controlflow.OperationExecutionRecord;
import kieker.tools.bridge.connector.ConnectorDataTransmissionException;
import kieker.tools.bridge.connector.ConnectorEndOfDataException;
import kieker.tools.bridge.connector.IServiceConnector;

/**
 * Class for all connector tests providing three methods for initialization,
 * record processing and connector termination.
 * 
 * @author Reiner Jung, Pascale Brandt
 * 
 * @since 1.8
 */
public abstract class AbstractConnectorTest {

	private IServiceConnector connector;
	private int recordCount; // default initialization is 0

	public int getRecordCount() {
		return this.recordCount;
	}

	protected void setConnector(final IServiceConnector connector) {
		this.connector = connector;
	}

	/**
	 * Create the test record map.
	 * 
	 * @return the record map
	 */
	protected ConcurrentMap<Integer, Class<? extends IMonitoringRecord>> createRecordMap() {
		final ConcurrentMap<Integer, Class<? extends IMonitoringRecord>> map = new ConcurrentHashMap<Integer, Class<? extends IMonitoringRecord>>();
		map.put(ConfigurationParameters.TEST_RECORD_ID, OperationExecutionRecord.class);

		return map;
	}

	/**
	 * Initialize a service connector and trigger a failure on error.
	 */
	protected void initialize() {
		try {
			this.connector.initialize();
		} catch (final ConnectorDataTransmissionException e) {
			Assert.fail("Connector initialization failed: " + e.getMessage());
		}
	}

	/**
	 * Close a service connector and trigger a failure on errors.
	 * 
	 * @param numberOfRecords
	 *            number of expected records
	 */
	protected void close(final int numberOfRecords) {
		try {
			this.connector.close();
		} catch (final ConnectorDataTransmissionException e) {
			Assert.fail("Connector termination failed: " + e.getMessage());
		}
		Assert.assertEquals("Number of send records is not equal to number of received records",
				numberOfRecords,
				this.getRecordCount());
	}

	/**
	 * Read number of records from the input stream and trigger assertion errors if necessary.
	 * 
	 * @param numberOfRecords
	 *            number of expected records to receive
	 */
	protected void deserialize(final int numberOfRecords) {
		for (int i = 0; i < numberOfRecords; i++) {
			try {
				final OperationExecutionRecord record = (OperationExecutionRecord) this.connector.deserializeNextRecord();
				Assert.assertEquals("Tin is not equal", ConfigurationParameters.TEST_TIN, record.getTin());
				Assert.assertEquals("Tout is not equal", ConfigurationParameters.TEST_TOUT, record.getTout());
				Assert.assertEquals("TraceId is not equal", ConfigurationParameters.TEST_TRACE_ID, record.getTraceId());
				Assert.assertEquals("Eoi is not equal", ConfigurationParameters.TEST_EOI, record.getEoi());
				Assert.assertEquals("Ess is not equal", ConfigurationParameters.TEST_ESS, record.getEss());
				Assert.assertEquals("Hostname is not equal", ConfigurationParameters.TEST_HOSTNAME, record.getHostname());
				Assert.assertEquals("OperationSignature is not equal", ConfigurationParameters.TEST_OPERATION_SIGNATURE, record.getOperationSignature());
				Assert.assertEquals("SessionId is not equal", ConfigurationParameters.TEST_SESSION_ID, record.getSessionId());
				this.recordCount++;
			} catch (final ConnectorDataTransmissionException e) {
				Assert.fail("Error receiving data: " + e.getMessage());
			} catch (final ConnectorEndOfDataException e) {
				Assert.fail("Connector has not terminated: " + e.getMessage());
			}
		}
	}

}
