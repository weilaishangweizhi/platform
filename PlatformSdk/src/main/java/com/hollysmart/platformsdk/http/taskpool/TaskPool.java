package com.hollysmart.platformsdk.http.taskpool;


import java.util.LinkedList;

/**
 * Created by cai on 16/4/7.
 */
public class TaskPool {

    private LinkedList<INetModel> taskQueue = new LinkedList<>();
    private boolean isRuning;

    public void addTask(INetModel updateModel) {
        taskQueue.addLast(updateModel);
    }

    public int getTotal() {
        return taskQueue.size();
    }

    public void clear(){
        taskQueue.clear();
    }

    public boolean isRuning(){
        return isRuning;
    }
    public void execute(OnNetRequestListener onNetRequestListener) {
        isRuning = true;
        if (!taskQueue.isEmpty()) {
            INetModel updateModel = taskQueue.removeFirst();
            updateModel.request();
        } else{
            onNetRequestListener.onFinish();
            isRuning = false;
        }
    }
}