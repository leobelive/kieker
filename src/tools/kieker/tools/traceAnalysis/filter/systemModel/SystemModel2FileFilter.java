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

package kieker.tools.traceAnalysis.filter.systemModel;

import java.io.File;

import kieker.analysis.plugin.annotation.Plugin;
import kieker.analysis.plugin.annotation.RepositoryPort;
import kieker.common.configuration.Configuration;
import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;
import kieker.tools.traceAnalysis.filter.AbstractTraceAnalysisFilter;
import kieker.tools.traceAnalysis.systemModel.repository.SystemModelRepository;

/**
 * Writes the contents of a connected {@link SystemModelRepository} to files.
 * Currently, only HTML output is supported.
 * 
 * @author Andre van Hoorn
 * 
 */
@Plugin(
		description = "Prints the contents of a connected SystemModelRepository to an HTML file",
		repositoryPorts = {
				@RepositoryPort(name = AbstractTraceAnalysisFilter.SYSTEM_MODEL_REPOSITORY_NAME, repositoryType = SystemModelRepository.class)
		})
public class SystemModel2FileFilter extends AbstractTraceAnalysisFilter {
	private static final Log LOG = LogFactory.getLog(SystemModel2FileFilter.class);

	/**
	 * Name of the configuration property to pass the filename of the HTML output.
	 */
	public static final String CONFIG_HTML_OUTPUT_FN = "outputfn-html";

	/**
	 * By default, writes HTML output file to this file in the working directory.
	 */
	private static final String DEFAULT_HTML_OUTPUT_FN = "system-model.html";

	private final String outputFnHTML;

	public SystemModel2FileFilter(final Configuration configuration) {
		super(configuration);
		this.outputFnHTML = configuration.getProperty(SystemModel2FileFilter.CONFIG_HTML_OUTPUT_FN);
	}

	@Override
	protected Configuration getDefaultConfiguration() {
		final Configuration defaultConfig = new Configuration();
		defaultConfig.setProperty(SystemModel2FileFilter.CONFIG_HTML_OUTPUT_FN, SystemModel2FileFilter.DEFAULT_HTML_OUTPUT_FN);
		return defaultConfig;
	}

	@Override
	public Configuration getCurrentConfiguration() {
		final Configuration currentConfiguration = new Configuration();
		currentConfiguration.setProperty(SystemModel2FileFilter.CONFIG_HTML_OUTPUT_FN, this.outputFnHTML);
		return currentConfiguration;
	}

	@Override
	public void terminate(final boolean errorBeforeTermination) {
		String outputFnHTMLCanonical = this.outputFnHTML; // not yet canonical here

		/**
		 * Used to keep track of whether an error occurred, regardless
		 * of whether before or during termination.
		 */
		boolean error = errorBeforeTermination;
		if (!error) {
			try {
				{
					/*
					 * Trying to create the canonical file path here.
					 * Using a code block to hide the File.
					 */
					final File outputFileHTML = new File(this.outputFnHTML);
					outputFnHTMLCanonical = outputFileHTML.getCanonicalPath(); // may throw IOExecption
				}

				final SystemModelRepository sysModelRepo = super.getSystemEntityFactory();
				if (sysModelRepo == null) {
					final String errorMsg = "Failed to get system model repository";
					SystemModel2FileFilter.LOG.error(errorMsg);
					error = true;
				} else {
					sysModelRepo.saveSystemToHTMLFile(outputFnHTMLCanonical);
				}
			} catch (final Exception e) {
				final String errorMsg = "Failed to save system model to file " + outputFnHTMLCanonical;
				SystemModel2FileFilter.LOG.error(errorMsg, e);
				error = true;
			}
		}

		if (!error) {
			this.printMessage(new String[] { "Wrote HTML output of system model to file '" + outputFnHTMLCanonical + "'" });
		} else {
			this.printMessage(new String[] { "Failed to write HTML output of system model to file '" + outputFnHTMLCanonical + "'" });
		}
	}
}