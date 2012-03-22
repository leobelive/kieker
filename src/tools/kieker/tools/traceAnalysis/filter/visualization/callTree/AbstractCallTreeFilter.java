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

package kieker.tools.traceAnalysis.filter.visualization.callTree;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import kieker.analysis.plugin.annotation.Plugin;
import kieker.analysis.plugin.annotation.RepositoryPort;
import kieker.common.configuration.Configuration;
import kieker.common.util.Signature;
import kieker.tools.traceAnalysis.filter.AbstractMessageTraceProcessingFilter;
import kieker.tools.traceAnalysis.filter.AbstractTraceAnalysisFilter;
import kieker.tools.traceAnalysis.filter.traceReconstruction.TraceProcessingException;
import kieker.tools.traceAnalysis.filter.visualization.util.IntContainer;
import kieker.tools.traceAnalysis.filter.visualization.util.dot.DotFactory;
import kieker.tools.traceAnalysis.systemModel.AbstractMessage;
import kieker.tools.traceAnalysis.systemModel.AllocationComponent;
import kieker.tools.traceAnalysis.systemModel.AssemblyComponent;
import kieker.tools.traceAnalysis.systemModel.MessageTrace;
import kieker.tools.traceAnalysis.systemModel.Operation;
import kieker.tools.traceAnalysis.systemModel.SynchronousCallMessage;
import kieker.tools.traceAnalysis.systemModel.SynchronousReplyMessage;
import kieker.tools.traceAnalysis.systemModel.repository.SystemModelRepository;
import kieker.tools.traceAnalysis.systemModel.util.AllocationComponentOperationPair;
import kieker.tools.traceAnalysis.systemModel.util.AssemblyComponentOperationPair;

/**
 * Plugin providing the creation of calling trees both for individual traces
 * and an aggregated form mulitple traces.
 * 
 * @author Andre van Hoorn
 */
@Plugin(repositoryPorts = @RepositoryPort(name = AbstractTraceAnalysisFilter.SYSTEM_MODEL_REPOSITORY_NAME, repositoryType = SystemModelRepository.class))
public abstract class AbstractCallTreeFilter<T> extends AbstractMessageTraceProcessingFilter {

	public interface PairFactory {
		public Object createPair(final SynchronousCallMessage callMsg);
	}

	// private static final Log LOG = LogFactory.getLog(AbstractCallTreePlugin.class);

	private static final String ENCODING = "UTF-8";

	public AbstractCallTreeFilter(final Configuration configuration) {
		super(configuration);
	}

	private static final String assemblyComponentOperationPairNodeLabel(final AbstractCallTreeNode<AssemblyComponentOperationPair> node, final boolean shortLabels) {
		final AssemblyComponentOperationPair p = node.getEntity();
		final AssemblyComponent component = p.getAssemblyComponent();
		final Operation operation = p.getOperation();
		final String assemblyComponentName = component.getName();
		final String componentTypePackagePrefx = component.getType().getPackageName();
		final String componentTypeIdentifier = component.getType().getTypeName();

		final StringBuilder strBuild = new StringBuilder(assemblyComponentName).append(":");
		if (!shortLabels) {
			strBuild.append(componentTypePackagePrefx);
		} else {
			strBuild.append("..");
		}
		strBuild.append(componentTypeIdentifier).append("\\n.");

		final Signature sig = operation.getSignature();
		final StringBuilder opLabel = new StringBuilder(sig.getName());
		opLabel.append("(");
		final String[] paramList = sig.getParamTypeList();
		if ((paramList != null) && (paramList.length > 0)) {
			opLabel.append("..");
		}
		opLabel.append(")");

		strBuild.append(opLabel.toString());
		return strBuild.toString();
	}

	private static final String allocationComponentOperationPairNodeLabel(final AbstractCallTreeNode<AllocationComponentOperationPair> node,
			final boolean shortLabels) {
		final AllocationComponentOperationPair p = node.getEntity();
		final AllocationComponent component = p.getAllocationComponent();
		final Operation operation = p.getOperation();
		final String resourceContainerName = component.getExecutionContainer().getName();
		final String assemblyComponentName = component.getAssemblyComponent().getName();
		final String componentTypePackagePrefx = component.getAssemblyComponent().getType().getPackageName();
		final String componentTypeIdentifier = component.getAssemblyComponent().getType().getTypeName();

		final StringBuilder strBuild = new StringBuilder(resourceContainerName).append("::\\n").append(assemblyComponentName).append(":");
		if (!shortLabels) {
			strBuild.append(componentTypePackagePrefx);
		} else {
			strBuild.append("..");
		}
		strBuild.append(componentTypeIdentifier).append("\\n.");

		final Signature sig = operation.getSignature();
		final StringBuilder opLabel = new StringBuilder(sig.getName());
		opLabel.append("(");
		final String[] paramList = sig.getParamTypeList();
		if ((paramList != null) && (paramList.length > 0)) {
			opLabel.append("..");
		}
		opLabel.append(")");

		strBuild.append(opLabel.toString());

		return strBuild.toString();
	}

	@SuppressWarnings("unchecked")
	// javac reports unchecked casts
	protected static final String nodeLabel(final AbstractCallTreeNode<?> node, final boolean shortLabels) {
		if (node.getEntity() instanceof AllocationComponentOperationPair) {
			return AbstractCallTreeFilter.allocationComponentOperationPairNodeLabel((AbstractCallTreeNode<AllocationComponentOperationPair>) node, shortLabels);
		} else if (node.getEntity() instanceof AssemblyComponentOperationPair) {
			return AbstractCallTreeFilter.assemblyComponentOperationPairNodeLabel((AbstractCallTreeNode<AssemblyComponentOperationPair>) node, shortLabels);
		} else {
			throw new UnsupportedOperationException("Node type not supported: " + node.getEntity().getClass().getName());
		}
	}

