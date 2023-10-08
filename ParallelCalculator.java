import java.util.*;

class Storage {
    Integer counter = 0;
    List<Delta> deltaList = new ArrayList<>();
    List<Integer> keysToRemove = new LinkedList<>();
}

class ParallelCalculator implements DeltaParallelCalculator {

    Integer priority = 0;
    final Map<Integer, Data> dataMap = new HashMap<>();
    final Queue<Work> workQueue = new LinkedList<>();
    final HashMap<Integer, Storage> acceptors = new HashMap<>();
    int threads;
    DeltaReceiver deltaReceiver;

    class Work implements Runnable {
        List<Delta> temporary = new LinkedList<>();
        Data first;
        Data second;
        int dataId;
        int threadNumber;

        void setUpData(Data data1, Data data2) {
            this.first = data1;
            this.second = data2;
        }

        void setUpThread(Integer thread) {
            this.threadNumber = thread;
        }

        void setUpDataId(Integer id) {
            this.dataId = id;
        }

        void removeKeys(Storage acceptor) {
            synchronized (this) {
                for (Integer key : acceptor.keysToRemove) {
                    acceptors.remove(key);
                }
            }
        }

        void increasePriority() {
            priority++;
        }

        void loopAcceptors(Storage acceptor) {
            synchronized (this) {
                acceptors.forEach((key, value) -> {
                    if (key.equals(priority)) {
                        if (value.counter.equals(threads)) {
                            deltaReceiver.accept(value.deltaList);
                            acceptor.keysToRemove.add(key);
                            increasePriority();
                        }
                    }
                });
            }
        }

        public void processAcceptors(Storage acceptor) {
            synchronized (this) {
                acceptor.counter++;
                if (acceptor.counter.equals(threads)) {
                    loopAcceptors(acceptor);
                }
                removeKeys(acceptor);
            }
        }

        @Override
        public void run() {
            calculateDeltas();
            synchronized (acceptors) {
                acceptors.get(dataId).deltaList.addAll(temporary);
                Storage acceptor = acceptors.get(dataId);
                processAcceptors(acceptor);
            }
        }

        void calculateDeltas() {
            for (int step = threadNumber; step < first.getSize(); step += threads) {
                Integer valueFirst = first.getValue(step);
                Integer valueSecond = second.getValue(step);
                if ((valueSecond - valueFirst) != 0) {
                    temporary.add(new Delta(dataId, step, valueSecond - valueFirst));
                }
            }
        }
    }


    @Override
    public void setThreadsNumber(int threads) {
        this.threads = threads;
        for (int j = 0; j < threads; j++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    Work work = null;
                    synchronized (workQueue) {
                        while (workQueue.isEmpty()) {
                            try {
                                workQueue.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        work = workQueue.poll();
                    }
                    work.run();
                }
            });
            thread.setName(String.valueOf(j));
            thread.start();
        }
    }

    @Override
    public void setDeltaReceiver(DeltaReceiver receiver) {
        this.deltaReceiver = receiver;
    }

    @Override
    public void addData(Data data) {
        synchronized (dataMap) {
            dataMap.put(data.getDataId(), data);
            checkWorkForThreads();
        }
    }

    void checkWorkForThreads() {
        for (Integer ids : dataMap.keySet()) {
            Data first = dataMap.get(ids);
            Integer nextDataId = ids + 1;
            if (dataMap.containsKey(nextDataId) && !acceptors.containsKey(first.getDataId()) && ids + 1 == dataMap.get(nextDataId).getDataId()) {
                Data second = dataMap.get(nextDataId);
                acceptors.put(ids, new Storage());
                for (int num = 0; num < threads; num++) {
                    //Initialize Work
                    Work work = new Work();
                    work.setUpData(first, second);
                    work.setUpDataId(ids);
                    work.setUpThread(num);

                    //Adding work
                    workQueue.add(work);
                    synchronized (workQueue) {
                        workQueue.notifyAll();
                    }
                }
            }
        }
    }

}