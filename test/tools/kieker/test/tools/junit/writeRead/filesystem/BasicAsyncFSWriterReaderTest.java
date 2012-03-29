/***************************************************************************
 * Copyright 2011 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
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

package kieker.test.tools.junit.writeRead.filesystem;

import kieker.common.configuration.Configuration;
import kieker.monitoring.writer.IMonitoringWriter;
import kieker.monitoring.writer.filesystem.AsyncFsWriter;
import kieker.monitoring.writer.filesystem.SyncFsWriter;

/**
 * 
 * @author André van Hoorn
 * 
 */
public class BasicAsyncFSWriterReaderTest extends AbstractTestFSWriterReader { // NOPMD (TestClassWithoutTestCases) // NOCS (MissingCtorCheck)
	private static final boolean FLUSH = true;

	@Override
	protected Class<? extends IMonitoringWriter> getTestedWriterClazz() {
		return AsyncFsWriter.class;
	}

	@Override
	protected boolean terminateBeforeLogInspection() {
		return !BasicAsyncFSWriterReaderTest.FLUSH;
	}

	@Override
	protected void refineConfiguration(final Configuration config, final int numRecordsWritten) {
		config.setProperty(this.getClass().getName() + "." + SyncFsWriter.CONFIG_FLUSH, Boolean.toString(BasicAsyncFSWriterReaderTest.FLUSH));
		// TODO: additional configuration parameters
	}
}