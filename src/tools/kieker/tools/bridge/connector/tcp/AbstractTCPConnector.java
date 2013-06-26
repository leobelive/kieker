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

package kieker.tools.bridge.connector.tcp;

import java.util.concurrent.ConcurrentMap;

import kieker.common.record.IMonitoringRecord;
import kieker.tools.bridge.LookupEntity;
import kieker.tools.bridge.connector.ConnectorDataTransmissionException;
import kieker.tools.bridge.connector.IServiceConnector;
import kieker.tools.bridge.connector.ServiceConnectorFactory;

/**
 * 
 * 
 * @author Reiner Jung
 * @since 1.8
 */
public abstract class AbstractTCPConnector implements IServiceConnector {

	/**
	 * Map containing record ids and the assigned constructor and field type list
	 */
	protected ConcurrentMap<Integer, LookupEntity> lookupEntityMap;

	private final ConcurrentMap<Integer, Class<IMonitoringRecord>> recordMap;

	/**
	 * AbstractTCPService constructor.
	 * 
	 * @param recordMap
	 *            IMonitoringRecord to id map
	 */
	public AbstractTCPConnector(final ConcurrentMap<Integer, Class<IMonitoringRecord>> recordMap) {
		this.recordMap = recordMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kieker.tools.bridge.connector.IServiceConnector#initialize()
	 */
	public void initialize() throws ConnectorDataTransmissionException {
		this.lookupEntityMap = ServiceConnectorFactory.createLookupEntityMap(this.recordMap);
	}
}
