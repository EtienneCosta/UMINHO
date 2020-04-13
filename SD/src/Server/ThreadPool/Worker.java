package Server.ThreadPool;

import Server.Tasks.*;

/**
 * Classe usada pelas threads da thread pool
 */
public class Worker implements Runnable{
    private BoundedBuffer<Task> taskQueue;

    /**
     * Metodo construtor
     *
     * @param taskQueue : queue da thread pool
     */
    public Worker(BoundedBuffer<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    /**
     * As threads retiram tarefas da queue e executam-nas
     */
    @Override
    public void run() {
        try {
            while(true){
                Runnable r = taskQueue.get();
                r.run();
            }
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