	/** Traverse tree recursively and generate dot code for edges. */
	private static void dotEdgesFromSubTree(final AbstractCallTreeNode<?> n,
			final Map<AbstractCallTreeNode<?>, Integer> nodeIds, final IntContainer nextNodeId, final PrintStream ps, final boolean shortLabels) {
		final StringBuilder strBuild = new StringBuilder();
		nodeIds.put(n, nextNodeId.getValue());
		strBuild.append(nextNodeId.getAndIncValue()).append("[label =\"").append(n.isRootNode() ? "$" : AbstractCallTreeFilter.nodeLabel(n, shortLabels)) // NOCS
				.append("\",shape=" + DotFactory.DOT_SHAPE_NONE + "];");
		ps.println(strBuild.toString());
		for (final WeightedDirectedCallTreeEdge<?> child : n.getChildEdges()) {
			AbstractCallTreeFilter.dotEdgesFromSubTree(child.getDestination(), nodeIds, nextNodeId, ps, shortLabels);
		}
	}

	/** Traverse tree recursively and generate dot code for vertices. */
	private static void dotVerticesFromSubTree(final AbstractCallTreeNode<?> n, final IntContainer eoiCounter,
			final Map<AbstractCallTreeNode<?>, Integer> nodeIds, final PrintStream ps, final boolean includeWeights) {
		final int thisId = nodeIds.get(n);
		for (final WeightedDirectedCallTreeEdge<?> child : n.getChildEdges()) {
			final StringBuilder strBuild = new StringBuilder(1024); // NOPMD (new in Loop)
			final int childId = nodeIds.get(child.getDestination());
			strBuild.append("\n").append(thisId).append("->").append(childId).append("[style=solid,arrowhead=none");
			if (includeWeights) {
				strBuild.append(",label=\"").append(child.getOutgoingWeight()).append("\"");
			} else if (eoiCounter != null) {
				strBuild.append(",label=\"").append(eoiCounter.getAndIncValue()).append(".\"");
			}
			strBuild.append(" ]");
			ps.println(strBuild.toString());
			AbstractCallTreeFilter.dotVerticesFromSubTree(child.getDestination(), eoiCounter, nodeIds, ps, includeWeights);
		}
	}

	private static void dotFromCallingTree(final AbstractCallTreeNode<?> root, final PrintStream ps,
			final boolean includeWeights, final boolean includeEois, final boolean shortLabels) {
		// preamble:
		ps.println("digraph G {");
		final StringBuilder edgestringBuilder = new StringBuilder();

		final Map<AbstractCallTreeNode<?>, Integer> nodeIds = new Hashtable<AbstractCallTreeNode<?>, Integer>(); // NOPMD (not synchronized)

		AbstractCallTreeFilter.dotEdgesFromSubTree(root, nodeIds, new IntContainer(0), ps, shortLabels);
		AbstractCallTreeFilter.dotVerticesFromSubTree(root, includeEois ? new IntContainer(1) : null, nodeIds, ps, includeWeights); // NOCS // NOPMD

		ps.println(edgestringBuilder.toString());
		ps.println("}");
	}

	protected static void saveTreeToDotFile(final AbstractCallTreeNode<?> root, final String outputFnBase,
			final boolean includeWeights, final boolean includeEois, final boolean shortLabels) throws FileNotFoundException, UnsupportedEncodingException {
		final PrintStream ps = new PrintStream(new FileOutputStream(outputFnBase + ".dot"), false, AbstractCallTreeFilter.ENCODING);
		AbstractCallTreeFilter.dotFromCallingTree(root, ps, includeWeights, includeEois, shortLabels);
		ps.flush();
		ps.close();
	}

	protected static void addTraceToTree(final AbstractCallTreeNode<?> root, final MessageTrace t, final PairFactory pairFactory, final boolean aggregated)
			throws TraceProcessingException {
		final Stack<AbstractCallTreeNode<?>> curStack = new Stack<AbstractCallTreeNode<?>>();

		final Collection<AbstractMessage> msgTraceVec = t.getSequenceAsVector();
		AbstractCallTreeNode<?> curNode = root;
		curStack.push(curNode);
		for (final AbstractMessage m : msgTraceVec) {
			if (m instanceof SynchronousCallMessage) {
				curNode = curStack.peek();
				AbstractCallTreeNode<?> child;
				child = curNode.newCall(pairFactory.createPair((SynchronousCallMessage) m));
				curNode = child;
				curStack.push(curNode);
			} else if (m instanceof SynchronousReplyMessage) {
				curNode = curStack.pop();
			} else {
				throw new TraceProcessingException("Message type not supported:" + m.getClass().getName());
			}
		}
		if (curStack.pop() != root) {
			throw new TraceProcessingException("Stack not empty after processing trace");
		}
	}

	public static void writeDotForMessageTrace(final AbstractCallTreeNode<?> root, final PairFactory pairFactory, final MessageTrace msgTrace,
			final String outputFilename, final boolean includeWeights, final boolean shortLabels) throws FileNotFoundException, TraceProcessingException,
			UnsupportedEncodingException {

		AbstractCallTreeFilter.addTraceToTree(root, msgTrace, pairFactory, false); // false: no aggregation
		AbstractCallTreeFilter.saveTreeToDotFile(root, outputFilename, includeWeights, true, shortLabels); // includeEois
	}

}