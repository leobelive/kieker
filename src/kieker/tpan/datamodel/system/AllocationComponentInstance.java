package kieker.tpan.datamodel.system;

/**
 *
 * @author Andre van Hoorn
 */
public class AllocationComponentInstance {


    private final int id;
    private final AssemblyComponentInstance assemblyComponent;
    private final ExecutionContainer executionContainer;

    private AllocationComponentInstance(){
        this.id = -1;
        this.assemblyComponent = null;
        this.executionContainer = null;
    }

    public AllocationComponentInstance(
            final int id,
            final AssemblyComponentInstance assemblyComponent,
            final ExecutionContainer executionContainer){
        this.id = id;
        this.assemblyComponent = assemblyComponent;
        this.executionContainer = executionContainer;
    }

    public final int getId() {
        return this.id;
    }

    public final AssemblyComponentInstance getAssemblyComponent() {
        return this.assemblyComponent;
    }

    public final ExecutionContainer getExecutionContainer() {
        return this.executionContainer;
    }

    @Override
    public final String toString(){
        StringBuilder strBuild = new StringBuilder();
        strBuild.append(this.executionContainer.getName()).append("::")
                .append(this.assemblyComponent.toString());
        return strBuild.toString();
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AllocationComponentInstance)){
            return false;
        }
        AllocationComponentInstance other = (AllocationComponentInstance)obj;
        return other.id == this.id;
    }
}
