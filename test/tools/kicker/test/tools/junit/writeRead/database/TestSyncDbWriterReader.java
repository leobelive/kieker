/***************************************************************************
 * Copyright 2014 Kicker Project (http://kicker-monitoring.net)
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

package kicker.test.tools.junit.writeRead.database;

import kicker.common.configuration.Configuration;
import kicker.monitoring.core.configuration.ConfigurationFactory;
import kicker.monitoring.core.controller.IMonitoringController;
import kicker.monitoring.core.controller.MonitoringController;
import kicker.monitoring.writer.database.SyncDbWriter;

/**
 * @author Jan Waller
 * 
 * @since 1.5
 */
public final class TestSyncDbWriterReader extends AbstractTestDbWriterReader { // NOPMD (TestClassWithoutTestCases)
	/**
	 * Default constructor.
	 */
	public TestSyncDbWriterReader() {
		// empty default constructor
	}

	@Override
	protected IMonitoringController createController(final int numRecordsWritten) throws Exception {
		final Configuration config = ConfigurationFactory.createDefaultConfiguration();
		config.setProperty(ConfigurationFactory.METADATA, "false");
		config.setProperty(ConfigurationFactory.WRITER_CLASSNAME, SyncDbWriter.class.getName());
		config.setProperty(SyncDbWriter.CONFIG_DRIVERCLASSNAME, DRIVERCLASSNAME);
		config.setProperty(SyncDbWriter.CONFIG_CONNECTIONSTRING, this.getConnectionString() + ";create=true");
		config.setProperty(SyncDbWriter.CONFIG_TABLEPREFIX, TABLEPREFIX);
		return MonitoringController.createInstance(config);
	}
}