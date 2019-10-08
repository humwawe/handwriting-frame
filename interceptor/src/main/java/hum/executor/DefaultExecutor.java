package hum.executor;


/**
 * @author hum
 */
public class DefaultExecutor implements Executor {
    @Override
    public void execute(String statement) {
        System.out.println("execute " + statement);
    }
}
